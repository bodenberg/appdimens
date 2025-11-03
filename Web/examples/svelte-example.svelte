<!--
  Svelte Integration Example - WebDimens v2.0.0
  
  This example demonstrates the new scaling strategies introduced in v2.0.0
-->

<script lang="ts">
  import { webDimensStore, viewportStore, breakpointStore } from 'webdimens/svelte';
  
  // Reactive stores
  $: wd = $webDimensStore;
  $: viewport = $viewportStore;
  $: breakpoint = $breakpointStore;
  
  // Basic dimensions (Legacy + New)
  $: headerHeight = wd.fx(64);              // Legacy API (still works)
  $: containerPadding = wd.balanced(24);    // NEW: balanced() ⭐ RECOMMENDED
  $: titleSize = wd.fl(28, 42);             // Fluid (existing)
  $: subtitleSize = wd.balanced(16);        // NEW: balanced() ⭐
  $: sectionTitleSize = wd.balanced(20);    // NEW: balanced() ⭐
  
  // New Strategies (v2.0.0)
  $: balancedPadding = wd.balanced(16);     // ⭐ RECOMMENDED for most apps
  $: balancedBox = wd.balanced(48);         // Linear on phones, logarithmic on tablets
  $: logarithmicBox = wd.logarithmic(48);   // Pure logarithmic (Weber-Fechner)
  $: powerBox = wd.power(48, 0.75);         // Stevens' Power Law
  $: percentageBox = wd.percentage(48);     // 100% proportional (old Dynamic)
  
  // Smart API examples (auto-inference)
  $: smartButtonPadding = wd.balanced(12);      // Would use: smart(12).forElement('button')
  $: smartContainerPadding = wd.percentage(16); // Would use: smart(16).forElement('container')
  $: smartTextPadding = wd.balanced(10);        // Would use: smart(10).forElement('text')
  
  // Grid gap
  $: gridGap = wd.balanced(16);
  
  // Check breakpoint
  $: isMobile = breakpoint.current === 'mobile';
</script>

<div class="container" style="padding: {containerPadding}">
  <header style="height: {headerHeight}">
    <h1 style="font-size: {titleSize}">
      WebDimens v2.0.0 with Svelte
    </h1>
    <p class="subtitle" style="font-size: {subtitleSize}">
      13 Scaling Strategies + Smart API
    </p>
  </header>
  
  <main>
    <!-- Viewport Info -->
    <section class="info-section">
      <h2 style="font-size: {sectionTitleSize}">Viewport Info</h2>
      <p>Current breakpoint: {breakpoint.current}</p>
      <p>Viewport: {viewport.width}x{viewport.height}</p>
      <p>Is Mobile: {isMobile ? 'Yes' : 'No'}</p>
    </section>
    
    <!-- New Strategies -->
    <section class="strategies-section">
      <h2 style="font-size: {sectionTitleSize}">New Strategies (v2.0.0)</h2>
      <div class="strategy-grid" style="gap: {gridGap}">
        
        <div class="strategy-card balanced" style="padding: {balancedPadding}">
          <h3>BALANCED ⭐</h3>
          <p>Recommended for most apps</p>
          <div class="demo-box" style="width: {balancedBox}; height: {balancedBox}"></div>
          <code>balanced(48)</code>
        </div>
        
        <div class="strategy-card logarithmic" style="padding: {balancedPadding}">
          <h3>LOGARITHMIC</h3>
          <p>Maximum control</p>
          <div class="demo-box" style="width: {logarithmicBox}; height: {logarithmicBox}"></div>
          <code>logarithmic(48)</code>
        </div>
        
        <div class="strategy-card power" style="padding: {balancedPadding}">
          <h3>POWER</h3>
          <p>Stevens' Law</p>
          <div class="demo-box" style="width: {powerBox}; height: {powerBox}"></div>
          <code>power(48, 0.75)</code>
        </div>
        
        <div class="strategy-card percentage" style="padding: {balancedPadding}">
          <h3>PERCENTAGE</h3>
          <p>Pure proportional</p>
          <div class="demo-box" style="width: {percentageBox}; height: {percentageBox}"></div>
          <code>percentage(48)</code>
        </div>
        
      </div>
    </section>
    
    <!-- Smart API -->
    <section class="smart-api-section">
      <h2 style="font-size: {sectionTitleSize}">🧠 Smart API</h2>
      <p>Auto-inference based on element type</p>
      <div class="smart-examples" style="gap: {gridGap}">
        <div class="smart-card" style="padding: {smartButtonPadding}">
          <strong>Button</strong>
          <div>→ BALANCED</div>
        </div>
        <div class="smart-card" style="padding: {smartContainerPadding}">
          <strong>Container</strong>
          <div>→ PERCENTAGE</div>
        </div>
        <div class="smart-card" style="padding: {smartTextPadding}">
          <strong>Text</strong>
          <div>→ BALANCED</div>
        </div>
      </div>
    </section>
    
    <!-- Comparison -->
    <section class="comparison-section">
      <h2 style="font-size: {sectionTitleSize}">Strategy Comparison (48px base)</h2>
      <table>
        <tr>
          <th>Strategy</th>
          <th>Result</th>
          <th>Best For</th>
        </tr>
        <tr>
          <td>BALANCED ⭐</td>
          <td>{balancedBox}</td>
          <td>Multi-device apps</td>
        </tr>
        <tr>
          <td>LOGARITHMIC</td>
          <td>{logarithmicBox}</td>
          <td>TVs, large tablets</td>
        </tr>
        <tr>
          <td>POWER</td>
          <td>{powerBox}</td>
          <td>General purpose</td>
        </tr>
        <tr>
          <td>PERCENTAGE</td>
          <td>{percentageBox}</td>
          <td>Containers, layouts</td>
        </tr>
      </table>
    </section>
  </main>
