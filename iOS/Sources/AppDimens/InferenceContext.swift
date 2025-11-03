/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-02-01
 *
 * Library: AppDimens 2.0 - Inference Context (iOS)
 *
 * Description:
 * Classes for smart strategy inference with context analysis and weights.
 * This is the single source of truth for inference context across UIKit and SwiftUI.
 *
 * Licensed under the Apache License, Version 2.0
 */

import UIKit

/**
 * Context for strategy inference.
 *
 * Contains all relevant information about the current device and screen
 * configuration to help determine the best scaling strategy.
 */
public struct InferenceContext {
    public let smallestPt: CGFloat
    public let largestPt: CGFloat
    public let deviceType: DeviceType
    public let densityScale: CGFloat
    public let hasFluidConfig: Bool
    public let hasBounds: Bool
    
    public init(
        smallestPt: CGFloat,
        largestPt: CGFloat,
        deviceType: DeviceType,
        densityScale: CGFloat = UIScreen.main.scale,
        hasFluidConfig: Bool = false,
        hasBounds: Bool = false
    ) {
        self.smallestPt = smallestPt
        self.largestPt = largestPt
        self.deviceType = deviceType
        self.densityScale = densityScale
        self.hasFluidConfig = hasFluidConfig
        self.hasBounds = hasBounds
    }
}

/**
 * Device type classification for inference.
 *
 * Helps determine appropriate scaling strategies based on device category.
 */
public enum DeviceType {
    /// < 375pt
    case phoneSmall
    
    /// 375-479pt
    case phoneNormal
    
    /// 480-599pt (phablet)
    case phoneLarge
    
    /// 600-719pt
    case tabletSmall
    
    /// >= 720pt
    case tabletLarge
    
    /// Television
    case tv
    
    /// Watch
    case watch
    
    /// Auto/CarPlay
    case auto
    
    /**
     * Classify device based on smallest dimension.
     *
     * - Parameter smallestPt: The smallest screen dimension in pt
     * - Returns: The classified device type
     */
    public static func from(smallestPt: CGFloat) -> DeviceType {
        switch UIDevice.current.userInterfaceIdiom {
        case .tv:
            return .tv
        case .carPlay:
            return .auto
        case .watch:
            return .watch
        case .pad:
            if smallestPt < 720 {
                return .tabletSmall
            } else {
                return .tabletLarge
            }
        case .phone:
            if smallestPt < 375 {
                return .phoneSmall
            } else if smallestPt < 480 {
                return .phoneNormal
            } else {
                return .phoneLarge
            }
        default:
            return .phoneNormal
        }
    }
}

/**
 * Strategy weight for inference.
 *
 * Represents how strongly a particular strategy should be considered
 * based on the current context and element type.
 */
public struct StrategyWeight {
    public let strategy: ScalingStrategy
    public let weight: Float
    public let reason: String
    
    public init(strategy: ScalingStrategy, weight: Float, reason: String) {
        self.strategy = strategy
        self.weight = weight
        self.reason = reason
    }
}

/**
 * AutoSize mode for container-aware scaling.
 */
public enum AutoSizeMode {
    /// Adjusts proportionally within bounds
    case uniform
    
    /// Uses predefined size values
    case preset
}

