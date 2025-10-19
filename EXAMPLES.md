# 🎨 AppDimens - Practical Examples

This document provides comprehensive, real-world examples of how to use AppDimens across different platforms and scenarios.

> Languages: [Português (BR)](LANG/pt-BR/EXAMPLES.md) | [Español](LANG/es/EXAMPLES.md) | [हिन्दी](LANG/hi/EXAMPLES.md) | [Русский](LANG/ru/EXAMPLES.md) | [中文](LANG/zh/EXAMPLES.md) | [日本語](LANG/ja/EXAMPLES.md)

## 📋 Table of Contents

1. [Android Examples](#android-examples)
2. [iOS Examples](#ios-examples)
3. [Cross-Platform Patterns](#cross-platform-patterns)
4. [Advanced Use Cases](#advanced-use-cases)
5. [Performance Examples](#performance-examples)

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
                fontSize = 18.fxsp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.fxdp)
            )
            
            Text(
                text = content,
                fontSize = 14.dysp,
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
                        .width(100.dydp)
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
    val buttonSize = 80.scaledDp()
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
            // Container - dynamic width, fixed height
            containerView.centerXAnchor.constraint(equalTo: view.centerXAnchor),
            containerView.centerYAnchor.constraint(equalTo: view.centerYAnchor),
            containerView.widthAnchor.constraint(equalToConstant: 300.dypt),
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

- **Fixed (FX)**: For UI elements that should maintain visual consistency
- **Dynamic (DY)**: For layout containers that should scale proportionally
- **SDP/SSP**: For XML-based layouts with pre-calculated resources

### 2. Cache Frequently Used Dimensions

```kotlin
// Android
object AppDimensions {
    val buttonHeight = 44.fxdp
    val cardWidth = 300.dydp
    val padding = 16.fxdp
    val cornerRadius = 8.fxdp
}
```

```swift
// iOS
struct AppDimensions {
    static let buttonHeight = 44.fxpt
    static let cardWidth = 300.dypt
    static let padding = 16.fxpt
    static let cornerRadius = 8.fxpt
}
```

### 3. Use Conditional Scaling for Different Devices

```kotlin
// Android
val buttonSize = 80.scaledDp()
    .screen(UiModeType.WATCH, 40.dp)
    .screen(UiModeType.CAR, 120.dp)
    .screen(DpQualifier.SMALL_WIDTH, 720, 150)
```

```swift
// iOS
let buttonSize = AppDimens.fixed(80)
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
- [Android Documentation](Android/README.md)
- [iOS Documentation](iOS/README.md)
- [Complete API Reference](https://appdimens-project.web.app/)
