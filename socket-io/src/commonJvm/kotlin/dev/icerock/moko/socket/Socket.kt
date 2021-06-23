/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.socket

import io.socket.client.IO
import io.socket.engineio.client.transports.Polling
import io.socket.engineio.client.transports.WebSocket
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import org.json.JSONArray
import org.json.JSONObject
import io.socket.client.Socket as SocketIo

actual class Socket actual constructor(
    endpoint: String,
    config: SocketOptions?,
    build: SocketBuilder.() -> Unit
) {
    private val socketIo: SocketIo

    init {

        socketIo = IO.socket(endpoint, IO.Options().apply {
            transports = config?.transport?.let {
                when (it) {
                    SocketOptions.Transport.DEFAULT -> return@let null
                    SocketOptions.Transport.WEBSOCKET -> return@let arrayOf(WebSocket.NAME)
                    SocketOptions.Transport.POLLING -> return@let arrayOf(Polling.NAME)
                }
            }
            query = config?.queryParams?.run {
                if (size == 0) return@run null

                var result = ""
                forEach { (key, value) ->
                    result += "$key=$value"
                }

                result
            }
        })

        object : SocketBuilder {
            override fun on(event: String, action: Socket.(message: String) -> Unit) {
                socketIo.on(event) {
                    this@Socket.action(rawDataToString(it))
                }
            }

            override fun <T> on(socketEvent: SocketEvent<T>, action: Socket.(data: T) -> Unit) {
                socketEvent.socketIoEvents.forEach { event ->
                    socketIo.on(event) { data ->
                        this@Socket.action(socketEvent.mapper(data))
                    }
                }
            }
        }.build()
    }

    actual fun emit(event: String, data: JsonObject) {
        socketIo.emit(event, JSONObject(data.toString()))
    }

    actual fun emit(event: String, data: JsonArray) {
        socketIo.emit(event, JSONArray(data.toString()))
    }

    actual fun emit(event: String, data: String) {
        socketIo.emit(event, data)
    }

    actual fun connect() {
        socketIo.connect()
    }

    actual fun disconnect() {
        socketIo.disconnect()
    }

    actual fun isConnected(): Boolean {
        return socketIo.connected()
    }

    private companion object {
        fun rawDataToString(data: Array<out Any>): String {
            return data.last().toString()
        }
    }
}
