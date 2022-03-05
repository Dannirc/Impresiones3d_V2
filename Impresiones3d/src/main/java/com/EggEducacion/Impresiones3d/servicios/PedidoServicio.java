package com.EggEducacion.Impresiones3d.servicios;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.EggEducacion.Impresiones3d.entidades.Usuario;
import com.EggEducacion.Impresiones3d.entidades.Pedido;
import com.EggEducacion.Impresiones3d.entidades.Producto;
import com.EggEducacion.Impresiones3d.repositorios.PedidoRepositorio;


@Service
public class PedidoServicio {
	@Autowired
	private PedidoRepositorio pedidoRepositorio;
	
	
	@Transactional
	public Pedido crearPedido(String id, Producto producto, Usuario usuario, Date fecha) throws Exception {
		validar(producto, usuario, fecha);
		Pedido pedido = new Pedido();
		pedido.setId(id);
		pedido.setProducto(producto);
		pedido.setUsuario(usuario);
		pedido.setFecha(fecha);
		return pedidoRepositorio.save(pedido);
	}
	
	@Transactional
	public void modificarPedido(String id, Producto producto, Usuario usuario, Date fecha) throws Exception {
		Optional<Pedido> respuesta = pedidoRepositorio.findById(id);
		if (respuesta.isPresent()) {
			Pedido pedido = respuesta.get();
			pedido.setId(id);
			pedido.setProducto(producto);
			pedido.setUsuario(usuario);
			pedido.setFecha(fecha);
			pedidoRepositorio.save(pedido);
		} else {
			throw new Exception("error en el modificar-pedido");
		}
	}

	@Transactional
	public void eliminarPedido(String id) {
		Optional<Pedido> respuesta = pedidoRepositorio.findById(id);
		if(respuesta.isPresent()) {
			pedidoRepositorio.delete(respuesta.get());
		}
	}
	
	public Pedido buscarPorId(String id) throws Exception {
		Optional<Pedido> respuesta = pedidoRepositorio.findById(id);
		if (respuesta.isPresent()) {
			return respuesta.get();
		} else {
			throw new Exception("error");
		}
	}
	
	@Transactional(readOnly = true)
	public Pedido pedidoUsuario(String id) {
		Pedido pedido = pedidoRepositorio.buscarPedidoPorId(id);
		return pedido;
	}
	
	@Transactional
	public List<Pedido> listarTodos() {
		List<Pedido> listaPedidos = pedidoRepositorio.findAll();

		for (Pedido pedido : listaPedidos) {
			pedido.getProducto().getArchivo().getId();
		}


		return pedidoRepositorio.findAll();
	}
	
	public void validar(Producto producto, Usuario usuario, Date fecha) throws Exception {

		if (producto == null) {
			throw new Exception("Debe tener un producto valido");
		}
		
		if (usuario == null) {
			throw new Exception("Debe tener un usuario valido");
		}	
		
		if (fecha == null) {
			throw new Exception("Debe tener una fecha valida");
		}

	}
	
}