package com.EggEducacion.Impresiones3d.servicios;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.EggEducacion.Impresiones3d.entidades.Compra;
import com.EggEducacion.Impresiones3d.entidades.Presupuesto;
import com.EggEducacion.Impresiones3d.repositorios.CompraRepositorio;


@Service
public class CompraServicio {
	@Autowired
	private CompraRepositorio compraRepositorio;
	
	
	@Transactional
	public Compra crearCompra(Presupuesto presupuesto, Date fecha, Boolean pago, Boolean terminado) throws Exception {
		validar(presupuesto, pago, terminado);
		Compra Compra = new Compra();
		Compra.setPago(pago);
		Compra.setPresupuesto(presupuesto);
		Compra.setFecha(fecha);
		Compra.setTerminado(terminado);
		return compraRepositorio.save(Compra);
	}
	
	@Transactional
	public void modificarCompra(String id, Presupuesto presupuesto, Boolean pago, Boolean terminado) throws Exception {
		Optional<Compra> respuesta = compraRepositorio.findById(id);
		if (respuesta.isPresent()) {
			Compra Compra = respuesta.get();
			Compra.setId(id);
			Compra.setPago(pago);
			Compra.setPresupuesto(presupuesto);
			Compra.setTerminado(terminado);
			compraRepositorio.save(Compra);
		} else {
			throw new Exception("error en el modificar-Compra");
		}
	}

	@Transactional
	public void entregarCompra(String id) throws Exception {
		Optional<Compra> respuesta = compraRepositorio.findById(id);
		if (respuesta.isPresent()) {
			Compra compra = respuesta.get();
			compra.setTerminado(true);
			compraRepositorio.save(compra);
		} else {
			throw new Exception("error en el modificar-Compra");
		}
	}
	
	@Transactional
	public List<Compra> listarTodos() {
		return compraRepositorio.findAll();
	}
	
	public void validar(Presupuesto presupuesto, Boolean pago, Boolean terminado) throws Exception {

		if (presupuesto == null) {
			throw new Exception("Debe tener un presupuesto valido");
		}
		
		if (pago == null) {
			throw new Exception("Debe tener un pago valido");
		}	
		
		if (terminado == null) {
			throw new Exception("Debe tener un pago terminado valido");
		}


		

	}
	
}