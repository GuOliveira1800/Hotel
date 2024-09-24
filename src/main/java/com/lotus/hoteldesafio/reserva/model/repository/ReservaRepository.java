package com.lotus.hoteldesafio.reserva.model.repository;

import com.lotus.hoteldesafio.reserva.model.entity.ReservaEntity;
import com.lotus.hoteldesafio.reserva.model.entity.ReservaMovimentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReservaRepository extends JpaRepository<ReservaEntity,Long>, JpaSpecificationExecutor<ReservaEntity> {

    @Query("SELECT r FROM ReservaEntity r WHERE r.dataCheckIn is null")
    Optional<List<ReservaMovimentoEntity>> getReservas();

    @Query("SELECT r FROM ReservaEntity r WHERE r.dataCheckIn is not null and r.dataCheckOut is null")
    Optional<List<ReservaMovimentoEntity>> getReservasEmCurso();

}
