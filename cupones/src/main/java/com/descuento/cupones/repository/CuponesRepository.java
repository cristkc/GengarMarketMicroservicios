package com.descuento.cupones.repository;

import com.descuento.cupones.model.Cupones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CuponesRepository extends JpaRepository<Cupones,Integer>{

    Optional<Cupones> findByNombreIgnoreCase(String nombre);
    Optional<Cupones> findByCodigoIgnoreCase(String codigo);
}
