package com.TiendaZapatillas.Referencias.service;



import com.TiendaZapatillas.Referencias.Client.*;

import com.TiendaZapatillas.Referencias.model.Comentarios;
import com.TiendaZapatillas.Referencias.repository.ComentariosRepository;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
@Service

public class ComentariosService {
    
@Autowired
private ComentariosRepository repository;
@Autowired
private ClienteAutenticacion clienteAutenticacion;




public List<Comentarios> listar(){
    return repository.findAll();


}

public Optional<Comentarios> buscarPorId(Integer id){
return repository.findById(id);
}
public void eliminarPorId(Integer id){
    repository.deleteById(id);
}


public Comentarios guardar(Integer usuarioId,Comentarios comentario) {

        Boolean usuario = clienteAutenticacion.obtenerUsuario(usuarioId);
        if (usuario== null || usuario== false){
            return null;
        }


        return repository.save(comentario);
    }

    public Comentarios actualizar(Integer id, Comentarios com){
        Boolean usuario = clienteAutenticacion.obtenerUsuario(id);
                if (usuario== null || usuario== false){
            return null;
        }
        com.setId(id);
            return repository.save(com);

    }
}