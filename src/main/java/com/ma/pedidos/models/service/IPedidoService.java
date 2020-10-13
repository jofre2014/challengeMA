package com.ma.pedidos.models.service;

import java.time.LocalDate;
import java.util.List;

import com.ma.pedidos.dto.PedidoDto;

public interface IPedidoService {
	
	PedidoDto altaPedido(PedidoDto pedido);
	
	List<PedidoDto> pedidosXFecha(LocalDate fecha);
	
	

}
