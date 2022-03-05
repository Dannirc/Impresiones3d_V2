package com.EggEducacion.Impresiones3d.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.EggEducacion.Impresiones3d.entidades.Usuario;
import com.EggEducacion.Impresiones3d.servicios.UsuarioServicio;

@Controller
@RequestMapping("/usuario")
public class UsuarioControlador {

	@Autowired
	private UsuarioServicio usuarioService;

	/* solo usuario */
	@GetMapping
	public String menu() {
		return ("usuario/usuario");
	}

	@GetMapping("/registro")
	public String formularioRegistro() {
		return "usuario/registrar";
	}

	@PostMapping("/registro")
	public String guardarUsuario(ModelMap modelo, @RequestParam String nombre, @RequestParam String direccion,
			@RequestParam String email, @RequestParam String clave, @RequestParam String rol,
			@RequestParam String telefono) {

		try {
			usuarioService.crearUsuario(rol, nombre, direccion, email, telefono, clave);
			modelo.put("exito","registro exitoso");
			return "index/login";
		} catch (Exception e) {
			modelo.put("error", e.getMessage());
			return "usuario/registrar";
		}
	}

	/* usuario y admin */
	/* usuario no puede modificar rol */
	/* comprobar que un usuario solo pueda modificar sus datos */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_CLIENTE')")
	@GetMapping("/modificar-usuario")
	public String modificarUsuario(ModelMap modelo, @RequestParam("id") String id) {
		try {
			Usuario usuario = usuarioService.buscarPorId(id);
			modelo.addAttribute("usuario", usuario);
			return ("usuario/modificarUsuario");
		} catch (Exception e) {
			modelo.put("error", e.getMessage());
			return ("usuario/modificarUsuario");
		}
	}

	/* Metodo Post para modificar usuario */
	@PostMapping("/modificar-usuario") // no salta cartelito de error o exito
	public String guardarUsuario(ModelMap modelo, @RequestParam String id, @RequestParam String nombre,
			@RequestParam String direccion, @RequestParam String email, @RequestParam String clave,
			@RequestParam String rol, @RequestParam String telefono) {
		try {
			if (id == null) {
				usuarioService.crearUsuario(rol, nombre, direccion, email, telefono, clave);
				modelo.put("exito", "registro exitoso");
			} else {
				usuarioService.modificarUsuario(rol, id, nombre, direccion, email, telefono, clave);
				modelo.put("exito", "registro exitoso");
			}
			return ("redirect:/");
		} catch (Exception e) {
			modelo.put("error", e.getMessage());
			return ("redirect:/");
		}
	}

	/* solo admin */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/listar-usuarios")
	public String listarUsuarios(ModelMap modelo) {
		try {
			List<Usuario> listarUsuarios = usuarioService.listarTodos();
			modelo.addAttribute("usuarios", listarUsuarios);
			return "admin/listaUsuarios";
		} catch (Exception e) {
			return ("redirect:/index");
		}

	}

	/* solo admin */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/eliminar-usuario")
	public String eliminar(String id) {

		usuarioService.eliminarUsuario(id);

		return ("redirect:/usuario/listar-usuarios");
		// return "usuario/listaUsuarios";(esto es lo que habia antes)
	}

}