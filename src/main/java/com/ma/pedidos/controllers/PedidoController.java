package com.ma.pedidos.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ma.pedidos.dto.ErrorHandler;
import com.ma.pedidos.dto.ErrorMessage;
import com.ma.pedidos.dto.PedidoDto;
import com.ma.pedidos.models.service.IPedidoService;

@RestController
@RequestMapping(value="/pedidos")
public class PedidoController {
	
	@Autowired
	private IPedidoService pedidoService;

	@PostMapping
	public ResponseEntity<Object> nuevoPedido(@Valid @RequestBody PedidoDto pedido,  BindingResult bindingResult){
		ErrorHandler errorHandler = new ErrorHandler();
		List<ErrorMessage> errores = new ArrayList<>();
		
		if (bindingResult.hasErrors()) {
			
			for( FieldError fieldError : bindingResult.getFieldErrors()) {
				ErrorMessage error = new ErrorMessage();
				error.setError(fieldError.getDefaultMessage());
				errores.add(error);				
			}
			
			errorHandler.setErrores(errores);

			return new ResponseEntity<>(errorHandler, HttpStatus.BAD_REQUEST);
			
		}		
		
		PedidoDto pedidoDtoResponse = pedidoService.altaPedido(pedido);
		
		return new ResponseEntity<>(pedidoDtoResponse, HttpStatus.CREATED);
		
	}
	
	@GetMapping
	public ResponseEntity<Object> pedidosXFecha(@RequestParam("fecha") String fechaStr){
		
		LocalDate fecha = LocalDate.parse(fechaStr);
		
		List<PedidoDto> pedidos = pedidoService.pedidosXFecha(fecha);
		
		return new ResponseEntity<>(pedidos, HttpStatus.OK);
	}
	
	
}
