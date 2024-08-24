/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import java.util.Base64

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.multiplatform")
    id("kotlin-parcelize")
    id("dev.icerock.mobile.multiplatform.cocoapods")
    id("dev.icerock.mobile.multiplatform.android-manifest")
    id("org.gradle.maven-publish")
    id("signing")
}

group = "dev.icerock.moko"
version = libs.versions.mokoSocketIoVersion.get()

kotlin {
    jvmToolchain(11)
    androidTarget {
        publishLibraryVariants("release", "debug")
    }
    ios()
    iosSimulatorArm64()
    jvm()

    sourceSets {
        val commonMain by getting

        val commonJvm = create("commonJvm") {
            dependsOn(commonMain)
        }

        val androidMain by getting {
            dependsOn(commonJvm)
        }

        val jvmMain by getting {
            dependsOn(commonJvm)
        }

        val iosMain by getting
        val iosSimulatorArm64Main by getting {
            dependsOn(iosMain)
        }
    }

    targets.withType<KotlinNativeTarget>().configureEach {
        compilations.configureEach {
            cinterops.configureEach {
                extraOpts("-compiler-option", "-fmodules")
            }
        }
    }
}

dependencies {
    commonMainApi(libs.serialization)
    "androidMainImplementation"(libs.appCompat)
    "commonJvmImplementation"(libs.socketIo) {
        exclude(group = "org.json", module = "json")
    }
    "jvmMainImplementation"(libs.socketIo)
}
android {
    namespace = "dev.icerock.moko.socket"
}

val javadocJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
}

publishing {
    repositories.maven("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/") {
        name = "OSSRH"

        credentials {
            username = System.getenv("OSSRH_USER")
            password = System.getenv("OSSRH_KEY")
        }
    }

    publications.withType<MavenPublication> {
        // Stub javadoc.jar artifact
        artifact(javadocJar.get())

        // Provide artifacts information requited by Maven Central
        pom {
            name.set("MOKO socket io")
            description.set("Socket.IO implementation Kotlin Multiplatform library")
            url.set("https://github.com/icerockdev/moko-socket-io")
            licenses {
                license {
                    name.set("Apache-2.0")
                    url.set("https://github.com/icerockdev/moko-socket-io/blob/master/LICENSE.md")
                    distribution.set("repo")
                }
            }

            developers {
                developer {
                    id.set("Alex009")
                    name.set("Aleksey Mikhailov")
                    email.set("aleksey.mikhailov@icerockdev.com")
                }
                developer {
                    id.set("Dorofeev")
                    name.set("Andrey Dorofeev")
                    email.set("adorofeev@icerockdev.com")
                }
            }

            scm {
                connection.set("scm:git:ssh://github.com/icerockdev/moko-socket-io.git")
                developerConnection.set("scm:git:ssh://github.com/icerockdev/moko-socket-io.git")
                url.set("https://github.com/icerockdev/moko-socket-io")
            }
        }
    }

    signing {
        val signingKeyId: String? = System.getenv("SIGNING_KEY_ID")
        val signingPassword: String? = System.getenv("SIGNING_PASSWORD")
        val signingKey: String? = System.getenv("SIGNING_KEY")?.let { base64Key ->
            String(Base64.getDecoder().decode(base64Key))
        }
        if (signingKeyId != null) {
            useInMemoryPgpKeys(signingKeyId, signingKey, signingPassword)
            sign(publishing.publications)
        }
    }
}

cocoaPods {
    pod("mokoSocketIo")
}
