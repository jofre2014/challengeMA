package com.ma.pedidos.dto;

import java.io.Serializable;
import java.util.List;



public class ErrorHandler implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	private List<ErrorMessage> errores;

	public List<ErrorMessage> getErrores() {
		return errores;
	}

	public void setErrores(List<ErrorMessage> errores) {
		this.errores = errores;
	}

}
