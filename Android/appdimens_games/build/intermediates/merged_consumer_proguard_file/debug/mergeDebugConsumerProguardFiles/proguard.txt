# Consumer ProGuard rules for AppDimens Games
# These rules are applied to consumers of the library

# Keep native methods
-keepclasseswithmembernames class * {
    native <methods>;
}

# Keep JNI methods
-keepclasseswithmembernames class * {
    @com.appdimens.games.Keep <methods>;
}

# Keep AppDimens Games classes
-keep class com.appdimens.games.** { *; }

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
