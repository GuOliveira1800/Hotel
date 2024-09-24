package com.lotus.hoteldesafio.reserva.model.entity;

import com.lotus.hoteldesafio.reserva.model.dto.ReservaMovimentoDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reserva_movimento",schema = "hotel")
public class ReservaMovimentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long idReserva;
    private String descricao;
    private int quantidade;
    private Double valor;

    public ReservaMovimentoEntity(ReservaMovimentoDto reservaMovimentoDto) {
        this.id = reservaMovimentoDto.getId();
        this.idReserva = reservaMovimentoDto.getIdReserva();
        this.descricao = reservaMovimentoDto.getDescricao();
        this.quantidade = reservaMovimentoDto.getQuantidade();
        this.valor = reservaMovimentoDto.getValor();
    }
}
