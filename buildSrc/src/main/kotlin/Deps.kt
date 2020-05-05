/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

object Deps {
    object Plugins {
        val androidApplication = PluginDesc(id = "com.android.application")
        val androidLibrary = PluginDesc(id = "com.android.library")

        val kotlinMultiplatform = PluginDesc(id = "org.jetbrains.kotlin.multiplatform")
        val kotlinKapt = PluginDesc(id = "kotlin-kapt")
        val kotlinAndroid = PluginDesc(id = "kotlin-android")
        val kotlinAndroidExtensions = PluginDesc(id = "kotlin-android-extensions")

        val mobileMultiplatform = PluginDesc(id = "dev.icerock.mobile.multiplatform")

        val detekt = PluginDesc(id = "io.gitlab.arturbosch.detekt", version = Versions.detekt)
    }

    object Libs {
        object Android {
            val kotlinStdLib = AndroidLibrary(
                name = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
            )
            val appCompat = AndroidLibrary(
                name = "androidx.appcompat:appcompat:${Versions.Libs.Android.appCompat}"
            )
            val socketIo = AndroidLibrary(
                name = "io.socket:socket.io-client:${Versions.Libs.Android.socketIo}"
            )
        }

        object MultiPlatform {
            val kotlinStdLib = MultiPlatformLibrary(
                android = Android.kotlinStdLib.name,
                common = "org.jetbrains.kotlin:kotlin-stdlib-common:${Versions.kotlin}"
            )
            val serialization = MultiPlatformLibrary(
                android = "org.jetbrains.kotlinx:kotlinx-serialization-runtime:${Versions.Libs.MultiPlatform.serialization}",
                common = "org.jetbrains.kotlinx:kotlinx-serialization-runtime-common:${Versions.Libs.MultiPlatform.serialization}",
                ios = "org.jetbrains.kotlinx:kotlinx-serialization-runtime-native:${Versions.Libs.MultiPlatform.serialization}"
            )
            val mokoSocketIo = MultiPlatformLibrary(
                common = "dev.icerock.moko:socket-io:${Versions.Libs.MultiPlatform.mokoSocketIo}",
                iosX64 = "dev.icerock.moko:socket-io-iosx64:${Versions.Libs.MultiPlatform.mokoSocketIo}",
                iosArm64 = "dev.icerock.moko:socket-io-iosarm64:${Versions.Libs.MultiPlatform.mokoSocketIo}"
            )
        }

        object Jvm {
            const val detektFormatting = "io.gitlab.arturbosch.detekt:detekt-formatting:${Versions.detekt}"
        }
    }
}
