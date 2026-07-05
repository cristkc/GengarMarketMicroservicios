package com.gengarmarket.gestionPedido.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gengarmarket.gestionPedido.model.Pedido;
import com.gengarmarket.gestionPedido.service.PedidoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
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

@WebMvcTest(PedidoController.class)
@Import(com.gengarmarket.gestionPedido.ManejoErrores.class)
public class PedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PedidoService pedidoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void crearPedido() throws Exception {
        Pedido pedido = new Pedido(
                1L,
                1L,
                "Juan Perez",
                "Calle 123",
                15000.0,
                LocalDate.now(),
                LocalDate.now().plusDays(5),
                "EN PREPARACION"
        );

        when(pedidoService.crearPedido(1L)).thenReturn(pedido);

        mockMvc.perform(post("/pedido/crear/1"))
                .andExpect(status().isCreated());
    }

    @Test
    void listar() throws Exception {
        List<Pedido> pedidos = List.of(
                new Pedido(
                        1L,
                        1L,
                        "Juan Perez",
                        "Calle 123",
                        15000.0,
                        LocalDate.now(),
                        LocalDate.now().plusDays(5),
                        "EN PREPARACION"
                )
        );

        when(pedidoService.listar()).thenReturn(pedidos);

        mockMvc.perform(get("/pedido"))
                .andExpect(status().isOk());
    }

    @Test
    void buscar() throws Exception {
        Pedido pedido = new Pedido(
                1L,
                1L,
                "Juan Perez",
                "Calle 123",
                15000.0,
                LocalDate.now(),
                LocalDate.now().plusDays(5),
                "EN PREPARACION"
        );

        when(pedidoService.buscarPorId(1L)).thenReturn(pedido);

        mockMvc.perform(get("/pedido/1"))
                .andExpect(status().isOk());
    }

    @Test
    void actualizar() throws Exception {
        Pedido request = new Pedido(
                null,
                1L,
                "Juan Actualizado",
                "Nueva direccion 456",
                15000.0,
                LocalDate.now(),
                LocalDate.now().plusDays(5),
                "EN CAMINO"
        );

        Pedido actualizado = new Pedido(
                1L,
                1L,
                "Juan Actualizado",
                "Nueva direccion 456",
                15000.0,
                LocalDate.now(),
                LocalDate.now().plusDays(5),
                "EN CAMINO"
        );

        when(pedidoService.actualizar(anyLong(), any(Pedido.class))).thenReturn(actualizado);

        mockMvc.perform(put("/pedido/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void eliminar() throws Exception {
        doNothing().when(pedidoService).eliminar(1L);

        mockMvc.perform(delete("/pedido/1"))
                .andExpect(status().isOk());
    }
}