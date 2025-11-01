/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-10-31
 *
 * Library: AppDimens Games - Physical Units
 *
 * Description:
 * Physical units conversion utilities (mm, cm, inch) for game development.
 * Provides cross-platform physical dimension calculations.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

#ifndef GAME_PHYSICAL_UNITS_H
#define GAME_PHYSICAL_UNITS_H

#include "AppDimensGames.h"

/**
 * Physical units conversion for game development
 * Converts mm, cm, and inches to pixels based on screen DPI
 */
class GamePhysicalUnits {
public:
    // Conversion constants
    static constexpr float MM_TO_CM_FACTOR = 10.0f;
    static constexpr float MM_TO_INCH_FACTOR = 25.4f;
    static constexpr float CM_TO_INCH_FACTOR = 2.54f;
    static constexpr float DEFAULT_DPI = 160.0f;  // Android default DPI
    
    /**
     * Convert millimeters to pixels
     * @param millimeters Value in millimeters
     * @param dpi Screen DPI (optional, uses screen config if not provided)
     * @return Value in pixels
     */
    static float mm(float millimeters, float dpi = 0.0f);
    
    /**
     * Convert centimeters to pixels
     * @param centimeters Value in centimeters
     * @param dpi Screen DPI (optional, uses screen config if not provided)
     * @return Value in pixels
     */
    static float cm(float centimeters, float dpi = 0.0f);
    
    /**
     * Convert inches to pixels
     * @param inches Value in inches
     * @param dpi Screen DPI (optional, uses screen config if not provided)
     * @return Value in pixels
     */
    static float inch(float inches, float dpi = 0.0f);
    
    /**
     * Set the current screen DPI
     * Called automatically when screen configuration changes
     * @param dpi The screen DPI
     */
    static void setScreenDPI(float dpi);
    
    /**
     * Get the current screen DPI
     * @return The screen DPI
     */
    static float getScreenDPI();
    
    // Reverse conversions (pixels to physical units)
    
    /**
     * Convert pixels to millimeters
     * @param pixels Value in pixels
     * @param dpi Screen DPI (optional, uses screen config if not provided)
     * @return Value in millimeters
     */
    static float pxToMm(float pixels, float dpi = 0.0f);
    
    /**
     * Convert pixels to centimeters
     * @param pixels Value in pixels
     * @param dpi Screen DPI (optional, uses screen config if not provided)
     * @return Value in centimeters
     */
    static float pxToCm(float pixels, float dpi = 0.0f);
    
    /**
     * Convert pixels to inches
     * @param pixels Value in pixels
     * @param dpi Screen DPI (optional, uses screen config if not provided)
     * @return Value in inches
     */
    static float pxToInch(float pixels, float dpi = 0.0f);
    
    // Utility conversions between physical units
    
    /**
     * Convert millimeters to centimeters
     */
    static float mmToCm(float millimeters);
    
    /**
     * Convert millimeters to inches
     */
    static float mmToInch(float millimeters);
    
    /**
     * Convert centimeters to millimeters
     */
    static float cmToMm(float centimeters);
    
    /**
     * Convert centimeters to inches
     */
    static float cmToInch(float centimeters);
    
    /**
     * Convert inches to millimeters
     */
    static float inchToMm(float inches);
    
    /**
     * Convert inches to centimeters
     */
    static float inchToCm(float inches);

private:
    static float currentScreenDPI;
};

#endif // GAME_PHYSICAL_UNITS_H

