package com.lotus.hoteldesafio.reserva.model.dto;

import com.lotus.hoteldesafio.hospede.model.dto.HospedeDto;
import com.lotus.hoteldesafio.reserva.model.entity.ReservaEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class ReservaDto {

    private Long id;
    private Date dataInicial;
    private Date dataFinal;
    private Date dataCheckIn;
    private Date dataCheckOut;
    private HospedeDto hospede;
    private boolean temEstacionamento;

    public ReservaDto (ReservaEntity reservaEntity) {
        this.id = reservaEntity.getId();
        this.dataInicial = reservaEntity.getDataInicial();
        this.dataFinal = reservaEntity.getDataFinal();
        if(reservaEntity.getId_hospede() != null) {
            this.hospede = new HospedeDto(reservaEntity.getId_hospede());
        }
        this.temEstacionamento = reservaEntity.isTemEstacionamento();
        this.dataCheckIn = reservaEntity.getDataCheckIn();
        this.dataCheckOut = reservaEntity.getDataCheckOut();
    }

    public List<ReservaDto> converteLista(List<ReservaEntity> reservas) {
        List<ReservaDto> hospedeDtos = new ArrayList<>();
        for (ReservaEntity hospedeEntity : reservas) {
            hospedeDtos.add(new ReservaDto(hospedeEntity));
        }
        return hospedeDtos;
    }

}
