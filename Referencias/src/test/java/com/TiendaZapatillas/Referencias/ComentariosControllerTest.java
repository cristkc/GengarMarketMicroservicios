package com.TiendaZapatillas.Referencias;
import org.mockito.Mockito;

import com.TiendaZapatillas.Referencias.controller.ComentariosController;
import com.TiendaZapatillas.Referencias.model.*;
import com.TiendaZapatillas.Referencias.service.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
@WebMvcTest(ComentariosController.class)

public class ComentariosControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private ComentariosService service;


    @Test
    void listar() throws Exception{
        List<Comentarios> com= List.of(
        new Comentarios(
            1,
            "zapatillas",
            "zapatillas enteras malas eeeee"
        ));
        when(service.listar()).thenReturn(com);
    
        mockMvc.perform(get("/comentarios/verTodoComentarios")).andExpect(status().isOk());


    
    }
  @Test
  void guardar()throws Exception{
    String ComentariosJson= """
            {
             "nombreDeProducto": "ZAPATO MALO",
             "comentario": "ENTERO MALO EL ZAPATO ESTE"
    
            }
            """;
            Comentarios comCreado= new Comentarios(
                1,
                "ZAPATO MALO",
                "ENTERO MALO EL ZAPATO ESTE"
            );
            when(service.guardar((Mockito.eq(1)),any(Comentarios.class))).thenReturn(comCreado);
            mockMvc.perform(post("/comentarios/agregar/1")
        .contentType(APPLICATION_JSON).content(ComentariosJson)).andExpect(status().isCreated());
  }
  @Test
  void eliminar()throws Exception{
    Comentarios com = new Comentarios(
            1,
            "zapatillas",
            "zapatillas enteras malas eeeee"
        );
        when(service.buscarPorId(1)).thenReturn(Optional.of(com));
        mockMvc.perform(delete("/comentarios/Eliminar/1")).andExpect(status().isOk());

  }
  @Test
  void actualizar()throws Exception{
    String ComentariosJsonPut= """
            {
             "nombreDeProducto": "ZAPATO No  TAN MALO",
             "comentario": "ENTERO MAOMENO EL ZAPATO ESTE"
    
            }
            """;
            Comentarios comModificado= new Comentarios(
                4,
                "ZAPATO No TAN MALO",
                "ENTERO MAOMENO EL ZAPATO ESTE"
            );
            when(service.actualizar(Mockito.eq(4),any(Comentarios.class))).thenReturn(comModificado);
            mockMvc.perform(put("/comentarios/actualizar/4").contentType(APPLICATION_JSON)
            .content(ComentariosJsonPut)).andExpect(status().isOk());
  }



}
