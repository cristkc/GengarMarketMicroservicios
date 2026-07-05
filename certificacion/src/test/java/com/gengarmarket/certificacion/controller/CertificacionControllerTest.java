package com.gengarmarket.certificacion.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gengarmarket.certificacion.dto.CertificacionRequest;
import com.gengarmarket.certificacion.model.Certificacion;
import com.gengarmarket.certificacion.service.CertificacionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

@WebMvcTest(CertificacionController.class)
public class CertificacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CertificacionService service;

    @Test
    void crear() throws Exception {
        CertificacionRequest request = new CertificacionRequest();
        request.setRecibidoPor("Juan Perez");
        request.setRutRecibe("12.345.678-9");
        request.setParentesco("Hermano");

        Certificacion certificacion = new Certificacion(
                1L,
                10L,
                "Carlos Soto",
                "Calle 123",
                25990.0,
                "Juan Perez",
                "12.345.678-9",
                "Hermano",
                LocalDate.now(),
                "ENTREGADO"
        );

        when(service.crear(anyLong(), any(CertificacionRequest.class))).thenReturn(certificacion);

        mockMvc.perform(post("/certificacion/crear/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void listar() throws Exception {
        List<Certificacion> certificaciones = List.of(
                new Certificacion(
                        1L,
                        10L,
                        "Carlos Soto",
                        "Calle 123",
                        25990.0,
                        "Juan Perez",
                        "12.345.678-9",
                        "Hermano",
                        LocalDate.now(),
                        "ENTREGADO"
                ),
                new Certificacion(
                        2L,
                        11L,
                        "Maria Lopez",
                        "Avenida Central 456",
                        45990.0,
                        "Ana Lopez",
                        "11.111.111-1",
                        "Madre",
                        LocalDate.now(),
                        "ENTREGADO"
                )
        );

        when(service.listar()).thenReturn(certificaciones);

        mockMvc.perform(get("/certificacion/listar"))
                .andExpect(status().isOk());
    }

    @Test
    void buscar() throws Exception {
        Certificacion certificacion = new Certificacion(
                1L,
                10L,
                "Carlos Soto",
                "Calle 123",
                25990.0,
                "Juan Perez",
                "12.345.678-9",
                "Hermano",
                LocalDate.now(),
                "ENTREGADO"
        );

        when(service.buscar(1L)).thenReturn(certificacion);

        mockMvc.perform(get("/certificacion/1"))
                .andExpect(status().isOk());
    }

    @Test
    void actualizar() throws Exception {
        CertificacionRequest request = new CertificacionRequest();
        request.setRecibidoPor("Pedro Actualizado");
        request.setRutRecibe("22.222.222-2");
        request.setParentesco("Padre");

        Certificacion certificacionActualizada = new Certificacion(
                1L,
                10L,
                "Carlos Soto",
                "Calle 123",
                25990.0,
                "Pedro Actualizado",
                "22.222.222-2",
                "Padre",
                LocalDate.now(),
                "ENTREGADO"
        );

        when(service.actualizar(anyLong(), any(CertificacionRequest.class))).thenReturn(certificacionActualizada);

        mockMvc.perform(put("/certificacion/actualizar/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void eliminar() throws Exception {
        doNothing().when(service).eliminar(1L);

        mockMvc.perform(delete("/certificacion/eliminar/1"))
                .andExpect(status().isOk());
    }
}