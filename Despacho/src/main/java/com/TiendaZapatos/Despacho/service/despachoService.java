package com.TiendaZapatos.Despacho.service;
import com.TiendaZapatos.Despacho.Client.PedidoClient;
import com.TiendaZapatos.Despacho.dto.gestionPedidoDTO;
import com.TiendaZapatos.Despacho.model.*;
import com.TiendaZapatos.Despacho.repository.despachoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
@Service

public class despachoService {
    @Autowired
    private despachoRepository repository;

    @Autowired
    private  PedidoClient pedidoClient;

    public List<despacho> listar(){
    return repository.findAll();
}
public Optional<despacho> buscarPorId(Integer id){
    return repository.findById(id);
}
public despacho guardarDespachador(despacho repo){
    return repository.save(repo);
}
public void eliminarPorId(Integer id){
    repository.deleteById(id);
}
public despacho actualizarPorId(Integer id , despacho des){
   
    if(buscarPorId(id).isPresent()){
    des.setId(id);
    return repository.save(des);
    }else{
        return null;
    }
}
public String obtenerInformacionDespacho(Integer despachoId,Long pedidoId){

        despacho despacho = repository.findById(despachoId)
                .orElseThrow(() ->
                        new NoSuchElementException(
                                "Despacho no encontrado"
                        ));

        gestionPedidoDTO pedido;

        try {

            pedido = pedidoClient
                    .obtenerPedido(pedidoId)
                    .block();

        } catch (NoSuchElementException e){

            throw e;

        } catch (Exception e){

            throw new RuntimeException(
                    "No se pudo conectar con gestionPedido"
            );
        }

        if(pedido == null){

            throw new NoSuchElementException(
                    "Pedido no encontrado"
            );
        }

        return despacho.getNombre()
                +" "
                + despacho.getApellido()
                + " es el encargado de mandar el pedido a : "
                + pedido.getDireccionDelusuario()
                + " con el camion con la patente "
                + despacho.getPatenteCamion()
                + " en la fecha "
                + despacho.getFechaSalida();
    }
}



