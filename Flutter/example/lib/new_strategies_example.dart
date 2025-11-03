/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-11-02
 *
 * Library: AppDimens Flutter v2.0.0 - New Scaling Strategies Example
 *
 * Description:
 * Example demonstrating the new 13 scaling strategies introduced in v2.0.0
 *
 * Licensed under the Apache License, Version 2.0
 */

import 'package:flutter/material.dart';
import 'package:appdimens/appdimens.dart';

enum ScalingStrategy {
  balanced,
  percentage,
  defaultStrategy,
  logarithmic,
  power,
  fluid,
  interpolated,
  diagonal,
  perimeter,
  fit,
  fill,
  autosize,
  none,
}

class StrategyInfo {
  final String name;
  final String description;
  final String bestFor;

  const StrategyInfo({
    required this.name,
    required this.description,
    required this.bestFor,
  });
}

const Map<ScalingStrategy, StrategyInfo> strategyInfoMap = {
  ScalingStrategy.balanced: StrategyInfo(
    name: 'BALANCED ⭐',
    description: 'Linear on phones (<480), logarithmic on tablets (≥480). RECOMMENDED for most apps.',
    bestFor: 'Multi-device apps, buttons, spacing',
  ),
  ScalingStrategy.percentage: StrategyInfo(
    name: 'PERCENTAGE',
    description: '100% proportional scaling (old Dynamic)',
    bestFor: 'Containers, fluid layouts, images',
  ),
  ScalingStrategy.defaultStrategy: StrategyInfo(
    name: 'DEFAULT',
    description: '~97% linear + AR adjustment (old Fixed)',
    bestFor: 'Phone-only apps, icons, backward compatibility',
  ),
  ScalingStrategy.logarithmic: StrategyInfo(
    name: 'LOGARITHMIC',
    description: 'Pure logarithmic (Weber-Fechner Law). Maximum control.',
    bestFor: 'TVs, very large tablets',
  ),
  ScalingStrategy.power: StrategyInfo(
    name: 'POWER',
    description: "Stevens' Power Law with configurable exponent",
    bestFor: 'General purpose, configurable apps',
  ),
  ScalingStrategy.fluid: StrategyInfo(
    name: 'FLUID',
    description: 'CSS clamp-like with min/max boundaries',
    bestFor: 'Typography, bounded spacing',
  ),
  ScalingStrategy.interpolated: StrategyInfo(
    name: 'INTERPOLATED',
    description: '50% moderated linear growth',
    bestFor: 'Medium screens, balanced approach',
  ),
  ScalingStrategy.diagonal: StrategyInfo(
    name: 'DIAGONAL',
    description: 'Scale based on screen diagonal',
    bestFor: 'Size-aware components',
  ),
  ScalingStrategy.perimeter: StrategyInfo(
    name: 'PERIMETER',
    description: 'Scale based on perimeter (W+H)',
    bestFor: 'Peripheral UI elements',
  ),
  ScalingStrategy.fit: StrategyInfo(
    name: 'FIT',
    description: 'Letterbox scaling (min ratio) - Game fit',
    bestFor: 'Game UI, letterbox content',
  ),
  ScalingStrategy.fill: StrategyInfo(
    name: 'FILL',
    description: 'Cover scaling (max ratio) - Game fill',
    bestFor: 'Game UI, cover content',
  ),
  ScalingStrategy.autosize: StrategyInfo(
    name: 'AUTOSIZE',
    description: 'Auto-adjust based on component size',
    bestFor: 'Adaptive components',
  ),
  ScalingStrategy.none: StrategyInfo(
    name: 'NONE',
    description: 'No scaling (constant size)',
    bestFor: 'Fixed-size elements',
  ),
};

class NewStrategiesExample extends StatefulWidget {
  const NewStrategiesExample({Key? key}) : super(key: key);

  @override
  _NewStrategiesExampleState createState() => _NewStrategiesExampleState();
}

class _NewStrategiesExampleState extends State<NewStrategiesExample> {
  ScalingStrategy selectedStrategy = ScalingStrategy.balanced;

