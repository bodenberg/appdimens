/**
 * Angular Integration Example - WebDimens v2.0.0
 * 
 * This example demonstrates the new scaling strategies introduced in v2.0.0
 */

import { Component, OnInit, OnDestroy } from '@angular/core';
import { WebDimensService } from 'webdimens/angular';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-example',
  template: `
    <div class="container" [ngStyle]="{ padding: containerPadding }">
      <header [ngStyle]="{ height: headerHeight }">
        <h1 [ngStyle]="{ fontSize: titleSize }">
          WebDimens v2.0.0 with Angular
        </h1>
        <p class="subtitle" [ngStyle]="{ fontSize: subtitleSize }">
          13 Scaling Strategies + Smart API
        </p>
      </header>
      
      <main>
        <section class="info-section">
          <h2 [ngStyle]="{ fontSize: sectionTitleSize }">Viewport Info</h2>
          <p>Current breakpoint: {{ breakpoint }}</p>
          <p>Viewport: {{ viewport.width }}x{{ viewport.height }}</p>
          <p>Is Mobile: {{ isMobile ? 'Yes' : 'No' }}</p>
        </section>
        
        <section class="strategies-section">
          <h2 [ngStyle]="{ fontSize: sectionTitleSize }">New Strategies (v2.0.0)</h2>
          <div class="strategy-grid" [ngStyle]="{ gap: gridGap }">
            <div class="strategy-card balanced" [ngStyle]="{ padding: balancedPadding }">
              <h3>BALANCED ⭐</h3>
              <p>Recommended for most apps</p>
              <div class="demo-box" [ngStyle]="{ width: balancedBox, height: balancedBox }"></div>
            </div>
            
            <div class="strategy-card logarithmic" [ngStyle]="{ padding: cardPadding }">
              <h3>LOGARITHMIC</h3>
              <p>Maximum control on large screens</p>
              <div class="demo-box" [ngStyle]="{ width: logarithmicBox, height: logarithmicBox }"></div>
            </div>
            
            <div class="strategy-card power" [ngStyle]="{ padding: cardPadding }">
              <h3>POWER</h3>
              <p>Stevens' Power Law</p>
              <div class="demo-box" [ngStyle]="{ width: powerBox, height: powerBox }"></div>
            </div>
          </div>
        </section>
        
        <section class="smart-api-section">
          <h2 [ngStyle]="{ fontSize: sectionTitleSize }">🧠 Smart API</h2>
          <p [ngStyle]="{ fontSize: bodyTextSize }">
            Auto-inference based on element type
          </p>
          <div class="smart-examples" [ngStyle]="{ gap: gridGap }">
            <div class="smart-card" [ngStyle]="{ padding: smartButtonPadding }">
              <strong>Button</strong> → BALANCED
            </div>
            <div class="smart-card" [ngStyle]="{ padding: smartContainerPadding }">
              <strong>Container</strong> → PERCENTAGE
            </div>
            <div class="smart-card" [ngStyle]="{ padding: smartTextPadding }">
              <strong>Text</strong> → BALANCED
            </div>
          </div>
        </section>
        
        <section class="legacy-section">
          <h2 [ngStyle]="{ fontSize: sectionTitleSize }">Classic Grid (Legacy API)</h2>
          <div class="grid" [ngStyle]="{ gap: gridGap }">
            <div class="card" [ngStyle]="{ padding: cardPadding }">
              Card 1 (Default)
            </div>
            <div class="card" [ngStyle]="{ padding: cardPadding }">
              Card 2 (Default)
            </div>
            <div class="card" [ngStyle]="{ padding: cardPadding }">
              Card 3 (Default)
            </div>
          </div>
        </section>
      </main>
    </div>
  `,
  styles: [`
    .container {
      max-width: 1200px;
      margin: 0 auto;
    }
    
    header {
      border-bottom: 1px solid #ccc;
      padding: 20px 0;
    }
    
    .subtitle {
      color: #666;
      margin-top: 8px;
    }
    
    section {
      margin: 32px 0;
    }
    
    .info-section {
      background: #e3f2fd;
      padding: 16px;
      border-radius: 12px;
    }
    
    .strategies-section {
      background: #fff3e0;
      padding: 20px;
      border-radius: 12px;
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
    
    .demo-box {
      background: rgba(0,0,0,0.2);
      margin: 12px auto;
      border-radius: 8px;
    }
    
    .smart-api-section {
      background: #fce4ec;
      padding: 20px;
      border-radius: 12px;
    }
    
    .smart-examples {
      display: flex;
      flex-wrap: wrap;
      margin-top: 16px;
    }
    
    .smart-card {
      background: white;
      border: 1px solid #e91e63;
      border-radius: 8px;
      flex: 1;
      min-width: 150px;
      text-align: center;
    }
    
    .legacy-section {
      background: #f5f5f5;
      padding: 20px;
      border-radius: 12px;
    }
    
    .grid {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
    }
    
    .card {
      border: 1px solid #ddd;
      border-radius: 8px;
      background: #fff;
    }
  `]
})
export class ExampleComponent implements OnInit, OnDestroy {
  // Basic Dimensions (Legacy API)
  headerHeight = '';
  containerPadding = '';
  titleSize = '';
  subtitleSize = '';
  sectionTitleSize = '';
  bodyTextSize = '';
  gridGap = '';
  cardPadding = '';
  
