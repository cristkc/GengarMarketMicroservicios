package com.gengarmarket.gestionPedido.repository;

import com.gengarmarket.gestionPedido.model.Pedido;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido,Long>{
}