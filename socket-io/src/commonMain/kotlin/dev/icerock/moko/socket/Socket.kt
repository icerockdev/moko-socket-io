/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.socket

import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject

expect class Socket(
    endpoint: String,
    config: SocketOptions? = null,
    build: SocketBuilder.() -> Unit
) {
    fun emit(event: String, data: JsonObject)
    fun emit(event: String, data: JsonArray)
    fun emit(event: String, data: String)
    fun connect()
    fun disconnect()
    fun isConnected(): Boolean
}
