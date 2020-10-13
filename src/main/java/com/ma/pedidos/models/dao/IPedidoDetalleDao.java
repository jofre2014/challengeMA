package com.ma.pedidos.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ma.pedidos.models.entity.PedidoDetalle;


public interface IPedidoDetalleDao extends JpaRepository<PedidoDetalle, String>{
	
	@Query(value =  "SELECT * FROM pedido_detalle WHERE pedido_cabecera_id = :cabeceraId", nativeQuery = true )
	List<PedidoDetalle> findByCabecera(@Param("cabeceraId") Long pedidoCabeceraId);


}
