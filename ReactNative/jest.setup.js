// Mock React Native modules
jest.mock('react-native', () => {
  // Mock Dimensions
  const mockDimensions = {
    get: jest.fn(() => ({
      width: 375,
      height: 667,
      scale: 2,
      fontScale: 1,
    })),
    addEventListener: jest.fn(),
    removeEventListener: jest.fn(),
  };
  
  // Mock PixelRatio
  const mockPixelRatio = {
    get: jest.fn(() => 2),
    getFontScale: jest.fn(() => 1),
    getPixelSizeForLayoutSize: jest.fn((size) => size * 2),
    roundToNearestPixel: jest.fn((size) => size),
  };
  
  // Mock Platform
  const mockPlatform = {
    OS: 'ios',
    select: jest.fn((obj) => obj.ios || obj.default),
  };
  
  return {
    Dimensions: mockDimensions,
    PixelRatio: mockPixelRatio,
    Platform: mockPlatform,
    StyleSheet: {
      create: jest.fn((styles) => styles),
      flatten: jest.fn((styles) => styles),
    },
  };
});

// Mock console methods to reduce noise in tests
global.console = {
  ...console,
  log: jest.fn(),
  debug: jest.fn(),
  info: jest.fn(),
  warn: jest.fn(),
  error: jest.fn(),
};
