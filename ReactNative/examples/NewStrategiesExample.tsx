/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-11-02
 *
 * Library: AppDimens React Native v2.0.0 - New Scaling Strategies Example
 *
 * Description:
 * Example demonstrating the new 13 scaling strategies introduced in v2.0.0
 *
 * Licensed under the Apache License, Version 2.0
 */

import React, {useState} from 'react';
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  TouchableOpacity,
  SafeAreaView,
} from 'react-native';
import {useAppDimens, AppDimensProvider} from 'appdimens-react-native';

type Strategy =
  | 'balanced'
  | 'percentage'
  | 'default'
  | 'logarithmic'
  | 'power'
  | 'fluid'
  | 'interpolated'
  | 'diagonal'
  | 'perimeter'
  | 'fit'
  | 'fill'
  | 'autosize'
  | 'none';

interface StrategyInfo {
  name: string;
  description: string;
  bestFor: string;
}

const STRATEGIES: Record<Strategy, StrategyInfo> = {
  balanced: {
    name: 'BALANCED ⭐',
    description:
      'Linear on phones (<480), logarithmic on tablets (≥480). RECOMMENDED for most apps.',
    bestFor: 'Multi-device apps, buttons, spacing',
  },
  percentage: {
    name: 'PERCENTAGE',
    description: '100% proportional scaling (old Dynamic)',
    bestFor: 'Containers, fluid layouts, images',
  },
  default: {
    name: 'DEFAULT',
    description: '~97% linear + AR adjustment (old Fixed)',
    bestFor: 'Phone-only apps, icons, backward compatibility',
  },
  logarithmic: {
    name: 'LOGARITHMIC',
    description: 'Pure logarithmic (Weber-Fechner Law). Maximum control.',
    bestFor: 'TVs, very large tablets',
  },
  power: {
    name: 'POWER',
    description: "Stevens' Power Law with configurable exponent",
    bestFor: 'General purpose, configurable apps',
  },
  fluid: {
    name: 'FLUID',
    description: 'CSS clamp-like with min/max boundaries',
    bestFor: 'Typography, bounded spacing',
  },
  interpolated: {
    name: 'INTERPOLATED',
    description: '50% moderated linear growth',
    bestFor: 'Medium screens, balanced approach',
  },
  diagonal: {
    name: 'DIAGONAL',
    description: 'Scale based on screen diagonal',
    bestFor: 'Size-aware components',
  },
  perimeter: {
    name: 'PERIMETER',
    description: 'Scale based on perimeter (W+H)',
    bestFor: 'Peripheral UI elements',
  },
  fit: {
    name: 'FIT',
    description: 'Letterbox scaling (min ratio) - Game fit',
    bestFor: 'Game UI, letterbox content',
  },
  fill: {
    name: 'FILL',
    description: 'Cover scaling (max ratio) - Game fill',
    bestFor: 'Game UI, cover content',
  },
  autosize: {
    name: 'AUTOSIZE',
    description: 'Auto-adjust based on component size',
    bestFor: 'Adaptive components',
  },
  none: {
    name: 'NONE',
    description: 'No scaling (constant size)',
    bestFor: 'Fixed-size elements',
  },
};

