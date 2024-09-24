import { bootstrapApplication } from '@angular/platform-browser';
import { provideRouter, RouterModule } from '@angular/router';
import {HttpClient, provideHttpClient} from '@angular/common/http';
import { AppComponent } from './app/app.component';
import { routes } from './app/app.routes';
import {provideZoneChangeDetection} from "@angular/core";
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';

bootstrapApplication(AppComponent, {
  providers: [
    provideRouter(routes),
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideHttpClient(), provideAnimationsAsync()
  ]
}).catch(err => console.error(err));
