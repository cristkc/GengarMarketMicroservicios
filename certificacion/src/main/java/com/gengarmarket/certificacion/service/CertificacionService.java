package com.gengarmarket.certificacion.service;

import com.gengarmarket.certificacion.client.PedidoClient;
import com.gengarmarket.certificacion.dto.CertificacionRequest;
import com.gengarmarket.certificacion.dto.PedidoResponse;
import com.gengarmarket.certificacion.model.Certificacion;
import com.gengarmarket.certificacion.repository.CertificacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CertificacionService {

    private final CertificacionRepository repository;
    private final PedidoClient pedidoClient;

    public Certificacion crear(Long pedidoId, CertificacionRequest request) {

        PedidoResponse pedido;

        try {
            pedido = pedidoClient.obtenerPedido(pedidoId).block();
        } catch (NoSuchElementException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("No se pudo conectar con Pedido");
        }

        if (pedido == null) {
            throw new NoSuchElementException("Pedido no encontrado");
        }

        if (!pedido.getEstado().equalsIgnoreCase("ENTREGADO")) {
            throw new IllegalArgumentException("Pedido aún no entregado");
        }

        Certificacion certificacion = new Certificacion();
        certificacion.setUsuarioId(pedido.getUsuarioId());
        certificacion.setNombreCliente(pedido.getNombreCliente());
        certificacion.setDireccion(pedido.getDireccion());
        certificacion.setTotalFinal(pedido.getTotalFinal());
        certificacion.setRecibidoPor(request.getRecibidoPor());
        certificacion.setRutRecibe(request.getRutRecibe());
        certificacion.setParentesco(request.getParentesco());
        certificacion.setFechaRecepcion(LocalDate.now());
        certificacion.setEstadoEntrega("ENTREGADO");

        return repository.save(certificacion);
    }

    public List<Certificacion> listar() {
        return repository.findAll();
    }

    public Certificacion buscar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Certificación no encontrada"));
    }

    public Certificacion actualizar(Long id, CertificacionRequest request) {
        Certificacion certificacion = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Certificación no encontrada"));

        certificacion.setRecibidoPor(request.getRecibidoPor());
        certificacion.setRutRecibe(request.getRutRecibe());
        certificacion.setParentesco(request.getParentesco());

        return repository.save(certificacion);
    }

    public void eliminar(Long id) {
        Certificacion certificacion = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Certificación no encontrada"));

        repository.delete(certificacion);
    }
}