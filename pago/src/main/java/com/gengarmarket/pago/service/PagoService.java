package com.gengarmarket.pago.service;

import com.gengarmarket.pago.client.CarritoClient;
import com.gengarmarket.pago.client.CuponClient;
import com.gengarmarket.pago.dto.CarritoResponse;
import com.gengarmarket.pago.dto.CuponResponse;
import com.gengarmarket.pago.dto.PagoRequest;
import com.gengarmarket.pago.dto.PagoResponse;
import com.gengarmarket.pago.model.Pago;
import com.gengarmarket.pago.repository.PagoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PagoService {

    private final PagoRepository pagoRepository;
    private final CarritoClient carritoClient;
    private final CuponClient cuponClient;

    public PagoResponse procesarPago(PagoRequest request) {

        CarritoResponse carrito;

        try {
            carrito = carritoClient.obtenerCarrito(request.getUsuarioId()).block();
        } catch (NoSuchElementException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("No se pudo conectar con Carrito");
        }

        if (carrito == null) {
            throw new NoSuchElementException("Carrito no encontrado");
        }

        if (carrito.getItems() == null || carrito.getItems().isEmpty()) {
            throw new IllegalArgumentException("No se puede procesar el pago porque el carrito está vacío");
        }

        Integer subtotal = carrito.getTotal();
        Integer descuento = 0;
        Integer porcentajeDescuento = 0;

        if (request.getCodigoCupon() != null && !request.getCodigoCupon().isBlank()) {
            CuponResponse cupon;

            try {
                cupon = cuponClient.obtenerCupon(request.getCodigoCupon()).block();
            } catch (NoSuchElementException e) {
                throw e;
            } catch (Exception e) {
                throw new RuntimeException("Error al conectar con microservicio Cupón");
            }

            if (cupon != null && cupon.getPorcentaje() != null && cupon.getPorcentaje() > 0) {
                porcentajeDescuento = cupon.getPorcentaje();
                descuento = subtotal * porcentajeDescuento / 100;
            }
        }

        Integer totalFinal = subtotal - descuento;

        if (totalFinal < 0) {
            totalFinal = 0;
        }

        Pago pago = Pago.builder()
                .usuarioId(request.getUsuarioId())
                .nombreCliente(request.getNombreCliente())
                .correo(request.getCorreo())
                .direccion(request.getDireccion())
                .numeroTarjeta(request.getNumeroTarjeta())
                .titularTarjeta(request.getTitularTarjeta())
                .fechaExpiracion(request.getFechaExpiracion())
                .cvv(request.getCvv())
                .codigoCupon(request.getCodigoCupon())
                .subtotal(subtotal)
                .descuento(descuento)
                .totalFinal(totalFinal)
                .estadoPedido("En preparacion")
                .mensaje("Pago exitoso")
                .fechaPago(LocalDateTime.now())
                .build();

        pagoRepository.save(pago);

        return new PagoResponse(
                "Pago exitoso",
                "En preparacion",
                subtotal,
                descuento,
                totalFinal,
                porcentajeDescuento
        );
    }

    public List<Pago> listarPagos() {
        return pagoRepository.findAll();
    }

    public Pago buscarPorUsuario(Long usuarioId) {
        List<Pago> pagos = pagoRepository.findByUsuarioId(usuarioId);

        if (pagos.isEmpty()) {
            throw new NoSuchElementException("Pago no encontrado");
        }

        return pagos.get(pagos.size() - 1);
    }

    public void eliminarPago(Long id) {
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Pago no encontrado"));

        pagoRepository.delete(pago);
    }

    public Pago actualizarPago(Long id, PagoRequest request) {
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Pago no encontrado"));

        pago.setNombreCliente(request.getNombreCliente());
        pago.setCorreo(request.getCorreo());
        pago.setDireccion(request.getDireccion());
        pago.setNumeroTarjeta(request.getNumeroTarjeta());
        pago.setTitularTarjeta(request.getTitularTarjeta());
        pago.setFechaExpiracion(request.getFechaExpiracion());
        pago.setCvv(request.getCvv());
        pago.setCodigoCupon(request.getCodigoCupon());

        return pagoRepository.save(pago);
    }
}