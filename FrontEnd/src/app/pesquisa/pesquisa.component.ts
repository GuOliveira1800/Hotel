import {Component} from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ReservaService } from '../service/reserva/reserva.service';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { CommonModule } from '@angular/common';
import {MatListModule} from "@angular/material/list";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatCard, MatCardTitle} from "@angular/material/card";
import {MatButton} from "@angular/material/button";
import {MatDialog} from "@angular/material/dialog";
import {Observable} from "rxjs";
import {ModalValoresComponent} from "../modal-valores/modal-valores.component";

@Component({
  selector: 'app-pesquisa',
  standalone: true,
  imports: [FormsModule, MatCheckboxModule, CommonModule, MatListModule, MatFormFieldModule, MatInputModule, MatCard, MatCardTitle, MatButton],
  templateUrl: './pesquisa.component.html',
  styleUrls: ['./pesquisa.component.css'],
})
export class PesquisaComponent {
  hospedeNome: string = '';
  hospedeDocumento: string = '';
  hospedeTelefone: string = '';
  hospedeNoHotel: boolean = false;
  reservaNaoiniciada: boolean = false;
  listaHospedes : any[] = [];
  listaValores : any[] = [];

  constructor(private reservaService : ReservaService, private dialog: MatDialog) {}

  onSubmit() {
    const body = {
      hospede: {
        documento: this.hospedeDocumento.replace(/\D/g, ''),
        telefone: this.hospedeTelefone.replace(/\D/g, ''),
        nome: this.hospedeNome,
      },
      hospedeNoHotel: this.hospedeNoHotel,
      reservaNaoiniciada: this.reservaNaoiniciada,
    };

    this.reservaService.reqPost(body,"/pesquisa").subscribe(
      (data) => {
        console.log(data)
        this.listaHospedes = data;
      },
      (error) => {
        console.error('Erro ao pesquisar reservas:', error);
      }
    );
  }

  onCheckboxChange(selected: string) {
    if (selected === 'hospedeNoHotel') {
      this.reservaNaoiniciada = false;
    } else if (selected === 'reservaNaoiniciada') {
      this.hospedeNoHotel = false;
    }
  }

  onCheckIn(item: any) {
    console.log(item.dataCheckIn !== null)
    if(item.dataCheckIn === null){
    item.dataCheckIn = new Date().toISOString();
    this.listaHospedes.find((itemBusca) => itemBusca.id === item.id).dataCheckIn = item.dataCheckIn
    this.reservaService.reqPost(item,"/checkin");

    window.alert("Check In feito com sucesso");
    }else{
      window.alert("Check In ja feito");
    }
  }

  onCheckOut(item: any) {
    if(item.dataCheckOut === null){
      item.dataCheckOut = new Date().toISOString();
      this.listaHospedes.find((itemBusca) => itemBusca.id === item.id).dataCheckOut = item.dataCheckOut
      this.reservaService.reqPost(item,"/checkout").subscribe(
        (data) => {
          this.dialog.open(ModalValoresComponent, {
            data: data,
            maxWidth: '700px', // largura máxima como uma porcentagem da tela
            height: 'auto' // altura automática com base no conteúdo
          });

        },
        (error) => {
          console.error('Erro ao pesquisar reservas:', error);
        }
      );
    }
  }

}
