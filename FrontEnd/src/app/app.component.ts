import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { MenuLateralComponent } from './menu-lateral/menu-lateral.component';
import { CriarReservaComponent } from "./criar-reserva/criar-reserva.component";
import { PesquisaComponent } from "./pesquisa/pesquisa.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    RouterOutlet,
    MenuLateralComponent,
    CriarReservaComponent,
    MenuLateralComponent,
    PesquisaComponent
  ],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {

  title = 'FrontEnd';
}
