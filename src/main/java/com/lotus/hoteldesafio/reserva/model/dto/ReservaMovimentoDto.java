package com.lotus.hoteldesafio.reserva.model.dto;

import jakarta.persistence.*;
import lombok.*;

@Getter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class ReservaMovimentoDto {

    private Long id;
    private Long idReserva;
    private String descricao;
    private Double valor;
    private int quantidade;

    public ReservaMovimentoDto(String descricao, int quantidade,Double valor, Long idReserva) {
        this.descricao = descricao;
        this.valor = valor;
        this.quantidade = quantidade;
        this.idReserva = idReserva;
    }
}
