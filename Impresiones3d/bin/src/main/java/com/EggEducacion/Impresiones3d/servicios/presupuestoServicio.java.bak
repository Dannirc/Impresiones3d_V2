package com.EggEducacion.Impresiones3d.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.EggEducacion.Impresiones3d.entidades.Pedido;
import com.EggEducacion.Impresiones3d.entidades.Presupuesto;
import com.EggEducacion.Impresiones3d.repositorios.presupuestoRepositorio;


@Service
public class presupuestoServicio {
	@Autowired
	private presupuestoRepositorio presupuestoRepositorio;
	
	
	@Transactional
	public Presupuesto crearPresupuesto(String id, Pedido pedido, Float precio, Integer plazoEntrega) throws Exception {
		Presupuesto presupuesto = new Presupuesto();
		presupuesto.setId(id);
		presupuesto.setPedido(pedido);
		presupuesto.setPrecio(precio);
		return presupuestoRepositorio.save(presupuesto);
	}
	
	@Transactional
	public void modificarPresupuesto(String id, Pedido pedido, Float precio, Integer plazoEntrega) throws Exception {
		Optional<Presupuesto> respuesta = presupuestoRepositorio.findById(id);
		if (respuesta.isPresent()) {
			Presupuesto presupuesto = respuesta.get();
			presupuesto.setId(id);
			presupuesto.setPedido(pedido);
			presupuesto.setPrecio(precio);
			presupuestoRepositorio.save(presupuesto);
		} else {
			throw new Exception("error en el modificar-presupuesto");
		}
	}
	
	@Transactional
	public List<Presupuesto> listarTodos() {
		return presupuestoRepositorio.findAll();
	}
	
}