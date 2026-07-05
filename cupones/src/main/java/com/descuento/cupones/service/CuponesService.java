package com.descuento.cupones.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.descuento.cupones.model.Cupones;
import com.descuento.cupones.repository.CuponesRepository;

@Service
public class CuponesService {

    @Autowired
    private CuponesRepository repository;

    public List<Cupones> listar(){
        return repository.findAll();
    }

    public Cupones buscarPorNombre(String nombre){

        return repository.findByNombreIgnoreCase(nombre).orElseThrow(() -> new RuntimeException("Cupon no encontrado"));
    }

        public void guardar(Cupones cupones){

        List<Cupones> lista = repository.findAll();

            for(Cupones p : lista){

                if(p.getNombre().equalsIgnoreCase(cupones.getNombre())){

                throw new IllegalArgumentException("El cupon ya existe");
        }
    }

    repository.save(cupones);

    }

    public void eliminar(Integer id){

        Cupones cupon = repository.findById(id).orElseThrow(() -> new RuntimeException("El id del cupon no encontrado"));

        repository.delete(cupon);
    }
    
    public void actualizar(Cupones cupones){

        Cupones existente = repository.findById(cupones.getId()).orElseThrow(() -> new RuntimeException("Cupon no encontrado"));

        existente.setNombre(cupones.getNombre());
   

    repository.save(existente);
    }

    public Cupones buscarPorCodigo(String codigo){

    return repository.findByCodigoIgnoreCase(codigo)
            .orElseThrow(() -> new RuntimeException("Cupón no encontrado"));
    }

}
