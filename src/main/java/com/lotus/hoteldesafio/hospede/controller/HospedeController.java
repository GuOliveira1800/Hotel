package com.lotus.hoteldesafio.hospede.controller;

import com.lotus.hoteldesafio.hospede.model.dto.HospedeDto;
import com.lotus.hoteldesafio.hospede.service.HospedeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("hospede")
public class HospedeController {

    @Autowired
    HospedeService hospedeService;

    @PostMapping("/salvar")
    public ResponseEntity<HospedeDto> salvar (@RequestBody HospedeDto hospedeDto){
        return hospedeService.salvar(hospedeDto);
    }

    @GetMapping("/lista/{id}")
    public ResponseEntity<HospedeDto> buscar (@PathVariable("id") Long id){
        return hospedeService.buscarPorId(id);
    }

    @GetMapping("/remove/{id}")
    public ResponseEntity<String> remover (@PathVariable("id") Long id){
        return hospedeService.excluir(id);
    }

    @GetMapping(value = "/lista")
    public ResponseEntity<List<HospedeDto>> listar(
            @RequestParam(defaultValue = "") String nome,
            @RequestParam(defaultValue = "") String documento,
            @RequestParam(defaultValue = "") String telefone
    ){
        return hospedeService.buscarHospedes(nome,documento,telefone);
    }
}
