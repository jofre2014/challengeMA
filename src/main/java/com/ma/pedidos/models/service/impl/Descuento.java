package com.ma.pedidos.models.service.impl;

public class Descuento {
	private float porcentajeDescuento;

	public Descuento(float porcentajeDescuento) {
		this.porcentajeDescuento = porcentajeDescuento;
	}
	
	public Double calcularDescuento(Double importe) {
		return (importe * this.porcentajeDescuento) /100;
	}

	
	
	
	

}
