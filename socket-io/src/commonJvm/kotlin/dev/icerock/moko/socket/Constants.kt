/*
 * Copyright 2024 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.socket

object Constants {
    const val EVENT_PING = "ping"
    const val EVENT_PONG = "pong"
    const val EVENT_ERROR = "error"
    const val EVENT_MESSAGE = "message"
    const val EVENT_CONNECTING = "connecting"
    const val EVENT_RECONNECT = "reconnect"
    const val EVENT_RECONNECT_ATTEMPT = "reconnect_attempt"
    const val EVENT_RECONNECT_ERROR = "reconnect_error"
}
