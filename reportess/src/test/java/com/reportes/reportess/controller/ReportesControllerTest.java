package com.reportes.reportess.controller;
import org.mockito.Mockito;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status; import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import com.reportes.reportess.model.*;
import com.reportes.reportess.security.Seguridad;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import com.reportes.reportess.service.ReportesServices;

import org.springframework.test.context.bean.override.mockito.MockitoBean;
@WebMvcTest(ReportesController.class)
@Import(Seguridad.class)
public class ReportesControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ReportesServices service;


    @Test
    void buscarPorId() throws Exception{
        reportes repo = new reportes(1,"23-06-2006",5000);
        when(service.buscarPorId(1)).thenReturn(Optional.of(repo));
        mockMvc.perform(get("/reportes/1")).andExpect(status().isOk());

    }
    @Test
    void verPagos() throws Exception{
        when(service.mostrarPagosTotales()).thenReturn(List.of(100000,50000,600000));
        mockMvc.perform(get("/reportes/pagos")).andExpect(status().isOk());
    }
    @Test
    void verGanancias() throws Exception{
        reportes repo = new reportes(
            1,
            "26-06-2026",
            1000000
        );
        when(service.listaReportes()).thenReturn(repo);
        mockMvc.perform(get("/reportes/ganancias")).andExpect(status().isOk());
    }
    @Test
    void eliminar() throws Exception{
        reportes repo = new reportes(
            1,
            "26-06-2026",
            1000000
        );
        when(service.buscarPorId(1)).thenReturn(Optional.of(repo));
        mockMvc.perform(delete("/reportes/eliminar/1")).andExpect(status().isOk());
      
}
@Test void actualizar()throws Exception{
    String reporteJson="""
            {
             "fecha":"26/06/2026",
             "ganancia": 10000
            }
            """;
            reportes repoactualisado = new reportes(
                1,
                "11/9/2025",
                60000
            );
            when(service.actualizarPorId(Mockito.eq(1), any(reportes.class))).thenReturn(repoactualisado);
            mockMvc.perform(put("/reportes/actualizar/1").contentType(APPLICATION_JSON).content(reporteJson))
            .andExpect(status().isOk());



}           
@Test void guardarReportes()throws Exception{
String reporteJson="""
            {
             "fecha":"26/06/2026",
             "ganancia": 10000
            }
            """;
            reportes repoactualisado = new reportes(
                1,
                "26/06/2026",
                10000
            );
            when(service.guardarReportes(any(reportes.class))).thenReturn(repoactualisado);
            mockMvc.perform(post("/reportes/agregar").contentType(APPLICATION_JSON).content(reporteJson)).andExpect(status().isCreated());
}

}
