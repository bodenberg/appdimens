# 🎨 AppDimens - Practical Examples

> **Languages:** English | [Português (BR)](../LANG/pt-BR/EXAMPLES.md) | [Español](../LANG/es/EXAMPLES.md)

**Real-World Code Samples for All Platforms**  
*Author: Jean Bodenberg*  
*Date: February 2025*  
*Version: 2.0.0*

> **🆕 Version 2.0:** Now featuring **13 scaling strategies** with **BALANCED** as primary recommendation and **DEFAULT** as secondary. All examples updated to showcase the new Smart API.

---

## 📋 Table of Contents

1. [Version 2.0 Quick Start](#1-version-20-quick-start)
2. [Android Examples](#2-android-examples)
3. [iOS Examples](#3-ios-examples)
4. [Flutter Examples](#4-flutter-examples)
5. [React Native Examples](#5-react-native-examples)
6. [Web Examples](#6-web-examples)
7. [Cross-Platform Patterns](#7-cross-platform-patterns)
8. [Advanced Use Cases](#8-advanced-use-cases)
9. [Game Development](#9-game-development)
10. [Migration Examples](#10-migration-examples)

---

## 1. Version 2.0 Quick Start

### 1.1 Strategy Selection Guide

**Use BALANCED ⭐ (Primary) for:**
- 95% of applications
- Multi-device apps (phones, tablets, TVs)
- Buttons, spacing, padding, text

**Use DEFAULT (Secondary) for:**
- Phone-focused apps
- Icons and small elements
- Backward compatibility with v1.x

**Use PERCENTAGE for:**
- Very large containers
- Proportional images
- Full-width grids

**Use Other Strategies for:**
- FLUID → Typography with bounds
- LOGARITHMIC → TV apps
- FIT/FILL → Games

### 1.2 Quick Examples (All Platforms)

**Android:**
```kotlin
Text("Hello", fontSize = 16.balanced().sp)  // ⭐ Primary
Icon(modifier = Modifier.size(24.defaultDp))  // Secondary
```

**iOS:**
```swift
Text("Hello").font(.system(size: AppDimens.shared.balanced(16).toPoints()))
```

**Flutter:**
```dart
Text('Hello', style: TextStyle(fontSize: AppDimens.balanced(16).calculate(context)))
```

**React Native:**
{% raw %}
```typescript
<Text style={{fontSize: balanced(16)}}>Hello</Text>
```
{% endraw %}

**Web:**
{% raw %}
```typescript
<h2 style={{fontSize: balanced(16)}}>Hello</h2>
```
{% endraw %}

---

## 2. Android Examples

### 2.1 Jetpack Compose - Complete App Example

#### Social Media Feed (Multi-Device App)

```kotlin
@Composable
fun SocialFeedScreen() {
    Scaffold(
        topBar = { FeedTopBar() },
        floatingActionButton = { NewPostFAB() }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.balanced().dp)  // ⭐ BALANCED
        ) {
            items(posts) { post ->
                PostCard(post)
                Spacer(modifier = Modifier.height(12.balanced().dp))
            }
        }
    }
}

@Composable
fun FeedTopBar() {
    TopAppBar(
        title = {
            Text(
                text = "Social Feed",
                fontSize = 20.balanced().sp  // ⭐ BALANCED
            )
        },
        navigationIcon = {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu",
                    modifier = Modifier.size(24.balanced().dp)
                )
            }
        },
        actions = {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    modifier = Modifier.size(24.balanced().dp)
                )
            }
        },
        modifier = Modifier.height(56.balanced().dp)
    )
}

@Composable
fun PostCard(post: Post) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.balanced().dp)
    ) {
        Column(modifier = Modifier.padding(16.balanced().dp)) {
            // Header: Avatar + Username
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                AsyncImage(
                    model = post.userAvatar,
                    contentDescription = "Avatar",
                    modifier = Modifier
                        .size(40.balanced().dp)
                        .clip(CircleShape)
                )
                
                Spacer(modifier = Modifier.width(12.balanced().dp))
                
                Column {
                    Text(
                        text = post.username,
                        fontSize = 14.balanced().sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = post.timestamp,
                        fontSize = 12.balanced().sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(12.balanced().dp))
            
            // Post content
            Text(
                text = post.content,
                fontSize = 14.balanced().sp,
                lineHeight = 20.balanced().sp
            )
            
            // Post image (if exists)
            post.imageUrl?.let { imageUrl ->
                Spacer(modifier = Modifier.height(12.balanced().dp))
                AsyncImage(
                    model = imageUrl,
                    contentDescription = "Post image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.percentageDp.dp)  // Proportional image
                        .clip(RoundedCornerShape(8.balanced().dp))
                )
            }
            
            Spacer(modifier = Modifier.height(12.balanced().dp))
            
            // Action buttons
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row {
                    IconButton(
                        onClick = { },
                        modifier = Modifier.size(40.balanced().dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Like",
                            modifier = Modifier.size(20.balanced().dp)
                        )
                    }
                    
                    Text(
                        text = "${post.likes}",
                        fontSize = 14.balanced().sp,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    
                    Spacer(modifier = Modifier.width(16.balanced().dp))
                    
                    IconButton(
                        onClick = { },
                        modifier = Modifier.size(40.balanced().dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Comment,
                            contentDescription = "Comment",
                            modifier = Modifier.size(20.balanced().dp)
                        )
                    }
                    
                    Text(
                        text = "${post.comments}",
                        fontSize = 14.balanced().sp,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
                
                IconButton(
                    onClick = { },
                    modifier = Modifier.size(40.balanced().dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        modifier = Modifier.size(20.balanced().dp)
                    )
                }
            }
        }
    }
}

@Composable
fun NewPostFAB() {
    FloatingActionButton(
        onClick = { },
        modifier = Modifier.size(56.balanced().dp)
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "New Post",
            modifier = Modifier.size(24.balanced().dp)
        )
    }
}
```

#### E-Commerce Product List

```kotlin
@Composable
fun ProductListScreen() {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 160.percentageDp.dp),  // Proportional grid
        horizontalArrangement = Arrangement.spacedBy(12.balanced().dp),
        verticalArrangement = Arrangement.spacedBy(12.balanced().dp),
        contentPadding = PaddingValues(16.balanced().dp)
    ) {
        items(products) { product ->
            ProductCard(product)
        }
    }
}

@Composable
fun ProductCard(product: Product) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(2.balanced().dp)
    ) {
        Column {
            // Product image
            AsyncImage(
                model = product.imageUrl,
                contentDescription = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.percentageDp.dp)  // Proportional
                    .clip(RoundedCornerShape(topStart = 8.balanced().dp, topEnd = 8.balanced().dp))
            )
            
            Column(modifier = Modifier.padding(12.balanced().dp)) {
                // Product name
                Text(
                    text = product.name,
                    fontSize = 14.balanced().sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                
                Spacer(modifier = Modifier.height(4.balanced().dp))
                
                // Product price
                Text(
                    text = "$${product.price}",
                    fontSize = 16.balanced().sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                
                Spacer(modifier = Modifier.height(8.balanced().dp))
                
                // Add to cart button
                Button(
                    onClick = { },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.balanced().dp)
                ) {
                    Text(
                        text = "Add to Cart",
                        fontSize = 14.balanced().sp
                    )
                }
            }
        }
    }
}
```

#### Using Smart API

```kotlin
@Composable
fun SmartAPIExample() {
    Column(modifier = Modifier.padding(16.balanced().dp)) {
        // Smart API automatically selects best strategy
        Button(
            onClick = { },
            modifier = Modifier.height(
                AppDimens.from(48).smart().forElement(ElementType.BUTTON).dp
            )
            // → Automatically selects BALANCED for buttons on tablets
        ) {
            Text("Smart Button")
        }
        
        // For containers
        Box(
            modifier = Modifier.width(
                AppDimens.from(300).smart().forElement(ElementType.CONTAINER).dp
            )
            // → Automatically selects PERCENTAGE for containers
        )
        
        // For text
        Text(
            text = "Smart Text",
            fontSize = AppDimens.from(16).smart().forElement(ElementType.TEXT).sp
            // → Automatically selects FLUID for text (if configured)
        )
    }
}
```

### 2.2 Using Different Strategies

```kotlin
@Composable
fun AllStrategiesDemo() {
    Column(modifier = Modifier.padding(16.balanced().dp)) {
        // BALANCED ⭐ (Primary)
        Text("Balanced", fontSize = 16.balanced().sp)
        
        // DEFAULT (Secondary - phone-focused)
        Text("Default", fontSize = 16.defaultDp.sp)
        
        // PERCENTAGE (Large containers)
        Box(modifier = Modifier.width(300.percentageDp.dp))
        
        // LOGARITHMIC (TV apps)
        Text("Logarithmic", fontSize = 16.logarithmic().sp)
        
        // POWER (Configurable)
        Text("Power", fontSize = 16.power(exponent = 0.75f).sp)
        
        // FLUID (Typography with bounds)
        Text("Fluid", fontSize = fluidSp(14f, 20f))
        
        // INTERPOLATED (Moderate)
        Text("Interpolated", fontSize = 16.interpolated().sp)
        
        // DIAGONAL (Physical size)
        Box(modifier = Modifier.size(48.diagonal().dp))
        
        // PERIMETER (W+H)
        Box(modifier = Modifier.size(48.perimeter().dp))
        
        // FIT/FILL (Games)
        Box(modifier = Modifier.size(48.fit().dp))
        Box(modifier = Modifier.size(48.fill().dp))
        
        // NONE (No scaling)
        Divider(thickness = 1.none().dp)  // Always 1dp
    }
}
```

---

## 3. iOS Examples

### 3.1 SwiftUI - Complete App Example

#### News Reader App

```swift
struct NewsReaderView: View {
    @State private var articles: [Article] = []
    
    var body: some View {
        NavigationView {
            ScrollView {
                VStack(spacing: AppDimens.shared.balanced(16).toPoints()) {
                    ForEach(articles) { article in
                        ArticleCard(article: article)
                    }
                }
                .padding(AppDimens.shared.balanced(16).toPoints())
            }
            .navigationTitle("News")
            .navigationBarTitleDisplayMode(.large)
        }
    }
}

struct ArticleCard: View {
    let article: Article
    
    var body: some View {
        VStack(alignment: .leading, spacing: AppDimens.shared.balanced(12).toPoints()) {
            // Article image
            AsyncImage(url: article.imageURL) { image in
                image
                    .resizable()
                    .aspectRatio(contentMode: .fill)
            } placeholder: {
                Rectangle().fill(Color.gray.opacity(0.2))
            }
            .frame(
                height: AppDimens.shared.percentage(200).toPoints()  // Proportional
            )
            .clipped()
            .cornerRadius(AppDimens.shared.balanced(8).toPoints())
            
            // Category badge
            Text(article.category.uppercased())
                .font(.system(
                    size: AppDimens.shared.balanced(10).toPoints(),
                    weight: .semibold
                ))
                .foregroundColor(.white)
                .padding(.horizontal, AppDimens.shared.balanced(8).toPoints())
                .padding(.vertical, AppDimens.shared.balanced(4).toPoints())
                .background(Color.blue)
                .cornerRadius(AppDimens.shared.balanced(4).toPoints())
            
            // Article title
            Text(article.title)
                .font(.system(
                    size: AppDimens.shared.balanced(16).toPoints(),
                    weight: .bold
                ))
                .lineLimit(2)
            
            // Article excerpt
            Text(article.excerpt)
                .font(.system(size: AppDimens.shared.balanced(14).toPoints()))
                .foregroundColor(.secondary)
                .lineLimit(3)
            
            // Metadata row
            HStack(spacing: AppDimens.shared.balanced(12).toPoints()) {
                Label(
                    article.author,
                    systemImage: "person.circle"
                )
                .font(.system(size: AppDimens.shared.balanced(12).toPoints()))
                
                Label(
                    article.readTime,
                    systemImage: "clock"
                )
                .font(.system(size: AppDimens.shared.balanced(12).toPoints()))
            }
            .foregroundColor(.secondary)
        }
        .padding(AppDimens.shared.balanced(12).toPoints())
        .background(Color.white)
        .cornerRadius(AppDimens.shared.balanced(12).toPoints())
        .shadow(radius: AppDimens.shared.balanced(2).toPoints())
    }
}
```

### 3.2 UIKit - Settings Screen

```swift
class SettingsViewController: UIViewController {
    
    private lazy var tableView: UITableView = {
        let table = UITableView(frame: .zero, style: .insetGrouped)
        table.delegate = self
        table.dataSource = self
        table.rowHeight = AppDimens.shared.balanced(56).toPoints()
        return table
    }()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        title = "Settings"
        view.backgroundColor = .systemBackground
        
        // Setup table view
        view.addSubview(tableView)
        tableView.translatesAutoresizingMaskIntoConstraints = false
        NSLayoutConstraint.activate([
            tableView.topAnchor.constraint(equalTo: view.topAnchor),
            tableView.leadingAnchor.constraint(equalTo: view.leadingAnchor),
            tableView.trailingAnchor.constraint(equalTo: view.trailingAnchor),
            tableView.bottomAnchor.constraint(equalTo: view.bottomAnchor)
        ])
    }
}

extension SettingsViewController: UITableViewDelegate, UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return settings.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = UITableViewCell(style: .value1, reuseIdentifier: "cell")
        let setting = settings[indexPath.row]
        
        cell.textLabel?.text = setting.title
        cell.textLabel?.font = .systemFont(
            ofSize: AppDimens.shared.balanced(16).toPoints()
        )
        
        cell.detailTextLabel?.text = setting.value
        cell.detailTextLabel?.font = .systemFont(
            ofSize: AppDimens.shared.balanced(14).toPoints()
        )
        
        cell.imageView?.image = UIImage(systemName: setting.icon)
        cell.imageView?.tintColor = .systemBlue
        
        return cell
    }
}
```

### 3.3 Using Smart API (iOS)

```swift
struct SmartAPIView: View {
    var body: some View {
        VStack(spacing: AppDimens.shared.balanced(16).toPoints()) {
            // Smart button (automatically selects BALANCED on tablets)
            Button("Smart Button") { }
                .frame(height: AppDimens.shared.smart(48).forElement(.button).toPoints())
            
            // Smart container (automatically selects PERCENTAGE)
            Rectangle()
                .fill(Color.blue.opacity(0.1))
                .frame(width: AppDimens.shared.smart(300).forElement(.container).toPoints())
            
            // Smart text (automatically selects FLUID if configured)
            Text("Smart Text")
                .font(.system(size: AppDimens.shared.smart(16).forElement(.text).toPoints()))
        }
    }
}
```

---

## 4. Flutter Examples

### 4.1 Complete App - Task Manager

```dart
class TaskManagerApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Task Manager',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: TaskListScreen(),
    );
  }
}

class TaskListScreen extends StatefulWidget {
  @override
  _TaskListScreenState createState() => _TaskListScreenState();
}

class _TaskListScreenState extends State<TaskListScreen> {
  final List<Task> tasks = [];
  
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(
          'My Tasks',
          style: TextStyle(
            fontSize: AppDimens.balanced(20).calculate(context),
          ),
        ),
        actions: [
          IconButton(
            icon: Icon(Icons.search),
            iconSize: AppDimens.balanced(24).calculate(context),
            onPressed: () {},
          ),
        ],
        toolbarHeight: AppDimens.balanced(56).calculate(context),
      ),
      body: ListView.separated(
        padding: EdgeInsets.all(AppDimens.balanced(16).calculate(context)),
        itemCount: tasks.length,
        separatorBuilder: (context, index) => SizedBox(
          height: AppDimens.balanced(12).calculate(context),
        ),
        itemBuilder: (context, index) {
          return TaskCard(task: tasks[index]);
        },
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: () {},
        child: Icon(
          Icons.add,
          size: AppDimens.balanced(24).calculate(context),
        ),
      ),
    );
  }
}

class TaskCard extends StatelessWidget {
  final Task task;
  
  const TaskCard({required this.task});
  
  @override
  Widget build(BuildContext context) {
    return Card(
      elevation: AppDimens.balanced(2).calculate(context),
      shape: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(
          AppDimens.balanced(8).calculate(context),
        ),
      ),
      child: Padding(
        padding: EdgeInsets.all(AppDimens.balanced(16).calculate(context)),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            // Task header
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                // Priority indicator
                Container(
                  width: AppDimens.balanced(8).calculate(context),
                  height: AppDimens.balanced(8).calculate(context),
                  decoration: BoxDecoration(
                    color: task.priorityColor,
                    shape: BoxShape.circle,
                  ),
                ),
                
                SizedBox(width: AppDimens.balanced(12).calculate(context)),
                
                // Task title
                Expanded(
                  child: Text(
                    task.title,
                    style: TextStyle(
                      fontSize: AppDimens.balanced(16).calculate(context),
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                ),
                
                // Checkbox
                SizedBox(
                  width: AppDimens.balanced(24).calculate(context),
                  height: AppDimens.balanced(24).calculate(context),
                  child: Checkbox(
                    value: task.completed,
                    onChanged: (value) {},
                  ),
                ),
              ],
            ),
            
            SizedBox(height: AppDimens.balanced(8).calculate(context)),
            
            // Task description
            Text(
              task.description,
              style: TextStyle(
                fontSize: AppDimens.balanced(14).calculate(context),
                color: Colors.grey[600],
              ),
              maxLines: 2,
              overflow: TextOverflow.ellipsis,
            ),
            
            SizedBox(height: AppDimens.balanced(12).calculate(context)),
            
            // Task metadata
            Row(
              children: [
                Icon(
                  Icons.calendar_today,
                  size: AppDimens.balanced(16).calculate(context),
                  color: Colors.grey,
                ),
                SizedBox(width: AppDimens.balanced(4).calculate(context)),
                Text(
                  task.dueDate,
                  style: TextStyle(
                    fontSize: AppDimens.balanced(12).calculate(context),
                    color: Colors.grey,
                  ),
                ),
                
                SizedBox(width: AppDimens.balanced(16).calculate(context)),
                
                Icon(
                  Icons.label,
                  size: AppDimens.balanced(16).calculate(context),
                  color: Colors.grey,
                ),
                SizedBox(width: AppDimens.balanced(4).calculate(context)),
                Text(
                  task.category,
                  style: TextStyle(
                    fontSize: AppDimens.balanced(12).calculate(context),
                    color: Colors.grey,
                  ),
                ),
              ],
            ),
          ],
        ),
      ),
    );
  }
}
```

### 4.2 Using Extensions

```dart
// Flutter supports extensions for cleaner syntax
Container(
  width: 300.0.balanced(),
  height: 200.0.defaultScaling(),
  padding: EdgeInsets.all(16.0.balanced()),
  child: Text(
    'With Extensions',
    style: TextStyle(fontSize: 14.0.balanced()),
  ),
)
```

---

## 5. React Native Examples

### 5.1 Complete App - Weather App

{% raw %}
```typescript
import React from 'react';
import {View, Text, StyleSheet, ScrollView, Image} from 'react-native';
import {useAppDimens} from 'appdimens-react-native';

interface WeatherData {
  city: string;
  temperature: number;
  condition: string;
  icon: string;
  forecast: DayForecast[];
}

export default function WeatherScreen() {
  const {balanced, smart} = useAppDimens();
  const weather: WeatherData = useWeatherData();
  
  const styles = StyleSheet.create({
    container: {
      flex: 1,
      backgroundColor: '#F5F5F5',
    },
    header: {
      padding: balanced(16),
      backgroundColor: '#2196F3',
    },
    cityName: {
      fontSize: balanced(24),
      fontWeight: 'bold',
      color: 'white',
      marginBottom: balanced(8),
    },
    currentTemp: {
      fontSize: balanced(64),
      fontWeight: '200',
      color: 'white',
    },
    condition: {
      fontSize: balanced(18),
      color: 'white',
      opacity: 0.9,
    },
    forecastContainer: {
      padding: balanced(16),
    },
    forecastCard: {
      backgroundColor: 'white',
      borderRadius: balanced(12),
      padding: balanced(16),
      marginBottom: balanced(12),
      shadowColor: '#000',
      shadowOffset: {width: 0, height: 2},
      shadowOpacity: 0.1,
      shadowRadius: balanced(4),
      elevation: 2,
    },
    dayRow: {
      flexDirection: 'row',
      justifyContent: 'space-between',
      alignItems: 'center',
      marginBottom: balanced(8),
    },
    dayName: {
      fontSize: balanced(16),
      fontWeight: '600',
    },
    dayTemp: {
      fontSize: balanced(18),
      fontWeight: 'bold',
      color: '#2196F3',
    },
    icon: {
      width: smart(48).forElement('icon'),
      height: smart(48).forElement('icon'),
    },
  });
  
  return (
    <View style={styles.container}>
      {/* Current weather */}
      <View style={styles.header}>
        <Text style={styles.cityName}>{weather.city}</Text>
        <Text style={styles.currentTemp}>{weather.temperature}°</Text>
        <Text style={styles.condition}>{weather.condition}</Text>
      </View>
      
      {/* Forecast */}
      <ScrollView style={styles.forecastContainer}>
        {weather.forecast.map((day, index) => (
          <View key={index} style={styles.forecastCard}>
            <View style={styles.dayRow}>
              <Text style={styles.dayName}>{day.name}</Text>
              <Image source={{uri: day.icon}} style={styles.icon} />
              <Text style={styles.dayTemp}>{day.temp}°</Text>
            </View>
          </View>
        ))}
      </ScrollView>
    </View>
  );
}
```
{% endraw %}

### 5.2 Using Different Strategies

{% raw %}
```typescript
import {useAppDimens} from 'appdimens-react-native';

function AllStrategiesExample() {
  const {
    balanced,        // Primary ⭐
    defaultScaling,  // Secondary
    percentage,      // Containers
    logarithmic,     // TV
    power,           // Configurable
    fluid,           // Typography
  } = useAppDimens();
  
  return (
    <View style={{padding: balanced(16)}}>
      {/* BALANCED (Primary) */}
      <Text style={{fontSize: balanced(16)}}>Balanced Text</Text>
      
      {/* DEFAULT (Secondary) */}
      <View style={{width: defaultScaling(24), height: defaultScaling(24)}} />
      
      {/* PERCENTAGE (Large containers) */}
      <View style={{width: percentage(300)}} />
      
      {/* FLUID (Typography) */}
      <Text style={{fontSize: fluid(16, 24)}}>Fluid Typography</Text>
      
      {/* POWER (Configurable) */}
      <Text style={{fontSize: power(16, {exponent: 0.75})}}>Power Law</Text>
    </View>
  );
}
```
{% endraw %}

---

## 6. Web Examples

### 6.1 React - Dashboard Example

{% raw %}
```typescript
import React from 'react';
import {useWebDimens} from 'webdimens/react';

interface DashboardProps {
  stats: StatCard[];
}

export function Dashboard({stats}: DashboardProps) {
  const {balanced, percentage, fluid} = useWebDimens();
  
  return (
    <div style={{
      padding: balanced(24),
      maxWidth: '1200px',
      margin: '0 auto',
    }}>
      {/* Page title */}
      <h1 style={{
        fontSize: fluid(28, 40),  // Bounded typography
        fontWeight: 'bold',
        marginBottom: balanced(24),
      }}>
        Dashboard
      </h1>
      
      {/* Stats grid */}
      <div style={{
        display: 'grid',
        gridTemplateColumns: `repeat(auto-fit, minmax(${percentage(280)}, 1fr))`,
        gap: balanced(16),
        marginBottom: balanced(32),
      }}>
        {stats.map((stat, index) => (
          <StatCard key={index} stat={stat} />
        ))}
      </div>
      
      {/* Charts section */}
      <div style={{
        backgroundColor: 'white',
        borderRadius: balanced(12),
        padding: balanced(24),
        boxShadow: '0 2px 8px rgba(0,0,0,0.1)',
      }}>
        <h2 style={{
          fontSize: balanced(20),
          marginBottom: balanced(16),
        }}>
          Analytics
        </h2>
        <ChartComponent />
      </div>
    </div>
  );
}

function StatCard({stat}: {stat: StatCard}) {
  const {balanced} = useWebDimens();
  
  return (
    <div style={{
      backgroundColor: 'white',
      borderRadius: balanced(12),
      padding: balanced(20),
      boxShadow: '0 2px 8px rgba(0,0,0,0.1)',
    }}>
      <div style={{
        fontSize: balanced(14),
        color: '#666',
        marginBottom: balanced(8),
      }}>
        {stat.title}
      </div>
      <div style={{
        fontSize: balanced(32),
        fontWeight: 'bold',
        color: '#333',
        marginBottom: balanced(4),
      }}>
        {stat.value}
      </div>
      <div style={{
        fontSize: balanced(12),
        color: stat.changePositive ? '#4CAF50' : '#F44336',
      }}>
        {stat.change}
      </div>
    </div>
  );
}
```
{% endraw %}

### 6.2 Vanilla JavaScript

```html
<!DOCTYPE html>
<html>
<head>
  <script src="https://cdn.jsdelivr.net/npm/webdimens@2.0.0/dist/index.js"></script>
  <style>
    body {
      font-family: -apple-system, system-ui, sans-serif;
      margin: 0;
      padding: 0;
    }
  </style>
</head>
<body>
  <div id="app"></div>
  
  <script type="module">
    import {balanced, fluid} from 'https://cdn.jsdelivr.net/npm/webdimens@2.0.0/dist/index.mjs';
    
    // Apply dimensions
    const app = document.getElementById('app');
    app.style.padding = balanced(24);
    app.style.maxWidth = '1200px';
    app.style.margin = '0 auto';
    
    // Create header
    const header = document.createElement('header');
    header.style.marginBottom = balanced(24);
    
    const title = document.createElement('h1');
    title.textContent = 'Welcome';
    title.style.fontSize = fluid(28, 40);
    title.style.fontWeight = 'bold';
    header.appendChild(title);
    
    app.appendChild(header);
    
    // Create cards
    const cardsContainer = document.createElement('div');
    cardsContainer.style.display = 'grid';
    cardsContainer.style.gridTemplateColumns = 'repeat(auto-fit, minmax(280px, 1fr))';
    cardsContainer.style.gap = balanced(16);
    
    for (let i = 0; i < 3; i++) {
      const card = document.createElement('div');
      card.style.backgroundColor = 'white';
      card.style.borderRadius = balanced(12);
      card.style.padding = balanced(20);
      card.style.boxShadow = '0 2px 8px rgba(0,0,0,0.1)';
      
      const cardTitle = document.createElement('h3');
      cardTitle.textContent = `Card ${i + 1}`;
      cardTitle.style.fontSize = balanced(18);
      cardTitle.style.marginBottom = balanced(12);
      
      const cardText = document.createElement('p');
      cardText.textContent = 'Sample content with balanced sizing.';
      cardText.style.fontSize = balanced(14);
      cardText.style.color = '#666';
      
      card.appendChild(cardTitle);
      card.appendChild(cardText);
      cardsContainer.appendChild(card);
    }
    
    app.appendChild(cardsContainer);
  </script>
</body>
</html>
```

---

## 7. Cross-Platform Patterns

### 7.1 Design System (Same across all platforms)

**Define once, use everywhere:**

```
Design System Values:
- spacing_xs: 4
- spacing_sm: 8
- spacing_md: 16
- spacing_lg: 24
- spacing_xl: 32

- font_caption: 12
- font_body: 14
- font_subtitle: 16
- font_title: 20
- font_headline: 24

- icon_sm: 16
- icon_md: 24
- icon_lg: 32

- button_height: 48
- card_radius: 12
```

**Android:**
```kotlin
object AppDimensions {
    val spacingXS = 4.balanced().dp
    val spacingMD = 16.balanced().dp
    val fontBody = 14.balanced().sp
    val buttonHeight = 48.balanced().dp
}
```

**iOS:**
```swift
enum AppDimensions {
    static let spacingXS = AppDimens.shared.balanced(4).toPoints()
    static let spacingMD = AppDimens.shared.balanced(16).toPoints()
    static let fontBody = AppDimens.shared.balanced(14).toPoints()
    static let buttonHeight = AppDimens.shared.balanced(48).toPoints()
}
```

**Flutter:**
```dart
class AppDimensions {
  static double spacingXS(BuildContext context) => 
      AppDimens.balanced(4).calculate(context);
  static double spacingMD(BuildContext context) => 
      AppDimens.balanced(16).calculate(context);
  static double fontBody(BuildContext context) => 
      AppDimens.balanced(14).calculate(context);
  static double buttonHeight(BuildContext context) => 
      AppDimens.balanced(48).calculate(context);
}
```

**React Native:**
```typescript
export const AppDimensions = {
  spacingXS: () => balanced(4),
  spacingMD: () => balanced(16),
  fontBody: () => balanced(14),
  buttonHeight: () => balanced(48),
};
```

**Web:**
```typescript
export const AppDimensions = {
  spacingXS: () => balanced(4),
  spacingMD: () => balanced(16),
  fontBody: () => balanced(14),
  buttonHeight: () => balanced(48),
};
```

---

## 8. Advanced Use Cases

### 8.1 Conditional Sizing (All Platforms)

**Android:**
```kotlin
val buttonSize = 48.balanced()
    .screen(UiModeType.TV, 96.dp)
    .screen(UiModeType.WATCH, 40.dp)
    .screen(DpQualifier.SMALL_WIDTH, 600, 72.dp)
    .dp
```

**iOS:**
```swift
let buttonSize = AppDimens.shared.balanced(48)
    .screen(.tv, customValue: 96)
    .screen(.watch, customValue: 40)
    .toPoints()
```

### 8.2 Physical Units

**Android:**
```kotlin
val cardWidth = 8.cm
val buttonHeight = AppDimensPhysicalUnits.toInch(0.5f, resources)
```

**iOS:**
```swift
let cardWidth = AppDimensPhysicalUnits.cm(8)
```

**Flutter:**
```dart
final cardWidth = AppDimensPhysicalUnits.cmToPixels(8, context);
```

---

## 9. Game Development

### 9.1 Android (C++/NDK)

```kotlin
val games = AppDimensGames.getInstance()
games.initialize(context)

// UI elements
val buttonSize = games.calculateButtonSize(48f)

// Game world
val playerSize = games.calculatePlayerSize(64f)
val enemySize = games.calculateEnemySize(32f)

// Vectors
val position = GameVector2D(100f, 200f)
val scaled = games.calculateVector2D(position, GameDimensionType.GAME_WORLD)
```

### 9.2 iOS (Metal)

```swift
// Metal integration
let buttonSize = gameUniform(48)
let playerSize = gameAspectRatio(64)
```

---

## 10. Migration Examples

### 10.1 From v1.x to v2.0

**Before (v1.x):**
```kotlin
Text("Hello", fontSize = 16.fxsp)  // Deprecated
Container(modifier = Modifier.width(300.dydp))  // Deprecated
```

**After (v2.0) - Recommended:**
```kotlin
Text("Hello", fontSize = 16.balanced().sp)  // ⭐ Primary
Container(modifier = Modifier.width(300.percentageDp.dp))  // For containers
```

### 10.2 From SDP to AppDimens

**Before (SDP):**
```xml
<TextView android:textSize="@dimen/_16ssp" />
```

**After (AppDimens):**
```kotlin
Text(text = "Hello", fontSize = 16.balanced().sp)
```

---

## Conclusion

**Key Takeaways:**
- ✅ Use **BALANCED** for 95% of apps (primary)
- ✅ Use **DEFAULT** for phone-focused apps (secondary)
- ✅ 13 strategies cover all use cases
- ✅ Works consistently across all 5 platforms

**📖 More Resources:**
- [Complete Technical Guide](COMPREHENSIVE_TECHNICAL_GUIDE.md)
- [Mathematical Theory](MATHEMATICAL_THEORY.md)
- [Platform Guides](README.md#platform-specific-guides)

---

**Document created by:** Jean Bodenberg  
**Last updated:** February 2025  
**Version:** 2.0.0  
**Repository:** https://github.com/bodenberg/appdimens

---

**[⬆ Back to Top](#-appdimens---practical-examples)**
