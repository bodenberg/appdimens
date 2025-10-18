# ProGuard rules for AppDimens Games library
# These rules are applied when building the library

# Keep all public classes and methods
-keep public class com.appdimens.games.** {
    public *;
}

# Keep native methods
-keepclasseswithmembernames class * {
    native <methods>;
}

# Keep JNI methods
-keepclasseswithmembernames class * {
    @com.appdimens.games.Keep <methods>;
}

# Keep enum classes
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# Keep data classes
-keep class com.appdimens.games.GameScreenConfig { *; }
-keep class com.appdimens.games.GameVector2D { *; }
-keep class com.appdimens.games.GameRectangle { *; }

# Keep native library references
-keep class com.appdimens.games.AppDimensGames {
    static {
        System.loadLibrary("appdimens_games");
    }
}

# Keep companion objects
-keepclassmembers class com.appdimens.games.AppDimensGames$Companion {
    public static ** getInstance();
}

# Keep singleton pattern
-keep class com.appdimens.games.AppDimensGames {
    private static volatile com.appdimens.games.AppDimensGames INSTANCE;
}

# Keep reflection-accessed members
-keepclassmembers class com.appdimens.games.** {
    @androidx.annotation.Keep *;
}

# Keep serialization
-keepclassmembers class com.appdimens.games.** implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# Keep annotations
-keepattributes *Annotation*
-keepattributes Signature
-keepattributes InnerClasses
-keepattributes EnclosingMethod

# Keep line numbers for debugging
-keepattributes SourceFile,LineNumberTable

# Keep generic signatures
-keepattributes Signature

# Keep exceptions
-keepattributes Exceptions
