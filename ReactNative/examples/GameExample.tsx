/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-27
 *
 * Library: AppDimens React Native - Game Example
 *
 * Description:
 * Game development example demonstrating AppDimens usage for game UI elements.
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

import React, {useState, useEffect} from 'react';
import {
  View,
  Text,
  StyleSheet,
  TouchableOpacity,
  SafeAreaView,
  Animated,
  Dimensions,
} from 'react-native';
import {useAppDimens, AppDimensProvider} from 'appdimens-react-native';

function GameExample() {
  const {fx, dy, deviceInfo, screenDimensions} = useAppDimens();
  const [score, setScore] = useState(0);
  const [playerPosition, setPlayerPosition] = useState({x: 0, y: 0});
  const [enemies, setEnemies] = useState<
    Array<{id: number; x: number; y: number}>
  >([]);

  // Game-specific dimensions
  const gameButtonSize = fx(48)
    .screen('tablet', 64)
    .screen('phone', 40)
    .toPixels();
  const playerSize = fx(64).screen('tablet', 80).screen('phone', 48).toPixels();
  const enemySize = fx(32).screen('tablet', 40).screen('phone', 24).toPixels();
  const uiOverlaySize = fx(24)
    .screen('tablet', 32)
    .screen('phone', 16)
    .toPixels();

  // Game area dimensions
  const gameAreaWidth = dy(300).screen('tablet', 400).toPixels();
  const gameAreaHeight = dy(400).screen('tablet', 500).toPixels();

  // UI element dimensions
  const hudPadding = fx(16).toPixels();
  const buttonSpacing = fx(8).toPixels();

  // Animation values
  const playerAnimation = new Animated.ValueXY({x: 0, y: 0});

  useEffect(() => {
    // Initialize player position
    setPlayerPosition({
      x: gameAreaWidth / 2 - playerSize / 2,
      y: gameAreaHeight / 2 - playerSize / 2,
    });

    // Spawn initial enemies
    spawnEnemies();
  }, []);

  const spawnEnemies = () => {
    const newEnemies = [];
    for (let i = 0; i < 5; i++) {
      newEnemies.push({
        id: i,
        x: Math.random() * (gameAreaWidth - enemySize),
        y: Math.random() * (gameAreaHeight - enemySize),
      });
    }
    setEnemies(newEnemies);
  };

  const movePlayer = (direction: 'up' | 'down' | 'left' | 'right') => {
    const moveDistance = fx(20).toPixels();
    let newX = playerPosition.x;
    let newY = playerPosition.y;

    switch (direction) {
      case 'up':
        newY = Math.max(0, newY - moveDistance);
        break;
      case 'down':
        newY = Math.min(gameAreaHeight - playerSize, newY + moveDistance);
        break;
      case 'left':
        newX = Math.max(0, newX - moveDistance);
        break;
      case 'right':
        newX = Math.min(gameAreaWidth - playerSize, newX + moveDistance);
        break;
    }

    setPlayerPosition({x: newX, y: newY});

    // Animate player movement
    Animated.spring(playerAnimation, {
      toValue: {x: newX, y: newY},
      useNativeDriver: false,
    }).start();

    // Check collisions
    checkCollisions(newX, newY);
  };

  const checkCollisions = (playerX: number, playerY: number) => {
    const updatedEnemies = enemies.filter(enemy => {
      const distance = Math.sqrt(
        Math.pow(playerX - enemy.x, 2) + Math.pow(playerY - enemy.y, 2),
      );
      return distance > (playerSize + enemySize) / 2;
    });

    if (updatedEnemies.length < enemies.length) {
      setScore(score + 10);
      setEnemies(updatedEnemies);

      // Spawn new enemy if needed
      if (updatedEnemies.length === 0) {
        spawnEnemies();
      }
    }
  };

  const resetGame = () => {
    setScore(0);
    setPlayerPosition({
      x: gameAreaWidth / 2 - playerSize / 2,
      y: gameAreaHeight / 2 - playerSize / 2,
    });
    spawnEnemies();
  };

  return (
    <SafeAreaView style={styles.container}>
      {/* Game HUD */}
      <View style={styles.hud}>
        <View style={styles.scoreContainer}>
          <Text style={styles.scoreText}>Score: {score}</Text>
        </View>

        <TouchableOpacity style={styles.resetButton} onPress={resetGame}>
          <Text style={styles.resetButtonText}>Reset</Text>
        </TouchableOpacity>
      </View>

      {/* Game Area */}
      <View
        style={[
          styles.gameArea,
          {width: gameAreaWidth, height: gameAreaHeight},
        ]}>
        {/* Player */}
        <Animated.View
          style={[
            styles.player,
            {
              width: playerSize,
              height: playerSize,
              left: playerAnimation.x,
              top: playerAnimation.y,
            },
          ]}
        />

        {/* Enemies */}
        {enemies.map(enemy => (
          <View
            key={enemy.id}
            style={[
              styles.enemy,
              {
                width: enemySize,
                height: enemySize,
                left: enemy.x,
                top: enemy.y,
              },
            ]}
          />
        ))}
      </View>

      {/* Control Buttons */}
      <View style={styles.controls}>
        {/* Movement Controls */}
        <View style={styles.movementControls}>
          <View style={styles.movementRow}>
            <TouchableOpacity
              style={[
                styles.controlButton,
                {width: gameButtonSize, height: gameButtonSize},
              ]}
              onPress={() => movePlayer('up')}>
              <Text style={styles.controlButtonText}>↑</Text>
            </TouchableOpacity>
          </View>

          <View style={styles.movementRow}>
            <TouchableOpacity
              style={[
                styles.controlButton,
                {width: gameButtonSize, height: gameButtonSize},
              ]}
              onPress={() => movePlayer('left')}>
              <Text style={styles.controlButtonText}>←</Text>
            </TouchableOpacity>

            <TouchableOpacity
              style={[
                styles.controlButton,
                {width: gameButtonSize, height: gameButtonSize},
              ]}
              onPress={() => movePlayer('right')}>
              <Text style={styles.controlButtonText}>→</Text>
            </TouchableOpacity>
          </View>

          <View style={styles.movementRow}>
            <TouchableOpacity
              style={[
                styles.controlButton,
                {width: gameButtonSize, height: gameButtonSize},
              ]}
              onPress={() => movePlayer('down')}>
              <Text style={styles.controlButtonText}>↓</Text>
            </TouchableOpacity>
          </View>
        </View>

        {/* Action Buttons */}
        <View style={styles.actionControls}>
          <TouchableOpacity
            style={[styles.actionButton, {padding: uiOverlaySize}]}
            onPress={spawnEnemies}>
            <Text style={styles.actionButtonText}>Spawn</Text>
          </TouchableOpacity>
        </View>
      </View>

      {/* Game Info */}
      <View style={styles.gameInfo}>
        <Text style={styles.infoText}>Device: {deviceInfo.type}</Text>
        <Text style={styles.infoText}>
          Screen: {Math.round(screenDimensions.width)} ×{' '}
          {Math.round(screenDimensions.height)}
        </Text>
        <Text style={styles.infoText}>
          Player Size: {Math.round(playerSize)}px
        </Text>
        <Text style={styles.infoText}>
          Button Size: {Math.round(gameButtonSize)}px
        </Text>
      </View>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#1a1a1a',
    alignItems: 'center',
    justifyContent: 'center',
  },
  hud: {
    position: 'absolute',
    top: 50,
    left: 0,
    right: 0,
    flexDirection: 'row',
    justifyContent: 'space-between',
    paddingHorizontal: 20,
    zIndex: 10,
  },
  scoreContainer: {
    backgroundColor: 'rgba(0, 0, 0, 0.7)',
    paddingHorizontal: 16,
    paddingVertical: 8,
    borderRadius: 20,
  },
  scoreText: {
    color: '#FFFFFF',
    fontSize: 18,
    fontWeight: 'bold',
  },
  resetButton: {
    backgroundColor: '#FF4444',
    paddingHorizontal: 16,
    paddingVertical: 8,
    borderRadius: 20,
  },
  resetButtonText: {
    color: '#FFFFFF',
    fontSize: 16,
    fontWeight: 'bold',
  },
  gameArea: {
    backgroundColor: '#2a2a2a',
    borderWidth: 2,
    borderColor: '#444444',
    borderRadius: 12,
    position: 'relative',
    marginVertical: 20,
  },
  player: {
    backgroundColor: '#00FF00',
    borderRadius: 50,
    position: 'absolute',
    shadowColor: '#00FF00',
    shadowOffset: {width: 0, height: 0},
    shadowOpacity: 0.8,
    shadowRadius: 10,
    elevation: 10,
  },
  enemy: {
    backgroundColor: '#FF0000',
    borderRadius: 50,
    position: 'absolute',
    shadowColor: '#FF0000',
    shadowOffset: {width: 0, height: 0},
    shadowOpacity: 0.8,
    shadowRadius: 8,
    elevation: 8,
  },
  controls: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    width: '100%',
    paddingHorizontal: 20,
    marginBottom: 20,
  },
  movementControls: {
    alignItems: 'center',
  },
  movementRow: {
    flexDirection: 'row',
    justifyContent: 'center',
    marginVertical: 4,
  },
  controlButton: {
    backgroundColor: 'rgba(255, 255, 255, 0.2)',
    borderRadius: 8,
    justifyContent: 'center',
    alignItems: 'center',
    marginHorizontal: 4,
    borderWidth: 1,
    borderColor: 'rgba(255, 255, 255, 0.3)',
  },
  controlButtonText: {
    color: '#FFFFFF',
    fontSize: 24,
    fontWeight: 'bold',
  },
  actionControls: {
    justifyContent: 'center',
  },
  actionButton: {
    backgroundColor: '#007AFF',
    borderRadius: 8,
    justifyContent: 'center',
    alignItems: 'center',
    minWidth: 80,
  },
  actionButtonText: {
    color: '#FFFFFF',
    fontSize: 16,
    fontWeight: 'bold',
  },
  gameInfo: {
    position: 'absolute',
    bottom: 20,
    left: 20,
    right: 20,
    backgroundColor: 'rgba(0, 0, 0, 0.7)',
    padding: 12,
    borderRadius: 8,
  },
  infoText: {
    color: '#FFFFFF',
    fontSize: 12,
    marginBottom: 2,
  },
});

// App wrapper with provider
export default function App() {
  return (
    <AppDimensProvider>
      <GameExample />
    </AppDimensProvider>
  );
}
