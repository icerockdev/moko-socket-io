Pod::Spec.new do |spec|
    spec.name                     = 'mokoSocketIo'
    spec.version                  = '0.2.0'
    spec.homepage                 = 'https://github.com/icerockdev/moko-socket-io'
    spec.source                   = { :git => "https://github.com/icerockdev/moko-socket-io.git", :tag => "release/#{spec.version}" }
    spec.authors                  = 'IceRock Development'
    spec.license                  = { :type => 'Apache 2', :file => 'LICENSE.md' }
    spec.summary                  = 'Swift additions to Kotlin/Native library'

    spec.module_name              = "#{spec.name}"
    spec.source_files             = "socket-io/src/iosMain/swift/**/*.{h,m,swift}"
    spec.resources                = "socket-io/src/iosMain/bundle/**/*"

    spec.dependency 'Socket.IO-Client-Swift', '~> 15.2.0'

    spec.ios.deployment_target  = '11.0'
    spec.swift_version = '5.0'

    spec.pod_target_xcconfig = {
        'VALID_ARCHS' => '$(ARCHS_STANDARD_64_BIT)'
    }
end
