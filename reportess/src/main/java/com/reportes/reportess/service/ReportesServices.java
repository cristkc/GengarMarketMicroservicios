package com.reportes.reportess.service;

import com.reportes.reportess.repository.ReportesRepository;
import com.reportes.reportess.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.reportes.reportess.Client.*;
import com.reportes.reportess.dto.PagoResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
@Service
public class ReportesServices {
@Autowired
private ReportesRepository repository;
@Autowired
private PagoClient pagoClient;


public List<reportes> listar(){
    return repository.findAll();
}
public Optional<reportes> buscarPorId(Integer id){
    return repository.findById(id);
}
public reportes guardarReportes(reportes repo){
    return repository.save(repo);
}
public void eliminarPorId(Integer id){
    repository.deleteById(id);
}
public reportes actualizarPorId(Integer id , reportes re){
    re.setId(id);
    return repository.save(re);
}
public List<Integer> mostrarPagosTotales(){

    List<PagoResponse> pagos = pagoClient
            .obtenerPagos()
            .block();

    if(pagos == null || pagos.isEmpty()){
        throw new NoSuchElementException(
                "No se han encontrado pagos"
        );
    }

    return pagos.stream()
            .map(PagoResponse::getTotalFinal)
            .toList();
}


public reportes listaReportes(){

    List<PagoResponse> pagos = pagoClient
            .obtenerPagos()
            .block();

    if(pagos == null || pagos.isEmpty()){
        throw new NoSuchElementException(
                "No se han encontrado pagos"
        );
    }

    Integer ganancia = pagos.stream()
            .mapToInt(PagoResponse::getTotalFinal)
            .sum();

    reportes reporte = new reportes();

    reporte.setGanancia(ganancia);

    reporte.setFecha(
            LocalDate.now().toString()
    );

    return repository.save(reporte);
}
}
