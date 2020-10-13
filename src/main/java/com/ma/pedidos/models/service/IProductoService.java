package com.ma.pedidos.models.service;

import java.util.List;
import java.util.Optional;

import com.ma.pedidos.models.entity.Producto;



public interface IProductoService {
	
    List<Producto> findAll();
	
	void createProduct(Producto producto);
	
	Optional<Producto> findById(String id);
	
	void deleteById(String id);

}
