/**
 * Author & Developer: Jean Bodenberg
 * Based on: AppDimens Android - AppDimensPhysicalUnits
 * 
 * Physical Units Conversion
 * Converts physical measurements (MM, CM, Inch) to pixels
 */

import { MM_TO_CM_FACTOR, MM_TO_INCH_FACTOR, CM_TO_INCH_FACTOR, DEFAULT_DPI } from '../constants';

/**
 * Physical Units Converter
 * Handles conversions between physical units and pixels
 */
export class PhysicalUnits {
  private dpi: number;

  constructor() {
    this.dpi = this.detectDPI();
  }

  /**
   * Detect actual device DPI
   */
  private detectDPI(): number {
    if (typeof window === 'undefined') {
      return DEFAULT_DPI;
    }

    try {
      // Create a temporary element with 1 inch size
      const div = document.createElement('div');
      div.style.width = '1in';
      div.style.height = '1in';
      div.style.position = 'absolute';
      div.style.left = '-100%';
      div.style.top = '-100%';
      div.style.visibility = 'hidden';
      
      document.body.appendChild(div);
      const dpi = div.offsetWidth;
      document.body.removeChild(div);
      
      return dpi || DEFAULT_DPI;
    } catch {
      return DEFAULT_DPI;
    }
  }

  /**
   * Get current DPI
   */
  getDPI(): number {
    return this.dpi;
  }

  /**
   * Millimeters conversions
   */
  mmToPx(mm: number): number {
    return (mm / MM_TO_INCH_FACTOR) * this.dpi;
  }

  mmToCm(mm: number): number {
    return mm / MM_TO_CM_FACTOR;
  }

  mmToInch(mm: number): number {
    return mm / MM_TO_INCH_FACTOR;
  }

  /**
   * Centimeters conversions
   */
  cmToPx(cm: number): number {
    return (cm / CM_TO_INCH_FACTOR) * this.dpi;
  }

  cmToMm(cm: number): number {
    return cm * MM_TO_CM_FACTOR;
  }

  cmToInch(cm: number): number {
    return cm / CM_TO_INCH_FACTOR;
  }

  /**
   * Inches conversions
   */
  inchToPx(inch: number): number {
    return inch * this.dpi;
  }

  inchToMm(inch: number): number {
    return inch * MM_TO_INCH_FACTOR;
  }

  inchToCm(inch: number): number {
    return inch * CM_TO_INCH_FACTOR;
  }

  /**
   * Convert to CSS string
   */
  mm(value: number): string {
    return `${this.mmToPx(value)}px`;
  }

  cm(value: number): string {
    return `${this.cmToPx(value)}px`;
  }

  inch(value: number): string {
    return `${this.inchToPx(value)}px`;
  }

  /**
   * Calculate radius from diameter
   */
  radius(diameter: number, unit: 'mm' | 'cm' | 'inch' | 'px'): number {
    let px: number;

    switch (unit) {
      case 'mm':
        px = this.mmToPx(diameter);
        break;
      case 'cm':
        px = this.cmToPx(diameter);
        break;
      case 'inch':
        px = this.inchToPx(diameter);
        break;
      default:
        px = diameter;
    }

    return px / 2;
  }

  /**
   * Calculate circumference
   */
  circumference(diameter: number, unit: 'mm' | 'cm' | 'inch' | 'px'): number {
    const radius = this.radius(diameter, unit);
    return 2 * Math.PI * radius;
  }
}

/**
 * Global physical units instance
 */
export const physicalUnits = new PhysicalUnits();

