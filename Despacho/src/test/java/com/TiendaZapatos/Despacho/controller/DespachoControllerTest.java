package com.TiendaZapatos.Despacho.controller;
import org.mockito.Mockito;
import com.TiendaZapatos.Despacho.service.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import java.util.List;
import com.TiendaZapatos.Despacho.model.*;
import com.TiendaZapatos.Despacho.security.Seguridad;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
@WebMvcTest(despachoController.class)
@Import(Seguridad.class)
public class DespachoControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @MockitoBean
    private despachoService service;
        

    @Test
    void listar()throws Exception{
        List<despacho>despachos = List.of(
            new despacho(
                1,
                "alvaro",
                "gallardo",
                "12w3uf",
                "07/12"
            )

        );
        when(service.listar()).thenReturn(despachos);

        mockMvc.perform(get("/despacho/listar")).andExpect(status().isOk());
    }
    @Test
    void guardarDespachador() throws Exception{
        String DespachadorJson = """
                {
        "nombre": "matias",
        "apellido": "ortega",
        "patenteCamion": "6767rr",
        "fechaSalida" : "27/1/2026"
                }
                """;
                despacho despachocreado= new despacho(
                    3,
                    "matias",
                    "ortega",
                    "6767rr",
                    "27/1/2026"
                );
                when(service.guardarDespachador(any(despacho.class))).thenReturn(despachocreado);
                mockMvc.perform(post("/despacho/agregar")
                .contentType(APPLICATION_JSON).content(DespachadorJson)).andExpect(status().isCreated());

    }
    @Test void actualizar() throws Exception{
        String DespachadorJson = """
                {
        "nombre": "matias cambio",
        "apellido": "ortega cambio",
        "patenteCamion": "6767rrCC",
        "fechaSalida" : "21/6/2026"
                }
                """;
                despacho despachocreado= new despacho(
                    3,
                    "matias cambio",
                    "ortega cambio",
                    "6767rrCC",
                    "21/6/2026"
                );
                when(service.actualizarPorId(Mockito.eq(3),any(despacho.class))).thenReturn(despachocreado);
                mockMvc.perform(put("/despacho/actualizar/3")
                .contentType(APPLICATION_JSON).content(DespachadorJson)).andExpect(status().isOk());
            }

            @Test 
            void eliminar() throws Exception{
                despacho desp = new despacho(
            1,
            "matias",
            "ortega",
            "6767rr",
            "27/1/2026"
    );

    when(service.buscarPorId(1))
            .thenReturn(Optional.of(desp));

    mockMvc.perform(delete("/despacho/eliminar/1"))
            .andExpect(status().isOk());
}
@Test void obtenerInfo() throws Exception{
     when(service.obtenerInformacionDespacho(1, 10L))
                .thenReturn("Despacho encontrado correctamente");

        mockMvc.perform(get("/despacho/info/1/10"))
                .andExpect(status().isOk())
                .andExpect(content().string("Despacho encontrado correctamente"));

        verify(service).obtenerInformacionDespacho(1, 10L);



}



            }