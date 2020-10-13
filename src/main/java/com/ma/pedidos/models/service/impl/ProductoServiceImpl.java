package com.ma.pedidos.models.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ma.pedidos.models.dao.IProductoDao;
import com.ma.pedidos.models.entity.Producto;
import com.ma.pedidos.models.service.IProductoService;

@Service
public class ProductoServiceImpl implements IProductoService{
	
	@Autowired
	private IProductoDao productoDao;

	@Override
	public List<Producto> findAll() {
		return productoDao.findAll();
	}

	@Override
	public void createProduct(Producto producto) {
			productoDao.save(producto);		
	}

	@Override
	public Optional<Producto> findById(String id) {
		return productoDao.findById(id);
	}

	@Override
	public void deleteById(String id) {
		productoDao.deleteById(id);
		
	}

}
