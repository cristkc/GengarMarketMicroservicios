package com.gengarmarket.pago.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gengarmarket.pago.dto.PagoRequest;
import com.gengarmarket.pago.dto.PagoResponse;
import com.gengarmarket.pago.model.Pago;
import com.gengarmarket.pago.service.PagoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PagoController.class)
public class PagoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PagoService pagoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void listarPagos() throws Exception {
        List<Pago> pagos = List.of(
                Pago.builder()
                        .id(1L)
                        .usuarioId(1L)
                        .nombreCliente("Juan")
                        .correo("juan@gmail.com")
                        .direccion("Calle 123")
                        .numeroTarjeta("1234567812345678")
                        .titularTarjeta("Juan")
                        .fechaExpiracion("12/29")
                        .cvv("123")
                        .subtotal(10000)
                        .descuento(0)
                        .totalFinal(10000)
                        .estadoPedido("En preparacion")
                        .mensaje("Pago exitoso")
                        .build()
        );

        when(pagoService.listarPagos()).thenReturn(pagos);

        mockMvc.perform(get("/pagos"))
                .andExpect(status().isOk());
    }

    @Test
    void buscarPorUsuario() throws Exception {
        Pago pago = Pago.builder()
                .id(1L)
                .usuarioId(1L)
                .nombreCliente("Juan")
                .correo("juan@gmail.com")
                .direccion("Calle 123")
                .numeroTarjeta("1234567812345678")
                .titularTarjeta("Juan")
                .fechaExpiracion("12/29")
                .cvv("123")
                .subtotal(10000)
                .descuento(0)
                .totalFinal(10000)
                .estadoPedido("En preparacion")
                .mensaje("Pago exitoso")
                .build();

        when(pagoService.buscarPorUsuario(1L)).thenReturn(pago);

        mockMvc.perform(get("/pagos/1"))
                .andExpect(status().isOk());
    }

    @Test
    void realizarPago() throws Exception {
        PagoRequest request = new PagoRequest();
        request.setUsuarioId(1L);
        request.setNombreCliente("Juan Perez");
        request.setCorreo("juan@gmail.com");
        request.setDireccion("Calle 123");
        request.setNumeroTarjeta("1234567812345678");
        request.setTitularTarjeta("Juan Perez");
        request.setFechaExpiracion("12/29");
        request.setCvv("123");
        request.setCodigoCupon("DESC10");

        PagoResponse response = new PagoResponse(
                "Pago exitoso",
                "En preparacion",
                10000,
                1000,
                9000,
                10
        );

        when(pagoService.procesarPago(any(PagoRequest.class))).thenReturn(response);

        mockMvc.perform(post("/pagos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void actualizarPago() throws Exception {
        PagoRequest request = new PagoRequest();
        request.setUsuarioId(1L);
        request.setNombreCliente("Juan Actualizado");
        request.setCorreo("juanactualizado@gmail.com");
        request.setDireccion("Nueva direccion");
        request.setNumeroTarjeta("1234567812345678");
        request.setTitularTarjeta("Juan Actualizado");
        request.setFechaExpiracion("10/30");
        request.setCvv("123");
        request.setCodigoCupon("DESC20");

        Pago pagoActualizado = Pago.builder()
                .id(1L)
                .usuarioId(1L)
                .nombreCliente("Juan Actualizado")
                .correo("juanactualizado@gmail.com")
                .direccion("Nueva direccion")
                .numeroTarjeta("1234567812345678")
                .titularTarjeta("Juan Actualizado")
                .fechaExpiracion("10/30")
                .cvv("123")
                .codigoCupon("DESC20")
                .subtotal(10000)
                .descuento(2000)
                .totalFinal(8000)
                .estadoPedido("En preparacion")
                .mensaje("Pago exitoso")
                .build();

        when(pagoService.actualizarPago(anyLong(), any(PagoRequest.class))).thenReturn(pagoActualizado);

        mockMvc.perform(put("/pagos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void eliminarPago() throws Exception {
        doNothing().when(pagoService).eliminarPago(1L);

        mockMvc.perform(delete("/pagos/1"))
                .andExpect(status().isOk());
    }
}