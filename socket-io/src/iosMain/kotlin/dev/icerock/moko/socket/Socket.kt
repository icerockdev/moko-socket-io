/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.socket

import cocoapods.mokoSocketIo.SocketIo
import cocoapods.mokoSocketIo.SocketIoTransportPolling
import cocoapods.mokoSocketIo.SocketIoTransportUndefined
import cocoapods.mokoSocketIo.SocketIoTransportWebsocket
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject

actual class Socket actual constructor(
    endpoint: String,
    config: SocketOptions?,
    build: SocketBuilder.() -> Unit
) {
    private val mSocket: SocketIo = SocketIo(
        endpoint = endpoint,
        queryParams = config?.queryParams as Map<Any?, *>?,
        transport = when (config?.transport) {
            SocketOptions.Transport.WEBSOCKET -> SocketIoTransportWebsocket
            SocketOptions.Transport.POLLING -> SocketIoTransportPolling
            else -> SocketIoTransportUndefined
        }
    )

    init {
        object : SocketBuilder {
            override fun on(event: String, action: Socket.(message: String) -> Unit) {
                mSocket.onEvent(event) {
                    this@Socket.action(requireNotNull(it))
                }
            }

            override fun <T> on(socketEvent: SocketEvent<T>, action: Socket.(data: T) -> Unit) {
                mSocket.onSocketEvent(socketEvent.platformEvent) {
                    val data = socketEvent.mapper(requireNotNull(it))
                    this@Socket.action(data)
                }
            }
        }.build()
    }

    actual fun emit(event: String, data: JsonObject) {
        mSocket.emitWithEvent(event, data = listOf(data.toString())) // FIXME pass object for SocketIO json
    }

    actual fun emit(event: String, data: JsonArray) {
        mSocket.emitWithEvent(event = event, data = listOf(data.toString())) // FIXME pass object for SocketIO json
    }

    actual fun emit(event: String, data: String) {
        mSocket.emitWithEvent(event = event, string = data)
    }

    actual fun connect() {
        mSocket.connect()
    }

    actual fun disconnect() {
        mSocket.disconnect()
    }

    actual fun isConnected(): Boolean {
        return mSocket.isConnected()
    }
}
