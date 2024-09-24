import {Component, Inject} from '@angular/core';
import {MatList, MatListItem} from "@angular/material/list";
import {
  MAT_DIALOG_DATA,
  MatDialogActions,
  MatDialogClose,
  MatDialogContent,
  MatDialogTitle
} from "@angular/material/dialog";
import {CommonModule, CurrencyPipe} from "@angular/common";
import {MatButton} from "@angular/material/button";

@Component({
  selector: 'app-modal-valores',
  standalone: true,
  imports: [
    MatList,
    MatListItem,
    CurrencyPipe,
    MatDialogTitle,
    MatDialogContent,
    MatDialogActions,
    MatButton,
    MatDialogClose,
    CommonModule
  ],
  templateUrl: './modal-valores.component.html',
  styleUrl: './modal-valores.component.css'
})
export class ModalValoresComponent {
  constructor(@Inject(MAT_DIALOG_DATA) public data: any) {}

  calcularTotal(itens: any[]): number {
    return itens.reduce((total, item) => total + item.valor, 0);
  }
}
