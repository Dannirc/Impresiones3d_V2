package com.EggEducacion.Impresiones3d.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.EggEducacion.Impresiones3d.entidades.Pedido;


@Repository
public interface PedidoRepositorio extends JpaRepository<Pedido, String> {
	
	@Query("SELECT c FROM Pedido c WHERE c.id = :id")
	public Pedido buscarPedidoPorId(@Param("id") String id);
}
