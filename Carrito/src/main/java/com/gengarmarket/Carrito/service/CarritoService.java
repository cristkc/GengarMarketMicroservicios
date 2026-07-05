package com.gengarmarket.Carrito.service;

import com.gengarmarket.Carrito.client.AuthClient;
import com.gengarmarket.Carrito.client.CatalogoClient;
import com.gengarmarket.Carrito.dto.CarritoResponse;
import com.gengarmarket.Carrito.dto.ZapatillaResponse;
import com.gengarmarket.Carrito.model.Carrito;
import com.gengarmarket.Carrito.model.CarritoItem;
import com.gengarmarket.Carrito.repository.CarritoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CarritoService {

    private final AuthClient authClient;
    private final CarritoRepository repository;
    private final CatalogoClient catalogoClient;

    public Carrito obtenerOCrearCarrito(Long usuarioId) {
        if (usuarioId == null || usuarioId <= 0) {
            throw new IllegalArgumentException("El ID de usuario es inválido");
        }

        validarUsuario(usuarioId);

        return repository.findByUsuarioId(usuarioId).orElseGet(() -> {
            Carrito carrito = new Carrito();
            carrito.setUsuarioId(usuarioId);
            return repository.save(carrito);
        });
    }

    public CarritoResponse agregarItem(Long usuarioId, Integer sku, Integer cantidad) {
        if (cantidad == null || cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
        }

        Carrito carrito = obtenerOCrearCarrito(usuarioId);

        ZapatillaResponse zapatilla;
        try {
            zapatilla = catalogoClient.obtenerPorSku(sku).block();
        } catch (NoSuchElementException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("No se puede conectar al catálogo");
        }

        if (zapatilla == null) {
            throw new NoSuchElementException("Producto con sku " + sku + " no encontrado");
        }

        CarritoItem itemExistente = carrito.getItems().stream()
                .filter(item -> item.getSku().equals(sku))
                .findFirst()
                .orElse(null);

        int cantidadTotal = cantidad;
        if (itemExistente != null) {
            cantidadTotal += itemExistente.getCantidad();
        }

        if (cantidadTotal > zapatilla.getStock()) {
            throw new IllegalArgumentException("Stock insuficiente");
        }

        if (itemExistente != null) {
            itemExistente.setCantidad(itemExistente.getCantidad() + cantidad);
        } else {
            CarritoItem nuevoItem = new CarritoItem();
            nuevoItem.setCarrito(carrito);
            nuevoItem.setSku(sku);
            nuevoItem.setNombre(zapatilla.getNombre());
            nuevoItem.setMarca(zapatilla.getMarca());
            nuevoItem.setPrecioUnitario(zapatilla.getPrecio());
            nuevoItem.setCantidad(cantidad);
            carrito.getItems().add(nuevoItem);
        }

        Carrito carritoGuardado = repository.save(carrito);
        return new CarritoResponse(carritoGuardado);
    }

    public CarritoResponse eliminarItem(Long usuarioId, Integer sku) {
        Carrito carrito = obtenerOCrearCarrito(usuarioId);

        boolean eliminado = carrito.getItems().removeIf(item -> item.getSku().equals(sku));

        if (!eliminado) {
            throw new NoSuchElementException("Item con SKU " + sku + " no encontrado en el carrito");
        }

        repository.save(carrito);
        return new CarritoResponse(carrito);
    }

    public CarritoResponse obtenerCarrito(Long usuarioId) {
        Carrito carrito = obtenerOCrearCarrito(usuarioId);
        return new CarritoResponse(carrito);
    }

    public CarritoResponse vaciarCarrito(Long usuarioId) {

        Carrito carrito = obtenerOCrearCarrito(usuarioId);

        if (carrito.getItems().isEmpty()) {
            throw new NoSuchElementException("El carrito ya está vacío");
        }

        carrito.getItems().clear();
        repository.save(carrito);

        return new CarritoResponse(carrito);
    }

    private void validarUsuario(Long usuarioId) {
        Boolean usuarioValido;
        try {
            usuarioValido = authClient.validarUsuario(usuarioId).block();
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("No se puede conectar al servicio de autenticación");
        }

        if (!Boolean.TRUE.equals(usuarioValido)) {
            throw new IllegalArgumentException("Usuario no válido o no existe");
        }
    }

        public CarritoResponse actualizarCantidad(Long usuarioId, Integer sku, Integer cantidad) {
        if (cantidad == null || cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
        }

        Carrito carrito = obtenerOCrearCarrito(usuarioId);

        CarritoItem itemExistente = carrito.getItems().stream()
                .filter(item -> item.getSku().equals(sku))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(
                        "Item con SKU " + sku + " no encontrado en el carrito"
                ));

        ZapatillaResponse zapatilla;
        try {
            zapatilla = catalogoClient.obtenerPorSku(sku).block();
        } catch (NoSuchElementException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("No se puede conectar al catálogo");
        }

        if (zapatilla == null) {
            throw new NoSuchElementException("Producto con sku " + sku + " no encontrado");
        }

        if (cantidad > zapatilla.getStock()) {
            throw new IllegalArgumentException("Stock insuficiente");
        }

        itemExistente.setCantidad(cantidad);

        Carrito carritoGuardado = repository.save(carrito);
        return new CarritoResponse(carritoGuardado);
    }
}