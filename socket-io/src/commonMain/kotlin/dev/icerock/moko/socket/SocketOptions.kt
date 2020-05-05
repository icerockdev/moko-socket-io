/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.socket

data class SocketOptions(
    val queryParams: Map<String, String>?,
    val transport: Transport = Transport.DEFAULT
) {
    enum class Transport {
        WEBSOCKET,
        POLLING,
        DEFAULT
    }
}
