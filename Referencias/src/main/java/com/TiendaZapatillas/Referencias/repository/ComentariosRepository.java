package com.TiendaZapatillas.Referencias.repository;

import org.springframework.stereotype.Repository;
import com.TiendaZapatillas.Referencias.model.Comentarios;
import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface ComentariosRepository extends JpaRepository<Comentarios,Integer> {

  
  

}
