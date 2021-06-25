/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.multiplatform")
    id("dev.icerock.mobile.multiplatform")
    id("dev.icerock.mobile.multiplatform.ios-framework")
}

dependencies {
    commonMainImplementation(libs.serialization)
    commonMainApi(projects.socketIo)
}

framework {
    export(project(":socket-io"))
}

cocoaPods {
    podsProject = file("../ios-app/Pods/Pods.xcodeproj")

    pod("mokoSocketIo", onlyLink = true)
}
