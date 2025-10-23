/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-15
 *
 * Library: AppDimens Flutter - Example App
 *
 * Description:
 * Example Flutter app demonstrating the usage of AppDimens plugin.
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

import 'package:flutter/material.dart';
import 'package:appdimens/appdimens.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return AppDimensApp(
      config: const AppDimensConfig(
        aspectRatioEnabled: true,
        cacheEnabled: true,
        ignoreMultiWindowAdjustment: false,
      ),
      child: MaterialApp(
        title: 'AppDimens Flutter Example',
        theme: ThemeData(
          primarySwatch: Colors.blue,
        ),
        home: const MyHomePage(title: 'AppDimens Flutter Example'),
      ),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({Key? key, required this.title}) : super(key: key);

  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  int _counter = 0;

  void _incrementCounter() {
    setState(() {
      _counter++;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            // Example 1: Basic usage with extensions
            Container(
              width: 100.fx.calculate(context),
              height: 100.fx.calculate(context),
              decoration: BoxDecoration(
                color: Colors.blue,
                borderRadius: BorderRadius.circular(16.fx.calculate(context)),
              ),
              child: Center(
                child: Text(
                  'Fixed Box',
                  style: TextStyle(
                    color: Colors.white,
                    fontSize: 16.fx.calculate(context),
                  ),
                ),
              ),
            ),
            
            SizedBox(height: 20.fx.calculate(context)),
            
            // Example 2: Dynamic scaling
            Container(
              width: 200.dy.calculate(context),
              height: 100.dy.calculate(context),
              decoration: BoxDecoration(
                color: Colors.green,
                borderRadius: BorderRadius.circular(8.dy.calculate(context)),
              ),
              child: Center(
                child: Text(
                  'Dynamic Box',
                  style: TextStyle(
                    color: Colors.white,
                    fontSize: 18.dy.calculate(context),
                  ),
                ),
              ),
            ),
            
            SizedBox(height: 20.fx.calculate(context)),
            
            // Example 3: Custom values based on device type
            Container(
              width: AppDimens.fixed(150)
                  .deviceType(DeviceType.tablet, 200)
                  .deviceType(DeviceType.tv, 300)
                  .calculate(context),
              height: AppDimens.dynamic(80)
                  .deviceType(DeviceType.tablet, 120)
                  .deviceType(DeviceType.tv, 150)
                  .calculate(context),
              decoration: BoxDecoration(
                color: Colors.orange,
                borderRadius: BorderRadius.circular(12.fx.calculate(context)),
              ),
              child: Center(
                child: Text(
                  'Custom Box',
                  style: TextStyle(
                    color: Colors.white,
                    fontSize: AppDimens.fixed(16)
                        .deviceType(DeviceType.tablet, 20)
                        .deviceType(DeviceType.tv, 24)
                        .calculate(context),
                  ),
                ),
              ),
            ),
            
            SizedBox(height: 20.fx.calculate(context)),
            
            // Example 4: Physical units
            Container(
              width: AppDimensPhysicalUnits.toPxFromMm(50, context),
              height: AppDimensPhysicalUnits.toPxFromMm(25, context),
              decoration: BoxDecoration(
                color: Colors.purple,
                borderRadius: BorderRadius.circular(AppDimensPhysicalUnits.toPxFromMm(5, context)),
              ),
              child: Center(
                child: Text(
                  '50mm x 25mm',
                  style: TextStyle(
                    color: Colors.white,
                    fontSize: AppDimensPhysicalUnits.toSpFromMm(5, context),
                  ),
                ),
              ),
            ),
            
            SizedBox(height: 20.fx.calculate(context)),
            
            // Example 5: Counter with responsive text
            Text(
              'You have pushed the button this many times:',
              style: TextStyle(fontSize: 16.fx.calculate(context)),
            ),
            Text(
              '$_counter',
              style: TextStyle(
                fontSize: 48.fx.calculate(context),
                fontWeight: FontWeight.bold,
              ),
            ),
            
            SizedBox(height: 20.fx.calculate(context)),
            
            // Example 6: Responsive grid
            Container(
              width: 300.dy.calculate(context),
              height: 200.dy.calculate(context),
              child: GridView.builder(
                gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
                  crossAxisCount: 3,
                  crossAxisSpacing: 8.fx.calculate(context),
                  mainAxisSpacing: 8.fx.calculate(context),
                ),
                itemCount: 9,
                itemBuilder: (context, index) {
                  return Container(
                    decoration: BoxDecoration(
                      color: Colors.blue.withOpacity(0.3),
                      borderRadius: BorderRadius.circular(4.fx.calculate(context)),
                    ),
                    child: Center(
                      child: Text(
                        '${index + 1}',
                        style: TextStyle(
                          fontSize: 20.fx.calculate(context),
                          fontWeight: FontWeight.bold,
                        ),
                      ),
                    ),
                  );
                },
              ),
            ),
          ],
        ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: _incrementCounter,
        tooltip: 'Increment',
        child: Icon(Icons.add),
      ),
    );
  }
}
