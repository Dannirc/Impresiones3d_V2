package com.EggEducacion.Impresiones3d.controladores;

import java.util.List;

import com.EggEducacion.Impresiones3d.entidades.Compra;
import com.EggEducacion.Impresiones3d.servicios.CompraServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/compra")
public class CompraControlador {

    @Autowired
    private CompraServicio compraServicio;

    /* admin */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_CLIENTE')")
    /* usuario solo puede ver sus compras */
    @GetMapping("/lista-compras")
    public String listaCompras(Model model) throws Exception {

        List<Compra> listaCompras = compraServicio.listarTodos();

        model.addAttribute("listaCompras", listaCompras);

        return "compra/listaCompras";
    }

    /* solo admin */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/entregar-compra")
    public String entregado(String id) throws Exception {

        compraServicio.entregarCompra(id);
        
        return "redirect:/compra/lista-compras";
    }

}