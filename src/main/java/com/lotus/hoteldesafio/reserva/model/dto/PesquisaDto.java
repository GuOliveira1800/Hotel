package com.lotus.hoteldesafio.reserva.model.dto;

import com.lotus.hoteldesafio.hospede.model.dto.HospedeDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.util.Date;

@Getter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class PesquisaDto {

    private Date dataInicial;
    private Date dataFinal;
    private Date dataCheckOut;
    private Date dataCheckIn;
    private HospedeDto hospede;
    private boolean hospedeNoHotel;
    private boolean reservaNaoiniciada;

}
