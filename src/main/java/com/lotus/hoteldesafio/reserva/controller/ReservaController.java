package com.lotus.hoteldesafio.reserva.controller;

import com.lotus.hoteldesafio.reserva.model.dto.PesquisaDto;
import com.lotus.hoteldesafio.reserva.model.dto.ReservaDto;
import com.lotus.hoteldesafio.reserva.model.dto.ReservaMovimentoDto;
import com.lotus.hoteldesafio.reserva.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("reserva")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @PostMapping("/simula")
    public ResponseEntity<List<ReservaMovimentoDto>> simula (@RequestBody ReservaDto reservaDto){
        return reservaService.simulaReserva(reservaDto);
    }

    @PostMapping("/checkout")
    public ResponseEntity<List<ReservaMovimentoDto>> checkOut (@RequestBody ReservaDto reservaDto){
        return reservaService.checkOut(reservaDto);
    }

    @PostMapping("/checkin")
    public ResponseEntity<String> checkIn (@RequestBody ReservaDto reservaDto){
        return reservaService.checkIn(reservaDto);
    }

    @GetMapping("/remove/{id}")
    public ResponseEntity<String> remover (@PathVariable("id") Long id){
        return reservaService.excluir(id);
    }

    @PostMapping("/salvar")
    public ResponseEntity<ReservaDto> salvar (@RequestBody ReservaDto reservaDto){
        return reservaService.salvar(reservaDto);
    }

    @GetMapping(value = "/lista")
    public ResponseEntity<List<ReservaDto>> listar(){
        return reservaService.listar();
    }

    @GetMapping("/lista/{id}")
    public ResponseEntity<ReservaDto> listar (@PathVariable("id") Long id){
        return reservaService.buscarPorId(id);
    }

    @PostMapping("/pesquisa")
    public ResponseEntity<List<ReservaDto>> buscarReservas (@RequestBody PesquisaDto pesquisaDto){
        return reservaService.buscarReservas(pesquisaDto);
    }

}
