/**
 * AppDimens React Native v2.0 - Practical Usage Examples
 * Demonstra todas as 13 estratégias em casos de uso reais
 */

import React from 'react';
import { View, Text, StyleSheet, ScrollView } from 'react-native';
import {
  balanced,
  logarithmic,
  power,
  defaultStrategy,
  percentage,
  smart,
  ElementType,
  AppDimensBuilder,
} from 'appdimens-react-native';

/**
 * Exemplo 1: App Multi-Dispositivo (BALANCED - Recomendado)
 */
export function MultiDeviceApp() {
  return (
    <View style={styles.container}>
      <Text style={styles.title}>Multi-Device App</Text>
      <View style={styles.card}>
        <Text style={styles.cardText}>Card com BALANCED</Text>
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: balanced(16).toPixels(), // ⭐ BALANCED
  },
  title: {
    fontSize: balanced(24).toSp(), // ⭐ BALANCED para texto
    marginBottom: balanced(20).toPixels(),
  },
  card: {
    width: balanced(300).toPixels(), // ⭐ BALANCED para container
    height: balanced(200).toPixels(),
    padding: balanced(16).toPixels(),
    borderRadius: balanced(12).toPixels(),
    backgroundColor: '#007AFF',
  },
  cardText: {
    fontSize: balanced(16).toSp(),
    color: '#FFFFFF',
  },
});

/**
 * Exemplo 2: Smart Inference (Auto-seleção)
 */
export function SmartInferenceExample() {
  return (
    <View style={smartStyles.container}>
      <View style={smartStyles.button}>
        <Text style={smartStyles.buttonText}>Button</Text>
      </View>
      <View style={smartStyles.icon} />
      <View style={smartStyles.card}>
        <Text>Card</Text>
      </View>
    </View>
  );
}

const smartStyles = StyleSheet.create({
  container: {
    flex: 1,
    padding: smart(16, ElementType.SPACING).toPixels(),
  },
  button: {
    padding: smart(12, ElementType.BUTTON).toPixels(), // → BALANCED
    borderRadius: smart(8, ElementType.BUTTON).toPixels(),
  },
  buttonText: {
    fontSize: smart(16, ElementType.TEXT).toSp(), // → BALANCED
  },
  icon: {
    width: smart(24, ElementType.ICON).toPixels(), // → DEFAULT
    height: smart(24, ElementType.ICON).toPixels(),
  },
  card: {
    width: smart(300, ElementType.CARD).toPixels(), // → PERCENTAGE
    height: smart(200, ElementType.CARD).toPixels(),
  },
});

/**
 * Exemplo 3: App para TV (LOGARITHMIC)
 */
export function TVApp() {
  return (
    <View style={tvStyles.container}>
      <Text style={tvStyles.title}>TV App</Text>
      <Text style={tvStyles.description}>
        Usa LOGARITHMIC para controlar crescimento em telas grandes
      </Text>
    </View>
  );
}

const tvStyles = StyleSheet.create({
  container: {
    flex: 1,
    padding: logarithmic(32).toPixels(), // LOGARITHMIC
  },
  title: {
    fontSize: logarithmic(48).toSp(), // Controla crescimento em TVs
    marginBottom: logarithmic(24).toPixels(),
  },
  description: {
    fontSize: logarithmic(20).toSp(),
  },
});

/**
 * Exemplo 4: Jogo com UI Adaptativa (FIT/FILL)
 */
export function GameUI() {
  return (
    <View style={gameStyles.container}>
      <View style={gameStyles.gameArea}>
        <Text style={gameStyles.gameText}>Game Area (FIT)</Text>
      </View>
      <View style={gameStyles.background} />
    </View>
  );
}

const gameStyles = StyleSheet.create({
  container: {
    flex: 1,
  },
  gameArea: {
    width: new AppDimensBuilder(1920).fit().toPixels(), // FIT - letterbox
    height: new AppDimensBuilder(1080).fit().toPixels(),
    backgroundColor: '#333333',
  },
  gameText: {
    fontSize: new AppDimensBuilder(32).fit().toSp(),
    color: '#FFFFFF',
  },
  background: {
    position: 'absolute',
    width: new AppDimensBuilder(1920).fill().toPixels(), // FILL - cover
    height: new AppDimensBuilder(1080).fill().toPixels(),
    backgroundColor: '#000000',
    zIndex: -1,
  },
});

/**
 * Exemplo 5: Configuração Avançada
 */
export function AdvancedConfiguration() {
  // BALANCED com sensibilidade customizada
  const customSensitivity = new AppDimensBuilder(48)
    .balanced()
    .withSensitivity(0.50) // Mais agressivo
    .toPixels();

  // LOGARITHMIC com constraints
  const constrainedSize = logarithmic(48)
    .withMin(32) // Mínimo 32dp
    .withMax(96) // Máximo 96dp
    .toPixels();

  // POWER com expoente customizado
  const powerSize = power(48, 0.80) // Expoente 0.80
    .portraitLowest()
    .toPixels();

  // FLUID com breakpoints customizados
  const fluidSize = new AppDimensBuilder(48)
    .fluid(40, 80, 320, 1024) // min, max, minWidth, maxWidth
    .toPixels();

  return (
    <View style={{ padding: customSensitivity }}>
      <Text>Custom Sensitivity: {customSensitivity.toFixed(1)}px</Text>
      <Text>Constrained: {constrainedSize.toFixed(1)}px</Text>
      <Text>Power: {powerSize.toFixed(1)}px</Text>
      <Text>Fluid: {fluidSize.toFixed(1)}px</Text>
    </View>
  );
}

