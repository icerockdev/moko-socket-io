/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

enableFeaturePreview("GRADLE_METADATA")

val properties: Map<String, String> = startParameter.projectProperties
val libraryPublish: Boolean = properties.containsKey("libraryPublish")

include(":socket-io")
if (!libraryPublish) {
    include(":sample:android-app")
    include(":sample:mpp-library")
}