function NewStrategiesExample() {
  const [selectedStrategy, setSelectedStrategy] = useState<Strategy>('balanced');
  const {balanced, percentage, defaultScaling, logarithmic, power, fluid, interpolated, diagonal, perimeter, fit, fill, autosize, smart, deviceInfo, screenDimensions} = useAppDimens();

  const getStrategySize = (base: number, strategy: Strategy) => {
    switch (strategy) {
      case 'balanced':
        return balanced(base).toPixels();
      case 'percentage':
        return percentage(base).toPixels();
      case 'default':
        return defaultScaling(base).toPixels();
      case 'logarithmic':
        return logarithmic(base).toPixels();
      case 'power':
        return power(base, 0.75).toPixels();
      case 'fluid':
        return fluid(base * 0.8, base * 1.2).toPixels();
      case 'interpolated':
        return interpolated(base).toPixels();
      case 'diagonal':
        return diagonal(base).toPixels();
      case 'perimeter':
        return perimeter(base).toPixels();
      case 'fit':
        return fit(base).toPixels();
      case 'fill':
        return fill(base).toPixels();
      case 'autosize':
        return autosize(base).toPixels();
      case 'none':
        return base;
      default:
        return base;
    }
  };

  const currentInfo = STRATEGIES[selectedStrategy];

  return (
    <SafeAreaView style={styles.container}>
      <ScrollView contentContainerStyle={styles.scrollContent}>
        {/* Header */}
        <View style={styles.header}>
          <Text style={styles.title}>AppDimens v2.0.0</Text>
          <Text style={styles.subtitle}>13 Scaling Strategies</Text>
        </View>

        {/* Device Info */}
        <View style={styles.infoCard}>
          <Text style={styles.cardTitle}>Device Information</Text>
          <Text style={styles.infoText}>Type: {deviceInfo.type}</Text>
          <Text style={styles.infoText}>Platform: {deviceInfo.platform}</Text>
          <Text style={styles.infoText}>
            Screen: {Math.round(screenDimensions.width)} ×{' '}
            {Math.round(screenDimensions.height)}
          </Text>
        </View>

        {/* About Strategies */}
        <View style={styles.infoCard}>
          <Text style={styles.cardTitle}>About Strategies</Text>
          <Text style={styles.infoDescription}>
            Version 2.0.0 introduces 13 scaling strategies:
          </Text>
          <View style={styles.bulletList}>
            <BulletPoint text="BALANCED ⭐ (Recommended): Best for multi-device apps" />
            <BulletPoint text="PERCENTAGE: Pure proportional (old Dynamic)" />
            <BulletPoint text="DEFAULT: Legacy fixed (old Fixed)" />
            <BulletPoint text="LOGARITHMIC: Maximum control on large screens" />
            <BulletPoint text="POWER: Scientific Stevens' Law" />
            <BulletPoint text="And 8 more specialized strategies..." />
          </View>
        </View>

        {/* Strategy Selector */}
        <View style={styles.selectorSection}>
          <Text style={styles.sectionTitle}>Select Strategy</Text>
          <ScrollView
            horizontal
            showsHorizontalScrollIndicator={false}
            contentContainerStyle={styles.chipContainer}>
            {(Object.keys(STRATEGIES) as Strategy[]).map(strategy => (
              <TouchableOpacity
                key={strategy}
                style={[
                  styles.chip,
                  selectedStrategy === strategy && styles.chipSelected,
                ]}
                onPress={() => setSelectedStrategy(strategy)}>
                <Text
                  style={[
                    styles.chipText,
                    selectedStrategy === strategy && styles.chipTextSelected,
                  ]}>
                  {STRATEGIES[strategy].name}
                </Text>
              </TouchableOpacity>
            ))}
          </ScrollView>
        </View>

        {/* Strategy Details */}
        <View style={styles.detailCard}>
          <Text style={styles.detailTitle}>{currentInfo.name}</Text>
          <Text style={styles.detailDescription}>{currentInfo.description}</Text>
          <View style={styles.detailBestFor}>
            <Text style={styles.detailBestForLabel}>Best for:</Text>
            <Text style={styles.detailBestForText}>{currentInfo.bestFor}</Text>
          </View>
        </View>

        {/* Visual Comparison */}
        <View style={styles.visualCard}>
          <Text style={styles.cardTitle}>Visual Comparison</Text>
          <Text style={styles.infoDescription}>
            See how different base values scale with {currentInfo.name}:
          </Text>
          <View style={styles.demoBoxContainer}>
            <DemoBox size={getStrategySize(48, selectedStrategy)} label="48" />
            <DemoBox size={getStrategySize(64, selectedStrategy)} label="64" />
            <DemoBox size={getStrategySize(96, selectedStrategy)} label="96" />
          </View>
        </View>

        {/* Code Examples */}
        <View style={styles.codeCard}>
          <Text style={styles.cardTitle}>Code Examples</Text>
          <View style={styles.codeBlock}>
            <Text style={styles.codeLabel}>React Native:</Text>
            <Text style={styles.codeText}>
              {getCodeExample(selectedStrategy)}
            </Text>
          </View>
          <View style={styles.codeBlock}>
            <Text style={styles.codeLabel}>With hooks:</Text>
            <Text style={styles.codeText}>
              {getHookExample(selectedStrategy)}
            </Text>
          </View>
        </View>

        {/* Smart API Demo */}
        <View style={styles.smartCard}>
          <Text style={styles.smartTitle}>🧠 Smart API (Auto-Inference)</Text>
          <Text style={styles.infoDescription}>
            The Smart API automatically selects the best strategy based on the
            element type:
          </Text>
          <SmartAPIExample
            elementType="Button"
            code="{smart(48).forElement('button').toPixels()}"
            strategy="BALANCED"
          />
          <SmartAPIExample
            elementType="Container"
            code="{smart(300).forElement('container').toPixels()}"
            strategy="PERCENTAGE"
          />
          <SmartAPIExample
            elementType="Text"
            code="{smart(16).forElement('text').toPixels()}"
            strategy="BALANCED"
          />
          <SmartAPIExample
            elementType="Icon"
            code="{smart(24).forElement('icon').toPixels()}"
            strategy="DEFAULT"
          />
        </View>

        {/* Migration Note */}
        <View style={styles.migrationCard}>
          <Text style={styles.migrationTitle}>📝 Migration Note</Text>
          <Text style={styles.migrationText}>
            Old API still works (deprecated):
          </Text>
          <Text style={styles.codeText}>• fx() → default()</Text>
          <Text style={styles.codeText}>• dy() → percentage()</Text>
          <Text style={styles.migrationText} style={{marginTop: 8}}>
            New recommended API:
          </Text>
          <Text style={styles.codeText}>• balanced() ⭐ (most cases)</Text>
          <Text style={styles.codeText}>• smart() (auto-inference)</Text>
        </View>
      </ScrollView>
    </SafeAreaView>
  );
}

