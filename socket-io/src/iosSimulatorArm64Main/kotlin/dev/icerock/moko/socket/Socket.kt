package dev.icerock.moko.socket

import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject

actual class Socket actual constructor(
    endpoint: String,
    config: SocketOptions?,
    build: SocketBuilder.() -> Unit
) {
    actual fun emit(event: String, data: JsonObject) {
    }

    actual fun emit(event: String, data: JsonArray) {
    }

    actual fun emit(event: String, data: String) {
    }

    actual fun connect() {
    }

    actual fun disconnect() {
    }

    actual fun isConnected(): Boolean {
        TODO("Not yet implemented")
    }
}