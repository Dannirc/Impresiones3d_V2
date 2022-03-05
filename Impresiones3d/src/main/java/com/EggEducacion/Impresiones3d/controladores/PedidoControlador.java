package com.EggEducacion.Impresiones3d.controladores;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.EggEducacion.Impresiones3d.entidades.Archivo;
import com.EggEducacion.Impresiones3d.entidades.Categoria;
import com.EggEducacion.Impresiones3d.entidades.Pedido;
import com.EggEducacion.Impresiones3d.entidades.Producto;
import com.EggEducacion.Impresiones3d.entidades.Usuario;
import com.EggEducacion.Impresiones3d.repositorios.ArchivoRepositorio;
import com.EggEducacion.Impresiones3d.repositorios.CategoriaRepositorio;
import com.EggEducacion.Impresiones3d.repositorios.PedidoRepositorio;
import com.EggEducacion.Impresiones3d.repositorios.ProductoRepositorio;
import com.EggEducacion.Impresiones3d.repositorios.UsuarioRepositorio;
import com.EggEducacion.Impresiones3d.servicios.PedidoServicio;

@Controller
@RequestMapping("/pedido")
public class PedidoControlador {

	@Autowired
	private PedidoRepositorio pedidoRepo;
	@Autowired
	private ArchivoRepositorio archivoRepo;
	@Autowired
	private CategoriaRepositorio categoriaRepo;
	@Autowired
	private ProductoRepositorio productoRepo;
	@Autowired
	private UsuarioRepositorio usuarioRepo;
	@Autowired
	private PedidoServicio pedidoService;

	/* usuario y admin */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_CLIENTE')")
	@GetMapping("/crear-pedido")
	public String crearPedido() {
		return ("pedido/crearPedido");
	}

