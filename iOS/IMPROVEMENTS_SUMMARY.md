# AppDimens iOS - Version 2.0 Improvements

**Major Update Summary**

---

## 🆕 New Features

1. **13 Scaling Strategies** (vs 2 in v1.x)
   - BALANCED ⭐ (primary)
   - LOGARITHMIC, POWER (perceptual)
   - FLUID, AUTOSIZE, and 8 more

2. **Smart Inference**
   - Automatic strategy selection
   - 18 element types
   - Weight-based algorithm

3. **Performance**
   - 5x faster overall
   - Lock-free cache
   - Pre-calculated constants

4. **Enhanced Metal Support**
   - Improved game scaling
   - SIMD optimizations

---

## 🔄 Breaking Changes

**None!** Fully backward compatible.

**Renamed for clarity:**
- Fixed → DEFAULT
- Dynamic → PERCENTAGE

**Old code still works** (deprecated warnings)

---

## 📈 Improvements

- 40% oversizing reduction on iPad (vs linear)
- 56% reduction on Apple TV
- Better typography support (FLUID)
- Game development enhancements

---

**Full Documentation:** [README.md](README.md)
