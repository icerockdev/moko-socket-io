/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

import SocketIO

@objc
public enum SocketIoTransport: Int {
  case websocket
  case polling
  case undefined
}

@objc
public enum SocketEvent: Int {
  case connect
  case connecting
  case disconnect
  case error
  case message
  case reconnect
  case reconnectAttempt
  case ping
  case pong
}

@objc
public class SocketIo: NSObject {
  private let socketManager: SocketManager
  private let socket: SocketIOClient
  
  @objc
  public init(
    endpoint: String,
    queryParams: [String: Any]?,
    transport: SocketIoTransport
  ) {
    var configuration: SocketIOClientConfiguration = [ .compress ]
    if let queryParams = queryParams {
      configuration.insert(.connectParams(queryParams))
    }
    
    switch transport {
    case .websocket:
      configuration.insert(.forceWebsockets(true))
    case .polling:
      configuration.insert(.forcePolling(true))
    case .undefined: do {}
    }
    
    socketManager = SocketManager(socketURL: URL(string: endpoint)!,
                                  config: configuration)
    socket = socketManager.defaultSocket
  }
  
  @objc
  public func connect() {
    socket.connect()
  }
  
  @objc
  public func disconnect() {
    socket.disconnect()
  }
  
  @objc
  public func isConnected() -> Bool {
    return socket.status == SocketIOStatus.connected
  }
  
  @objc
  public func on(event: String, action: @escaping (String) -> Void) {
    // FIXME сейчас получается что SocketIo десериализует строку в json (dictionary), а мы после этого сериализуем обратно в строку, чтобы на уровне общей логики мультиплатформенный json парсер спарсил данные (результат парсинга iOS и Android варианта socketio разный - приводить к общему виду проблемно, проще в json вернуть и в общем коде преобразовать)
    socket.on(event) { data, emitter in
      let jsonData = try! JSONSerialization.data(withJSONObject: data[0], options: .prettyPrinted)
      let jsonString = String(data: jsonData, encoding: .utf8)!
      let _ = action(jsonString)
    }
  }
  
  @objc
  public func on(socketEvent: SocketEvent, action: @escaping (Array<Any>) -> Void) {
    let clientEvent: SocketClientEvent
    switch socketEvent {
    case .connect:
      clientEvent = .connect
      break
    case .error:
      clientEvent = .error
      break
    case .message:
      socket.onAny { anyEvent in
        if let data = anyEvent.items {
          action(data)
        } else {
          action([])
        }
      }
      return
    case .disconnect:
      clientEvent = .disconnect
      break
    case .reconnect:
      clientEvent = .reconnect
    case .reconnectAttempt:
      clientEvent = .reconnectAttempt
    case .ping:
      clientEvent = .ping
    case .pong:
      clientEvent = .pong
    case .connecting:
      socket.on(clientEvent: .statusChange) { [weak self] data, _ in
        if self?.socket.status == .connecting {
          action(data)
        }
      }
      return
    default:
      return
    }
    socket.on(clientEvent: clientEvent) { data, _ in
      action(data)
    }
  }
  
  @objc
  public func emit(event: String, data: Array<Any>) {
    var result = Array<Any>()
    for i in (0...(data.count - 1)) {
      let item = data[i]
      if let itemData = (item as? String)?.data(using: .utf8) {
        do {
          let itemObject = try JSONSerialization.jsonObject(with: itemData, options: []) as? [String: Any]
          result.append(itemObject)
        } catch {
          print(error.localizedDescription)
        }
      } else {
        result.append(item)
      }
    }
    socket.emit(event, with: result)
  }
  
  @objc
  public func emit(event: String, string: String) {
    socket.emit(event, with: [string])
  }
}

private extension UUID {
  func add(to array: inout Array<UUID>) {
    array.append(self)
  }
}

private extension SocketIOClient {
  func off(ids: Array<UUID>) {
    for id in ids {
      off(id: id)
    }
  }
}
