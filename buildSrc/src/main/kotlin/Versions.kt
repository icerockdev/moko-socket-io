/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

object Versions {
    object Android {
        const val compileSdk = 28
        const val targetSdk = 28
        const val minSdk = 16
    }

    const val kotlin = "1.3.72"
    const val detekt = "1.7.4"

    object Plugins {
        const val android = "3.6.2"

        const val kotlin = Versions.kotlin
    }

    object Libs {
        object Android {
            const val appCompat = "1.1.0"
            const val socketIo = "1.0.0"
        }

        object MultiPlatform {
            const val mokoSocketIo = "0.1.0"
            const val serialization = "0.20.0"
        }
    }
}
