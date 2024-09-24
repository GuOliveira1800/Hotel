package com.lotus.hoteldesafio.hospede.service;

import com.lotus.hoteldesafio.hospede.model.dto.HospedeDto;
import com.lotus.hoteldesafio.hospede.model.entity.HospedeEntity;
import com.lotus.hoteldesafio.hospede.model.repository.HospedeRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HospedeService {

    @Autowired
    private HospedeRepository hospedeRepository;

    public ResponseEntity<HospedeDto> salvar(HospedeDto hospedeDto) {
        HospedeEntity hospede = new HospedeEntity(hospedeDto);
        hospedeRepository.save(hospede);
        return ResponseEntity.ok(hospedeDto);
    }

    public ResponseEntity<List<HospedeDto>> listar() {
        List<HospedeEntity> hospedeEntities = hospedeRepository.findAll();
        List<HospedeDto> hospedeDtos = new ArrayList<>();
        for (HospedeEntity hospedeEntity : hospedeEntities) {
            HospedeDto hospedeDto = new HospedeDto(hospedeEntity);
            hospedeDtos.add(hospedeDto);
        }
        return ResponseEntity.ok(hospedeDtos);
    }
    public ResponseEntity<HospedeDto> buscarPorId(Long id) {
        Optional<HospedeEntity> hospedeEntity = hospedeRepository.findById(id);
        if (hospedeEntity.isPresent()) {
            HospedeDto hospedeDto = new HospedeDto(hospedeEntity.get());
            return ResponseEntity.ok(hospedeDto);
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<String> excluir(Long id) {
        hospedeRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<List<HospedeDto>> buscarHospedes(String nome, String documento, String telefone) {
        List<HospedeEntity> hospedeEntities = hospedeRepository.findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (nome != null && !nome.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("nome"), "%" + nome + "%"));
            }
            if (documento != null && !documento.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("documento"), "%" + documento + "%"));
            }
            if (telefone != null && !telefone.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("telefone"), "%" + telefone + "%"));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });

        return ResponseEntity.ok(new HospedeDto().converteLista(hospedeEntities));
    }
}
