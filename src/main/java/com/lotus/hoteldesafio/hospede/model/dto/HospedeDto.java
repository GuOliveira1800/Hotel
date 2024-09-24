package com.lotus.hoteldesafio.hospede.model.dto;

import com.lotus.hoteldesafio.hospede.model.entity.HospedeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class HospedeDto {
    private Long id;
    private String nome;
    private String documento;
    private String telefone;
    private String email;

    public HospedeDto(HospedeEntity hospede) {
        this.id = hospede.getId();
        this.nome = hospede.getNome();
        this.documento = hospede.getDocumento();
        this.telefone = hospede.getTelefone();
        this.email = hospede.getEmail();
    }

    public List<HospedeDto> converteLista(List<HospedeEntity> hospedes) {
        List<HospedeDto> hospedeDtos = new ArrayList<>();
        for (HospedeEntity hospedeEntity : hospedes) {
            hospedeDtos.add(new HospedeDto(hospedeEntity));
        }
        return hospedeDtos;
    }

}
