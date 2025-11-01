/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-31
 *
 * Example: Fluid Scaling in React Native
 *
 * Demonstrates the use of AppDimensFluid for smooth, clamp-like scaling
 * between minimum and maximum values based on screen width.
 */

import React from 'react';
import {View, Text, StyleSheet, ScrollView} from 'react-native';
import {
  fluid,
  useFluid,
  useFluidMultiple,
  DeviceType,
  ScreenQualifier,
} from 'appdimens-react-native';

/**
 * Example 1: Basic Fluid Scaling
 */
export function BasicFluidExample() {
  // Font size that scales smoothly from 16 to 24 between 320-768px
  const fontSize = useFluid(16, 24);

  return (
    <View style={styles.container}>
      <Text style={[styles.text, {fontSize}]}>Fluid Typography: 16-24px</Text>
      <Text style={styles.description}>
        Font size smoothly scales between 16px (320px screen) and 24px (768px
        screen)
      </Text>
    </View>
  );
}

/**
 * Example 2: Multiple Fluid Values
 */
export function MultipleFluidExample() {
  // Multiple fluid values with shared breakpoints
  const [fontSize, padding, margin] = useFluidMultiple([
    [14, 20], // fontSize: 14-20
    [8, 16], // padding: 8-16
    [12, 24], // margin: 12-24
  ]);

  return (
    <View style={[styles.container, {margin}]}>
      <View style={{padding}}>
        <Text style={[styles.text, {fontSize}]}>Multi-Fluid Component</Text>
        <Text style={styles.description}>
          Font: {fontSize.toFixed(1)}px | Padding: {padding.toFixed(1)}px |
          Margin: {margin.toFixed(1)}px
        </Text>
      </View>
    </View>
  );
}

/**
 * Example 3: Fluid with Device Type Qualifiers
 */
export function FluidWithQualifiersExample() {
  const fluidWithQualifiers = fluid(16, 24)
    .device(DeviceType.Tablet, 20, 32) // Tablets: 20-32px
    .device(DeviceType.TV, 24, 40); // TVs: 24-40px

  const fontSize = fluidWithQualifiers.calculate();

  return (
    <View style={styles.container}>
      <Text style={[styles.text, {fontSize}]}>Device-Aware Fluid</Text>
      <Text style={styles.description}>
        Phones: 16-24px | Tablets: 20-32px | TVs: 24-40px
      </Text>
    </View>
  );
}

/**
 * Example 4: Fluid with Screen Width Qualifiers
 */
export function FluidWithScreenQualifiersExample() {
  const fluidWithScreens = fluid(12, 18)
    .screen(ScreenQualifier.SW600, 16, 24) // sw600: 16-24px
    .screen(ScreenQualifier.SW840, 20, 28); // sw840: 20-28px

  const fontSize = fluidWithScreens.calculate();

  return (
    <View style={styles.container}>
      <Text style={[styles.text, {fontSize}]}>Screen-Aware Fluid</Text>
      <Text style={styles.description}>
        Default: 12-18px | 600dp+: 16-24px | 840dp+: 20-28px
      </Text>
    </View>
  );
}

/**
 * Example 5: Custom Breakpoints
 */
export function CustomBreakpointsExample() {
  // Custom breakpoints: 280-600px range
  const fontSize = useFluid(12, 20, 280, 600);
  const padding = useFluid(6, 12, 280, 600);

  return (
    <View style={styles.container}>
      <View style={{padding}}>
        <Text style={[styles.text, {fontSize}]}>Custom Breakpoints</Text>
        <Text style={styles.description}>
          Range: 280px-600px (narrower than default)
        </Text>
      </View>
    </View>
  );
}

/**
 * Example 6: Responsive Card with Fluid Dimensions
 */
export function ResponsiveCardExample() {
  const titleSize = useFluid(18, 28);
  const bodySize = useFluid(14, 18);
  const padding = useFluid(12, 20);
  const borderRadius = useFluid(8, 16);

  return (
    <View
      style={[
        styles.card,
        {
          padding,
          borderRadius,
        },
      ]}>
      <Text style={[styles.cardTitle, {fontSize: titleSize}]}>
        Fluid Card Design
      </Text>
      <Text style={[styles.cardBody, {fontSize: bodySize}]}>
        All dimensions (title, body, padding, radius) scale smoothly using fluid
        scaling model for optimal responsive behavior.
      </Text>
    </View>
  );
}

/**
 * Example 7: Fluid Builder Methods
 */
export function FluidBuilderExample() {
  const fluidInstance = fluid(16, 24);

  return (
    <View style={styles.container}>
      <Text style={styles.text}>Fluid Builder Info</Text>
      <Text style={styles.description}>Min: {fluidInstance.getMin()}px</Text>
      <Text style={styles.description}>Max: {fluidInstance.getMax()}px</Text>
      <Text style={styles.description}>
        Preferred: {fluidInstance.getPreferred()}px
      </Text>
      <Text style={styles.description}>
        At 25%: {fluidInstance.lerp(0.25)}px
      </Text>
      <Text style={styles.description}>
        At 50%: {fluidInstance.lerp(0.5)}px
      </Text>
      <Text style={styles.description}>
        At 75%: {fluidInstance.lerp(0.75)}px
      </Text>
    </View>
  );
}

/**
 * Main Example Component
 */
export default function FluidExamples() {
  return (
    <ScrollView style={styles.scrollView}>
      <Text style={styles.header}>AppDimens Fluid Examples</Text>

      <BasicFluidExample />
      <MultipleFluidExample />
      <FluidWithQualifiersExample />
      <FluidWithScreenQualifiersExample />
      <CustomBreakpointsExample />
      <ResponsiveCardExample />
      <FluidBuilderExample />
    </ScrollView>
  );
}

const styles = StyleSheet.create({
  scrollView: {
    flex: 1,
    backgroundColor: '#f5f5f5',
  },
  header: {
    fontSize: 24,
    fontWeight: 'bold',
    textAlign: 'center',
    marginVertical: 20,
    color: '#333',
  },
  container: {
    backgroundColor: 'white',
    marginHorizontal: 16,
    marginVertical: 8,
    padding: 16,
    borderRadius: 8,
    shadowColor: '#000',
    shadowOffset: {width: 0, height: 2},
    shadowOpacity: 0.1,
    shadowRadius: 4,
    elevation: 3,
  },
  text: {
    fontWeight: '600',
    color: '#333',
    marginBottom: 8,
  },
  description: {
    fontSize: 12,
    color: '#666',
    marginTop: 4,
  },
  card: {
    backgroundColor: 'white',
    marginHorizontal: 16,
    marginVertical: 8,
    shadowColor: '#000',
    shadowOffset: {width: 0, height: 2},
    shadowOpacity: 0.1,
    shadowRadius: 4,
    elevation: 3,
  },
  cardTitle: {
    fontWeight: 'bold',
    color: '#333',
    marginBottom: 8,
  },
  cardBody: {
    color: '#666',
    lineHeight: 20,
  },
});