const BulletPoint = ({text}: {text: string}) => (
  <View style={styles.bulletItem}>
    <Text style={styles.bulletDot}>•</Text>
    <Text style={styles.bulletText}>{text}</Text>
  </View>
);

const DemoBox = ({size, label}: {size: number; label: string}) => (
  <View style={styles.demoBoxWrapper}>
    <View
      style={[
        styles.demoBox,
        {width: size, height: size, borderRadius: size * 0.15},
      ]}
    />
    <Text style={styles.demoBoxLabel}>{label}</Text>
    <Text style={styles.demoBoxSize}>{Math.round(size)}px</Text>
  </View>
);

const SmartAPIExample = ({
  elementType,
  code,
  strategy,
}: {
  elementType: string;
  code: string;
  strategy: string;
}) => (
  <View style={styles.smartExample}>
    <View style={styles.smartExampleHeader}>
      <Text style={styles.smartElementType}>{elementType}</Text>
      <Text style={styles.smartStrategy}>→ {strategy}</Text>
    </View>
    <Text style={styles.smartCode}>{code}</Text>
  </View>
);

const getCodeExample = (strategy: Strategy): string => {
  const examples: Record<Strategy, string> = {
    balanced: 'balanced(300).toPixels()',
    percentage: 'percentage(300).toPixels()',
    default: 'defaultScaling(300).toPixels()',
    logarithmic: 'logarithmic(300).toPixels()',
    power: 'power(300, 0.75).toPixels()',
    fluid: 'fluid(240, 360).toPixels()',
    interpolated: 'interpolated(300).toPixels()',
    diagonal: 'diagonal(300).toPixels()',
    perimeter: 'perimeter(300).toPixels()',
    fit: 'fit(300).toPixels()',
    fill: 'fill(300).toPixels()',
    autosize: 'autosize(300).toPixels()',
    none: '300 // no scaling',
  };
  return examples[strategy];
};

