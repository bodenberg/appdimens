// swift-tools-version:5.9
// The swift-tools-version declares the minimum version of Swift required to build this package.

import PackageDescription

let package = Package(
    name: "AppDimens",
    platforms: [
        .iOS(.v13),
        .macOS(.v10_15),
        .tvOS(.v13),
        .watchOS(.v6)
    ],
    products: [
        // Products define the executables and libraries a package produces, making them visible to other packages.
        .library(
            name: "AppDimens",
            targets: ["AppDimens", "AppDimensCore", "AppDimensUI", "AppDimensGames"]
        ),
        .library(
            name: "AppDimensCore",
            targets: ["AppDimensCore"]
        ),
        .library(
            name: "AppDimensUI",
            targets: ["AppDimensUI"]
        ),
        .library(
            name: "AppDimensGames",
            targets: ["AppDimensGames"]
        )
    ],
    dependencies: [
        // Dependencies declare other packages that this package depends on.
        // .package(url: /* package url */, from: "1.0.5"),
    ],
    targets: [
        // Targets are the basic building blocks of a package. A target can define a module or a test suite.
        // Targets can depend on other targets in this package, and on products in packages this package depends on.
        .target(
            name: "AppDimensCore",
            dependencies: [],
            path: "iOS/Sources/AppDimensCore"
        ),
        .target(
            name: "AppDimensUI",
            dependencies: ["AppDimensCore"],
            path: "iOS/Sources/AppDimensUI"
        ),
        .target(
            name: "AppDimensGames",
            dependencies: ["AppDimensCore"],
            path: "iOS/Sources/AppDimensGames"
        ),
        .target(
            name: "AppDimens",
            dependencies: ["AppDimensCore", "AppDimensUI"],
            path: "iOS/Sources/AppDimens"
        ),
        .testTarget(
            name: "AppDimensTests",
            dependencies: ["AppDimens", "AppDimensCore", "AppDimensUI", "AppDimensGames"],
            path: "iOS/Tests"
        )
    ]
)

