package com.EggEducacion.Impresiones3d.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.EggEducacion.Impresiones3d.entidades.Usuario;
import com.EggEducacion.Impresiones3d.enums.Rol;
import com.EggEducacion.Impresiones3d.errores.WebException;
import com.EggEducacion.Impresiones3d.repositorios.UsuarioRepositorio;

@Service
public class UsuarioServicio implements UserDetailsService {
	@Autowired
	private UsuarioRepositorio usuarioRepositorio;

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { WebException.class, Exception.class })
	public Usuario crearUsuario(String rol, String nombre, String direccion, String email, String telefono,
			String clave) throws WebException {
		validar(telefono, direccion, nombre, email, clave, rol);
		Usuario usuario = new Usuario();
		usuario.setRol(Rol.valueOf(rol));
		// usuario.setId(id);
		usuario.setNombre(nombre);
		usuario.setClave(new BCryptPasswordEncoder().encode(clave));
		usuario.setDireccion(direccion);
		usuario.setTelefono(telefono);
		usuario.setEmail(email);
		return usuarioRepositorio.save(usuario);
	}

	@Transactional
	public void modificarUsuario(String rol, String id, String nombre, String direccion, String email, String telefono,
			String clave) throws WebException {
		Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
		if (respuesta.isPresent()) {
			Usuario usuario = respuesta.get();

			if (nombre != null && !nombre.isEmpty()) {
				usuario.setNombre(nombre);
			}else {
				usuario.setNombre(usuario.getNombre());
			}

			if (clave != null && !clave.isEmpty()) {
				usuario.setClave(new BCryptPasswordEncoder().encode(clave));
			}else {
				usuario.setClave(usuario.getClave());
			}

			if (direccion != null && !direccion.isEmpty()) {
				usuario.setDireccion(direccion);
			}else {
				usuario.setDireccion(usuario.getDireccion());
			}

			if (telefono != null && !telefono.isEmpty()) {
				usuario.setTelefono(telefono);
			}else {
				usuario.setTelefono(usuario.getTelefono());
			}

			if (email != null && !email.isEmpty()) {
				usuario.setEmail(email);
			}else {
				usuario.setEmail(usuario.getEmail());
			}
			
			usuarioRepositorio.save(usuario);
		} else {
			throw new WebException("error en el modificar-cliente");
		}
	}

	@Transactional
	public List<Usuario> listarTodos() {
		return usuarioRepositorio.findAll();
	}

	@Transactional(readOnly = true)
	public Usuario consultaUsuario(String id) {
		Usuario usuario = usuarioRepositorio.buscarUsuarioPorId(id);
		return usuario;
	}

	public Usuario buscarPorId(String id) throws Exception {
		Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
		if (respuesta.isPresent()) {
			return respuesta.get();
		} else {
			throw new Exception("error");
		}
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		Usuario user = usuarioRepositorio.buscarPorEmail(email);

		if (user != null) {
			List<GrantedAuthority> permissions = new ArrayList<>();
			GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + user.getRol().toString());
			permissions.add(p);
			ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			HttpSession session = attr.getRequest().getSession(true);
			session.setAttribute("usuario", user);
			return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getClave(),
					permissions);
		}
		return null;

	}

	public void validar(String telefono, String direccion, String nombre, String email, String clave, String rol)
			throws WebException {

		if (direccion == null || direccion.isEmpty() || direccion.contains("  ")) {
			throw new WebException("Debe tener una direccion valida");
		}

		if (telefono == null || telefono.isEmpty() || telefono.contains("  ")) {
			throw new WebException("Debe tener un telefono valido");
		}

		if (nombre == null || nombre.isEmpty() || nombre.contains("  ")) {
			throw new WebException("Debe tener un nombre valido");
		}

		if (email == null || email.isEmpty() || email.contains("  ")) {
			throw new WebException("Debe tener un email valido");
		}

		if (usuarioRepositorio.buscarPorEmail(email) != null) {
			throw new WebException("Debe tener un email valido");
		}

		if (clave == null || clave.isEmpty() || clave.contains("  ") || clave.length() < 8 || clave.length() > 12) {
			throw new WebException("Debe tener una clave valida");
		}

		if (!Rol.ADMIN.toString().equals(rol) && !Rol.CLIENTE.toString().equals(rol)) {
			throw new WebException("Debe tener rol valido");
		}

	}

	@Transactional
	public void eliminarUsuario(String id) {
		Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
		if (respuesta.isPresent()) {
			usuarioRepositorio.delete(respuesta.get());
		}
	}

}