  double getStrategySize(BuildContext context, double base, ScalingStrategy strategy) {
    // Note: Flutter v1.1.0 currently only supports fixed() and dynamic()
    // New strategies will be implemented in v2.0.0
    switch (strategy) {
      case ScalingStrategy.balanced:
      case ScalingStrategy.defaultStrategy:
      case ScalingStrategy.logarithmic:
      case ScalingStrategy.power:
      case ScalingStrategy.interpolated:
      case ScalingStrategy.diagonal:
      case ScalingStrategy.perimeter:
      case ScalingStrategy.fit:
      case ScalingStrategy.fill:
      case ScalingStrategy.autosize:
        // Currently mapped to fixed() - will be properly implemented in v2.0.0
        return AppDimens.fixed(base).calculate(context);
      case ScalingStrategy.percentage:
        return AppDimens.dynamic(base).calculate(context);
      case ScalingStrategy.fluid:
        return AppDimensFluid(base * 0.8, base * 1.2).calculate(context);
      case ScalingStrategy.none:
        return base;
    }
  }

  String getCodeExample(ScalingStrategy strategy) {
    // Current API (v1.1.0) - New strategies coming in v2.0.0
    final examples = {
      ScalingStrategy.balanced: 'AppDimens.fixed(300).calculate(context) // v2.0.0: .balanced()',
      ScalingStrategy.percentage: 'AppDimens.dynamic(300).calculate(context)',
      ScalingStrategy.defaultStrategy: 'AppDimens.fixed(300).calculate(context)',
      ScalingStrategy.logarithmic: 'AppDimens.fixed(300).calculate(context) // v2.0.0: .logarithmic()',
      ScalingStrategy.power: 'AppDimens.fixed(300).calculate(context) // v2.0.0: .power()',
      ScalingStrategy.fluid: 'AppDimensFluid(240, 360).calculate(context)',
      ScalingStrategy.interpolated: 'AppDimens.fixed(300).calculate(context) // v2.0.0: .interpolated()',
      ScalingStrategy.diagonal: 'AppDimens.fixed(300).calculate(context) // v2.0.0: .diagonal()',
      ScalingStrategy.perimeter: 'AppDimens.fixed(300).calculate(context) // v2.0.0: .perimeter()',
      ScalingStrategy.fit: 'AppDimens.fixed(300).calculate(context) // v2.0.0: .fit()',
      ScalingStrategy.fill: 'AppDimens.fixed(300).calculate(context) // v2.0.0: .fill()',
      ScalingStrategy.autosize: 'AppDimens.fixed(300).calculate(context) // v2.0.0: .autosize()',
      ScalingStrategy.none: '300.0 // no scaling',
    };
    return examples[strategy] ?? '';
  }

  String getExtensionExample(ScalingStrategy strategy) {
    // Current API (v1.1.0) - New strategies coming in v2.0.0
    final examples = {
      ScalingStrategy.balanced: '300.fx.calculate(context) // v2.0.0: .balanced()',
      ScalingStrategy.percentage: '300.dy.calculate(context)',
      ScalingStrategy.defaultStrategy: '300.fx.calculate(context)',
      ScalingStrategy.logarithmic: '300.fx.calculate(context) // v2.0.0: .logarithmic()',
      ScalingStrategy.power: '300.fx.calculate(context) // v2.0.0: .power()',
      ScalingStrategy.fluid: 'AppDimensFluid(240, 360).calculate(context)',
      ScalingStrategy.interpolated: '300.fx.calculate(context) // v2.0.0: .interpolated()',
      ScalingStrategy.diagonal: '300.fx.calculate(context) // v2.0.0: .diagonal()',
      ScalingStrategy.perimeter: '300.fx.calculate(context) // v2.0.0: .perimeter()',
      ScalingStrategy.fit: '300.fx.calculate(context) // v2.0.0: .fit()',
      ScalingStrategy.fill: '300.fx.calculate(context) // v2.0.0: .fill()',
      ScalingStrategy.autosize: '300.fx.calculate(context) // v2.0.0: .autosize()',
      ScalingStrategy.none: '300.0 // no scaling',
    };
    return examples[strategy] ?? '';
  }

