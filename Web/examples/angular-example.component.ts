import { Component, OnInit, OnDestroy } from '@angular/core';
import { WebDimensService } from 'webdimens/angular';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-example',
  template: `
    <div class="container" [ngStyle]="{ padding: containerPadding }">
      <header [ngStyle]="{ height: headerHeight }">
        <h1 [ngStyle]="{ fontSize: titleSize }">
          WebDimens with Angular
        </h1>
      </header>
      
      <main>
        <p>Current breakpoint: {{ breakpoint }}</p>
        <p>Viewport: {{ viewport.width }}x{{ viewport.height }}</p>
        <p>Is Mobile: {{ isMobile ? 'Yes' : 'No' }}</p>
        
        <div class="grid" [ngStyle]="{ gap: gridGap }">
          <div class="card" [ngStyle]="{ padding: cardPadding }">
            Card 1
          </div>
          <div class="card" [ngStyle]="{ padding: cardPadding }">
            Card 2
          </div>
          <div class="card" [ngStyle]="{ padding: cardPadding }">
            Card 3
          </div>
        </div>
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
    }
    
    .grid {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
    }
    
    .card {
      border: 1px solid #ddd;
      border-radius: 8px;
      background: #f9f9f9;
    }
  `]
})
export class ExampleComponent implements OnInit, OnDestroy {
  // Dimensions
  headerHeight = '';
  containerPadding = '';
  titleSize = '';
  gridGap = '';
  cardPadding = '';
  
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
    
    this.headerHeight = wd.fx(64);
    this.containerPadding = wd.dy(24);
    this.titleSize = wd.fl(24, 48);
    this.gridGap = wd.dy(16);
    this.cardPadding = wd.fx(20);
  }
}

