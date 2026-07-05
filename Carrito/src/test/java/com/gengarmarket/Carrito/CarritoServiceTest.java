package com.gengarmarket.Carrito;

import com.gengarmarket.Carrito.client.AuthClient;
import com.gengarmarket.Carrito.client.CatalogoClient;
import com.gengarmarket.Carrito.dto.ZapatillaResponse;
import com.gengarmarket.Carrito.model.Carrito;
import com.gengarmarket.Carrito.model.CarritoItem;
import com.gengarmarket.Carrito.repository.CarritoRepository;
import com.gengarmarket.Carrito.service.CarritoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarritoServiceTest {

    @Mock
    private AuthClient authClient;

    @Mock
    private CatalogoClient catalogoClient;

    @Mock
    private CarritoRepository repository;

    @InjectMocks
    private CarritoService service;

    private Carrito carrito;
    private ZapatillaResponse zapatilla;

    @BeforeEach
    void setUp() {
        carrito = new Carrito();
        carrito.setId(1L);
        carrito.setUsuarioId(10L);
        carrito.setItems(new ArrayList<>());

        zapatilla = new ZapatillaResponse();
        zapatilla.setSku(100);
        zapatilla.setNombre("Air Max");
        zapatilla.setMarca("Nike");
        zapatilla.setPrecio(89990);
        zapatilla.setStock(10);
    }

    @Test
    void obtenerOCrearCarritoDebeRetornarCarritoExistente() {
        when(authClient.validarUsuario(10L)).thenReturn(Mono.just(true));
        when(repository.findByUsuarioId(10L)).thenReturn(Optional.of(carrito));

        Carrito resultado = service.obtenerOCrearCarrito(10L);

        assertNotNull(resultado);
        assertEquals(10L, resultado.getUsuarioId());
        verify(repository).findByUsuarioId(10L);
        verify(repository, never()).save(any());
    }

    @Test
    void obtenerOCrearCarritoDebeCrearCarritoSiNoExiste() {
        when(authClient.validarUsuario(10L)).thenReturn(Mono.just(true));
        when(repository.findByUsuarioId(10L)).thenReturn(Optional.empty());
        when(repository.save(any(Carrito.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Carrito resultado = service.obtenerOCrearCarrito(10L);

        assertNotNull(resultado);
        assertEquals(10L, resultado.getUsuarioId());
        verify(repository).save(any(Carrito.class));
    }

    @Test
    void obtenerOCrearCarritoDebeLanzarErrorSiUsuarioIdEsInvalido() {
        assertThrows(IllegalArgumentException.class,
                () -> service.obtenerOCrearCarrito(0L));

        verify(authClient, never()).validarUsuario(any());
        verify(repository, never()).findByUsuarioId(any());
    }

    @Test
    void agregarItemDebeAgregarProductoAlCarrito() {
        when(authClient.validarUsuario(10L)).thenReturn(Mono.just(true));
        when(repository.findByUsuarioId(10L)).thenReturn(Optional.of(carrito));
        when(catalogoClient.obtenerPorSku(100)).thenReturn(Mono.just(zapatilla));
        when(repository.save(any(Carrito.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var respuesta = service.agregarItem(10L, 100, 2);

        assertNotNull(respuesta);
        assertEquals(1, carrito.getItems().size());
        assertEquals(100, carrito.getItems().get(0).getSku());
        assertEquals(2, carrito.getItems().get(0).getCantidad());
        verify(repository).save(carrito);
    }

    @Test
    void agregarItemDebeLanzarErrorSiCantidadEsInvalida() {
        assertThrows(IllegalArgumentException.class,
                () -> service.agregarItem(10L, 100, 0));

        verify(repository, never()).save(any());
    }

    @Test
    void agregarItemDebeLanzarErrorSiStockEsInsuficiente() {
        when(authClient.validarUsuario(10L)).thenReturn(Mono.just(true));
        when(repository.findByUsuarioId(10L)).thenReturn(Optional.of(carrito));
        when(catalogoClient.obtenerPorSku(100)).thenReturn(Mono.just(zapatilla));

        assertThrows(IllegalArgumentException.class,
                () -> service.agregarItem(10L, 100, 20));

        verify(repository, never()).save(any());
    }

    @Test
    void eliminarItemDebeEliminarProductoExistente() {
        CarritoItem item = new CarritoItem();
        item.setSku(100);
        item.setCantidad(2);
        carrito.getItems().add(item);

        when(authClient.validarUsuario(10L)).thenReturn(Mono.just(true));
        when(repository.findByUsuarioId(10L)).thenReturn(Optional.of(carrito));
        when(repository.save(carrito)).thenReturn(carrito);

        var respuesta = service.eliminarItem(10L, 100);

        assertNotNull(respuesta);
        assertTrue(carrito.getItems().isEmpty());
        verify(repository).save(carrito);
    }

    @Test
    void eliminarItemDebeLanzarErrorSiItemNoExiste() {
        when(authClient.validarUsuario(10L)).thenReturn(Mono.just(true));
        when(repository.findByUsuarioId(10L)).thenReturn(Optional.of(carrito));

        assertThrows(NoSuchElementException.class,
                () -> service.eliminarItem(10L, 999));

        verify(repository, never()).save(any());
    }

    @Test
    void vaciarCarritoDebeVaciarCuandoTieneItems() {
        CarritoItem item = new CarritoItem();
        item.setSku(100);
        carrito.getItems().add(item);

        when(authClient.validarUsuario(10L)).thenReturn(Mono.just(true));
        when(repository.findByUsuarioId(10L)).thenReturn(Optional.of(carrito));
        when(repository.save(carrito)).thenReturn(carrito);

        var respuesta = service.vaciarCarrito(10L);

        assertNotNull(respuesta);
        assertTrue(carrito.getItems().isEmpty());
        verify(repository).save(carrito);
    }

    @Test
    void vaciarCarritoDebeLanzarErrorSiYaEstaVacio() {
        when(authClient.validarUsuario(10L)).thenReturn(Mono.just(true));
        when(repository.findByUsuarioId(10L)).thenReturn(Optional.of(carrito));

        assertThrows(NoSuchElementException.class,
                () -> service.vaciarCarrito(10L));

        verify(repository, never()).save(any());
    }
}