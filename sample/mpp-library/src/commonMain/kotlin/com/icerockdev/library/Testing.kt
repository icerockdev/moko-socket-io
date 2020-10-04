/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package com.icerockdev.library

import dev.icerock.moko.socket.Socket
import dev.icerock.moko.socket.SocketEvent
import dev.icerock.moko.socket.SocketOptions

class Testing {
    val socket = Socket(
        endpoint = "https://socketio-chat-h9jt.herokuapp.com",
        config = SocketOptions(
            queryParams = null,
            transport = SocketOptions.Transport.WEBSOCKET
        )
    ) {
        on(SocketEvent.Connect) {
            println("connect")
        }

        on(SocketEvent.Connecting) {
            println("connecting")
        }

        on(SocketEvent.Disconnect) {
            println("disconnect")
        }

        on(SocketEvent.Error) {
            println("error $it")
        }

        on(SocketEvent.Reconnect) {
            println("reconnect")
        }

        on(SocketEvent.ReconnectAttempt) {
            println("reconnect attempt $it")
        }

        on(SocketEvent.Ping) {
            println("ping")
        }

        on(SocketEvent.Pong) {
            println("pong")
        }

        listOf(
            "input",
            "login",
            "new message",
            "user joined",
            "user left",
            "typing",
            "stop typing"
        ).forEach { eventName ->
            on(eventName) { data ->
                println("$eventName $data")
            }
        }
    }

    fun login() {
        socket.emit("add user", "mokoSocketIo")
    }
}
