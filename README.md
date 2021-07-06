![moko-socket-io](https://user-images.githubusercontent.com/5010169/80988267-712b7e80-8e5d-11ea-955e-c788a567c64e.png)  
[![GitHub license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0) [![Download](https://img.shields.io/maven-central/v/dev.icerock.moko/socket-io) ](https://repo1.maven.org/maven2/dev/icerock/moko/socket-io) ![kotlin-version](https://kotlin-version.aws.icerock.dev/kotlin-version?group=dev.icerock.moko&name=socket-io)

# Mobile Kotlin socket io
This is a Kotlin MultiPlatform library that provides real-time, event-based communication for iOS and Android.

## Table of Contents
- [Features](#features)
- [Requirements](#requirements)
- [Versions](#versions)
- [Installation](#installation)
- [Usage](#usage)
- [Samples](#samples)
- [Set Up Locally](#set-up-locally)
- [Contributing](#contributing)
- [License](#license)

## Features
- **Socket.IO in common code** - actual implementations is [socket.io-client-java](https://github.com/socketio/socket.io-client-java) and [socket.io-client-swift](https://github.com/socketio/socket.io-client-swift);

## Requirements
- Gradle version 6.8+
- Android API 16+
- iOS version 11.0+

## Installation
root build.gradle  
```groovy
allprojects {
    repositories {
        mavenCentral()
    }
}
```

project build.gradle
```groovy
dependencies {
    commonMainApi("dev.icerock.moko:socket-io:0.3.0")
    commonMainApi("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.1")
}

cocoaPods {
    podsProject = file("../ios-app/Pods/Pods.xcodeproj") // here should be path to Pods xcode project

    pod("mokoSocketIo", onlyLink = true)
}
```

Podfile
```ruby
pod 'mokoSocketIo', :git => 'https://github.com/icerockdev/moko-socket-io.git', :tag => 'release/0.3.0'
```

## Usage
`common`:
```kotlin
val socket = Socket(
    endpoint = "wss://my-super-server:8080",
    config = SocketOptions(
        queryParams = mapOf("token" to "MySuperToken"),
        transport = SocketOptions.Transport.WEBSOCKET
    )
) {
    on(SocketEvent.Connect) {
        println("connect")
    }

    on(SocketEvent.Connecting) {
        println("connecting")
    }

    on(SocketEvent.Disconnect) {
        println("disconnect")
    }

    on(SocketEvent.Error) {
        println("error $it")
    }

    on(SocketEvent.Reconnect) {
        println("reconnect")
    }

    on(SocketEvent.ReconnectAttempt) {
        println("reconnect attempt $it")
    }

    on(SocketEvent.Ping) {
        println("ping")
    }

    on(SocketEvent.Pong) {
        println("pong")
    }

    on("employee.connected") { data ->
        val serializer = DeliveryCar.serializer()
        val json = JSON.nonstrict
        val deliveryCar: DeliveryCar = json.parse(serializer, data)
        //...
    }
}
```

## Samples
Please see more examples in the [sample directory](sample).

## Set Up Locally 
- The [socket-io directory](socket-io) contains the `socket-io` library;
- The [sample directory](sample) contains sample apps for Android and iOS; plus the mpp-library connected to the apps;

## Contributing
All development (both new features and bug fixes) is performed in the `develop` branch. This way `master` always contains the sources of the most recently released version. Please send PRs with bug fixes to the `develop` branch. Documentation fixes in the markdown files are an exception to this rule. They are updated directly in `master`.

The `develop` branch is pushed to `master` on release.

For more details on contributing please see the [contributing guide](CONTRIBUTING.md).

## License
        
    Copyright 2020 IceRock MAG Inc.
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
       http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
