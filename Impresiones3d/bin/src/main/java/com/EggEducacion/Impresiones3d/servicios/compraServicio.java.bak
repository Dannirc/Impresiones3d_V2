package com.EggEducacion.Impresiones3d.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.EggEducacion.Impresiones3d.entidades.Compra;
import com.EggEducacion.Impresiones3d.entidades.Presupuesto;
import com.EggEducacion.Impresiones3d.repositorios.compraRepositorio;


@Service
public class compraServicio {
	@Autowired
	private compraRepositorio CompraRepositorio;
	
	
	@Transactional
	public Compra crearCompra(String id, Presupuesto presupuesto, Boolean pago, Boolean terminado) throws Exception {
		Compra Compra = new Compra();
		Compra.setId(id);
		Compra.setPago(pago);
		Compra.setPresupuesto(presupuesto);
		Compra.setTerminado(terminado);
		return CompraRepositorio.save(Compra);
	}
	
	@Transactional
	public void modificarCompra(String id, Presupuesto presupuesto, Boolean pago, Boolean terminado) throws Exception {
		Optional<Compra> respuesta = CompraRepositorio.findById(id);
		if (respuesta.isPresent()) {
			Compra Compra = respuesta.get();
			Compra.setId(id);
			Compra.setPago(pago);
			Compra.setPresupuesto(presupuesto);
			Compra.setTerminado(terminado);
			CompraRepositorio.save(Compra);
		} else {
			throw new Exception("error en el modificar-Compra");
		}
	}
	
	@Transactional
	public List<Compra> listarTodos() {
		return CompraRepositorio.findAll();
	}
	
}