const getHookExample = (strategy: Strategy): string => {
  const examples: Record<Strategy, string> = {
    balanced: "const size = useBalanced(300);",
    percentage: "const size = usePercentage(300);",
    default: "const size = useDefault(300);",
    logarithmic: "const size = useLogarithmic(300);",
    power: "const size = usePower(300, 0.75);",
    fluid: "const size = useFluid(240, 360);",
    interpolated: "const size = useInterpolated(300);",
    diagonal: "const size = useDiagonal(300);",
    perimeter: "const size = usePerimeter(300);",
    fit: "const size = useFit(300);",
    fill: "const size = useFill(300);",
    autosize: "const size = useAutosize(300);",
    none: "const size = 300; // no scaling",
  };
  return examples[strategy];
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#F5F5F5',
  },
  scrollContent: {
    padding: 16,
  },
  header: {
    alignItems: 'center',
    marginBottom: 20,
    paddingVertical: 16,
  },
  title: {
    fontSize: 28,
    fontWeight: 'bold',
    color: '#333333',
    marginBottom: 4,
  },
  subtitle: {
    fontSize: 16,
    color: '#666666',
  },
  infoCard: {
    backgroundColor: '#FFFFFF',
    padding: 16,
    borderRadius: 12,
    marginBottom: 16,
    shadowColor: '#000',
    shadowOffset: {width: 0, height: 2},
    shadowOpacity: 0.1,
    shadowRadius: 4,
    elevation: 3,
  },
  cardTitle: {
    fontSize: 18,
    fontWeight: 'bold',
    color: '#333333',
    marginBottom: 8,
  },
  infoText: {
    fontSize: 14,
    color: '#666666',
    marginBottom: 4,
  },
  infoDescription: {
    fontSize: 14,
    color: '#666666',
    marginBottom: 12,
  },
  bulletList: {
    marginTop: 8,
  },
  bulletItem: {
    flexDirection: 'row',
    marginBottom: 4,
  },
  bulletDot: {
    fontSize: 14,
    color: '#666666',
    marginRight: 6,
  },
  bulletText: {
    fontSize: 12,
    color: '#666666',
    flex: 1,
  },
  selectorSection: {
    marginBottom: 16,
  },
  sectionTitle: {
    fontSize: 16,
    fontWeight: '600',
    color: '#333333',
    marginBottom: 12,
  },
  chipContainer: {
    flexDirection: 'row',
    paddingVertical: 4,
  },
  chip: {
    backgroundColor: '#E0E0E0',
    paddingHorizontal: 12,
    paddingVertical: 8,
    borderRadius: 20,
    marginRight: 8,
  },
  chipSelected: {
    backgroundColor: '#007AFF',
  },
  chipText: {
    fontSize: 12,
    fontWeight: '600',
    color: '#333333',
  },
  chipTextSelected: {
    color: '#FFFFFF',
  },
  detailCard: {
    backgroundColor: '#E3F2FD',
    padding: 16,
    borderRadius: 12,
    marginBottom: 16,
  },
  detailTitle: {
    fontSize: 20,
    fontWeight: 'bold',
    color: '#1976D2',
    marginBottom: 8,
  },
  detailDescription: {
    fontSize: 14,
    color: '#424242',
    marginBottom: 12,
  },
  detailBestFor: {
    marginTop: 8,
  },
  detailBestForLabel: {
    fontSize: 13,
    fontWeight: '600',
    color: '#1976D2',
  },
  detailBestForText: {
    fontSize: 13,
    color: '#424242',
    marginTop: 4,
  },
  visualCard: {
    backgroundColor: '#FFFFFF',
    padding: 16,
    borderRadius: 12,
    marginBottom: 16,
    shadowColor: '#000',
    shadowOffset: {width: 0, height: 2},
    shadowOpacity: 0.1,
    shadowRadius: 4,
    elevation: 3,
  },
  demoBoxContainer: {
    flexDirection: 'row',
    justifyContent: 'space-around',
    alignItems: 'center',
    marginTop: 16,
  },
  demoBoxWrapper: {
    alignItems: 'center',
  },
  demoBox: {
    backgroundColor: '#BBDEFB',
    marginBottom: 8,
  },
  demoBoxLabel: {
    fontSize: 11,
    color: '#666666',
  },
  demoBoxSize: {
    fontSize: 11,
    fontWeight: 'bold',
    color: '#333333',
  },
  codeCard: {
    backgroundColor: '#F5F5F5',
    padding: 16,
    borderRadius: 12,
    marginBottom: 16,
  },
  codeBlock: {
    marginBottom: 12,
  },
  codeLabel: {
    fontSize: 13,
    fontWeight: '600',
    color: '#333333',
    marginBottom: 4,
  },
  codeText: {
    fontFamily: 'Courier',
    fontSize: 11,
    color: '#1976D2',
    backgroundColor: '#E8E8E8',
    padding: 8,
    borderRadius: 6,
  },
  smartCard: {
    backgroundColor: '#FFF3E0',
    padding: 16,
    borderRadius: 12,
    marginBottom: 16,
  },
  smartTitle: {
    fontSize: 18,
    fontWeight: 'bold',
    color: '#E65100',
    marginBottom: 8,
  },
  smartExample: {
    marginTop: 12,
  },
  smartExampleHeader: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    marginBottom: 4,
  },
  smartElementType: {
    fontSize: 14,
    fontWeight: '600',
    color: '#333333',
  },
  smartStrategy: {
    fontSize: 12,
    color: '#FF6F00',
  },
  smartCode: {
    fontFamily: 'Courier',
    fontSize: 10,
    color: '#E65100',
    backgroundColor: '#FFFFFF',
    padding: 8,
    borderRadius: 6,
  },
  migrationCard: {
    backgroundColor: '#E8F5E9',
    padding: 16,
    borderRadius: 12,
    marginBottom: 20,
  },
  migrationTitle: {
    fontSize: 16,
    fontWeight: 'bold',
    color: '#2E7D32',
    marginBottom: 8,
  },
  migrationText: {
    fontSize: 13,
    color: '#424242',
    marginBottom: 4,
  },
});

// App wrapper with provider
export default function App() {
  return (
    <AppDimensProvider>
      <NewStrategiesExample />
    </AppDimensProvider>
  );
}

