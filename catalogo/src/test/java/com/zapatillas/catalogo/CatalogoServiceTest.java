package com.zapatillas.catalogo;

import com.zapatillas.catalogo.model.Zapatilla;
import com.zapatillas.catalogo.repository.CatalogoRepository;
import com.zapatillas.catalogo.service.CatalogoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CatalogoControllerTest {

    @Mock
    private CatalogoRepository repository;

    @InjectMocks
    private CatalogoService service;

    private Zapatilla zapatilla;

    @BeforeEach
    void setUp() {
        zapatilla = new Zapatilla(
                1,
                "Air Max",
                "90",
                "Nike",
                "42",
                89990,
                10
        );
    }

    @Test
    void listarDebeRetornarZapatillas() {

        when(repository.findAll()).thenReturn(List.of(zapatilla));

        List<Zapatilla> resultado = service.listar();

        assertEquals(1, resultado.size());
        assertEquals("Air Max", resultado.get(0).getNombre());

        verify(repository).findAll();
    }

    @Test
    void buscarPorSkuDebeRetornarZapatillaCuandoExiste() {

        when(repository.findById(1)).thenReturn(Optional.of(zapatilla));

        Zapatilla resultado = service.buscarPorSKU(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getSku());

        verify(repository).findById(1);
    }

    @Test
    void buscarPorSkuDebeLanzarExcepcionCuandoNoExiste() {

        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> service.buscarPorSKU(99));

        verify(repository).findById(99);
    }

    @Test
    void guardarDebeGuardarZapatillaCuandoNoExisteNombre() {

        when(repository.findByNombreIgnoreCase("Air Max"))
                .thenReturn(Optional.empty());

        when(repository.save(zapatilla))
                .thenReturn(zapatilla);

        Zapatilla resultado = service.guardar(zapatilla);

        assertNotNull(resultado);
        assertEquals("Air Max", resultado.getNombre());

        verify(repository).save(zapatilla);
    }

    @Test
    void guardarDebeLanzarExcepcionCuandoNombreExiste() {

        when(repository.findByNombreIgnoreCase("Air Max"))
                .thenReturn(Optional.of(zapatilla));

        assertThrows(IllegalArgumentException.class,
                () -> service.guardar(zapatilla));

        verify(repository, never()).save(any());
    }

    @Test
    void eliminarDebeEliminarCuandoExiste() {

        when(repository.existsById(1)).thenReturn(true);

        service.eliminar(1);

        verify(repository).deleteById(1);
    }

    @Test
    void eliminarDebeLanzarExcepcionCuandoNoExiste() {

        when(repository.existsById(99)).thenReturn(false);

        assertThrows(NoSuchElementException.class,
                () -> service.eliminar(99));

        verify(repository, never()).deleteById(any());
    }
}