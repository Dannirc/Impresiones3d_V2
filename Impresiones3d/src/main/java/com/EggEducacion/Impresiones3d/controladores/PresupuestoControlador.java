package com.EggEducacion.Impresiones3d.controladores;

import java.util.Date;
import java.util.List;

import com.EggEducacion.Impresiones3d.entidades.Compra;
import com.EggEducacion.Impresiones3d.entidades.Pedido;
import com.EggEducacion.Impresiones3d.entidades.Presupuesto;
import com.EggEducacion.Impresiones3d.servicios.CompraServicio;
import com.EggEducacion.Impresiones3d.servicios.PedidoServicio;
import com.EggEducacion.Impresiones3d.servicios.PresupuestoServicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/presupuesto")
public class PresupuestoControlador {

	@Autowired
	private PedidoServicio pedidoServicio;
	@Autowired
	private PresupuestoServicio presupuestoServicio;
	@Autowired
	private CompraServicio compraServicio;

	/* solo admin */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/crear-presupuesto")
	public String crearPresupuesto(String id, Model model) throws Exception {
		Pedido pedido = pedidoServicio.buscarPorId(id);

		model.addAttribute("Pedido", pedido);

		return ("presupuesto/crearPresupuesto");
	}

	@PostMapping("/crear-presupuesto")
	public String crearPresupuesto(ModelMap modelo, @RequestParam String id, @RequestParam Float precio,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaEntrega) throws Exception {

		Pedido pedido = pedidoServicio.buscarPorId(id);

		try {
			presupuestoServicio.crearPresupuesto(pedido, precio, fechaEntrega, false);
			modelo.put("exito", "Presupuesto creado!");
			return ("redirect:/presupuesto/lista-presupuestos");
		} catch (Exception e) {
			modelo.put("error", e.getMessage());
			return ("redirect:/presupuesto/lista-presupuestos");
		}
	}

	/* admin y solo usuario */
	/* el usuario solo podra ver sus presupuestos */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_CLIENTE')")
	@GetMapping("/lista-presupuestos")
	public String listaPedidos(Model model) {

		List<Presupuesto> listaPresupuestos = presupuestoServicio.listarTodos();

		model.addAttribute("listaPresupuestos", listaPresupuestos);

		return "presupuesto/listaPresupuestos";
	}

	/* admin */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/eliminar-presupuesto")
	public String eliminarPresupuesto(String id) {

		presupuestoServicio.eliminarPresupuesto(id);

		return ("redirect:/presupuesto/lista-presupuestos");
	}

	/* admin y usuario */
	/* el usuario solo prodra confirmar su presupuesto */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_CLIENTE')")
	@GetMapping("/confirmar-presupuesto")
	public String confirmarPresupuesto(String id) throws Exception {

		Presupuesto presupuesto = presupuestoServicio.buscarPorId(id);
		presupuesto.setEstado(true);
		
		Compra compra = compraServicio.crearCompra(presupuesto, new Date(), false, false);

		return ("redirect:/compra/lista-compras");
	}

	/* falta terminar */
	/* admin */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/modificar-presupuesto")
	public String modificarPresupuesto(ModelMap modelo, @RequestParam("id") String id) {
		try {
			Presupuesto presupuesto = presupuestoServicio.buscarPorId(id);
			modelo.addAttribute("Presupuesto", presupuesto);
			return ("presupuesto/modificarPresupuesto");
		} catch (Exception e) {
			modelo.put("error", e.getMessage());
			return ("presupuesto/modificarPresupuesto");
		}
	}

	@PostMapping("/modificar-presupuesto")
	public String guardarPresupuesto(ModelMap modelo, @RequestParam String id,@RequestParam String idPresupuesto, @RequestParam Float precio,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaEntrega) throws Exception {

		Pedido pedido = pedidoServicio.buscarPorId(id);

		try {
			presupuestoServicio.modificarPresupuesto(idPresupuesto,pedido, precio, fechaEntrega);
			modelo.put("exito", "Presupuesto creado!");
			return ("redirect:/presupuesto/lista-presupuestos");
		} catch (Exception e) {
			modelo.put("error", e.getMessage());
			return ("redirect:/presupuesto/lista-presupuestos");
		}
	}

}