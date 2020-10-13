package com.ma.pedidos.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ma.pedidos.models.entity.Producto;



public interface IProductoDao extends JpaRepository<Producto, String> {

}