  // New Strategies (v2.0.0)
  balancedPadding = '';
  balancedBox = '';
  logarithmicBox = '';
  powerBox = '';
  
  // Smart API examples
  smartButtonPadding = '';
  smartContainerPadding = '';
  smartTextPadding = '';
  
  // Viewport data
  viewport = { width: 0, height: 0, aspectRatio: 0 };
  breakpoint = '';
  isMobile = false;
  
  private subscriptions = new Subscription();
  
  constructor(private webDimens: WebDimensService) {}
  
  ngOnInit(): void {
    // Subscribe to viewport changes
    this.subscriptions.add(
      this.webDimens.viewport$.subscribe(viewport => {
        this.viewport = viewport;
        this.updateDimensions();
      })
    );
    
    // Subscribe to breakpoint changes
    this.subscriptions.add(
      this.webDimens.breakpoint$.subscribe(breakpoint => {
        this.breakpoint = breakpoint.current;
        this.isMobile = breakpoint.current === 'mobile';
      })
    );
  }
  
  ngOnDestroy(): void {
    this.subscriptions.unsubscribe();
  }
  
  private updateDimensions(): void {
    const wd = this.webDimens.instance;
    
    // Legacy API (still works, but deprecated)
    this.headerHeight = wd.fx(64);           // OLD: fx() → NEW: default()
    this.containerPadding = wd.balanced(24); // NEW in v2.0.0 ⭐
    this.gridGap = wd.balanced(16);          // NEW: balanced() - RECOMMENDED
    this.cardPadding = wd.default(20);       // NEW: default() (was fx)
    
    // Typography with new strategies
    this.titleSize = wd.fl(28, 42);          // Fluid (existing)
    this.subtitleSize = wd.balanced(16);     // NEW: balanced() ⭐
    this.sectionTitleSize = wd.balanced(20); // NEW: balanced() ⭐
    this.bodyTextSize = wd.balanced(14);     // NEW: balanced() ⭐
    
    // New strategies demonstration
    this.balancedPadding = wd.balanced(16);        // ⭐ RECOMMENDED
    this.balancedBox = wd.balanced(48);            // Linear on phones, log on tablets
    this.logarithmicBox = wd.logarithmic(48);      // Pure logarithmic
    this.powerBox = wd.power(48, 0.75);            // Stevens' Power Law
    
    // Smart API (auto-inference based on element type)
    // In a real app, you would use: wd.smart(16).forElement('button')
    this.smartButtonPadding = wd.balanced(12);     // → BALANCED for buttons
    this.smartContainerPadding = wd.percentage(16); // → PERCENTAGE for containers
    this.smartTextPadding = wd.balanced(10);       // → BALANCED for text
  }
}

