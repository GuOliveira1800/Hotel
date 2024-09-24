package com.lotus.hoteldesafio.reserva.model.repository;

import com.lotus.hoteldesafio.reserva.model.entity.ReservaEntity;
import com.lotus.hoteldesafio.reserva.model.entity.ReservaMovimentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReservaMovimentoRepository extends JpaRepository<ReservaMovimentoEntity,Long>, JpaSpecificationExecutor<ReservaMovimentoEntity> {

    @Query("SELECT r FROM ReservaMovimentoEntity r WHERE r.idReserva = :idReserva")
    Optional<List<ReservaMovimentoEntity>> getByReserva(@Param("idReserva") Long idReserva);

}