  @override
  Widget build(BuildContext context) {
    final currentInfo = strategyInfoMap[selectedStrategy]!;
    final screenWidth = MediaQuery.of(context).size.width;
    final screenHeight = MediaQuery.of(context).size.height;

    return Scaffold(
      appBar: AppBar(
        title: Text('New Strategies v2.0'),
      ),
      body: SingleChildScrollView(
        padding: EdgeInsets.all(AppDimens.fixed(16).calculate(context)),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            // Header
            Center(
              child: Column(
                children: [
                  Text(
                    'AppDimens v2.0.0',
                    style: TextStyle(
                      fontSize: AppDimens.fixed(28).calculate(context),
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                  SizedBox(height: AppDimens.fixed(8).calculate(context)),
                  Text(
                    '13 Scaling Strategies',
                    style: TextStyle(
                      fontSize: AppDimens.fixed(16).calculate(context),
                      color: Colors.grey[600],
                    ),
                  ),
                ],
              ),
            ),
            
            SizedBox(height: AppDimens.fixed(20).calculate(context)),

            // Device Info Card
            Card(
              elevation: 2,
              child: Padding(
                padding: EdgeInsets.all(AppDimens.fixed(16).calculate(context)),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text(
                      'Device Information',
                      style: TextStyle(
                        fontSize: AppDimens.fixed(18).calculate(context),
                        fontWeight: FontWeight.bold,
                      ),
                    ),
                    SizedBox(height: AppDimens.fixed(8).calculate(context)),
                    Text('Screen: ${screenWidth.toInt()} × ${screenHeight.toInt()}'),
                    Text('Density: ${MediaQuery.of(context).devicePixelRatio}'),
                  ],
                ),
              ),
            ),

            SizedBox(height: AppDimens.fixed(16).calculate(context)),

            // About Strategies Card
            Card(
              elevation: 2,
              color: Colors.orange[50],
              child: Padding(
                padding: EdgeInsets.all(AppDimens.fixed(16).calculate(context)),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text(
                      '⚠️ Flutter Version Note',
                      style: TextStyle(
                        fontSize: AppDimens.fixed(18).calculate(context),
                        fontWeight: FontWeight.bold,
                        color: Colors.orange[900],
                      ),
                    ),
                    SizedBox(height: AppDimens.fixed(8).calculate(context)),
                    Text(
                      'Flutter v1.1.0 currently supports:',
                      style: TextStyle(fontWeight: FontWeight.w600),
                    ),
                    SizedBox(height: AppDimens.fixed(8).calculate(context)),
                    _buildBulletPoint('• fixed() - Logarithmic scaling'),
                    _buildBulletPoint('• dynamic() - Proportional scaling'),
                    _buildBulletPoint('• fluid() - Min/max boundaries'),
                    SizedBox(height: AppDimens.fixed(12).calculate(context)),
                    Text(
                      'Version 2.0.0 (coming soon) will add:',
                      style: TextStyle(fontWeight: FontWeight.w600),
                    ),
                    SizedBox(height: AppDimens.fixed(8).calculate(context)),
                    _buildBulletPoint('• BALANCED ⭐ strategy'),
                    _buildBulletPoint('• 10 additional strategies'),
                    _buildBulletPoint('• Smart API auto-inference'),
                    SizedBox(height: AppDimens.fixed(12).calculate(context)),
                    Container(
                      padding: EdgeInsets.all(AppDimens.fixed(8).calculate(context)),
                      color: Colors.orange[100],
                      child: Text(
                        'This demo shows the planned API. Currently, new strategies map to fixed() as a preview.',
                        style: TextStyle(fontSize: 12, fontStyle: FontStyle.italic),
                      ),
                    ),
                  ],
                ),
              ),
            ),

            SizedBox(height: AppDimens.fixed(16).calculate(context)),

            // Strategy Selector
            Text(
              'Select Strategy',
              style: TextStyle(
                fontSize: AppDimens.fixed(16).calculate(context),
                fontWeight: FontWeight.w600,
              ),
            ),
            SizedBox(height: AppDimens.fixed(12).calculate(context)),
            SingleChildScrollView(
              scrollDirection: Axis.horizontal,
              child: Row(
                children: ScalingStrategy.values.map((strategy) {
                  final info = strategyInfoMap[strategy]!;
                  final isSelected = selectedStrategy == strategy;
                  return Padding(
                    padding: EdgeInsets.only(right: 8),
                    child: ChoiceChip(
                      label: Text(info.name),
                      selected: isSelected,
                      onSelected: (selected) {
                        if (selected) {
                          setState(() {
                            selectedStrategy = strategy;
                          });
                        }
                      },
                    ),
                  );
                }).toList(),
              ),
            ),

            SizedBox(height: AppDimens.fixed(16).calculate(context)),

            // Strategy Details Card
            Card(
              elevation: 4,
              color: Colors.lightBlue[50],
              child: Padding(
                padding: EdgeInsets.all(AppDimens.fixed(16).calculate(context)),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text(
                      currentInfo.name,
                      style: TextStyle(
                        fontSize: AppDimens.fixed(20).calculate(context),
                        fontWeight: FontWeight.bold,
                        color: Colors.blue[900],
                      ),
                    ),
                    SizedBox(height: AppDimens.fixed(8).calculate(context)),
                    Text(currentInfo.description),
                    SizedBox(height: AppDimens.fixed(12).calculate(context)),
                    Text(
                      'Best for:',
                      style: TextStyle(fontWeight: FontWeight.w600),
                    ),
                    Text(currentInfo.bestFor),
                  ],
                ),
              ),
            ),

            SizedBox(height: AppDimens.fixed(16).calculate(context)),

            // Visual Comparison
            Card(
              elevation: 2,
              child: Padding(
                padding: EdgeInsets.all(AppDimens.fixed(16).calculate(context)),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text(
                      'Visual Comparison',
                      style: TextStyle(
                        fontSize: AppDimens.fixed(18).calculate(context),
                        fontWeight: FontWeight.bold,
                      ),
                    ),
                    SizedBox(height: AppDimens.fixed(12).calculate(context)),
                    Row(
                      mainAxisAlignment: MainAxisAlignment.spaceAround,
                      children: [
                        _buildDemoBox(context, 48),
                        _buildDemoBox(context, 64),
                        _buildDemoBox(context, 96),
                      ],
                    ),
                  ],
                ),
              ),
            ),

            SizedBox(height: AppDimens.fixed(16).calculate(context)),

            // Code Examples
            Card(
              elevation: 2,
              color: Colors.grey[100],
              child: Padding(
                padding: EdgeInsets.all(AppDimens.fixed(16).calculate(context)),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text(
                      'Code Examples',
                      style: TextStyle(
                        fontSize: AppDimens.fixed(18).calculate(context),
                        fontWeight: FontWeight.bold,
                      ),
                    ),
                    SizedBox(height: AppDimens.fixed(12).calculate(context)),
                    Text('Basic usage:'),
                    SizedBox(height: AppDimens.fixed(8).calculate(context)),
                    Container(
                      padding: EdgeInsets.all(8),
                      color: Colors.grey[200],
                      child: Text(
                        getCodeExample(selectedStrategy),
                        style: TextStyle(
                          fontFamily: 'monospace',
                          fontSize: 11,
                          color: Colors.blue[900],
                        ),
                      ),
                    ),
                    SizedBox(height: AppDimens.fixed(12).calculate(context)),
                    Text('Extension usage:'),
                    SizedBox(height: AppDimens.fixed(8).calculate(context)),
                    Container(
                      padding: EdgeInsets.all(8),
                      color: Colors.grey[200],
                      child: Text(
                        getExtensionExample(selectedStrategy),
                        style: TextStyle(
                          fontFamily: 'monospace',
                          fontSize: 11,
                          color: Colors.blue[900],
                        ),
                      ),
                    ),
                  ],
                ),
              ),
            ),

            SizedBox(height: AppDimens.fixed(16).calculate(context)),

            // Smart API Demo (Coming in v2.0.0)
            Card(
              elevation: 2,
              color: Colors.purple[50],
              child: Padding(
                padding: EdgeInsets.all(AppDimens.fixed(16).calculate(context)),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text(
                      '🧠 Smart API (Coming in v2.0.0)',
                      style: TextStyle(
                        fontSize: AppDimens.fixed(18).calculate(context),
                        fontWeight: FontWeight.bold,
                      ),
                    ),
                    SizedBox(height: AppDimens.fixed(8).calculate(context)),
                    Text('The Smart API will automatically select the best strategy:'),
                    SizedBox(height: AppDimens.fixed(12).calculate(context)),
                    _buildSmartExample('Button', 'AppDimens.smart(48).forElement(ElementType.button)', 'BALANCED'),
                    _buildSmartExample('Container', 'AppDimens.smart(300).forElement(ElementType.container)', 'PERCENTAGE'),
                    _buildSmartExample('Text', 'AppDimens.smart(16).forElement(ElementType.text)', 'BALANCED'),
                    _buildSmartExample('Icon', 'AppDimens.smart(24).forElement(ElementType.icon)', 'DEFAULT'),
                    SizedBox(height: AppDimens.fixed(8).calculate(context)),
                    Container(
                      padding: EdgeInsets.all(AppDimens.fixed(8).calculate(context)),
                      color: Colors.purple[100],
                      child: Text(
                        'Smart API will be available in Flutter v2.0.0',
                        style: TextStyle(fontSize: 11, fontStyle: FontStyle.italic),
                      ),
                    ),
                  ],
                ),
              ),
            ),

            SizedBox(height: AppDimens.fixed(16).calculate(context)),

            // Migration Note
            Card(
              elevation: 2,
              color: Colors.green[50],
              child: Padding(
                padding: EdgeInsets.all(AppDimens.fixed(16).calculate(context)),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text(
                      '📝 Migration Note',
                      style: TextStyle(
                        fontSize: AppDimens.fixed(16).calculate(context),
                        fontWeight: FontWeight.bold,
                      ),
                    ),
                    SizedBox(height: AppDimens.fixed(8).calculate(context)),
                    Text('Old API still works (deprecated):'),
                    _buildCodeLine('• fx() → defaultScaling()'),
                    _buildCodeLine('• dy() → percentage()'),
                    SizedBox(height: AppDimens.fixed(8).calculate(context)),
                    Text('New recommended API:'),
                    _buildCodeLine('• balanced() ⭐ (most cases)'),
                    _buildCodeLine('• smart() (auto-inference)'),
                  ],
                ),
              ),
            ),

            SizedBox(height: AppDimens.fixed(20).calculate(context)),
          ],
        ),
      ),
    );
  }

  Widget _buildBulletPoint(String text) {
    return Padding(
      padding: EdgeInsets.only(left: 8, top: 4),
      child: Row(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Text('• ', style: TextStyle(fontSize: 16)),
          Expanded(child: Text(text, style: TextStyle(fontSize: 12))),
        ],
      ),
    );
  }

  Widget _buildDemoBox(BuildContext context, double base) {
    final size = getStrategySize(context, base, selectedStrategy);
    return Column(
      children: [
        Container(
          width: size,
          height: size,
          decoration: BoxDecoration(
            color: Colors.blue[200],
            borderRadius: BorderRadius.circular(size * 0.15),
          ),
        ),
        SizedBox(height: 4),
        Text('$base', style: TextStyle(fontSize: 11, color: Colors.grey[600])),
        Text('${size.toInt()}', style: TextStyle(fontSize: 11, fontWeight: FontWeight.bold)),
      ],
    );
  }

  Widget _buildSmartExample(String elementType, String code, String strategy) {
    return Padding(
      padding: EdgeInsets.symmetric(vertical: 6),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              Text(elementType, style: TextStyle(fontWeight: FontWeight.w600)),
              Text('→ $strategy', style: TextStyle(fontSize: 12, color: Colors.orange[900])),
            ],
          ),
          Container(
            margin: EdgeInsets.only(top: 4),
            padding: EdgeInsets.all(8),
            color: Colors.white,
            child: Text(
              code,
              style: TextStyle(
                fontFamily: 'monospace',
                fontSize: 10,
                color: Colors.orange[900],
              ),
            ),
          ),
        ],
      ),
    );
  }

  Widget _buildCodeLine(String text) {
    return Padding(
      padding: EdgeInsets.only(left: 8, top: 2),
      child: Text(
        text,
        style: TextStyle(
          fontFamily: 'monospace',
          fontSize: 12,
          color: Colors.blue[900],
        ),
      ),
    );
  }
}

