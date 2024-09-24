import { Component } from '@angular/core';
import {FormsModule} from "@angular/forms";
import {MatCheckbox} from "@angular/material/checkbox";
import {MatFormField, MatFormFieldModule, MatLabel} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {MatButton} from "@angular/material/button";
import {ReservaService} from "../service/reserva/reserva.service";
import {CommonModule} from "@angular/common"; // Para mat-error


@Component({
  selector: 'app-criar-reserva',
  standalone: true,
  imports: [
    FormsModule,
    MatCheckbox,
    MatFormField,
    MatInput,
    MatLabel,
    MatButton,
    MatFormFieldModule,
    CommonModule
  ],
  templateUrl: './criar-reserva.component.html',
  styleUrl: './criar-reserva.component.css'
})
export class CriarReservaComponent {
  reserva : any = {
    "id": null,
    "dataInicial": null,
    "dataFinal": null,
    "dataCheckIn": null,
    "dataCheckOut": null,
    "hospede": {
      "id": null,
      "nome": "",
      "documento": "",
      "telefone": "",
      "email": ""
    },
    "temEstacionamento": false
  }

  constructor(private reservaService : ReservaService) {}

  onSubmit() {
    if (!this.reserva.dataInicial || !this.reserva.dataFinal ||
      !this.reserva.hospede.nome || !this.reserva.hospede.documento ||
      !this.reserva.hospede.telefone) {
      window.alert("Por favor, preencha todos os campos obrigatórios.");
      return;
    }
    console.log('Criando reserva com:', this.reserva);
    this.reservaService.reqPost(this.reserva, "/salvar").subscribe(
      (response) => {
        console.log("Reserva salva com sucesso:", response);
        window.alert("Adicionado com sucesso");
        this.clearForm();
      },
      (error) => {
        console.error("Erro ao salvar a reserva:", error);
        window.alert("Erro ao adicionar a reserva. Tente novamente.");
      }
    );
  }
  clearForm() {
    this.reserva = {
      dataInicial: null,
      dataFinal: null,
      hospede: {
        nome: '',
        documento: '',
        telefone: '',
        email: ''
      },
      temEstacionamento: false // Ajuste conforme necessário
    };
  }


}
