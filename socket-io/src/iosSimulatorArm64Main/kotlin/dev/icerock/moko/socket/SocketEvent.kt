package dev.icerock.moko.socket

actual sealed class SocketEvent<T> {
    actual object Connect : SocketEvent<Unit>()
    actual object Connecting : SocketEvent<Unit>()
    actual object Disconnect : SocketEvent<Unit>()
    actual object Error : SocketEvent<Throwable>()
    actual object Message : SocketEvent<Any>()
    actual object Reconnect : SocketEvent<Unit>()
    actual object ReconnectAttempt : SocketEvent<Int>()
    actual object Ping : SocketEvent<Unit>()
    actual object Pong : SocketEvent<Unit>()
}