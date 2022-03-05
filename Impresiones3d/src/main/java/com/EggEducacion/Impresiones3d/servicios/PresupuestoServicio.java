package com.EggEducacion.Impresiones3d.servicios;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.EggEducacion.Impresiones3d.entidades.Pedido;
import com.EggEducacion.Impresiones3d.entidades.Presupuesto;
import com.EggEducacion.Impresiones3d.repositorios.PresupuestoRepositorio;


@Service
public class PresupuestoServicio {
	@Autowired
	private PresupuestoRepositorio presupuestoRepositorio;
	
	
	@Transactional
	public Presupuesto crearPresupuesto(Pedido pedido, Float precio, Date fechaEntrega, Boolean estado) throws Exception {
		validar(pedido, precio, fechaEntrega);
		Presupuesto presupuesto = new Presupuesto();
		presupuesto.setPedido(pedido);
		presupuesto.setPrecio(precio);
		presupuesto.setFechaEntrega(fechaEntrega);
		presupuesto.setEstado(false);
		return presupuestoRepositorio.save(presupuesto);
	}
	
	@Transactional
	public void modificarPresupuesto(String id, Pedido pedido, Float precio, Date fechaEntrega) throws Exception {
		Optional<Presupuesto> respuesta = presupuestoRepositorio.findById(id);
		if (respuesta.isPresent()) {
			Presupuesto presupuesto = respuesta.get();
			presupuesto.setId(id);
			presupuesto.setPedido(pedido);
			presupuesto.setPrecio(precio);
			presupuesto.setFechaEntrega(fechaEntrega);
			presupuesto.setEstado(presupuesto.getEstado());
			presupuestoRepositorio.save(presupuesto);
		} else {
			throw new Exception("error en el modificar-presupuesto");
		}
	}
	
	@Transactional
	public List<Presupuesto> listarTodos() {
		return presupuestoRepositorio.findAll();
	}

	@Transactional
	public Presupuesto buscarPorId(String id) throws Exception {
		Optional<Presupuesto> respuesta = presupuestoRepositorio.findById(id);
		if (respuesta.isPresent()) {
			return respuesta.get();
		} else {
			throw new Exception("error");
		}
	}

	@Transactional
	public void eliminarPresupuesto(String id) {
		Optional<Presupuesto> respuesta = presupuestoRepositorio.findById(id);
		if(respuesta.isPresent()) {
			presupuestoRepositorio.delete(respuesta.get());
		}
	}
	
	public void validar(Pedido pedido, Float precio, Date fechaEntrega) throws Exception {

		if (pedido == null) {
			throw new Exception("Debe tener un pedido valido");
		}
		
		if (precio == null) {
			throw new Exception("Debe tener un precio valido");
		}	
		
		if (fechaEntrega == null) {
			throw new Exception("Debe tener un plazo de entrega valido");
		}
	}
	
}