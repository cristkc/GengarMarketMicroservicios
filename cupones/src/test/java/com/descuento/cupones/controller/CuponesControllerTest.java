package com.descuento.cupones.controller;

import com.descuento.cupones.model.Cupones;
import com.descuento.cupones.service.CuponesService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CuponesController.class)
public class CuponesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CuponesService service;

    @Test
    void listar() throws Exception {
        List<Cupones> cupones = List.of(
                new Cupones(1, "Descuento 10", "DESC10", 10),
                new Cupones(2, "Descuento 20", "DESC20", 20)
        );

        when(service.listar()).thenReturn(cupones);

        mockMvc.perform(get("/cupones/listar"))
                .andExpect(status().isOk());
    }

    @Test
    void buscarPorNombre() throws Exception {
        Cupones cupon = new Cupones(1, "Descuento 10", "DESC10", 10);

        when(service.buscarPorNombre("Descuento 10")).thenReturn(cupon);

        mockMvc.perform(get("/cupones/nombre/Descuento 10"))
                .andExpect(status().isOk());
    }

    @Test
    void crear() throws Exception {
        Cupones cupon = new Cupones(1, "Descuento 15", "DESC15", 15);

        doNothing().when(service).guardar(org.mockito.ArgumentMatchers.any(Cupones.class));

        mockMvc.perform(post("/cupones/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cupon)))
                .andExpect(status().isCreated());
    }

    @Test
    void eliminar() throws Exception {
        doNothing().when(service).eliminar(1);

        mockMvc.perform(delete("/cupones/eliminar/1"))
                .andExpect(status().isOk());
    }

    @Test
    void actualizar() throws Exception {
        Cupones cupon = new Cupones(1, "Descuento actualizado", "DESC25", 25);

        doNothing().when(service).actualizar(org.mockito.ArgumentMatchers.any(Cupones.class));

        mockMvc.perform(put("/cupones/actualizar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cupon)))
                .andExpect(status().isOk());
    }

    @Test
    void buscarPorCodigo() throws Exception {
        Cupones cupon = new Cupones(1, "Descuento 30", "DESC30", 30);

        when(service.buscarPorCodigo("DESC30")).thenReturn(cupon);

        mockMvc.perform(get("/cupones/codigo/DESC30"))
                .andExpect(status().isOk());
    }
}