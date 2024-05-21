/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.multiplatform")
    id("dev.icerock.mobile.multiplatform.cocoapods")
    id("dev.icerock.mobile.multiplatform.android-manifest")
    id("dev.icerock.mobile.multiplatform.ios-framework")
}

kotlin {
    jvmToolchain(11)
    androidTarget()
    ios()
    iosSimulatorArm64()
}

dependencies {
    commonMainImplementation(libs.serialization)
    commonMainApi(projects.socketIo)
}
android {
    namespace = "com.icerockdev.library"
}

framework {
    export(projects.socketIo)
}

cocoaPods {
    pod("mokoSocketIo", onlyLink = true)
}
