<script lang="ts">
  import { webDimensStore, viewportStore, breakpointStore } from 'webdimens/svelte';
  
  // Reactive stores
  $: wd = $webDimensStore;
  $: viewport = $viewportStore;
  $: breakpoint = $breakpointStore;
  
  // Calculate dimensions
  $: headerHeight = wd.fx(64);
  $: containerPadding = wd.dy(24);
  $: titleSize = wd.fl(24, 48);
  
  // Check breakpoint
  $: isMobile = breakpoint.current === 'mobile';
</script>

<div class="container" style="padding: {containerPadding}">
  <header style="height: {headerHeight}">
    <h1 style="font-size: {titleSize}">
      WebDimens with Svelte
    </h1>
  </header>
  
  <main>
    <p>Current breakpoint: {breakpoint.current}</p>
    <p>Viewport: {viewport.width}x{viewport.height}</p>
    <p>Is Mobile: {isMobile ? 'Yes' : 'No'}</p>
  </main>
</div>

<style>
  .container {
    max-width: 1200px;
    margin: 0 auto;
  }
  
  header {
    border-bottom: 1px solid #ccc;
  }
</style>

