package com.lotus.hoteldesafio.reserva.service;

import com.lotus.hoteldesafio.hospede.model.entity.HospedeEntity;
import com.lotus.hoteldesafio.hospede.model.repository.HospedeRepository;
import com.lotus.hoteldesafio.reserva.model.Valores.ValoresMovimento;
import com.lotus.hoteldesafio.reserva.model.dto.PesquisaDto;
import com.lotus.hoteldesafio.reserva.model.dto.ReservaDto;
import com.lotus.hoteldesafio.reserva.model.dto.ReservaMovimentoDto;
import com.lotus.hoteldesafio.reserva.model.entity.ReservaEntity;
import com.lotus.hoteldesafio.reserva.model.entity.ReservaMovimentoEntity;
import com.lotus.hoteldesafio.reserva.model.repository.ReservaMovimentoRepository;
import com.lotus.hoteldesafio.reserva.model.repository.ReservaRepository;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private HospedeRepository hospedeRepository;

    @Autowired
    private ReservaMovimentoRepository reservaMovimentoRepository;

    public ResponseEntity<ReservaDto> salvar(ReservaDto reservaDto) {
        ReservaEntity reserva = new ReservaEntity(reservaDto);
        hospedeRepository.save(reserva.getId_hospede());
        ReservaEntity reservaSalva = reservaRepository.save(reserva);
        return ResponseEntity.ok(new ReservaDto(reservaSalva));
    }

    public ResponseEntity<List<ReservaMovimentoDto>> simulaReserva(ReservaDto reservaDto) {
        return ResponseEntity.ok(this.calculaMovimento(reservaDto));
    }

    public ResponseEntity<List<ReservaMovimentoDto>> checkOut(ReservaDto reservaDto) {

        Optional<List<ReservaMovimentoEntity>> listaBanco = reservaMovimentoRepository.getByReserva(reservaDto.getId());

        if(listaBanco.isPresent() && !listaBanco.get().isEmpty()) {
            List<ReservaMovimentoDto> reservaMovimentoDtos = new ArrayList<>();
            for (ReservaMovimentoEntity reg : listaBanco.get()) {
                reservaMovimentoDtos.add(new ReservaMovimentoDto(
                    reg.getDescricao(),
                    reg.getQuantidade(),
                    reg.getValor(),
                    reg.getIdReserva()
                ));
            }
            return ResponseEntity.ok(reservaMovimentoDtos);
        }else{
            Optional<ReservaEntity> reservaEntity = reservaRepository.findById(reservaDto.getId());
            if(reservaEntity.isPresent() && reservaEntity.get().getDataCheckOut() == null){
                reservaEntity.get().setDataCheckOut(reservaDto.getDataCheckOut());
                reservaRepository.save(reservaEntity.get());

                List<ReservaMovimentoDto> listaValores = this.calculaMovimento(reservaDto);
                for (ReservaMovimentoDto movimento: listaValores) {
                    reservaMovimentoRepository.save(new ReservaMovimentoEntity(movimento));
                }

            return ResponseEntity.ok(listaValores);
            }
        }
        return ResponseEntity.badRequest().build();
    }

    public ResponseEntity<List<ReservaDto>> buscarReservas(PesquisaDto pesquisaDto) {
        List<ReservaEntity> reservaEntities = reservaRepository.findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Fazendo o join com a tabela Hospede
            Join<ReservaEntity, HospedeEntity> hospede = root.join("id_hospede");

            // Filtros para os campos do Hospede
            if (pesquisaDto.getHospede().getNome() != null && !pesquisaDto.getHospede().getNome().isEmpty()) {
                predicates.add(criteriaBuilder.like(hospede.get("nome"), "%" + pesquisaDto.getHospede().getNome() + "%"));
            }
            if (pesquisaDto.getHospede().getDocumento() != null && !pesquisaDto.getHospede().getDocumento().isEmpty()) {
                predicates.add(criteriaBuilder.like(hospede.get("documento"), "%" + pesquisaDto.getHospede().getDocumento() + "%"));
            }
            if (pesquisaDto.getHospede().getTelefone() != null && !pesquisaDto.getHospede().getTelefone().isEmpty()) {
                predicates.add(criteriaBuilder.like(hospede.get("telefone"), "%" + pesquisaDto.getHospede().getTelefone() + "%"));
            }
            if (pesquisaDto.isReservaNaoiniciada()) {
                predicates.add(criteriaBuilder.isNull(root.get("dataCheckIn")));
            }
            if (pesquisaDto.isHospedeNoHotel()) {
                predicates.add(criteriaBuilder.isNotNull(root.get("dataCheckIn")));
                predicates.add(criteriaBuilder.isNull(root.get("dataCheckOut")));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });

        return ResponseEntity.ok(new ReservaDto().converteLista(reservaEntities));
    }


    private boolean validaHora(Date horario, int horas) {
        if(horario == null)
            return false;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(horario);

        return calendar.get(Calendar.HOUR_OF_DAY) >= horas;
    }

    public ResponseEntity<String> checkIn(ReservaDto reservaDto) {
        if(validaHora(reservaDto.getDataCheckIn(),14)){
            Optional<ReservaEntity> reservaEntity = reservaRepository.findById(reservaDto.getId());
            if(reservaEntity.isPresent()){
                if(reservaEntity.get().getDataCheckIn() == null){
                reservaEntity.get().setDataCheckIn(reservaDto.getDataCheckIn());
                reservaRepository.save(reservaEntity.get());
                return ResponseEntity.ok("Check In feito com sucesso!");
                }else{
                    return ResponseEntity.ok("Check In ja foi feito!");
                }
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Check In nao pode ser feito por conta do horario!");
    }

    public ResponseEntity<List<ReservaDto>> listar() {
        List<ReservaEntity> reservaEntities = reservaRepository.findAll();
        List<ReservaDto> reservaDtos = new ReservaDto().converteLista(reservaEntities);

        return ResponseEntity.ok(reservaDtos);
    }
    public ResponseEntity<ReservaDto> buscarPorId(Long id) {
        Optional<ReservaEntity> hospedeEntity = reservaRepository.findById(id);

        if (hospedeEntity.isPresent()) {
            ReservaDto reservaDto = new ReservaDto(hospedeEntity.get());
            return ResponseEntity.ok(reservaDto);
        }

        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<String> excluir(Long id) {
        reservaRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    public List<ReservaMovimentoDto> calculaMovimento(ReservaDto reserva) {

        List<ReservaMovimentoDto> listaValores = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        double valorAdicional = 1.0;

        if(reserva.getDataCheckOut() != null){
            calendar.setTime(reserva.getDataCheckOut());
            valorAdicional = (calendar.get(Calendar.HOUR_OF_DAY) > 12 ? 1.5 : 1.0);
        }

        calendar.setTime(reserva.getDataInicial());
        int diasUteis = 0, diasFinais = 0;

        while (calendar.getTime().before(reserva.getDataFinal()) || calendar.getTime().equals(reserva.getDataFinal())) {
            if (calendar.get(Calendar.DAY_OF_WEEK) >= Calendar.MONDAY && calendar.get(Calendar.DAY_OF_WEEK) <= Calendar.FRIDAY) {
                diasUteis += 1;
            } else {
                diasFinais += 1;
            }
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        if(diasUteis > 0){
            listaValores.add(new ReservaMovimentoDto(
                    "Valores dias uteis: "+diasUteis,
                    diasUteis,
                    (ValoresMovimento.VALOR_DIA_UTIL*diasUteis) * valorAdicional,
                    reserva.getId()));

            System.out.println(reserva.isTemEstacionamento());
            if(reserva.isTemEstacionamento())
                listaValores.add(new ReservaMovimentoDto(
                        "Adicional estacionamento: "+diasUteis,
                        diasUteis,
                        (ValoresMovimento.VALOR_ESTACIONAMENTO_DIA_UTIL*diasUteis) * valorAdicional,
                        reserva.getId()));
        }

        if(diasFinais > 0){
            listaValores.add(new ReservaMovimentoDto(
                    "Valores dias nao uteis: "+diasFinais,
                    diasFinais,
                    (ValoresMovimento.VALOR_DIA_NAO_UTIL*diasFinais) * valorAdicional,
                    reserva.getId()));
            if(reserva.isTemEstacionamento()){
                listaValores.add(new ReservaMovimentoDto(
                        "Adicional estacionamento dia nao util: "+diasFinais,
                        diasFinais,
                        (ValoresMovimento.VALOR_ESTACIONAMENTO_DIA_NAO_UTIL*diasUteis) * valorAdicional,
                        reserva.getId()));
            }
        }

        return listaValores;
    }
}
