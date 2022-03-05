package com.EggEducacion.Impresiones3d.controladores;


import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.EggEducacion.Impresiones3d.entidades.Archivo;
import com.EggEducacion.Impresiones3d.entidades.Producto;
import com.EggEducacion.Impresiones3d.repositorios.ArchivoRepositorio;
import com.EggEducacion.Impresiones3d.repositorios.ProductoRepositorio;

@Controller
@RequestMapping("/producto")
public class ArchivoController {
	
	@Autowired
	private ArchivoRepositorio archivoRepo;
	@Autowired
	private ProductoRepositorio productoRepo;



	/*@GetMapping("/descargar")
	public String descargaCategoria(Model model) {
	
		List<Archivo> listArchivos = archivoRepo.buscarTodos();
		
		model.addAttribute("listaPedido", listArchivos);
		
		return("usuario/listaPedidos");
	}*/

	
	/*@GetMapping("/descargar")
	public String descargaArchivoPage(Model model) {
	
		List<Archivo> listArchivos = archivoRepo.buscarTodos();
		
		model.addAttribute("listArchivos", listArchivos);
		
		return("admin/descargarArchivo");
	}*/
	
	/*
	@PostMapping("/descarga")
	public void descargarArchivo(@Param("id") String id, HttpServletResponse response) throws Exception {
		
		Optional<Archivo> result = archivoRepo.findById(id);
		
		if(!result.isPresent()) {
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

	@GetMapping("/descarga2")
	public void descargarArchivo2(@Param("id") String id, HttpServletResponse response) throws Exception {
		
		Optional<Archivo> result = archivoRepo.findById(id);
		
		if(!result.isPresent()) {
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
	
	@PostMapping("/subida")
	public String subidaArchivo(@RequestParam("document") MultipartFile multipartFile,
			RedirectAttributes ra) {
		
		String archivoNombre = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		
		Archivo documento = new Archivo();
		
		try {
		documento.setNombre(archivoNombre);
		documento.setContenido(multipartFile.getBytes());
		documento.setSize(multipartFile.getSize());
		documento.setTiempoCarga(new Date());
		}catch(Exception e) {
			
		}
		
		archivoRepo.save(documento);
		
		//"message" se pone con una etiqueta de thymeleaf en el front para mostrar el mensaje, ej: <h2>[[${message}]]</h2> 
		ra.addFlashAttribute("message", "El archivo se ha cargado correctamente.");
		
		return "redirect:/producto/cargar";
	}*/
	
	
}