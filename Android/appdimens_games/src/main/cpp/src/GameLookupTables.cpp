/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-02-01
 *
 * Library: AppDimens Games 2.0 - Lookup Tables Implementation (C++)
 *
 * Description:
 * Implementation of optimized lookup tables for fast mathematical operations.
 *
 * Licensed under the Apache License, Version 2.0
 */

#include "GameLookupTables.h"
#include <cmath>
#include <cstdlib>

namespace GameLookup {
    
    // Define static constexpr members (required for C++14/17)
    constexpr float LnLookup::keys[TABLE_SIZE];
    constexpr float LnLookup::values[TABLE_SIZE];
    
    /**
     * Binary search for ln() value with tolerance matching.
     * 
     * Performance: O(log n) ≈ 6 comparisons for 45 entries
     * ~0.001µs lookup time vs ~0.012µs for std::log()
     * 
     * @param value Input value for logarithm
     * @return Pointer to ln(value) if found, nullptr otherwise
     */
    float* LnLookup::lookup(float value) {
        int low = 0;
        int high = TABLE_SIZE - 1;
        
        // Binary search for closest key
        while (low <= high) {
            int mid = (low + high) >> 1;  // Fast division by 2
            float midVal = keys[mid];
            
            // Check if within tolerance
            float diff = std::abs(value - midVal);
            if (diff <= TOLERANCE) {
                return const_cast<float*>(&values[mid]);
            }
            
            if (midVal < value) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        
        // Check neighbors
        if (high >= 0 && std::abs(value - keys[high]) <= TOLERANCE) {
            return const_cast<float*>(&values[high]);
        }
        
        if (low < TABLE_SIZE && std::abs(value - keys[low]) <= TOLERANCE) {
            return const_cast<float*>(&values[low]);
        }
        
        return nullptr;
    }
    
    /**
     * Fast ln() with lookup table fallback.
     * 
     * Tries lookup table first, falls back to std::log() if not found.
     * 
     * @param value Input value
     * @return Natural logarithm of value
     */
    float LnLookup::fastLn(float value) {
        float* cached = lookup(value);
        if (cached != nullptr) {
            return *cached;
        }
        return std::log(value);
    }
    
} // namespace GameLookup

