package com.ma.pedidos.dto;

import java.io.Serializable;

public class ErrorMessage implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String error;

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
	
	

}
