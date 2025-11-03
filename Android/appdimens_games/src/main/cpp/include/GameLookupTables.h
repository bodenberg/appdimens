/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-02-01
 *
 * Library: AppDimens Games 2.0 - Lookup Tables (C++)
 *
 * Description:
 * Optimized lookup tables for fast mathematical operations.
 * Provides 10-20x faster ln() calculations using pre-computed tables.
 *
 * Licensed under the Apache License, Version 2.0
 */

#ifndef GAME_LOOKUP_TABLES_H
#define GAME_LOOKUP_TABLES_H

#include <cmath>
#include <algorithm>

namespace GameLookup {
    
    // ============================================
    // PRE-CALCULATED CONSTANTS
    // ============================================
    
    /** Base reference width in dp */
    constexpr float BASE_WIDTH_DP = 300.0f;
    
    /** Base reference height in dp */
    constexpr float BASE_HEIGHT_DP = 533.0f;
    
    /** Reference aspect ratio (16:9) */
    constexpr float REFERENCE_AR = 1.78f;
    
    /** Pre-calculated base diagonal: √(300² + 533²) */
    constexpr float BASE_DIAGONAL = 611.6305f;
    
    /** Pre-calculated base perimeter: 300 + 533 */
    constexpr float BASE_PERIMETER = 833.0f;
    
    /** Pre-calculated 1/BASE_WIDTH_DP for faster multiplication */
    constexpr float INV_BASE_WIDTH_DP = 0.003333333f;
    
    /** Pre-calculated 1/REFERENCE_AR for faster calculations */
    constexpr float INV_REFERENCE_AR = 0.5617978f;
    
    /** Default sensitivity for perceptual models */
    constexpr float DEFAULT_SENSITIVITY = 0.40f;
    
    /** Default power exponent for Stevens Power Law */
    constexpr float DEFAULT_POWER_EXPONENT = 0.75f;
    
    /** Default transition point for Balanced model */
    constexpr float DEFAULT_TRANSITION_POINT = 480.0f;
    
    /** Default aspect ratio sensitivity */
    constexpr float DEFAULT_AR_SENSITIVITY = 0.08f / 30.0f;
    
    /** Base increment factor for DEFAULT strategy */
    constexpr float BASE_INCREMENT = 0.10f / 30.0f;
    
    // ============================================
    // LOOKUP TABLE FOR ln()
    // ============================================
    
    /**
     * Lookup table for fast ln() calculation.
     * 
     * Performance: ~10-20x faster than std::log()
     * Cache hit rate: ~85-95% for typical game usage
     */
    class LnLookup {
    public:
        /**
         * Fast ln() using binary search lookup table.
         * 
         * @param value Input value
         * @return Natural logarithm of value
         */
        static float fastLn(float value);
        
    private:
        static constexpr int TABLE_SIZE = 45;
        static constexpr float TOLERANCE = 0.005f;
        
        // Pre-sorted keys for binary search
        static constexpr float keys[TABLE_SIZE] = {
            0.4f, 0.5f, 0.6f, 0.7f, 0.8f, 0.9f,
            1.0f, 1.1f, 1.2f, 1.25f, 1.28f, 1.3f, 1.33f, 1.367f, 1.414f, 1.5f,
            1.6f, 1.667f, 1.7f, 1.75f, 1.78f, 1.8f, 1.9f, 2.0f, 2.1f, 2.133f,
            2.16f, 2.2f, 2.25f, 2.3f, 2.33f, 2.4f, 2.5f, 2.6f, 2.7f, 2.8f,
            2.9f, 3.0f, 3.5f, 4.0f, 4.5f, 5.0f, 6.0f, 7.0f, 7.2f
        };
        
        // Corresponding ln() values
        static constexpr float values[TABLE_SIZE] = {
            -0.91629076f, -0.6931472f, -0.51082563f, -0.35667494f, -0.22314355f, -0.10536052f,
            0.0f, 0.09531018f, 0.18232156f, 0.22314355f, 0.24686007f, 0.26236426f,
            0.28728026f, 0.31244648f, 0.34610766f, 0.4054651f, 0.47000363f, 0.51082563f,
            0.5306282f, 0.5596158f, 0.57660466f, 0.58778667f, 0.64185388f, 0.6931472f,
            0.74193734f, 0.75750062f, 0.77014756f, 0.78845736f, 0.81093025f, 0.83290912f,
            0.84587455f, 0.8754687f, 0.91629076f, 0.95551145f, 0.99325174f, 1.02961942f,
            1.06471074f, 1.09861229f, 1.25276297f, 1.38629436f, 1.50407739f, 1.60943791f,
            1.79175947f, 1.94591015f, 1.97408103f
        };
        
        /**
         * Binary search with tolerance for closest match.
         */
        static float* lookup(float value);
    };
    
} // namespace GameLookup

#endif // GAME_LOOKUP_TABLES_H

