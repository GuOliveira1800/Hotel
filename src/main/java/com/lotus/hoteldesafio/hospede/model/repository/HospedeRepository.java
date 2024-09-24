package com.lotus.hoteldesafio.hospede.model.repository;

import com.lotus.hoteldesafio.hospede.model.entity.HospedeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface HospedeRepository extends JpaRepository<HospedeEntity,Long>, JpaSpecificationExecutor<HospedeEntity> {

}
