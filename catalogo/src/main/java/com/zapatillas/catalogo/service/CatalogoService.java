package com.zapatillas.catalogo.service;

import com.zapatillas.catalogo.model.Zapatilla;
import com.zapatillas.catalogo.repository.CatalogoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CatalogoService {

    private final CatalogoRepository repository;

    public List<Zapatilla> listar() {
        return repository.findAll();
    }

    public Zapatilla buscarPorSKU(Integer sku) {
        return repository.findById(sku)
                .orElseThrow(() -> new NoSuchElementException("Zapatilla no encontrada"));
    }

    public Zapatilla buscarPorNombre(String nombre) {
        return repository.findByNombreIgnoreCase(nombre)
                .orElseThrow(() -> new NoSuchElementException("Zapatilla no encontrada"));
    }

    public Zapatilla buscarPorMarca(String marca) {
        return repository.findByMarcaIgnoreCase(marca)
                .orElseThrow(() -> new NoSuchElementException("Zapatilla no encontrada"));
    }

    public Zapatilla buscarPorModelo(String modelo) {
        return repository.findByModeloIgnoreCase(modelo)
                .orElseThrow(() -> new NoSuchElementException("Zapatilla no encontrada"));
    }

    public Zapatilla buscarPorTalla(String talla) {
        return repository.findByTallaIgnoreCase(talla)
                .orElseThrow(() -> new NoSuchElementException("Zapatilla no encontrada"));
    }

    public Zapatilla guardar(Zapatilla zapatilla) {
        if (repository.findByNombreIgnoreCase(zapatilla.getNombre()).isPresent()) {
            throw new IllegalArgumentException("La zapatilla ya existe");
        }
        return repository.save(zapatilla);
    }

    public Zapatilla actualizar(Zapatilla zapatilla) {
        if (zapatilla.getSku() == null) {
            throw new IllegalArgumentException("El sku es obligatorio para actualizar");
        }

        if (!repository.existsById(zapatilla.getSku())) {
            throw new NoSuchElementException("Zapatilla no encontrada");
        }

        return repository.save(zapatilla);
    }

    public void eliminar(Integer sku) {
        if (!repository.existsById(sku)) {
            throw new NoSuchElementException("Zapatilla no encontrada");
        }
        repository.deleteById(sku);
    }
}