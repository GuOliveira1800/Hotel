package com.lotus.hoteldesafio;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.lotus.hoteldesafio.hospede.model.dto.HospedeDto;
import com.lotus.hoteldesafio.hospede.model.entity.HospedeEntity;
import com.lotus.hoteldesafio.hospede.model.repository.HospedeRepository;
import com.lotus.hoteldesafio.reserva.model.dto.ReservaDto;
import com.lotus.hoteldesafio.reserva.model.entity.ReservaEntity;
import com.lotus.hoteldesafio.reserva.model.repository.ReservaMovimentoRepository;
import com.lotus.hoteldesafio.reserva.model.repository.ReservaRepository;
import com.lotus.hoteldesafio.reserva.model.dto.ReservaMovimentoDto;
import com.lotus.hoteldesafio.reserva.service.ReservaService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class HotelDesafioApplicationTests {

    @InjectMocks
    private ReservaService reservaService;

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private HospedeRepository hospedeRepository;

    @Mock
    private ReservaMovimentoRepository reservaMovimentoRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void deveSalvarReserva() {
        ReservaDto reservaDto = new ReservaDto(null, new Date(), new Date(), null, null,
            new HospedeDto(null,"Gustavo Oliveira","08991198910","33979966","teste@gmail.com"), false);
        ReservaEntity reservaEntity = new ReservaEntity(reservaDto);
        when(reservaRepository.save(any(ReservaEntity.class)))
                .thenReturn(new ReservaEntity(1L, reservaDto.getDataInicial(), reservaDto.getDataFinal(), null, null, false, new HospedeEntity(reservaDto.getHospede())));
        ReservaDto result = reservaService.salvar(reservaDto).getBody();
        assertNotNull(result);
        assertNotNull(result.getId());
    }

    @Test
    public void deveFazerCheckInComSucesso() {
        // Configuração do cenário de sucesso
        long reservaId = 1L;
        Date dataCheckIn = stringToDate("15/09/2024 14:00"); // Data e hora válida para check-in
        ReservaDto reservaDto = new ReservaDto(reservaId, null, null, dataCheckIn, null, new HospedeDto(), false);

        ReservaEntity reservaEntity = new ReservaEntity(reservaDto);
        reservaEntity.setDataCheckIn(null); // Check-in ainda não foi feito

        when(reservaRepository.findById(reservaId)).thenReturn(Optional.of(reservaEntity));
        when(reservaRepository.save(any(ReservaEntity.class))).thenReturn(reservaEntity);

        ResponseEntity<String> response = reservaService.checkIn(reservaDto);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Check In feito com sucesso!", response.getBody());
        assertNotNull(reservaEntity.getDataCheckIn()); // Verifica se a data de check-in foi setada
    }

    @Test
    public void deveFazerCheckInSemSucesso() {
        // Configuração do cenário de sucesso
        long reservaId = 1L;
        Date dataCheckIn = stringToDate("15/09/2024 08:00"); // Data e hora válida para check-in
        ReservaDto reservaDto = new ReservaDto(reservaId, null, null, dataCheckIn, null, new HospedeDto(), false);

        ReservaEntity reservaEntity = new ReservaEntity(reservaDto);
        reservaEntity.setDataCheckIn(null); // Check-in ainda não foi feito

        when(reservaRepository.findById(reservaId)).thenReturn(Optional.of(reservaEntity));
        when(reservaRepository.save(any(ReservaEntity.class))).thenReturn(reservaEntity);

        ResponseEntity<String> response = reservaService.checkIn(reservaDto);
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void testCheckOutValores() {
        ReservaDto reservaDto = new ReservaDto(null,
                stringToDate("15/09/2024 00:00"),
                stringToDate("16/09/2024 00:00"),
                stringToDate("15/09/2024 15:00"),
                stringToDate("17/09/2024 14:00"),
                new HospedeDto(null,"Gustavo Oliveira","08991198910","33979966","teste@gmail.com"),
                true);

        List<ReservaMovimentoDto> response = reservaService.calculaMovimento(reservaDto);
        List<ReservaMovimentoDto> movimentos = response;
        assertNotNull(movimentos);
        assertFalse(movimentos.isEmpty());
        assertEquals(502.5, movimentos.stream().mapToDouble(ReservaMovimentoDto::getValor).sum());

        reservaDto = new ReservaDto(null,
                stringToDate("15/09/2024 00:00"),
                stringToDate("16/09/2024 00:00"),
                stringToDate("15/09/2024 15:00"),
                stringToDate("17/09/2024 08:00"),
                new HospedeDto(null,"Gustavo Oliveira","08991198910","33979966","teste@gmail.com"),
                false);

        response = reservaService.calculaMovimento(reservaDto);
        movimentos = response;
        assertNotNull(movimentos);
        assertFalse(movimentos.isEmpty());
        assertEquals(300.0, movimentos.stream().mapToDouble(ReservaMovimentoDto::getValor).sum());
    }

    public static Date stringToDate(String dateString) {
        // Define o formato esperado para a data e hora
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        // Converte a string para um objeto Date
        try {
            return formatter.parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

}
