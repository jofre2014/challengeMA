package com.ma.pedidos.dto;

import java.io.Serializable;

import javax.validation.constraints.Min;

public class DetalleDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	
	private String producto;
	
	
	@Min(value=1,message = "falta ingresar cantidad")
	private int cantidad;
	
	private String nombre;
	
	private Double importe;

	public String getProducto() {
		return producto;
	}

	public void setProducto(String producto) {
		this.producto = producto;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Double getImporte() {
		return importe;
	}

	public void setImporte(Double importe) {
		this.importe = importe;
	}

}
