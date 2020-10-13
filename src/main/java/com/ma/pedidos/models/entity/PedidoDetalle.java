package com.ma.pedidos.models.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;



@Entity
@Table(name="pedido_detalle")
public class PedidoDetalle implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="pedido_detalle_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="pedido_cabecera_id")
	private PedidoCabecera pedidoCabeceraId;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="producto_id")
	private Producto productoId;
	
	private int cantidad;
	
	private Double precioUnitario;

	


	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public Producto getProductoId() {
		return productoId;
	}

	public void setProductoId(Producto productoId) {
		this.productoId = productoId;
	}

	public PedidoCabecera getPedidoCabeceraId() {
		return pedidoCabeceraId;
	}

	public void setPedidoCabeceraId(PedidoCabecera pedidoCabeceraId) {
		this.pedidoCabeceraId = pedidoCabeceraId;
	}



	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public Double getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(Double precioUnitario) {
		this.precioUnitario = precioUnitario;
	}
	
	
	
	

}
