/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.socket

import cocoapods.mokoSocketIo.SocketEventConnect
import cocoapods.mokoSocketIo.SocketEventConnecting
import cocoapods.mokoSocketIo.SocketEventDisconnect
import cocoapods.mokoSocketIo.SocketEventError
import cocoapods.mokoSocketIo.SocketEventMessage
import cocoapods.mokoSocketIo.SocketEventPing
import cocoapods.mokoSocketIo.SocketEventPong
import cocoapods.mokoSocketIo.SocketEventReconnect
import cocoapods.mokoSocketIo.SocketEventReconnectAttempt
import cocoapods.mokoSocketIo.SocketEvent as SocketIoEvent

actual sealed class SocketEvent<T> : Mapper<T> {
    actual object Connect : SocketEvent<Unit>(), Mapper<Unit> by UnitMapper() {
        override val platformEvent: SocketIoEvent = SocketEventConnect

        override fun mapper(data: List<*>) = Unit
    }

    actual object Connecting : SocketEvent<Unit>(), Mapper<Unit> by UnitMapper() {
        override val platformEvent: SocketIoEvent = SocketEventConnecting
    }

    actual object Disconnect : SocketEvent<Unit>(), Mapper<Unit> by UnitMapper() {
        override val platformEvent: SocketIoEvent = SocketEventDisconnect
    }

    actual object Error : SocketEvent<Throwable>() {
        override val platformEvent: SocketIoEvent = SocketEventError

        override fun mapper(data: List<*>): Throwable {
            val message = data.first() as String
            return Exception(message)
        }
    }

    actual object Message : SocketEvent<Any>() {
        override val platformEvent: SocketIoEvent = SocketEventMessage

        override fun mapper(data: List<*>): Any = data
    }

    actual object Reconnect : SocketEvent<Unit>(), Mapper<Unit> by UnitMapper() {
        override val platformEvent: SocketIoEvent = SocketEventReconnect
    }

    actual object ReconnectAttempt : SocketEvent<Int>() {
        override val platformEvent: SocketIoEvent = SocketEventReconnectAttempt

        override fun mapper(data: List<*>): Int {
            // ios send negative numbers of attempt
            return -(data.first() as Long).toInt()
        }
    }

    actual object Ping : SocketEvent<Unit>(), Mapper<Unit> by UnitMapper() {
        override val platformEvent: SocketIoEvent = SocketEventPing
    }

    actual object Pong : SocketEvent<Unit>(), Mapper<Unit> by UnitMapper() {
        override val platformEvent: SocketIoEvent = SocketEventPong
    }

    abstract val platformEvent: SocketIoEvent

    private class UnitMapper : Mapper<Unit> {
        override fun mapper(data: List<*>) = Unit
    }
}
