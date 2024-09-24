import { Routes } from '@angular/router';
import { PesquisaComponent } from './pesquisa/pesquisa.component';
import { CriarReservaComponent } from './criar-reserva/criar-reserva.component';

export const routes: Routes = [
  { path: 'pesquisa', component: PesquisaComponent },
  { path: 'criar-reserva', component: CriarReservaComponent },
  { path: '', redirectTo: '/pesquisa', pathMatch: 'full' },
  { path: '**', redirectTo: '/pesquisa' }
];
