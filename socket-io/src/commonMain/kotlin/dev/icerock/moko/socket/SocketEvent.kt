/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.socket

expect sealed class SocketEvent<T> {
    object Connect : SocketEvent<Unit>
    object Connecting : SocketEvent<Unit>
    object Disconnect : SocketEvent<Unit>
    object Error : SocketEvent<Throwable>
    object Message : SocketEvent<Any>
    object Reconnect : SocketEvent<Unit>
    object ReconnectAttempt : SocketEvent<Int>
    object Ping : SocketEvent<Unit>
    object Pong : SocketEvent<Unit>
}
