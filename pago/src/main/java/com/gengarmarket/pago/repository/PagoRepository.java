package com.gengarmarket.pago.repository;

import com.gengarmarket.pago.model.Pago;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PagoRepository extends JpaRepository<Pago, Long> {

    List<Pago> findByUsuarioId(Long usuarioId);

}