	@PostMapping("/crear-pedido")
	public String crearPedido(@RequestParam("document") MultipartFile multipartFile, RedirectAttributes ra,
			String material, Double diametroDeBoquilla, Integer velocidadDelImpresion, Integer porcentajeDeRelleno,
			Double alturaDeCapa, String soporte, String envio) {

		String archivoNombre = StringUtils.cleanPath(multipartFile.getOriginalFilename());

		Pedido pedido = new Pedido();
		Producto producto = new Producto();
		Archivo documento = new Archivo();
		Categoria categoria = new Categoria();

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String email = userDetails.getUsername();
		Usuario usuario = usuarioRepo.buscarPorEmail(email);

		try {
			/* carga de archivo */
			documento.setNombre(archivoNombre);
			documento.setContenido(multipartFile.getBytes());
			documento.setSize(multipartFile.getSize());
			documento.setTiempoCarga(new Date());

			/* carga de categoria */
			categoria.setNombre("");

			if (porcentajeDeRelleno != null) {
				categoria.setPorcentajeDeRelleno(porcentajeDeRelleno);
			} else {
				categoria.setPorcentajeDeRelleno(10);
			}

			categoria.setVelocidadDelImpresion(velocidadDelImpresion);
			categoria.setDiametroDeBoquilla(diametroDeBoquilla);

			categoria.setMaterial(material);

			if (alturaDeCapa != null) {
				categoria.setAlturaDeCapa(alturaDeCapa);
			} else {
				categoria.setAlturaDeCapa(0.15);
			}
			
			if(soporte.equals("si")) {
				categoria.setSoporte(true); 
			} else {
				categoria.setSoporte(false); 
			}

			/* carga producto */
			producto.setArchivo(documento);
			producto.setCategoria(categoria);

			/* carga pedido */
			pedido.setProducto(producto);
			pedido.setUsuario(usuario);
			pedido.setFecha(new Date());
			if(envio.equals("si")) {
				pedido.setEnvio(true);
			} else {
				pedido.setEnvio(false);
			}

		} catch (Exception e) {
			return "redirect:/pedido/crear-pedido";
		}

		archivoRepo.save(documento);
		categoriaRepo.save(categoria);
		productoRepo.save(producto);
		pedidoRepo.save(pedido);

		// "message" se pone con una etiqueta de thymeleaf en el front para mostrar el
		// mensaje, ej: <h2>[[${message}]]</h2>
		//ra.addFlashAttribute("message", "El archivo se ha cargado correctamente.");

		return "redirect:/pedido/lista-pedidos";
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_CLIENTE')")
	@GetMapping("/modificar-pedido")
	public String modificarPedido(ModelMap modelo, @RequestParam("id") String id) {
		try {
			Pedido pedido = pedidoService.buscarPorId(id);
			modelo.addAttribute("pedido", pedido);
			return ("pedido/modificarPedido");
		} catch (Exception e) {
			modelo.put("error", e.getMessage());
			return ("pedido/modificarPedido");
		}
	}

	@PostMapping("/modificar-pedido")
	public String guardarPedido(@RequestParam String id, @RequestParam("document") MultipartFile multipartFile,
			RedirectAttributes ra, String material, Double diametroDeBoquilla, Integer velocidadDelImpresion,
			Integer porcentajeDeRelleno, Double alturaDeCapa, String soporte, String envio) {

		Optional<Pedido> result = pedidoRepo.findById(id);

		String archivoNombre = StringUtils.cleanPath(multipartFile.getOriginalFilename());

		Pedido pedido = result.get();
		Archivo documento = pedido.getProducto().getArchivo();

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String email = userDetails.getUsername();
		Usuario usuario = usuarioRepo.buscarPorEmail(email);

		try {
			/* carga de archivo */
			if (multipartFile.getSize() != 0) {
				documento.setNombre(archivoNombre);
				documento.setContenido(multipartFile.getBytes());
				documento.setSize(multipartFile.getSize());
				documento.setTiempoCarga(new Date());
			}

			/* carga de categoria */
			pedido.getProducto().getCategoria().setNombre("");

			if (porcentajeDeRelleno != null) {
				pedido.getProducto().getCategoria().setPorcentajeDeRelleno(porcentajeDeRelleno);
			} else {
				pedido.getProducto().getCategoria()
						.setPorcentajeDeRelleno(pedido.getProducto().getCategoria().getPorcentajeDeRelleno());
			}

			pedido.getProducto().getCategoria().setVelocidadDelImpresion(velocidadDelImpresion);
			pedido.getProducto().getCategoria().setDiametroDeBoquilla(diametroDeBoquilla);
			pedido.getProducto().getCategoria().setMaterial(material);

			if (alturaDeCapa != null) {
				pedido.getProducto().getCategoria().setAlturaDeCapa(alturaDeCapa);
			} else {
				pedido.getProducto().getCategoria()
						.setAlturaDeCapa(pedido.getProducto().getCategoria().getAlturaDeCapa());
			}
			
			if(soporte.equals("si")) {
				pedido.getProducto().getCategoria().setSoporte(true); 
			} else {
				pedido.getProducto().getCategoria().setSoporte(false); 
			}

			/* carga producto */
			
			pedido.getProducto().setArchivo(documento);
			
			pedido.getProducto().setCategoria(pedido.getProducto().getCategoria());

			/* carga pedido */
			pedido.setProducto(pedido.getProducto());
			pedido.setUsuario(usuario);
			pedido.setFecha(new Date());
			if(envio.equals("si")) {
				pedido.setEnvio(true);
			} else {
				pedido.setEnvio(false);
			}

		} catch (Exception e) {
			return "redirect:/pedido/lista-pedidos";
		}

		
		archivoRepo.save(documento);
		categoriaRepo.save(pedido.getProducto().getCategoria());
		productoRepo.save(pedido.getProducto());
		pedidoRepo.save(pedido);

		// "message" se pone con una etiqueta de thymeleaf en el front para mostrar el
		// mensaje, ej: <h2>[[${message}]]</h2>
		ra.addFlashAttribute("message", "El archivo se ha cargado correctamente.");

		return "redirect:/pedido/lista-pedidos";
	}

	@GetMapping("/descarga-archivo")
	public void descargarArchivo(@Param("id") String id, HttpServletResponse response) throws Exception {

		Optional<Archivo> result = archivoRepo.findById(id);

		if (!result.isPresent()) {
			throw new Exception("No se puede encontrar el archivo ID: " + id);
		}

		Archivo documento = result.get();
		response.setContentType("application/octet-stream");
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=" + documento.getNombre();

		response.setHeader(headerKey, headerValue);

		ServletOutputStream outputStream = response.getOutputStream();

		outputStream.write(documento.getContenido());
		outputStream.close();
	}

	/* solo usuario */
	@PreAuthorize("hasAnyRole('ROLE_CLIENTE','ROLE_ADMIN')")
	/* lista de pedidos del usuario */
	@GetMapping("/lista-pedidos")
	public String listaPedidos(Model model) {

		List<Pedido> listaPedidos = pedidoRepo.findAll();

		model.addAttribute("listaPedidos", listaPedidos);

		return ("pedido/listaPedidos");
	}

	/* usuario y admin */

	/* el usuario solo podra eliminar sus pedidos */
	/* solo admin */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_CLIENTE')")
	@GetMapping("/eliminar-pedido")
	public String eliminar(String id) {

		pedidoService.eliminarPedido(id);

		return ("redirect:/pedido/lista-pedidos");
	}

}