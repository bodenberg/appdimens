# CMakeLists.txt for AppDimens Games C++ Native Library
cmake_minimum_required(VERSION 3.22.1)

project("appdimens_games")

# Set C++ standard
set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

# Find required libraries
find_library(log-lib log)
find_library(android-lib android)
find_library(EGL-lib EGL)
find_library(GLESv2-lib GLESv2)
find_library(GLESv3-lib GLESv3)

# Include directories
include_directories(${CMAKE_CURRENT_SOURCE_DIR}/include)

# Source files
set(SOURCES
    src/AppDimensGames.cpp
    src/GameDimensions.cpp
    src/ViewportManager.cpp
    src/GameScaling.cpp
    src/OpenGLUtils.cpp
    src/GameMath.cpp
    src/PerformanceMonitor.cpp
    src/GameCalculator.cpp
    src/GameCacheFast.cpp
    src/GameLookupTables.cpp
)

# Header files
set(HEADERS
    include/AppDimensGames.h
    include/GameDimensions.h
    include/ViewportManager.h
    include/GameScaling.h
    include/OpenGLUtils.h
    include/GameMath.h
    include/PerformanceMonitor.h
    include/GameCalculator.h
    include/GameCacheFast.h
    include/GameLookupTables.h
    include/GameScalingStrategy.h
)

# Create shared library
add_library(
    appdimens_games
    SHARED
    ${SOURCES}
    ${HEADERS}
)

# Link libraries
target_link_libraries(
    appdimens_games
    ${log-lib}
    ${android-lib}
    ${EGL-lib}
    ${GLESv2-lib}
    ${GLESv3-lib}
)

# Compiler flags
target_compile_definitions(appdimens_games PRIVATE
    ANDROID_NDK
)

# Optimization flags for release
set(CMAKE_CXX_FLAGS_RELEASE "-O3 -DNDEBUG -ffast-math -funroll-loops")
set(CMAKE_CXX_FLAGS_DEBUG "-O0 -g -DDEBUG")

# Enable RTTI and exceptions
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -frtti -fexceptions")

# Platform-specific optimizations
if(ANDROID_ABI STREQUAL "arm64-v8a")
    set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -march=armv8-a")
elseif(ANDROID_ABI STREQUAL "armeabi-v7a")
    set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -march=armv7-a -mfpu=neon")
elseif(ANDROID_ABI STREQUAL "x86")
    set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -march=i686")
elseif(ANDROID_ABI STREQUAL "x86_64")
    set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -march=x86-64")
endif()
