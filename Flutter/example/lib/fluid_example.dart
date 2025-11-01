/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-31
 *
 * Example: Fluid Scaling in Flutter
 *
 * Demonstrates the use of AppDimensFluid for smooth, clamp-like scaling
 * between minimum and maximum values based on screen width.
 */

import 'package:flutter/material.dart';
import 'package:appdimens/appdimens.dart';

/// Main example page showing all Fluid scaling examples
class FluidExamplesPage extends StatelessWidget {
  const FluidExamplesPage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('AppDimens Fluid Examples'),
      ),
      body: ListView(
        padding: const EdgeInsets.all(16),
        children: const [
          BasicFluidExample(),
          SizedBox(height: 16),
          FluidExtensionExample(),
          SizedBox(height: 16),
          FluidWithQualifiersExample(),
          SizedBox(height: 16),
          CustomBreakpointsExample(),
          SizedBox(height: 16),
          ResponsiveCardExample(),
          SizedBox(height: 16),
          FluidBuilderExample(),
        ],
      ),
    );
  }
}

/// Example 1: Basic Fluid Scaling
class BasicFluidExample extends StatelessWidget {
  const BasicFluidExample({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    // Font size that scales smoothly from 16 to 24 between 320-768px
    final fontSize = AppDimensFluid(16, 24).calculate(context);

    return Card(
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              'Fluid Typography: 16-24',
              style: TextStyle(
                fontSize: fontSize,
                fontWeight: FontWeight.w600,
              ),
            ),
            const SizedBox(height: 8),
            const Text(
              'Font size smoothly scales between 16 (320px screen) and 24 (768px screen)',
              style: TextStyle(fontSize: 12, color: Colors.grey),
            ),
          ],
        ),
      ),
    );
  }
}

/// Example 2: Using Fluid Extensions
class FluidExtensionExample extends StatelessWidget {
  const FluidExtensionExample({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    // Using extension methods for convenience
    final fontSize = 14.0.fluidTo(20).calculate(context);
    final padding = 8.0.fluidTo(16).calculate(context);

    return Card(
      child: Padding(
        padding: EdgeInsets.all(padding),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              'Fluid Extensions',
              style: TextStyle(
                fontSize: fontSize,
                fontWeight: FontWeight.w600,
              ),
            ),
            const SizedBox(height: 8),
            Text(
              'Font: ${fontSize.toStringAsFixed(1)} | Padding: ${padding.toStringAsFixed(1)}',
              style: const TextStyle(fontSize: 12, color: Colors.grey),
            ),
          ],
        ),
      ),
    );
  }
}

/// Example 3: Fluid with Device Type Qualifiers
class FluidWithQualifiersExample extends StatelessWidget {
  const FluidWithQualifiersExample({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    // Different ranges for different device types
    final fontSize = AppDimensFluid(16, 24)
      ..device(DeviceType.tablet, 20, 32)
      ..device(DeviceType.desktop, 24, 40);

    return Card(
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              'Device-Aware Fluid',
              style: TextStyle(
                fontSize: fontSize.calculate(context),
                fontWeight: FontWeight.w600,
              ),
            ),
            const SizedBox(height: 8),
            const Text(
              'Phones: 16-24 | Tablets: 20-32 | Desktop: 24-40',
              style: TextStyle(fontSize: 12, color: Colors.grey),
            ),
          ],
        ),
      ),
    );
  }
}

/// Example 4: Custom Breakpoints
class CustomBreakpointsExample extends StatelessWidget {
  const CustomBreakpointsExample({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    // Custom breakpoints: 280-600 range
    final fontSize = AppDimensFluid(
      12,
      20,
      minWidth: 280,
      maxWidth: 600,
    ).calculate(context);

    final padding = AppDimensFluid(
      6,
      12,
      minWidth: 280,
      maxWidth: 600,
    ).calculate(context);

    return Card(
      child: Padding(
        padding: EdgeInsets.all(padding),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              'Custom Breakpoints',
              style: TextStyle(
                fontSize: fontSize,
                fontWeight: FontWeight.w600,
              ),
            ),
            const SizedBox(height: 8),
            const Text(
              'Range: 280-600 (narrower than default 320-768)',
              style: TextStyle(fontSize: 12, color: Colors.grey),
            ),
          ],
        ),
      ),
    );
  }
}

/// Example 5: Responsive Card with Fluid Dimensions
class ResponsiveCardExample extends StatelessWidget {
  const ResponsiveCardExample({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final titleSize = 18.0.fluidTo(28).calculate(context);
    final bodySize = 14.0.fluidTo(18).calculate(context);
    final padding = 12.0.fluidTo(20).calculate(context);
    final borderRadius = 8.0.fluidTo(16).calculate(context);

    return Card(
      shape: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(borderRadius),
      ),
      child: Padding(
        padding: EdgeInsets.all(padding),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              'Fluid Card Design',
              style: TextStyle(
                fontSize: titleSize,
                fontWeight: FontWeight.bold,
              ),
            ),
            SizedBox(height: padding / 2),
            Text(
              'All dimensions (title, body, padding, radius) scale smoothly using '
              'fluid scaling model for optimal responsive behavior.',
              style: TextStyle(
                fontSize: bodySize,
                color: Colors.grey[700],
                height: 1.5,
              ),
            ),
          ],
        ),
      ),
    );
  }
}

/// Example 6: Fluid Builder Info
class FluidBuilderExample extends StatelessWidget {
  const FluidBuilderExample({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final fluidInstance = fluid(16, 24);

    return Card(
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            const Text(
              'Fluid Builder Info',
              style: TextStyle(
                fontSize: 16,
                fontWeight: FontWeight.w600,
              ),
            ),
            const SizedBox(height: 8),
            Text(
              'Min: ${fluidInstance.min}',
              style: const TextStyle(fontSize: 12, color: Colors.grey),
            ),
            Text(
              'Max: ${fluidInstance.max}',
              style: const TextStyle(fontSize: 12, color: Colors.grey),
            ),
            Text(
              'Preferred: ${fluidInstance.preferred}',
              style: const TextStyle(fontSize: 12, color: Colors.grey),
            ),
            Text(
              'At 25%: ${fluidInstance.lerp(0.25)}',
              style: const TextStyle(fontSize: 12, color: Colors.grey),
            ),
            Text(
              'At 50%: ${fluidInstance.lerp(0.5)}',
              style: const TextStyle(fontSize: 12, color: Colors.grey),
            ),
            Text(
              'At 75%: ${fluidInstance.lerp(0.75)}',
              style: const TextStyle(fontSize: 12, color: Colors.grey),
            ),
          ],
        ),
      ),
    );
  }
}

/// Example 7: Widget Extension Methods
class WidgetExtensionExample extends StatelessWidget {
  const WidgetExtensionExample({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return const Text('Fluid Widget Extensions')
        .fluidPadding(12, 20, context)
        .fluidMargin(8, 16, context)
        .fluidBorderRadius(8, 16, context);
  }
}

/// Example 8: TextStyle Extension
class TextStyleExtensionExample extends StatelessWidget {
  const TextStyleExtensionExample({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Text(
      'Fluid Font Size',
      style: const TextStyle(fontWeight: FontWeight.bold)
          .fluidFontSize(16, 24, context),
    );
  }
}