/**
 * Exemplo 6: Comparação de Todas as Estratégias
 */
export function StrategyComparison() {
  const baseValue = 48;

  const strategies = {
    DEFAULT: defaultStrategy(baseValue).toPixels(),
    PERCENTAGE: percentage(baseValue).toPixels(),
    BALANCED: balanced(baseValue).toPixels(),
    LOGARITHMIC: logarithmic(baseValue).toPixels(),
    POWER: power(baseValue, 0.75).toPixels(),
    INTERPOLATED: new AppDimensBuilder(baseValue).interpolated().toPixels(),
    DIAGONAL: new AppDimensBuilder(baseValue).diagonal().toPixels(),
    PERIMETER: new AppDimensBuilder(baseValue).perimeter().toPixels(),
    FIT: new AppDimensBuilder(baseValue).fit().toPixels(),
    FILL: new AppDimensBuilder(baseValue).fill().toPixels(),
    NONE: new AppDimensBuilder(baseValue).none().toPixels(),
  };

  return (
    <ScrollView style={{ padding: 20 }}>
      <Text style={{ fontSize: 24, marginBottom: 20 }}>
        Strategy Comparison (Base: {baseValue}dp)
      </Text>
      {Object.entries(strategies).map(([name, value]) => (
        <View key={name} style={{ marginBottom: 10 }}>
          <Text>
            {name.padEnd(15, ' ')}: {value.toFixed(2)}px
          </Text>
        </View>
      ))}
    </ScrollView>
  );
}

/**
 * Exemplo 7: Migração de v1.x para v2.0
 */
export function MigrationExample() {
  // v1.x - ainda funciona!
  const { fx, dy } = require('appdimens-react-native');
  
  const oldFixed = fx(48).toPixels();     // → DEFAULT strategy
  const oldDynamic = dy(48).toPixels();   // → PERCENTAGE strategy

  // v2.0 - recomendado
  const newBalanced = balanced(48).toPixels();
  
  return (
    <View>
      <Text>v1.x Fixed: {oldFixed.toFixed(1)}px</Text>
      <Text>v1.x Dynamic: {oldDynamic.toFixed(1)}px</Text>
      <Text>v2.0 BALANCED: {newBalanced.toFixed(1)}px (recomendado ⭐)</Text>
    </View>
  );
}

/**
 * Exemplo 8: Base Orientation
 */
export function BaseOrientationExample() {
  const portraitLowestSize = balanced(48).portraitLowest().toPixels();
  const landscapeHighestSize = balanced(48).landscapeHighest().toPixels();

  return (
    <View>
      <Text>Portrait Lowest: {portraitLowestSize.toFixed(1)}px</Text>
      <Text>Landscape Highest: {landscapeHighestSize.toFixed(1)}px</Text>
    </View>
  );
}

/**
 * Exemplo 9: Diferentes Elementos UI
 */
export function UIElementsShowcase() {
  return (
    <View style={uiStyles.container}>
      {/* Botão - BALANCED */}
      <View style={uiStyles.button}>
        <Text style={uiStyles.buttonText}>Button (BALANCED)</Text>
      </View>

      {/* Ícone - DEFAULT */}
      <View style={uiStyles.icon} />

      {/* Card - PERCENTAGE */}
      <View style={uiStyles.card}>
        <Text>Card (PERCENTAGE)</Text>
      </View>

      {/* Divisória - NONE */}
      <View style={uiStyles.divider} />

      {/* Spacing - BALANCED */}
      <View style={{ height: smart(24, ElementType.SPACING).toPixels() }} />
    </View>
  );
}

const uiStyles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 20,
  },
  button: {
    paddingVertical: smart(12, ElementType.BUTTON).toPixels(),
    paddingHorizontal: smart(24, ElementType.BUTTON).toPixels(),
    borderRadius: smart(8, ElementType.BUTTON).toPixels(),
    backgroundColor: '#007AFF',
  },
  buttonText: {
    fontSize: smart(16, ElementType.TEXT).toSp(),
    color: '#FFFFFF',
  },
  icon: {
    width: smart(24, ElementType.ICON).toPixels(),
    height: smart(24, ElementType.ICON).toPixels(),
    backgroundColor: '#FF3B30',
    borderRadius: smart(4, ElementType.ICON).toPixels(),
    marginVertical: 20,
  },
  card: {
    width: smart(300, ElementType.CARD).toPixels(),
    height: smart(200, ElementType.CARD).toPixels(),
    padding: 16,
    backgroundColor: '#F2F2F7',
    borderRadius: 12,
  },
  divider: {
    height: smart(1, ElementType.DIVIDER).toPixels(), // Sempre 1px
    backgroundColor: '#E5E5EA',
    marginVertical: 20,
  },
});

export default {
  MultiDeviceApp,
  SmartInferenceExample,
  TVApp,
  GameUI,
  AdvancedConfiguration,
  StrategyComparison,
  MigrationExample,
  BaseOrientationExample,
  UIElementsShowcase,
};

