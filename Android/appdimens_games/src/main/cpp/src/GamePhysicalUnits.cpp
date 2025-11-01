/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-10-31
 *
 * Library: AppDimens Games - Physical Units Implementation
 *
 * Description:
 * Implementation of physical units conversion utilities for game development.
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

#include "GamePhysicalUnits.h"
#include <cmath>

// Initialize static member
float GamePhysicalUnits::currentScreenDPI = DEFAULT_DPI;

float GamePhysicalUnits::mm(float millimeters, float dpi) {
    float effectiveDPI = (dpi > 0.0f) ? dpi : currentScreenDPI;
    // mm to pixels: (mm / 25.4) * DPI
    return (millimeters / MM_TO_INCH_FACTOR) * effectiveDPI;
}

float GamePhysicalUnits::cm(float centimeters, float dpi) {
    // Convert cm to mm, then to pixels
    return mm(centimeters * MM_TO_CM_FACTOR, dpi);
}

float GamePhysicalUnits::inch(float inches, float dpi) {
    float effectiveDPI = (dpi > 0.0f) ? dpi : currentScreenDPI;
    // inches to pixels: inches * DPI
    return inches * effectiveDPI;
}

void GamePhysicalUnits::setScreenDPI(float dpi) {
    if (dpi > 0.0f) {
        currentScreenDPI = dpi;
        LOGI("Screen DPI set to: %.2f", dpi);
    }
}

float GamePhysicalUnits::getScreenDPI() {
    return currentScreenDPI;
}

// Reverse conversions (pixels to physical units)

float GamePhysicalUnits::pxToMm(float pixels, float dpi) {
    float effectiveDPI = (dpi > 0.0f) ? dpi : currentScreenDPI;
    // pixels to mm: (pixels / DPI) * 25.4
    return (pixels / effectiveDPI) * MM_TO_INCH_FACTOR;
}

float GamePhysicalUnits::pxToCm(float pixels, float dpi) {
    // Convert pixels to mm, then to cm
    return pxToMm(pixels, dpi) / MM_TO_CM_FACTOR;
}

float GamePhysicalUnits::pxToInch(float pixels, float dpi) {
    float effectiveDPI = (dpi > 0.0f) ? dpi : currentScreenDPI;
    // pixels to inches: pixels / DPI
    return pixels / effectiveDPI;
}

// Utility conversions between physical units

float GamePhysicalUnits::mmToCm(float millimeters) {
    return millimeters / MM_TO_CM_FACTOR;
}

float GamePhysicalUnits::mmToInch(float millimeters) {
    return millimeters / MM_TO_INCH_FACTOR;
}

float GamePhysicalUnits::cmToMm(float centimeters) {
    return centimeters * MM_TO_CM_FACTOR;
}

float GamePhysicalUnits::cmToInch(float centimeters) {
    return centimeters / CM_TO_INCH_FACTOR;
}

float GamePhysicalUnits::inchToMm(float inches) {
    return inches * MM_TO_INCH_FACTOR;
}

float GamePhysicalUnits::inchToCm(float inches) {
    return inches * CM_TO_INCH_FACTOR;
}

