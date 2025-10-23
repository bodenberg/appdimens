Pod::Spec.new do |spec|
  spec.name         = "AppDimens"
  spec.version      = "1.0.8"
  spec.summary      = "A responsive dimension management system for iOS that automatically adjusts values based on screen dimensions"
  spec.description  = <<-DESC
                      AppDimens is a dimension management system that automatically adjusts Dp, Sp, and Px values in a responsive and mathematically refined way, ensuring layout consistency across any screen size or ratio. It provides two main scaling models: Fixed (FX) for subtle logarithmic adjustment and Dynamic (DY) for proportional adjustment.
                      
                      The library is organized into three modules:
                      - Main: Unified dimension management functionality with advanced caching and qualifiers
                      - UI: UIKit and SwiftUI extensions and integrations
                      - Games: Metal-specific functionality for game development
                      DESC
  spec.homepage     = "https://github.com/bodenberg/appdimens"
  spec.license      = { :type => "Apache License, Version 2.0", :file => "LICENSE" }
  spec.author       = { "Jean Bodenberg" => "jean.bodenberg@gmail.com" }
  spec.source       = { :git => "https://github.com/bodenberg/appdimens.git", :tag => "#{spec.version}" }
  
  spec.ios.deployment_target = "13.0"
  spec.swift_version = "5.0"
  
  # Default subspec includes all modules
  spec.default_subspecs = ['Main', 'UI']
  
  # Main subspec - Unified dimension management
  spec.subspec 'Main' do |main|
    main.source_files = "Sources/AppDimens/**/*.swift"
    main.frameworks = "Foundation", "UIKit"
    main.requires_arc = true
  end
  
  # UI subspec - UIKit and SwiftUI extensions
  spec.subspec 'UI' do |ui|
    ui.source_files = "Sources/AppDimensUI/**/*.swift"
    ui.frameworks = "UIKit", "SwiftUI"
    ui.dependency 'AppDimens/Main'
    ui.requires_arc = true
  end
  
  # Games subspec - Metal-specific functionality
  spec.subspec 'Games' do |games|
    games.source_files = "Sources/AppDimensGames/**/*.swift"
    games.frameworks = "Metal", "MetalKit", "simd"
    games.dependency 'AppDimens/Main'
    games.requires_arc = true
  end
  
  spec.documentation_url = "https://github.com/bodenberg/appdimens/blob/main/README.md"
  spec.social_media_url = "https://github.com/bodenberg"
end
