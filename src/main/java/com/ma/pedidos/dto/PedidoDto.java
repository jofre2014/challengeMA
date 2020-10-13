package com.ma.pedidos.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;


public class PedidoDto implements Serializable{
	
	
	private static final long serialVersionUID = 1L;

	private LocalDate fecha;
	
	@NotNull(message = "la direccion no puede estar nula")
	private String direccion;
	
	private String email;
	
	private String telefono;
	
	private LocalTime horario;
	
	
	@Valid
	private List<DetalleDto> detalle;
	
	
	private Double total;
	
	private Boolean descuento;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String estado;

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public LocalTime getHorario() {
		return horario;
	}

	public void setHorario(LocalTime horario) {
		this.horario = horario;
	}

	public List<DetalleDto> getDetalle() {
		return detalle;
	}

	public void setDetalle(List<DetalleDto> detalle) {
		this.detalle = detalle;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Boolean getDescuento() {
		return descuento;
	}

	public void setDescuento(Boolean descuento) {
		this.descuento = descuento;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	

}
