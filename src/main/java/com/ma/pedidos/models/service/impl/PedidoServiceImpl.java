package com.ma.pedidos.models.service.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ma.pedidos.dto.DetalleDto;
import com.ma.pedidos.dto.PedidoDto;
import com.ma.pedidos.models.dao.IPedidoCabeceraDao;
import com.ma.pedidos.models.dao.IPedidoDetalleDao;
import com.ma.pedidos.models.dao.IProductoDao;
import com.ma.pedidos.models.entity.PedidoCabecera;
import com.ma.pedidos.models.entity.PedidoDetalle;
import com.ma.pedidos.models.entity.Producto;
import com.ma.pedidos.models.service.IPedidoService;

@Service
public class PedidoServiceImpl implements IPedidoService {

	private final float DESCUENTO_30 = 30;

	private final String PENDIENTE = "PENDIENTE";

	private Double totalPedido;

	private Boolean descuento;

	private int cantProductos;
	
	

	@Autowired
	private IPedidoCabeceraDao pedidoCabeceraDao;

	@Autowired
	private IPedidoDetalleDao pedidoDetalleDao;

	@Autowired
	private IProductoDao productoDao;

	@Override
	public PedidoDto altaPedido(PedidoDto pedido) {
		totalPedido = 0.0;
		descuento = false;
		cantProductos = 0;
		
		

		LocalDate fechaActual = LocalDate.now();
		LocalTime HoraFormat = LocalTime.of(pedido.getHorario().getHour(), pedido.getHorario().getMinute());
		
		PedidoCabecera pedidoCabecera = new PedidoCabecera();
		pedidoCabecera.setDireccion(pedido.getDireccion());
		pedidoCabecera.setEmail(pedido.getEmail());
		pedidoCabecera.setTelefono(pedido.getTelefono());
		pedidoCabecera.setHorario(HoraFormat);
		pedidoCabecera.setFecha(fechaActual);
		pedidoCabecera.setEstado(PENDIENTE);
		pedidoCabecera.setDescuento(descuento);
		pedidoCabecera.setTotal(this.totalPedido);
		pedidoCabeceraDao.save(pedidoCabecera);
		
		

		List<DetalleDto> detalleDto = pedido.getDetalle().stream().map(det -> obtenerDetalle(det, pedidoCabecera))
				.collect(Collectors.toList());

		// Aplicar Descuento
		if (cantProductos > 3) {

			descuento = true;

			Descuento desc = new Descuento(DESCUENTO_30);
			this.totalPedido = this.totalPedido - desc.calcularDescuento(this.totalPedido);

		}
		


		pedidoCabecera.setDescuento(descuento);
		pedidoCabecera.setTotal(this.totalPedido);
		pedidoCabeceraDao.save(pedidoCabecera);

		pedido.setHorario(pedidoCabecera.getHorario());
		pedido.setDescuento(descuento);
		pedido.setDetalle(detalleDto);
		pedido.setFecha(fechaActual);
		pedido.setTotal(this.totalPedido);
		pedido.setEstado(PENDIENTE);

		return pedido;
	}

	
	private DetalleDto obtenerDetalle(DetalleDto detalle, PedidoCabecera pedidoCabecera) {

		DetalleDto newDto = new DetalleDto();
		Optional<Producto> producto = productoDao.findById(detalle.getProducto());

		if (!producto.isPresent())
			return null;

		newDto.setCantidad(detalle.getCantidad());
		newDto.setImporte(producto.get().getPrecioUnitario() * detalle.getCantidad());
		newDto.setNombre(producto.get().getDescripcionCorta());
		newDto.setProducto(detalle.getProducto());
		
		guardarDetalle(newDto, pedidoCabecera, producto);

		this.totalPedido += producto.get().getPrecioUnitario() * detalle.getCantidad();
		this.cantProductos += detalle.getCantidad();

		return newDto;

	}
	
	private Object guardarDetalle(DetalleDto dto, PedidoCabecera pedidoCabecera, Optional<Producto> producto) {
		PedidoDetalle pedidoDetalle = new PedidoDetalle();
		pedidoDetalle.setPedidoCabeceraId(pedidoCabecera);
		pedidoDetalle.setProductoId(producto.get());
		pedidoDetalle.setCantidad(dto.getCantidad());
		pedidoDetalle.setPrecioUnitario(producto.get().getPrecioUnitario());
		pedidoDetalleDao.save(pedidoDetalle);
		
		
		return null;
	}


	@Override
	public List<PedidoDto> pedidosXFecha(LocalDate fecha) {
		
		List<PedidoDto> pedidosDto = new ArrayList<>();
		
		List<PedidoCabecera>  pedidos = pedidoCabeceraDao.findByFecha(fecha).stream()
				.sorted(Comparator.comparing(PedidoCabecera::getHorario))
				.collect(Collectors.toList());
		
		for(PedidoCabecera pedido : pedidos) {
			PedidoDto pedidoDto = new PedidoDto();
			
			
			List<DetalleDto> detalleListDto = generarDetalleDto(pedido);
			
			
			pedidoDto.setFecha(pedido.getFecha());
			pedidoDto.setDireccion(pedido.getDireccion());
			pedidoDto.setEmail(pedido.getEmail());
			pedidoDto.setTelefono(pedido.getTelefono());
			pedidoDto.setHorario(pedido.getHorario());
			pedidoDto.setDetalle(detalleListDto);
			pedidoDto.setTotal(pedido.getTotal());
			pedidoDto.setDescuento(pedido.getDescuento());
		
			pedidosDto.add(pedidoDto);
		}
		

		
		return pedidosDto;
	}


	private List<DetalleDto> generarDetalleDto(PedidoCabecera pedido) {
		List<DetalleDto> detalleListDto = new ArrayList<DetalleDto>();
		
		List<PedidoDetalle> pedidoDetalleList = pedidoDetalleDao.findByCabecera(pedido.getId());
		
		for(PedidoDetalle pedidoDetalle : pedidoDetalleList) {
			Optional<Producto> producto = productoDao.findById(pedidoDetalle.getProductoId().getProductoId());
			
			DetalleDto detalleDto = new DetalleDto();
			detalleDto.setCantidad(pedidoDetalle.getCantidad());
			detalleDto.setImporte(pedidoDetalle.getPrecioUnitario() * pedidoDetalle.getCantidad());
			detalleDto.setNombre(producto.get().getDescripcionCorta());
			detalleDto.setProducto(producto.get().getProductoId());
			
			detalleListDto.add(detalleDto);
		}		
		
		return detalleListDto;
	}




}