</div>

<style>
  .container {
    max-width: 1200px;
    margin: 0 auto;
    font-family: system-ui, -apple-system, sans-serif;
  }
  
  header {
    border-bottom: 2px solid #ccc;
    padding: 20px 0;
  }
  
  .subtitle {
    color: #666;
    margin-top: 8px;
  }
  
  section {
    margin: 32px 0;
    padding: 20px;
    border-radius: 12px;
  }
  
  .info-section {
    background: #e3f2fd;
  }
  
  .strategies-section {
    background: #fff3e0;
  }
  
  .strategy-grid {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
    margin-top: 16px;
  }
  
  .strategy-card {
    border: 2px solid;
    border-radius: 12px;
    text-align: center;
    padding: 16px;
  }
  
  .strategy-card.balanced {
    background: #e8f5e9;
    border-color: #4caf50;
  }
  
  .strategy-card.logarithmic {
    background: #e3f2fd;
    border-color: #2196f3;
  }
  
  .strategy-card.power {
    background: #f3e5f5;
    border-color: #9c27b0;
  }
  
  .strategy-card.percentage {
    background: #fff9c4;
    border-color: #fbc02d;
  }
  
  .strategy-card h3 {
    margin: 0 0 8px 0;
    font-size: 16px;
  }
  
  .strategy-card p {
    font-size: 12px;
    color: #666;
    margin: 0 0 12px 0;
  }
  
  .strategy-card code {
    font-size: 10px;
    background: rgba(0,0,0,0.1);
    padding: 4px 8px;
    border-radius: 4px;
    display: block;
    margin-top: 8px;
  }
  
  .demo-box {
    background: rgba(0,0,0,0.2);
    margin: 12px auto;
    border-radius: 8px;
  }
  
  .smart-api-section {
    background: #fce4ec;
  }
  
  .smart-examples {
    display: flex;
    flex-wrap: wrap;
    margin-top: 16px;
  }
  
  .smart-card {
    background: white;
    border: 2px solid #e91e63;
    border-radius: 8px;
    flex: 1;
    min-width: 150px;
    text-align: center;
    padding: 12px;
  }
  
  .smart-card strong {
    display: block;
    margin-bottom: 4px;
  }
  
  .smart-card div {
    font-size: 12px;
    color: #e91e63;
    font-weight: bold;
  }
  
  .comparison-section {
    background: #f5f5f5;
  }
  
  table {
    width: 100%;
    border-collapse: collapse;
    margin-top: 16px;
  }
  
  th, td {
    padding: 12px;
    text-align: left;
    border-bottom: 1px solid #ddd;
  }
  
  th {
    background: #e0e0e0;
    font-weight: bold;
  }
  
  tr:hover {
    background: #f9f9f9;
  }
</style>

