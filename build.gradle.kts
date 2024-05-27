/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

buildscript {
    repositories {
        mavenCentral()
        google()

        gradlePluginPortal()
    }
    dependencies {
        classpath(libs.detekt.gradle.plugin)
        classpath(libs.mobile.multiplatform)
        classpath(libs.kotlin.gradle.plugin)
        classpath(libs.gradle)
    }
}

allprojects {
    apply(plugin = "io.gitlab.arturbosch.detekt")

    configure<io.gitlab.arturbosch.detekt.extensions.DetektExtension> {
        input.setFrom(
            "src/commonMain/kotlin",
            "src/androidMain/kotlin",
            "src/iosMain/kotlin",
            "src/commonJvm/kotlin"
        )
    }

    dependencies {
        "detektPlugins"(rootProject.libs.detektFormatting)
    }

    plugins.withId("com.android.library") {
        configure<com.android.build.gradle.LibraryExtension> {
            compileSdk = libs.versions.compileSdk.get().toInt()

            defaultConfig {
                minSdk = libs.versions.minSdk.get().toInt()
                testOptions.targetSdk = libs.versions.targetSdk.get().toInt()
            }
        }
    }

    val signingTasks = tasks.withType<Sign>()
    tasks.withType<AbstractPublishToMaven>().configureEach {
        dependsOn(signingTasks)
    }
}

tasks.register("clean", Delete::class).configure {
    group = "build"
    delete(layout.buildDirectory)
}
