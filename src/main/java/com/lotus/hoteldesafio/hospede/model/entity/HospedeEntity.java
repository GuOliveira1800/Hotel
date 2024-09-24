package com.lotus.hoteldesafio.hospede.model.entity;

import com.lotus.hoteldesafio.hospede.model.dto.HospedeDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hospede",schema = "hotel")
public class HospedeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String documento;
    private String telefone;
    private String email;

    public HospedeEntity(HospedeDto hospedeDto) {
        this.id = hospedeDto.getId();
        this.nome = hospedeDto.getNome();
        this.documento = hospedeDto.getDocumento();
        this.telefone = hospedeDto.getTelefone();
        this.email = hospedeDto.getEmail();
    }
}
