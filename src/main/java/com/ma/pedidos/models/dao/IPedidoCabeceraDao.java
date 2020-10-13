package com.ma.pedidos.models.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ma.pedidos.models.entity.PedidoCabecera;

public interface IPedidoCabeceraDao extends JpaRepository<PedidoCabecera, String>{
	
	List<PedidoCabecera> findByFecha(LocalDate fecha);
	

}
