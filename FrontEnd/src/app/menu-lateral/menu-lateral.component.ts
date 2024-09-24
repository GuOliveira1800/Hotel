import { Component } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { filter } from 'rxjs';

@Component({
  selector: 'app-menu-lateral',
  standalone: true,
  templateUrl: './menu-lateral.component.html',
  styleUrls: ['./menu-lateral.component.css']
})
export class MenuLateralComponent {
  currentRoute: string = '';

  constructor(private router: Router) {
    this.router.events
      .pipe(filter(event => event instanceof NavigationEnd))
      .subscribe((event: NavigationEnd) => {
        this.currentRoute = event.url; // Atualiza a rota atual
      });
  }

  isActive(route: string): boolean {
    return this.currentRoute === route; // Verifica se a rota atual é a rota do item do menu
  }

  navigateTo(route: string) {
    this.router.navigate([route]);
  }
}
