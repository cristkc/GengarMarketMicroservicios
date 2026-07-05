package com.gengarmarket.gestionPedido.service;

import com.gengarmarket.gestionPedido.client.PagoClient;
import com.gengarmarket.gestionPedido.dto.PagoResponse;
import com.gengarmarket.gestionPedido.model.Pedido;
import com.gengarmarket.gestionPedido.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository repository;
    private final PagoClient pagoClient;

    public Pedido crearPedido(Long usuarioId) {

        if (usuarioId <= 0) {
            throw new IllegalArgumentException("El usuarioId debe ser mayor a 0");
        }

        PagoResponse pago;

        try {
            pago = pagoClient.obtenerPago(usuarioId).block();
        } catch (NoSuchElementException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("No se pudo conectar con Pago");
        }

        if (pago == null) {
            throw new NoSuchElementException("Pago no encontrado");
        }

        Pedido pedido = new Pedido();
        pedido.setUsuarioId(pago.getUsuarioId());
        pedido.setNombreCliente(pago.getNombreCliente());
        pedido.setDireccion(pago.getDireccion());
        pedido.setTotalFinal(pago.getTotalFinal());
        pedido.setFechaCompra(LocalDate.now());
        pedido.setFechaEntrega(LocalDate.now().plusDays(5));
        pedido.setEstado("EN PREPARACION");

        return repository.save(pedido);
    }

    public List<Pedido> listar() {
        return repository.findAll();
    }

    public Pedido buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Pedido no encontrado"));
    }

    public Pedido actualizar(Long id, Pedido nuevo) {
        Pedido pedido = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Pedido no encontrado"));

        pedido.setNombreCliente(nuevo.getNombreCliente());
        pedido.setDireccion(nuevo.getDireccion());
        pedido.setEstado(nuevo.getEstado());

        return repository.save(pedido);
    }

    public void eliminar(Long id) {
        Pedido pedido = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Pedido no encontrado"));

        repository.delete(pedido);
    }
}