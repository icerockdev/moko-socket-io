/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.socket

interface SocketBuilder {
    fun on(event: String, action: Socket.(message: String) -> Unit)
    fun <T> on(socketEvent: SocketEvent<T>, action: Socket.(array: T) -> Unit)

    fun on(vararg events: String, action: Socket.(message: String) -> Unit) {
        events.forEach {
            on(it, action)
        }
    }

    fun <T> on(vararg socketEvents: SocketEvent<T>, action: Socket.(array: T) -> Unit) {
        socketEvents.forEach {
            on(it, action)
        }
    }
}
