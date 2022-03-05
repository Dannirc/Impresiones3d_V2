package com.EggEducacion.Impresiones3d.controladores;
import javax.servlet.http.HttpSession;

import com.EggEducacion.Impresiones3d.entidades.Usuario;
import com.EggEducacion.Impresiones3d.enums.Rol;
import com.EggEducacion.Impresiones3d.repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping({"/", "/index"})
public class IndexControlador {

	@Autowired
	UsuarioRepositorio usuarioRepositorio;

	@GetMapping
	public String index() {
		try {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String email = userDetails.getUsername();
		Usuario usuario = usuarioRepositorio.buscarPorEmail(email);

		if(usuario.getRol()== Rol.CLIENTE){
			return ("redirect:/usuario");
		}
		
		if(usuario.getRol()== Rol.ADMIN){
			return ("redirect:/admin");
		}

		return ("index/index");
		} catch (Exception e) {
			return ("index/index");
		}
		
	}
  
	@GetMapping("/login")
	public String login(HttpSession session, Authentication usuario, ModelMap modelo, @RequestParam(required = false) String error) {
		try {
			if (usuario.getName() != null) {
				session.invalidate();
				return "redirect:/";
			} else {
				
				if (error != null && !error.isEmpty()) {
					session.invalidate();
					modelo.addAttribute("error", "La direcci�n de mail o la contrase�a que ingreso son incorrectas.");
				}
				return "index/login";
			}
			
		} catch (Exception e) {
			if (error != null && !error.isEmpty()) {
				modelo.addAttribute("error", "La direccion de mail o la contrase�a que ingreso son incorrectas.");
			}
			session.invalidate();
			return "index/login";
		}
	}


	@GetMapping("/loginsuccess")
	public String loginresolver() {
		return "redirect:/";
	} 
	
	@GetMapping("instructivo")
	public String instrucciones() {		
		return ("index/instructivo2");
	}
	
	@GetMapping("team")
	public String team() {		
		return ("index/team");
	}
}