package com.TiendaZapatos.Despacho.repository;
import com.TiendaZapatos.Despacho.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface despachoRepository extends JpaRepository <despacho,Integer>  {




    
}
