package com.zapatillas.catalogo.repository;

import com.zapatillas.catalogo.model.Zapatilla;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CatalogoRepository extends JpaRepository<Zapatilla, Integer> {

    Optional<Zapatilla> findByNombreIgnoreCase(String nombre);
    Optional<Zapatilla> findByMarcaIgnoreCase(String marca);
    Optional<Zapatilla> findByModeloIgnoreCase(String modelo);
    Optional<Zapatilla> findByTallaIgnoreCase(String talla);
}