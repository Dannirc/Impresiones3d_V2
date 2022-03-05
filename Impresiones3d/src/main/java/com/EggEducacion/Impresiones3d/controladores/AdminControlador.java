package com.EggEducacion.Impresiones3d.controladores;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")

public class AdminControlador {

	/* solo admin */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping
	public String menu() {
		return ("/admin/admin");
	}

}
