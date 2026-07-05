package com.gengarmarket.certificacion.repository;

import com.gengarmarket.certificacion.model.Certificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CertificacionRepository
        extends JpaRepository<Certificacion, Long> {

}