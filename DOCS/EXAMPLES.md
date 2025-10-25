---
layout: default
title: "🎨 AppDimens - Practical Examples"
---

# 🎨 AppDimens - Practical Examples

This document provides comprehensive, real-world examples of how to use AppDimens across different platforms and scenarios.

> Languages: [Português (BR)](LANG/pt-BR/EXAMPLES.md) | [Español](LANG/es/EXAMPLES.md) | [हिन्दी](LANG/hi/EXAMPLES.md) | [Русский](LANG/ru/EXAMPLES.md) | [中文](LANG/zh/EXAMPLES.md) | [日本語](LANG/ja/EXAMPLES.md)

## 📋 Table of Contents

1. [Android Examples](#android-examples)
2. [iOS Examples](#ios-examples)
3. [Flutter Examples](#flutter-examples)
4. [React Native Examples](#react-native-examples)
5. [Web Examples](#web-examples)
6. [Game Development Examples](#game-development-examples)
7. [Cross-Platform Patterns](#cross-platform-patterns)
8. [Advanced Use Cases](#advanced-use-cases)
9. [Performance Examples](#performance-examples)
10. [Architecture Examples](#architecture-examples)

---

## 🤖 Android Examples

### 🧩 Jetpack Compose Examples

#### Basic Responsive Card

```kotlin
@Composable
fun ResponsiveCard(
    title: String,
    content: String,
    onActionClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.fxdp)
            .height(200.fxdp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.fxdp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.fxdp)
        ) {
            Text(
                text = title,
                fontSize = 18.fxsp,                    // Fixed font (RECOMMENDED)
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.fxdp)
            )
            
            Text(
                text = content,
                fontSize = 14.fxsp,                    // Fixed font (RECOMMENDED)
                color = Color.Gray,
                modifier = Modifier.weight(1f)
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = onActionClick,
                    modifier = Modifier
                        .width(100.fxdp)               // Fixed width (RECOMMENDED)
                        .height(36.fxdp)
                ) {
                    Text(
                        text = "Action",
                        fontSize = 14.fxsp
                    )
                }
            }
        }
    }
}
```

#### Responsive Grid Layout

```kotlin
@Composable
fun ResponsiveGrid(
    items: List<GridItem>,
    onItemClick: (GridItem) -> Unit
) {
    var spanCount by remember { mutableStateOf(2) }
    
    // Calculate optimal number of columns
    AppDimens.CalculateAvailableItemCount(
        itemSize = 120.dp,
        itemPadding = 8.dp,
        direction = DpQualifier.WIDTH,
        modifier = Modifier.fillMaxWidth(),
        onResult = { count ->
            if (count > 0) spanCount = count
        }
    )
    
    LazyVerticalGrid(
        columns = GridCells.Fixed(spanCount),
        contentPadding = PaddingValues(16.fxdp),
        horizontalArrangement = Arrangement.spacedBy(8.fxdp),
        verticalArrangement = Arrangement.spacedBy(8.fxdp)
    ) {
        items(items) { item ->
            GridItemCard(
                item = item,
                onClick = { onItemClick(item) }
            )
        }
    }
}

@Composable
fun GridItemCard(
    item: GridItem,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .aspectRatio(1f)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.fxdp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.fxdp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = null,
                modifier = Modifier.size(32.fxdp),
                tint = item.color
            )
            
            Spacer(modifier = Modifier.height(8.fxdp))
            
            Text(
                text = item.title,
                fontSize = 12.fxsp,
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
```

#### Conditional Scaling Example

```kotlin
@Composable
fun AdaptiveButton(
    text: String,
    onClick: () -> Unit
) {
    val buttonSize = 80.fixedDp()  // Fixed scaling (RECOMMENDED)
        // Priority 1: Watch with specific width
        .screen(
            uiModeType = UiModeType.WATCH,
            qualifierType = DpQualifier.SMALL_WIDTH,
            qualifierValue = 200,
            customValue = 40.dp
        )
        // Priority 2: Car mode
        .screen(
            type = UiModeType.CAR,
            customValue = 120.dp
        )
        // Priority 3: Large tablets
        .screen(
            type = DpQualifier.SMALL_WIDTH,
            value = 720,
            customValue = 150
        )
    
    Button(
        onClick = onClick,
        modifier = Modifier
            .size(buttonSize.sdp)
            .fxCornerRadius(8)
    ) {
        Text(
            text = text,
            fontSize = 14.fxsp
        )
    }
}
```

### 📄 XML Views Examples

#### Responsive Layout with Data Binding

```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto">
    
    <data>
        <variable
            name="viewModel"
            type="com.example.ViewModel" />
    </data>
    
    <ScrollView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:padding="@dimen/_16sdp">
        
        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="vertical"
            android:padding="@dimen/_8sdp">
            
            <!-- Header with SDP dimensions -->
            <TextView
                android:layout_width="match_parent"
                android:layout_height="@dimen/_48sdp"
                android:text="@{viewModel.title}"
                android:textSize="@dimen/_20ssp"
                android:gravity="center"
                android:background="@color/primary"
                android:textColor="@android:color/white" />
            
            <!-- Content cards with mixed scaling -->
            <androidx.cardview.widget.CardView
                android:layout_width="match_parent"
                android:layout_height="@dimen/_120hdp"
                android:layout_margin="@dimen/_8sdp"
                app:cardCornerRadius="@dimen/_8sdp"
                app:cardElevation="@dimen/_4sdp">
                
                <LinearLayout
                    android:layout_width="match_parent"
                    android:layout_height="match_parent"
                    android:orientation="vertical"
                    android:padding="@dimen/_12sdp">
                    
                    <TextView
                        android:layout_width="match_parent"
                        android:layout_height="wrap_content"
                        android:text="@{viewModel.cardTitle}"
                        android:textSize="@dimen/_16ssp"
                        android:textStyle="bold" />
                    
                    <TextView
                        android:layout_width="match_parent"
                        android:layout_height="0dp"
                        android:layout_weight="1"
                        android:text="@{viewModel.cardContent}"
                        android:textSize="@dimen/_14ssp" />
                    
                    <Button
                        android:layout_width="@dimen/_100sdp"
                        android:layout_height="@dimen/_36sdp"
                        android:text="Action"
                        android:textSize="@dimen/_12ssp"
                        android:onClick="@{() -> viewModel.onActionClick()}" />
                </LinearLayout>
            </androidx.cardview.widget.CardView>
        </LinearLayout>
    </ScrollView>
</layout>
```

### 🎮 Games Module Example

```kotlin
class GameActivity : Activity() {
    private lateinit var appDimensGames: AppDimensGames
    private lateinit var gameRenderer: GameRenderer
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Initialize AppDimens Games
        appDimensGames = AppDimensGames.getInstance()
        appDimensGames.initialize(this)
        
        // Set up game renderer
        gameRenderer = GameRenderer(this, appDimensGames)
        setContentView(gameRenderer)
    }
    
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        appDimensGames.updateScreenConfiguration(newConfig)
    }
}

class GameRenderer : GLSurfaceView.Renderer {
    private lateinit var appDimensGames: AppDimensGames
    
    override fun onDrawFrame(gl: GL10?) {
        // Calculate responsive dimensions for game elements
        val buttonSize = appDimensGames.calculateButtonSize(48.0f)
        val textSize = appDimensGames.calculateTextSize(16.0f)
        val playerSize = appDimensGames.calculatePlayerSize(64.0f)
        val enemySize = appDimensGames.calculateEnemySize(32.0f)
        
        // Use different scaling types
        val dynamicDimension = appDimensGames.calculateDimension(100.0f, GameDimensionType.DYNAMIC)
        val fixedDimension = appDimensGames.calculateDimension(100.0f, GameDimensionType.FIXED)
        val gameWorldDimension = appDimensGames.calculateDimension(100.0f, GameDimensionType.GAME_WORLD)
        val uiOverlayDimension = appDimensGames.calculateDimension(100.0f, GameDimensionType.UI_OVERLAY)
        
        // Render game elements with calculated dimensions
        renderGameElements(buttonSize, textSize, playerSize, enemySize)
    }
}
```

---

## 🎮 Game Development Examples

### 🤖 Android Game Development

#### Basic Game Setup

```kotlin
class GameActivity : AppCompatActivity() {
    private lateinit var gamesManager: AppDimensGames
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Initialize games manager
        gamesManager = AppDimensGames.getInstance()
        gamesManager.initialize(this)
        
        // Configure performance settings
        gamesManager.configurePerformance(
            GamePerformanceSettings.HIGH_PERFORMANCE
        )
    }
    
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        gamesManager.updateScreenConfiguration()
    }
}
```

#### Game UI Elements

```kotlin
class GameUI {
    private val gamesManager = AppDimensGames.getInstance()
    
    fun createGameButton(context: Context): Button {
        val buttonSize = gamesManager.calculateButtonSize(48f)
        val button = Button(context)
        button.layoutParams = ViewGroup.LayoutParams(
            buttonSize.toInt(), 
            buttonSize.toInt()
        )
        return button
    }
    
    fun createGameText(context: Context): TextView {
        val textSize = gamesManager.calculateTextSize(16f)
        val textView = TextView(context)
        textView.textSize = textSize
        return textView
    }
}
```

#### Vector Operations

```kotlin
class GamePhysics {
    private val gamesManager = AppDimensGames.getInstance()
    
    fun calculateMovement(
        position: GameVector2D, 
        velocity: GameVector2D
    ): GameVector2D {
        val scaledPosition = gamesManager.calculateVector2D(
            position, 
            GameDimensionType.GAME_WORLD
        )
        val scaledVelocity = gamesManager.calculateVector2D(
            velocity, 
            GameDimensionType.GAME_WORLD
        )
        
        return scaledPosition + scaledVelocity
    }
}
```

### 🍎 iOS Game Development

#### Metal Integration

```swift
import Metal
import AppDimensGames

class GameRenderer {
    private let gamesManager = AppDimensGames.shared
    
    func setupMetal(device: MTLDevice, viewport: MTLViewport) {
        gamesManager.initialize(device: device, viewport: viewport)
        gamesManager.configurePerformance(.highPerformance)
    }
    
    func calculateGameDimensions() {
        let buttonSize = gamesManager.uniform(48)      // Uniform scaling
        let playerSize = gamesManager.aspectRatio(64)  // Aspect ratio scaling
        let uiSize = gamesManager.viewport(24)         // Viewport scaling
    }
}
```

#### SwiftUI Game UI

```swift
struct GameView: View {
    @State private var gamesManager = AppDimensGames.shared
    
    var body: some View {
        VStack {
            // Game-specific dimensions
            Text("Score: 1000")
                .font(.system(size: gameUniform(24)))  // Uniform scaling
            
            // Metal viewport dimensions
            MetalGameView()
                .frame(
                    width: gameAspectRatio(320),
                    height: gameAspectRatio(240)
                )
        }
        .withAppDimens()  // Enable AppDimens environment
    }
}
```

---

## 🍎 iOS Examples

### 🧩 SwiftUI Examples

#### Responsive Card with Environment

```swift
struct ResponsiveCard: View {
    let title: String
    let content: String
    let onAction: () -> Void
    
    var body: some View {
        VStack(alignment: .leading, spacing: 12.fxpt) {
            Text(title)
                .font(.fxSystem(size: 18, weight: .semibold))
            
            Text(content)
                .font(.fxSystem(size: 14))
                .foregroundColor(.secondary)
                .lineLimit(3)
            
            HStack {
                Spacer()
                Button("Action", action: onAction)
                    .fxFrame(width: 80, height: 32)
                    .fxCornerRadius(6)
            }
        }
        .fxPadding(16)
        .dyFrame(width: 300)           // Dynamic width
        .fxFrame(height: 200)          // Fixed height
        .background(Color(.systemGray6))
        .fxCornerRadius(12)
        .shadow(color: .black.opacity(0.1), radius: 2, x: 0, y: 1)
    }
}
```

#### Responsive Grid with LazyVGrid

```swift
struct ResponsiveGrid: View {
    let items: [GridItem]
    let onItemTap: (GridItem) -> Void
    
    var body: some View {
        LazyVGrid(columns: [
            GridItem(.flexible(), spacing: 16.fxpt),
            GridItem(.flexible(), spacing: 16.fxpt)
        ], spacing: 16.fxpt) {
            ForEach(items, id: \.id) { item in
                GridItemView(item: item, onTap: { onItemTap(item) })
            }
        }
        .fxPadding(16)
    }
}

struct GridItemView: View {
    let item: GridItem
    let onTap: () -> Void
    
    var body: some View {
        VStack(spacing: 8.fxpt) {
            Image(systemName: item.iconName)
                .font(.fxSystem(size: 24))
                .foregroundColor(item.color)
            
            Text(item.title)
                .font(.fxSystem(size: 12))
                .multilineTextAlignment(.center)
                .lineLimit(2)
        }
        .fxFrame(width: 80, height: 80)
        .background(Color(.systemGray5))
        .fxCornerRadius(8)
        .onTapGesture(perform: onTap)
    }
}
```

#### Advanced Conditional Scaling

```swift
struct AdaptiveButton: View {
    let text: String
    let action: () -> Void
    
    var body: some View {
        let buttonSize = AppDimens.fixed(80)
            .screen(.watch, 40)           // 40pt for Apple Watch
            .screen(.tablet, 120)         // 120pt for iPad
            .aspectRatio(enable: true)    // Enable aspect ratio adjustment
            .toPoints()
        
        Button(text, action: action)
            .fxFrame(width: buttonSize, height: buttonSize)
            .fxCornerRadius(8)
            .font(.fxSystem(size: 14, weight: .medium))
    }
}
```

### 📱 UIKit Examples

#### Responsive View Controller

```swift
class ResponsiveViewController: UIViewController {
    private let containerView = UIView()
    private let titleLabel = UILabel()
    private let contentLabel = UILabel()
    private let actionButton = UIButton(type: .system)
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setupUI()
        setupConstraints()
    }
    
    private func setupUI() {
        // Container
        containerView.backgroundColor = .systemBlue
        containerView.fxCornerRadius(16)
        view.addSubview(containerView)
        
        // Title
        titleLabel.text = "Responsive Title"
        titleLabel.textAlignment = .center
        titleLabel.fxFontSize(20)
        titleLabel.textColor = .white
        containerView.addSubview(titleLabel)
        
        // Content
        contentLabel.text = "This content adapts to different screen sizes"
        contentLabel.textAlignment = .center
        contentLabel.fxFontSize(16)
        contentLabel.textColor = .white
        contentLabel.numberOfLines = 0
        containerView.addSubview(contentLabel)
        
        // Button
        actionButton.setTitle("Action", for: .normal)
        actionButton.fxTitleFontSize(16)
        actionButton.fxCornerRadius(8)
        actionButton.backgroundColor = .white
        actionButton.setTitleColor(.systemBlue, for: .normal)
        containerView.addSubview(actionButton)
    }
    
    private func setupConstraints() {
        [containerView, titleLabel, contentLabel, actionButton].forEach {
            $0.translatesAutoresizingMaskIntoConstraints = false
        }
        
        NSLayoutConstraint.activate([
            // Container - fixed width (RECOMMENDED), fixed height
            containerView.centerXAnchor.constraint(equalTo: view.centerXAnchor),
            containerView.centerYAnchor.constraint(equalTo: view.centerYAnchor),
            containerView.widthAnchor.constraint(equalToConstant: 300.fxpt),
            containerView.heightAnchor.constraint(equalToConstant: 200.fxpt),
            
            // Title
            titleLabel.topAnchor.constraint(equalTo: containerView.topAnchor, constant: 20.fxpt),
            titleLabel.leadingAnchor.constraint(equalTo: containerView.leadingAnchor, constant: 16.fxpt),
            titleLabel.trailingAnchor.constraint(equalTo: containerView.trailingAnchor, constant: -16.fxpt),
            
            // Content
            contentLabel.topAnchor.constraint(equalTo: titleLabel.bottomAnchor, constant: 12.fxpt),
            contentLabel.leadingAnchor.constraint(equalTo: containerView.leadingAnchor, constant: 16.fxpt),
            contentLabel.trailingAnchor.constraint(equalTo: containerView.trailingAnchor, constant: -16.fxpt),
            
            // Button
            actionButton.centerXAnchor.constraint(equalTo: containerView.centerXAnchor),
            actionButton.bottomAnchor.constraint(equalTo: containerView.bottomAnchor, constant: -20.fxpt),
            actionButton.widthAnchor.constraint(equalToConstant: 120.dypt),
            actionButton.heightAnchor.constraint(equalToConstant: 44.fxpt)
        ])
    }
}
```

#### Advanced UIKit Configuration

```swift
class AdvancedViewController: UIViewController {
    override func viewDidLoad() {
        super.viewDidLoad()
        setupAdvancedUI()
    }
    
    private func setupAdvancedUI() {
        // Custom dimensions with device-specific values
        let customDimension = AppDimens.fixed(16)
            .screen(.phone, 14)           // 14pt for phones
            .screen(.tablet, 18)          // 18pt for tablets
            .aspectRatio(enable: true)    // Enable aspect ratio adjustment
            .toPoints()
        
        // Dynamic with custom screen type
        let dynamicDimension = AppDimens.dynamic(100)
            .type(.highest)               // Use highest screen dimension
            .toPoints()
        
        // Apply to UI elements
        let label = UILabel()
        label.font = UIFont.systemFont(ofSize: customDimension)
        label.text = "Custom scaled text"
        
        let view = UIView()
        view.frame = CGRect(x: 0, y: 0, width: dynamicDimension, height: 50.fxpt)
        view.backgroundColor = .systemBlue
        view.fxCornerRadius(8)
        
        view.addSubview(label)
        self.view.addSubview(view)
        
        // Center the view
        view.center = self.view.center
        label.center = view.center
    }
}
```

---

## 🎯 Flutter Examples

### 📱 Basic Responsive Widget

```dart
import 'package:flutter/material.dart';
import 'package:appdimens/appdimens.dart';

class ResponsiveCard extends StatelessWidget {
  final String title;
  final String content;
  final VoidCallback onAction;

  const ResponsiveCard({
    required this.title,
    required this.content,
    required this.onAction,
  });

  @override
  Widget build(BuildContext context) {
    return Container(
      width: 300.fxdp(),          // Fixed width (RECOMMENDED)
      height: 200.fxdp(),         // Fixed height
      padding: EdgeInsets.all(16.fxdp()),
      decoration: BoxDecoration(
        color: Colors.white,
        borderRadius: BorderRadius.circular(12.fxdp()),
        boxShadow: [
          BoxShadow(
            color: Colors.black.withOpacity(0.1),
            blurRadius: 8.fxdp(),
            offset: Offset(0, 2.fxdp()),
          ),
        ],
      ),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Text(
            title,
            style: TextStyle(
              fontSize: 18.fxsp(),          // Fixed font (RECOMMENDED)
              fontWeight: FontWeight.bold,
            ),
          ),
          SizedBox(height: 8.fxdp()),
          Expanded(
            child: Text(
              content,
              style: TextStyle(
                fontSize: 14.fxsp(),        // Fixed font (RECOMMENDED)
                color: Colors.grey[600],
              ),
            ),
          ),
          SizedBox(height: 12.fxdp()),
          Align(
            alignment: Alignment.centerRight,
            child: ElevatedButton(
              onPressed: onAction,
              style: ElevatedButton.styleFrom(
                minimumSize: Size(100.fxdp(), 36.fxdp()),  // Fixed sizes (RECOMMENDED)
                padding: EdgeInsets.symmetric(
                  horizontal: 20.fxdp(),
                  vertical: 8.fxdp(),
                ),
              ),
              child: Text(
                'Action',
                style: TextStyle(fontSize: 14.fxsp()),
              ),
            ),
          ),
        ],
      ),
    );
  }
}
```

### 🌐 Responsive Grid Layout

```dart
class ResponsiveGrid extends StatelessWidget {
  final List<GridItem> items;
  final Function(GridItem) onItemTap;

  const ResponsiveGrid({
    required this.items,
    required this.onItemTap,
  });

  @override
  Widget build(BuildContext context) {
    // Calculate columns based on screen size
    final screenWidth = MediaQuery.of(context).size.width;
    final itemWidth = 120.fxdp();  // Fixed width (RECOMMENDED)
    final columns = (screenWidth / itemWidth).floor().clamp(2, 6);

    return GridView.builder(
      padding: EdgeInsets.all(16.fxdp()),
      gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
        crossAxisCount: columns,
        crossAxisSpacing: 12.fxdp(),
        mainAxisSpacing: 12.fxdp(),
        childAspectRatio: 1.0,
      ),
      itemCount: items.length,
      itemBuilder: (context, index) {
        return GridItemCard(
          item: items[index],
          onTap: () => onItemTap(items[index]),
        );
      },
    );
  }
}

class GridItemCard extends StatelessWidget {
  final GridItem item;
  final VoidCallback onTap;

  const GridItemCard({required this.item, required this.onTap});

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: onTap,
      child: Container(
        padding: EdgeInsets.all(12.fxdp()),
        decoration: BoxDecoration(
          color: Colors.white,
          borderRadius: BorderRadius.circular(8.fxdp()),
          boxShadow: [
            BoxShadow(
              color: Colors.black.withOpacity(0.05),
              blurRadius: 4.fxdp(),
            ),
          ],
        ),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Icon(
              item.icon,
              size: 32.fxdp(),
              color: item.color,
            ),
            SizedBox(height: 8.fxdp()),
            Text(
              item.title,
              style: TextStyle(fontSize: 12.fxsp()),  // Fixed font (RECOMMENDED)
              textAlign: TextAlign.center,
              maxLines: 2,
              overflow: TextOverflow.ellipsis,
            ),
          ],
        ),
      ),
    );
  }
}
```

### 📱 Platform-Adaptive Layout

```dart
class PlatformAdaptiveLayout extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final screenType = AppDimens.getScreenType(context);
    
    return Scaffold(
      appBar: AppBar(
        title: Text(
          'Adaptive Layout',
          style: TextStyle(fontSize: 20.fxsp()),
        ),
        toolbarHeight: 56.fxdp(),
      ),
      body: Padding(
        padding: EdgeInsets.all(16.fxdp()),
        child: screenType == ScreenType.phone
            ? _buildPhoneLayout()
            : screenType == ScreenType.tablet
                ? _buildTabletLayout()
                : _buildDesktopLayout(),
      ),
    );
  }

  Widget _buildPhoneLayout() {
    return ListView(
      children: [
        _buildCard('Card 1'),
        SizedBox(height: 12.fxdp()),
        _buildCard('Card 2'),
        SizedBox(height: 12.fxdp()),
        _buildCard('Card 3'),
      ],
    );
  }

  Widget _buildTabletLayout() {
    return GridView.count(
      crossAxisCount: 2,
      crossAxisSpacing: 16.fxdp(),
      mainAxisSpacing: 16.fxdp(),
      children: [
        _buildCard('Card 1'),
        _buildCard('Card 2'),
        _buildCard('Card 3'),
        _buildCard('Card 4'),
      ],
    );
  }

  Widget _buildDesktopLayout() {
    return Row(
      children: [
        Expanded(child: _buildCard('Card 1')),
        SizedBox(width: 16.fxdp()),
        Expanded(child: _buildCard('Card 2')),
        SizedBox(width: 16.fxdp()),
        Expanded(child: _buildCard('Card 3')),
      ],
    );
  }

  Widget _buildCard(String title) {
    return Container(
      padding: EdgeInsets.all(16.fxdp()),
      decoration: BoxDecoration(
        color: Colors.blue,
        borderRadius: BorderRadius.circular(12.fxdp()),
      ),
      child: Center(
        child: Text(
          title,
          style: TextStyle(
            color: Colors.white,
            fontSize: 18.fxsp(),
            fontWeight: FontWeight.bold,
          ),
        ),
      ),
    );
  }
}
```

---

## ⚛️ React Native Examples

### 📱 Basic Responsive Component

```typescript
import React from 'react';
import { View, Text, TouchableOpacity, StyleSheet } from 'react-native';
import { useAppDimens } from 'react-native-appdimens';

interface ResponsiveCardProps {
  title: string;
  content: string;
  onAction: () => void;
}

function ResponsiveCard({ title, content, onAction }: ResponsiveCardProps) {
  const { fixed, dynamic, fluid } = useAppDimens();
  
  const styles = StyleSheet.create({
    card: {
      width: dynamic(300),
      height: fixed(200),
      padding: fixed(16),
      backgroundColor: '#fff',
      borderRadius: fixed(12),
      shadowColor: '#000',
      shadowOffset: { width: 0, height: 2 },
      shadowOpacity: 0.1,
      shadowRadius: fixed(8),
      elevation: 3,
    },
    title: {
      fontSize: fixed(18),
      fontWeight: 'bold',
      marginBottom: fixed(8),
    },
    content: {
      fontSize: dynamic(14),
      color: '#666',
      flex: 1,
    },
    buttonContainer: {
      alignItems: 'flex-end',
      marginTop: fixed(12),
    },
    button: {
      backgroundColor: '#007AFF',
      paddingHorizontal: fixed(20),
      paddingVertical: fixed(10),
      borderRadius: fixed(6),
      minWidth: dynamic(100),
      minHeight: fixed(36),
    },
    buttonText: {
      color: '#fff',
      fontSize: fixed(14),
      fontWeight: '600',
    },
  });
  
  return (
    <View style={styles.card}>
      <Text style={styles.title}>{title}</Text>
      <Text style={styles.content}>{content}</Text>
      <View style={styles.buttonContainer}>
        <TouchableOpacity style={styles.button} onPress={onAction}>
          <Text style={styles.buttonText}>Action</Text>
        </TouchableOpacity>
      </View>
    </View>
  );
}

export default ResponsiveCard;
```

### 🌐 Responsive Grid with Hooks

```typescript
import React from 'react';
import { View, FlatList, Text, TouchableOpacity, StyleSheet } from 'react-native';
import { useAppDimens, useBreakpoint, useOrientation } from 'react-native-appdimens';

interface GridItem {
  id: string;
  title: string;
  color: string;
}

interface ResponsiveGridProps {
  items: GridItem[];
  onItemPress: (item: GridItem) => void;
}

function ResponsiveGrid({ items, onItemPress }: ResponsiveGridProps) {
  const { fixed, dynamic } = useAppDimens();
  const breakpoint = useBreakpoint();
  const orientation = useOrientation();
  
  // Determine number of columns based on breakpoint
  const numColumns = breakpoint === 'sm' ? 2 : breakpoint === 'md' ? 3 : 4;
  
  const styles = StyleSheet.create({
    container: {
      padding: fixed(16),
    },
    item: {
      flex: 1,
      margin: fixed(8),
      height: dynamic(120),
      borderRadius: fixed(12),
      justifyContent: 'center',
      alignItems: 'center',
      padding: fixed(16),
    },
    title: {
      fontSize: fixed(14),
      fontWeight: '600',
      textAlign: 'center',
      color: '#fff',
    },
  });
  
  const renderItem = ({ item }: { item: GridItem }) => (
    <TouchableOpacity
      style={[styles.item, { backgroundColor: item.color }]}
      onPress={() => onItemPress(item)}
    >
      <Text style={styles.title}>{item.title}</Text>
    </TouchableOpacity>
  );
  
  return (
    <FlatList
      data={items}
      renderItem={renderItem}
      keyExtractor={(item) => item.id}
      numColumns={numColumns}
      contentContainerStyle={styles.container}
      key={`${numColumns}-${orientation}`}
    />
  );
}

export default ResponsiveGrid;
```

### 🎨 Fluid Typography Example

```typescript
import React from 'react';
import { View, Text, StyleSheet } from 'react-native';
import { useAppDimens } from 'react-native-appdimens';

function FluidTypography() {
  const { fluid, fixed } = useAppDimens();
  
  const styles = StyleSheet.create({
    container: {
      padding: fixed(24),
      backgroundColor: '#f5f5f5',
    },
    hero: {
      fontSize: fluid(32, 64),  // Scales smoothly from 32 to 64
      fontWeight: 'bold',
      marginBottom: fixed(16),
    },
    subtitle: {
      fontSize: fluid(18, 24),  // Scales smoothly from 18 to 24
      color: '#666',
      marginBottom: fixed(24),
    },
    body: {
      fontSize: fluid(14, 18),  // Scales smoothly from 14 to 18
      lineHeight: fluid(20, 28),
      color: '#333',
    },
  });
  
  return (
    <View style={styles.container}>
      <Text style={styles.hero}>Fluid Typography</Text>
      <Text style={styles.subtitle}>
        Responsive text that scales smoothly
      </Text>
      <Text style={styles.body}>
        This text uses fluid scaling to adapt seamlessly across different screen
        sizes, providing optimal readability on all devices from small phones to
        large tablets.
      </Text>
    </View>
  );
}

export default FluidTypography;
```

---

## 🌐 Web Examples

### ⚛️ React with Hooks

```tsx
import React from 'react';
import { useWebDimens, useBreakpoint, useViewport } from 'webdimens/react';

function ResponsiveCard() {
  const { fx, dy, fl } = useWebDimens();
  const breakpoint = useBreakpoint();
  const viewport = useViewport();
  
  // Responsive styles using AppDimens
  const cardStyle = {
    width: dy(300),
    height: fx(200),
    padding: fx(16),
    borderRadius: fx(12),
    backgroundColor: '#fff',
    boxShadow: '0 ' + fx(2) + ' ' + fx(8) + ' rgba(0,0,0,0.1)',
  };
  
  const titleStyle = {
    fontSize: fx(18),
    marginBottom: fx(8)
  };
  
  return (
    <div style={cardStyle}>
      <h2 style={titleStyle}>
        Responsive Card
      </h2>
      <p style={{ fontSize: fl(14, 18), color: '#666' }}>
        Current breakpoint: {breakpoint.current}
      </p>
      <p style={{ fontSize: fl(12, 16) }}>
        Viewport: {viewport.width}x{viewport.height}
      </p>
      <button style={{
        marginTop: fx(12),
        padding: `${fx(10)} ${fx(20)}`,
        borderRadius: fx(6),
        border: 'none',
        backgroundColor: '#007AFF',
        color: '#fff',
        fontSize: fx(14),
        cursor: 'pointer',
      }}>
        Action
      </button>
    </div>
  );
}

export default ResponsiveCard;
```

### 🟢 Vue Composition API

```vue
<template>
  <div class="responsive-card" :style="cardStyles">
    <h2 :style="titleStyles">{{ title }}</h2>
    <p :style="contentStyles">{{ content }}</p>
    <p :style="infoStyles">
      Current breakpoint: {{ breakpoint.current }}
    </p>
    <button :style="buttonStyles" @click="handleAction">
      Action
    </button>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue';
import { useWebDimens, useBreakpoint, useViewport } from 'webdimens/vue';

interface Props {
  title: string;
  content: string;
}

const props = defineProps<Props>();
const emit = defineEmits<{
  (e: 'action'): void;
}>();

const { fx, dy, fl } = useWebDimens();
const breakpoint = useBreakpoint();
const viewport = useViewport();

const cardStyles = computed(() => ({
  width: dy(300),
  height: fx(200),
  padding: fx(16),
  borderRadius: fx(12),
  backgroundColor: '#fff',
  boxShadow: '0 ' + fx(2) + ' ' + fx(8) + ' rgba(0,0,0,0.1)',
}));

const titleStyles = computed(() => ({
  fontSize: fx(18),
  marginBottom: fx(8),
  fontWeight: 'bold',
}));

const contentStyles = computed(() => ({
  fontSize: fl(14, 18),
  color: '#666',
  marginBottom: fx(12),
}));

const infoStyles = computed(() => ({
  fontSize: fl(12, 16),
  color: '#999',
}));

const buttonStyles = computed(() => ({
  marginTop: fx(12),
  padding: `${fx(10)} ${fx(20)}`,
  borderRadius: fx(6),
  border: 'none',
  backgroundColor: '#007AFF',
  color: '#fff',
  fontSize: fx(14),
  cursor: 'pointer',
}));

const handleAction = () => {
  emit('action');
};
</script>
```

### 🔶 Angular Component

```typescript
import { Component } from '@angular/core';
import { WebDimensService } from 'webdimens/angular';

@Component({
  selector: 'app-responsive-card',
  template: `
    <div class="card" [ngStyle]="cardStyles">
      <h2 [ngStyle]="titleStyles">{{ title }}</h2>
      <p [ngStyle]="contentStyles">{{ content }}</p>
      <p [ngStyle]="infoStyles">
        Current breakpoint: {{ breakpoint }}
      </p>
      <button [ngStyle]="buttonStyles" (click)="onAction()">
        Action
      </button>
    </div>
  `,
})
export class ResponsiveCardComponent {
  title = 'Responsive Card';
  content = 'This card adapts to all screen sizes';
  breakpoint = '';
  
  cardStyles = {};
  titleStyles = {};
  contentStyles = {};
  infoStyles = {};
  buttonStyles = {};
  
  constructor(private webDimens: WebDimensService) {
    this.webDimens.breakpoint$.subscribe(bp => {
      this.breakpoint = bp.current;
      this.updateStyles();
    });
  }
  
  private updateStyles() {
    const wd = this.webDimens.instance;
    
    this.cardStyles = {
      width: wd.dy(300),
      height: wd.fx(200),
      padding: wd.fx(16),
      borderRadius: wd.fx(12),
      backgroundColor: '#fff',
      boxShadow: '0 ' + wd.fx(2) + ' ' + wd.fx(8) + ' rgba(0,0,0,0.1)',
    };
    
    this.titleStyles = {
      fontSize: wd.fx(18),
      marginBottom: wd.fx(8),
      fontWeight: 'bold',
    };
    
    this.contentStyles = {
      fontSize: wd.fl(14, 18),
      color: '#666',
    };
    
    this.infoStyles = {
      fontSize: wd.fl(12, 16),
      color: '#999',
    };
    
    this.buttonStyles = {
      marginTop: wd.fx(12),
      padding: `${wd.fx(10)} ${wd.fx(20)}`,
      borderRadius: wd.fx(6),
      border: 'none',
      backgroundColor: '#007AFF',
      color: '#fff',
      fontSize: wd.fx(14),
      cursor: 'pointer',
    };
  }
  
  onAction() {
    console.log('Action clicked!');
  }
}
```

### 🎨 Svelte with Stores

```svelte
<script lang="ts">
  import { webDimensStore, breakpointStore, viewportStore } from 'webdimens/svelte';
  
  export let title: string;
  export let content: string;
  
  $: wd = $webDimensStore;
  $: breakpoint = $breakpointStore;
  $: viewport = $viewportStore;
  
  function handleAction() {
    console.log('Action clicked!');
  }
</script>

<div class="card" style="
  width: {wd.dy(300)};
  height: {wd.fx(200)};
  padding: {wd.fx(16)};
  border-radius: {wd.fx(12)};
">
  <h2 style="font-size: {wd.fx(18)}; margin-bottom: {wd.fx(8)};">
    {title}
  </h2>
  <p style="font-size: {wd.fl(14, 18)}; color: #666;">
    {content}
  </p>
  <p style="font-size: {wd.fl(12, 16)}; color: #999;">
    Current breakpoint: {breakpoint.current}
  </p>
  <button 
    style="
      margin-top: {wd.fx(12)};
      padding: {wd.fx(10)} {wd.fx(20)};
      border-radius: {wd.fx(6)};
      font-size: {wd.fx(14)};
    "
    on:click={handleAction}
  >
    Action
  </button>
</div>

<style>
  .card {
    background-color: #fff;
    box-shadow: 0 2px 8px rgba(0,0,0,0.1);
  }
  
  button {
    border: none;
    background-color: #007AFF;
    color: #fff;
    cursor: pointer;
  }
  
  button:hover {
    background-color: #0056b3;
  }
</style>
```

---

## 🔄 Cross-Platform Patterns

### Common UI Patterns

#### Card Component Pattern

**Android (Compose):**
```kotlin
@Composable
fun Card(
    title: String,
    content: String,
    onAction: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.fxdp)
            .height(200.fxdp)
    ) {
        Column(
            modifier = Modifier.padding(16.fxdp)
        ) {
            Text(title, fontSize = 18.fxsp, fontWeight = FontWeight.Bold)
            Text(content, fontSize = 14.dysp, color = Color.Gray)
            Button(onClick = onAction) {
                Text("Action", fontSize = 14.fxsp)
            }
        }
    }
}
```

**iOS (SwiftUI):**
```swift
struct Card: View {
    let title: String
    let content: String
    let onAction: () -> Void
    
    var body: some View {
        VStack(alignment: .leading, spacing: 12.fxpt) {
            Text(title)
                .font(.fxSystem(size: 18, weight: .bold))
            
            Text(content)
                .font(.fxSystem(size: 14))
                .foregroundColor(.secondary)
            
            Button("Action", action: onAction)
                .fxFrame(width: 80, height: 32)
        }
        .fxPadding(16)
        .fxFrame(height: 200)
        .background(Color(.systemGray6))
        .fxCornerRadius(12)
    }
}
```

#### Grid Layout Pattern

**Android (Compose):**
```kotlin
@Composable
fun Grid(
    items: List<Item>,
    onItemClick: (Item) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.fxdp),
        horizontalArrangement = Arrangement.spacedBy(8.fxdp)
    ) {
        items(items) { item ->
            GridItem(item = item, onClick = { onItemClick(item) })
        }
    }
}
```

**iOS (SwiftUI):**
```swift
struct Grid: View {
    let items: [Item]
    let onItemTap: (Item) -> Void
    
    var body: some View {
        LazyVGrid(columns: [
            GridItem(.flexible(), spacing: 16.fxpt),
            GridItem(.flexible(), spacing: 16.fxpt)
        ], spacing: 16.fxpt) {
            ForEach(items, id: \.id) { item in
                GridItemView(item: item, onTap: { onItemTap(item) })
            }
        }
        .fxPadding(16)
    }
}
```

---

## 🚀 Advanced Use Cases

### E-commerce App Layout

```kotlin
// Android
@Composable
fun ProductCard(
    product: Product,
    onAddToCart: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(160.dydp)
            .height(240.fxdp)
            .padding(8.fxdp)
    ) {
        Column {
            // Product image
            AsyncImage(
                model = product.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dydp)
            )
            
            // Product info
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.fxdp)
            ) {
                Text(
                    text = product.name,
                    fontSize = 14.fxsp,
                    fontWeight = FontWeight.Medium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                
                Spacer(modifier = Modifier.height(4.fxdp))
                
                Text(
                    text = product.price,
                    fontSize = 16.fxsp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Red
                )
                
                Spacer(modifier = Modifier.height(8.fxdp))
                
                Button(
                    onClick = onAddToCart,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(32.fxdp)
                ) {
                    Text(
                        text = "Add to Cart",
                        fontSize = 12.fxsp
                    )
                }
            }
        }
    }
}
```

```swift
// iOS
struct ProductCard: View {
    let product: Product
    let onAddToCart: () -> Void
    
    var body: some View {
        VStack(alignment: .leading, spacing: 8.fxpt) {
            // Product image
            AsyncImage(url: URL(string: product.imageUrl)) { image in
                image
                    .resizable()
                    .aspectRatio(contentMode: .fill)
            } placeholder: {
                Rectangle()
                    .fill(Color.gray.opacity(0.3))
            }
            .frame(height: 120.dypt)
            .clipped()
            
            // Product info
            VStack(alignment: .leading, spacing: 4.fxpt) {
                Text(product.name)
                    .font(.fxSystem(size: 14, weight: .medium))
                    .lineLimit(2)
                
                Text(product.price)
                    .font(.fxSystem(size: 16, weight: .bold))
                    .foregroundColor(.red)
                
                Button("Add to Cart", action: onAddToCart)
                    .fxFrame(width: .infinity, height: 32)
                    .fxCornerRadius(6)
            }
            .fxPadding(12)
        }
        .dyFrame(width: 160)
        .fxFrame(height: 240)
        .background(Color(.systemBackground))
        .fxCornerRadius(12)
        .shadow(color: .black.opacity(0.1), radius: 2, x: 0, y: 1)
    }
}
```

### News App Layout

```kotlin
// Android
@Composable
fun NewsArticle(
    article: Article,
    onReadMore: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.fxdp, vertical = 8.fxdp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.fxdp)
        ) {
            // Article image
            AsyncImage(
                model = article.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .width(100.dydp)
                    .height(80.fxdp)
                    .clip(RoundedCornerShape(8.fxdp))
            )
            
            Spacer(modifier = Modifier.width(12.fxdp))
            
            // Article content
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = article.title,
                    fontSize = 16.fxsp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                
                Spacer(modifier = Modifier.height(4.fxdp))
                
                Text(
                    text = article.summary,
                    fontSize = 14.dysp,
                    color = Color.Gray,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                
                Spacer(modifier = Modifier.height(8.fxdp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = article.publishDate,
                        fontSize = 12.fxsp,
                        color = Color.Gray
                    )
                    
                    TextButton(onClick = onReadMore) {
                        Text(
                            text = "Read More",
                            fontSize = 12.fxsp
                        )
                    }
                }
            }
        }
    }
}
```

```swift
// iOS
struct NewsArticle: View {
    let article: Article
    let onReadMore: () -> Void
    
    var body: some View {
        HStack(spacing: 12.fxpt) {
            // Article image
            AsyncImage(url: URL(string: article.imageUrl)) { image in
                image
                    .resizable()
                    .aspectRatio(contentMode: .fill)
            } placeholder: {
                Rectangle()
                    .fill(Color.gray.opacity(0.3))
            }
            .dyFrame(width: 100)
            .fxFrame(height: 80)
            .fxCornerRadius(8)
            
            // Article content
            VStack(alignment: .leading, spacing: 4.fxpt) {
                Text(article.title)
                    .font(.fxSystem(size: 16, weight: .bold))
                    .lineLimit(2)
                
                Text(article.summary)
                    .font(.fxSystem(size: 14))
                    .foregroundColor(.secondary)
                    .lineLimit(3)
                
                HStack {
                    Text(article.publishDate)
                        .font(.fxSystem(size: 12))
                        .foregroundColor(.secondary)
                    
                    Spacer()
                    
                    Button("Read More", action: onReadMore)
                        .font(.fxSystem(size: 12))
                }
            }
        }
        .fxPadding(16)
        .background(Color(.systemBackground))
        .fxCornerRadius(12)
        .shadow(color: .black.opacity(0.1), radius: 2, x: 0, y: 1)
    }
}
```

---

## ⚡ Performance Examples

### Caching Dimensions

```kotlin
// Android
class OptimizedActivity : AppCompatActivity() {
    // Cache frequently used dimensions
    private val buttonHeight = AppDimens.fixed(44).toPx(resources).toInt()
    private val cardWidth = AppDimens.dynamic(300).toPx(resources).toInt()
    private val padding = 16.fxdp
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Use cached dimensions
    }
}
```

```swift
// iOS
class OptimizedViewController: UIViewController {
    // Cache frequently used dimensions
    private let buttonHeight = AppDimens.fixed(44).toPoints()
    private let cardWidth = AppDimens.dynamic(300).toPoints()
    private let padding = 16.fxpt
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Use cached dimensions
    }
}
```

### Lazy Loading with Dimensions

```kotlin
// Android
@Composable
fun LazyListWithDimensions(
    items: List<Item>
) {
    LazyColumn(
        contentPadding = PaddingValues(16.fxdp),
        verticalArrangement = Arrangement.spacedBy(8.fxdp)
    ) {
        items(items) { item ->
            ItemCard(
                item = item,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.fxdp)
            )
        }
    }
}
```

```swift
// iOS
struct LazyListWithDimensions: View {
    let items: [Item]
    
    var body: some View {
        LazyVStack(spacing: 8.fxpt) {
            ForEach(items, id: \.id) { item in
                ItemCard(item: item)
                    .fxFrame(height: 120)
            }
        }
        .fxPadding(16)
    }
}
```

---

## 📚 Best Practices

### 1. Use Appropriate Scaling Types

- **Fixed (FX)** ⭐ **RECOMMENDED**: For most UI elements - buttons, paddings, margins, icons, fonts, containers, cards
- **Dynamic (DY)**: Only for specific cases - large containers, full-width grids, viewport-dependent elements
- **SDP/SSP**: For XML-based layouts with pre-calculated resources

### 2. Cache Frequently Used Dimensions

```kotlin
// Android
object AppDimensions {
    val buttonHeight = 44.fxdp       // Fixed (RECOMMENDED)
    val cardWidth = 300.fxdp          // Fixed (RECOMMENDED)
    val padding = 16.fxdp
    val cornerRadius = 8.fxdp
}
```

```swift
// iOS
struct AppDimensions {
    static let buttonHeight = 44.fxpt     // Fixed (RECOMMENDED)
    static let cardWidth = 300.fxpt       // Fixed (RECOMMENDED)
    static let padding = 16.fxpt
    static let cornerRadius = 8.fxpt
}
```

### 3. Use Conditional Scaling for Different Devices

```kotlin
// Android
val buttonSize = 80.fixedDp()  // Fixed (RECOMMENDED)
    .screen(UiModeType.WATCH, 40.dp)
    .screen(UiModeType.CAR, 120.dp)
    .screen(DpQualifier.SMALL_WIDTH, 720, 150)
```

```swift
// iOS
let buttonSize = AppDimens.fixed(80)  // Fixed (RECOMMENDED)
    .screen(.watch, 40)
    .screen(.tablet, 120)
    .toPoints()
```

### 4. Test on Multiple Devices

- Use device simulators/emulators
- Test on different screen sizes and densities
- Verify accessibility compliance
- Check performance on older devices

---

## 🎯 Conclusion

These examples demonstrate the power and flexibility of AppDimens across different platforms and use cases. By following these patterns and best practices, you can create responsive, consistent, and performant user interfaces that work seamlessly across all device types.

For more examples and detailed documentation, visit:
- [Android Documentation](../Android/README.md)
- [iOS Documentation](../iOS/README.md)
- [Complete API Reference](https://appdimens-project.web.app/)
