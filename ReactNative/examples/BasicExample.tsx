/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-27
 *
 * Library: AppDimens React Native - Basic Example
 *
 * Description:
 * Basic example demonstrating AppDimens usage in React Native.
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

import React from 'react';
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  TouchableOpacity,
  SafeAreaView,
} from 'react-native';
import {useAppDimens, AppDimensProvider} from 'appdimens-react-native';

function BasicExample() {
  const {fx, dy, percentage, deviceInfo, screenDimensions} = useAppDimens();

  return (
    <SafeAreaView style={styles.container}>
      <ScrollView contentContainerStyle={styles.scrollContent}>
        {/* Header */}
        <View style={styles.header}>
          <Text style={styles.title}>AppDimens React Native</Text>
          <Text style={styles.subtitle}>Responsive design made simple</Text>
        </View>

        {/* Device Info */}
        <View style={styles.deviceInfo}>
          <Text style={styles.sectionTitle}>Device Information</Text>
          <Text style={styles.infoText}>Type: {deviceInfo.type}</Text>
          <Text style={styles.infoText}>Platform: {deviceInfo.platform}</Text>
          <Text style={styles.infoText}>
            Orientation: {deviceInfo.orientation}
          </Text>
          <Text style={styles.infoText}>
            Screen: {Math.round(screenDimensions.width)} ×{' '}
            {Math.round(screenDimensions.height)}
          </Text>
        </View>

        {/* Responsive Cards */}
        <View style={styles.cardsSection}>
          <Text style={styles.sectionTitle}>Responsive Cards</Text>

          {/* Fixed Card */}
          <View style={styles.card}>
            <Text style={styles.cardTitle}>Fixed Scaling</Text>
            <Text style={styles.cardDescription}>
              This card uses fixed scaling for consistent proportions
            </Text>
            <View style={styles.cardFooter}>
              <Text style={styles.cardFooterText}>
                Padding: {Math.round(fx(16).toPixels())}px
              </Text>
              <Text style={styles.cardFooterText}>
                Border Radius: {Math.round(fx(12).toPixels())}px
              </Text>
            </View>
          </View>

          {/* Dynamic Card */}
          <View style={styles.dynamicCard}>
            <Text style={styles.cardTitle}>Dynamic Scaling</Text>
            <Text style={styles.cardDescription}>
              This card uses dynamic scaling for proportional sizing
            </Text>
            <View style={styles.cardFooter}>
              <Text style={styles.cardFooterText}>
                Width: {Math.round(dy(300).toPixels())}px
              </Text>
              <Text style={styles.cardFooterText}>
                Height: {Math.round(dy(200).toPixels())}px
              </Text>
            </View>
          </View>

          {/* Percentage Card */}
          <View style={styles.percentageCard}>
            <Text style={styles.cardTitle}>Percentage Scaling</Text>
            <Text style={styles.cardDescription}>
              This card uses percentage-based scaling
            </Text>
            <View style={styles.cardFooter}>
              <Text style={styles.cardFooterText}>
                Width: {Math.round(percentage(0.8))}px (80%)
              </Text>
            </View>
          </View>
        </View>

        {/* Interactive Elements */}
        <View style={styles.interactiveSection}>
          <Text style={styles.sectionTitle}>Interactive Elements</Text>

          {/* Responsive Buttons */}
          <View style={styles.buttonRow}>
            <TouchableOpacity style={styles.primaryButton}>
              <Text style={styles.buttonText}>Primary</Text>
            </TouchableOpacity>

            <TouchableOpacity style={styles.secondaryButton}>
              <Text style={styles.secondaryButtonText}>Secondary</Text>
            </TouchableOpacity>
          </View>

          {/* Responsive Input Field */}
          <View style={styles.inputContainer}>
            <Text style={styles.inputLabel}>Responsive Input</Text>
            <View style={styles.inputField} />
          </View>
        </View>

        {/* Physical Units Demo */}
        <View style={styles.physicalUnitsSection}>
          <Text style={styles.sectionTitle}>Physical Units</Text>
          <View style={styles.physicalUnitsContainer}>
            <View
              style={[
                styles.physicalUnit,
                {width: fx(5).mm(), height: fx(20).toPixels()},
              ]}>
              <Text style={styles.physicalUnitText}>5mm</Text>
            </View>
            <View
              style={[
                styles.physicalUnit,
                {width: fx(2).cm(), height: fx(20).toPixels()},
              ]}>
              <Text style={styles.physicalUnitText}>2cm</Text>
            </View>
            <View
              style={[
                styles.physicalUnit,
                {width: fx(1).inch(), height: fx(20).toPixels()},
              ]}>
              <Text style={styles.physicalUnitText}>1in</Text>
            </View>
          </View>
        </View>
      </ScrollView>
    </SafeAreaView>
  );
}

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
    marginBottom: 24,
    paddingVertical: 16,
  },
  title: {
    fontSize: 28,
    fontWeight: 'bold',
    color: '#333333',
    textAlign: 'center',
    marginBottom: 8,
  },
  subtitle: {
    fontSize: 16,
    color: '#666666',
    textAlign: 'center',
  },
  deviceInfo: {
    backgroundColor: '#FFFFFF',
    padding: 16,
    borderRadius: 12,
    marginBottom: 24,
    shadowColor: '#000',
    shadowOffset: {width: 0, height: 2},
    shadowOpacity: 0.1,
    shadowRadius: 4,
    elevation: 3,
  },
  sectionTitle: {
    fontSize: 18,
    fontWeight: 'bold',
    color: '#333333',
    marginBottom: 12,
  },
  infoText: {
    fontSize: 14,
    color: '#666666',
    marginBottom: 4,
  },
  cardsSection: {
    marginBottom: 24,
  },
  card: {
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
  dynamicCard: {
    backgroundColor: '#E3F2FD',
    padding: 16,
    borderRadius: 12,
    marginBottom: 16,
    shadowColor: '#000',
    shadowOffset: {width: 0, height: 2},
    shadowOpacity: 0.1,
    shadowRadius: 4,
    elevation: 3,
  },
  percentageCard: {
    backgroundColor: '#F3E5F5',
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
    fontSize: 16,
    fontWeight: 'bold',
    color: '#333333',
    marginBottom: 8,
  },
  cardDescription: {
    fontSize: 14,
    color: '#666666',
    marginBottom: 12,
    lineHeight: 20,
  },
  cardFooter: {
    flexDirection: 'row',
    justifyContent: 'space-between',
  },
  cardFooterText: {
    fontSize: 12,
    color: '#999999',
  },
  interactiveSection: {
    marginBottom: 24,
  },
  buttonRow: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    marginBottom: 16,
  },
  primaryButton: {
    backgroundColor: '#007AFF',
    paddingHorizontal: 24,
    paddingVertical: 12,
    borderRadius: 8,
    flex: 0.48,
  },
  secondaryButton: {
    backgroundColor: 'transparent',
    borderWidth: 1,
    borderColor: '#007AFF',
    paddingHorizontal: 24,
    paddingVertical: 12,
    borderRadius: 8,
    flex: 0.48,
  },
  buttonText: {
    color: '#FFFFFF',
    fontSize: 16,
    fontWeight: '600',
    textAlign: 'center',
  },
  secondaryButtonText: {
    color: '#007AFF',
    fontSize: 16,
    fontWeight: '600',
    textAlign: 'center',
  },
  inputContainer: {
    marginBottom: 16,
  },
  inputLabel: {
    fontSize: 14,
    color: '#333333',
    marginBottom: 8,
  },
  inputField: {
    backgroundColor: '#FFFFFF',
    borderWidth: 1,
    borderColor: '#DDDDDD',
    borderRadius: 8,
    height: 44,
  },
  physicalUnitsSection: {
    marginBottom: 24,
  },
  physicalUnitsContainer: {
    flexDirection: 'row',
    justifyContent: 'space-around',
    alignItems: 'center',
  },
  physicalUnit: {
    backgroundColor: '#FF6B6B',
    justifyContent: 'center',
    alignItems: 'center',
    borderRadius: 4,
  },
  physicalUnitText: {
    color: '#FFFFFF',
    fontSize: 12,
    fontWeight: 'bold',
  },
});

// App wrapper with provider
export default function App() {
  return (
    <AppDimensProvider>
      <BasicExample />
    </AppDimensProvider>
  );
}
