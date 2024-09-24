package com.lotus.hoteldesafio.reserva.model.entity;

import com.lotus.hoteldesafio.hospede.model.entity.HospedeEntity;
import com.lotus.hoteldesafio.reserva.model.dto.ReservaDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reserva",schema = "hotel")
public class ReservaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date dataInicial;
    private Date dataFinal;

    private Date dataCheckOut;
    private Date dataCheckIn;

    private boolean temEstacionamento;

    @ManyToOne
    @JoinColumn(name = "id_hospede", nullable = false)
    private HospedeEntity id_hospede;


    public ReservaEntity(ReservaDto reservaDto) {
        this.id = reservaDto.getId();
        this.dataInicial = reservaDto.getDataInicial();
        this.dataFinal = reservaDto.getDataFinal();
        this.temEstacionamento = reservaDto.isTemEstacionamento();
        this.id_hospede = new HospedeEntity(reservaDto.getHospede());
    }

}
