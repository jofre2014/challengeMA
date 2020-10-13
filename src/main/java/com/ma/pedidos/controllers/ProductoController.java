package com.ma.pedidos.controllers;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ma.pedidos.dto.ErrorHandler;
import com.ma.pedidos.dto.ErrorMessage;
import com.ma.pedidos.models.entity.Producto;
import com.ma.pedidos.models.service.IProductoService;



@RestController
@RequestMapping(value="/productos")
public class ProductoController {

	@Autowired
	IProductoService productoService;
	
	@GetMapping()
	public ResponseEntity<Object>  listarProductos(){
		
		return new ResponseEntity<>(productoService.findAll(),HttpStatus.OK);
		
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<Object> getProducto(@PathVariable String id){
		Optional<Producto> prodOptional = productoService.findById(id);
		ErrorHandler errorHandler = new ErrorHandler();
		List<ErrorMessage> errores = new ArrayList<>();
		
		
		if(!prodOptional.isPresent()) {
			ErrorMessage error = new ErrorMessage();
			
			error.setError("Producto no encontrado");
			
			errores.add(error);			
			
			errorHandler.setErrores(errores);
			
			return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
			
		}
		
		return new ResponseEntity<>(prodOptional, HttpStatus.OK);
	}
	
	@PostMapping()
	@ResponseStatus(code = HttpStatus.CREATED)
	public void AgregarProducto(@Validated @RequestBody Producto producto) {
		productoService.createProduct(producto);
		
	}
	
	@PutMapping(value="{id}")
	public ResponseEntity<Object> ActualizarProducto(@RequestBody Producto producto, @PathVariable String id) {
		
		Optional<Producto> prodOptional = productoService.findById(id);
		
		if(!prodOptional.isPresent())
			return ResponseEntity.notFound().build();
		
		producto.setProductoId(id);
		
		productoService.createProduct(producto);
		
		return ResponseEntity.noContent().build();
		
	}
	
	@DeleteMapping(value="{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void EliminarProducto(@PathVariable String id) {
		productoService.deleteById(id);
		
	}
}
