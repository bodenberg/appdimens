Pod::Spec.new do |spec|
  spec.name         = "AppDimens"
  spec.version      = "2.0.0"
  spec.summary      = "Smart and Responsive Dimensioning for iOS with 13 Scaling Strategies - Mathematically responsive scaling for any screen size"
  spec.description  = <<-DESC
                      AppDimens 2.0 introduces 13 scaling strategies with intelligent auto-inference for iOS. 
                      Features: DEFAULT (fixed legacy), PERCENTAGE (proportional), BALANCED (recommended - linear on phones, log on tablets), 
                      LOGARITHMIC (maximum control), POWER (Stevens law), FLUID (CSS clamp-like), INTERPOLATED, DIAGONAL, PERIMETER, 
                      FIT (letterbox), FILL (cover), AUTOSIZE (container-aware), and NONE (constant).
                      
                      Key Features v2.0:
                      - 13 scaling strategies with smart inference
                      - 18 element types for automatic strategy selection
                      - Perceptual models (Weber-Fechner, Stevens)
                      - Lock-free cache with <0.001ms hit time
                      - Fast ln() lookup tables (10-20x faster)
                      - Base Orientation support for auto-rotation
                      - Physical units (mm, cm, inch)
                      - AutoSize helpers for dynamic content
                      - Device-specific qualifiers
                      
                      The library is organized into three modules:
                      - Main: Unified dimension management with 13 strategies and advanced caching
                      - UI: UIKit and SwiftUI extensions with Fluid model and AutoSize support
                      - Games: Metal-specific functionality for game development with SIMD optimizations
                      DESC
  spec.homepage     = "https://github.com/bodenberg/appdimens"
  spec.license      = { :type => "Apache License, Version 2.0", :file => "LICENSE" }
  spec.author       = { "Jean Bodenberg" => "jc8752719@gmail.com" }
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
