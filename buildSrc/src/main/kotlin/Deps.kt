/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

object Deps {

    private const val kotlinVersion = "1.4.31"
    private const val detektVersion = "1.7.4"

    private const val androidAppCompatVersion = "1.1.0"
    private const val androidSocketIoVersion = "1.0.0"
    private const val kotlinxSerializationVersion = "1.0.0-RC"

    const val mokoSocketIoVersion = "0.2.1"


    object Android {
        const val compileSdk = 28
        const val targetSdk = 28
        const val minSdk = 16
    }

    object Plugins {
        val androidApplication = GradlePlugin(id = "com.android.application")
        val androidLibrary = GradlePlugin(id = "com.android.library")

        val kotlinMultiplatform = GradlePlugin(id = "org.jetbrains.kotlin.multiplatform")
        val kotlinAndroid = GradlePlugin(id = "kotlin-android")
        val kotlinAndroidExtensions = GradlePlugin(id = "kotlin-android-extensions")

        val mobileMultiplatform = GradlePlugin(id = "dev.icerock.mobile.multiplatform")
        val mavenPublish = GradlePlugin(id = "org.gradle.maven-publish")
        val signing = GradlePlugin(id = "signing")

        val detekt = GradlePlugin(id = "io.gitlab.arturbosch.detekt", version = detektVersion)
        val iosFramework = GradlePlugin(id = "dev.icerock.mobile.multiplatform.ios-framework")
    }

    object Libs {
        object Android {
            const val appCompat = "androidx.appcompat:appcompat:$androidAppCompatVersion"

            const val socketIo = "io.socket:socket.io-client:$androidSocketIoVersion"
        }

        object MultiPlatform {

            const val serialization = "org.jetbrains.kotlinx:kotlinx-serialization-core:$kotlinxSerializationVersion"
            const val mokoSocketIo = "dev.icerock.moko:socket-io:$mokoSocketIoVersion"
        }

        object Jvm {
            const val detektFormatting =
                "io.gitlab.arturbosch.detekt:detekt-formatting:$detektVersion"
        }
    }
}
