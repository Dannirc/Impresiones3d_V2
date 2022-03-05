package com.EggEducacion.Impresiones3d.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.EggEducacion.Impresiones3d.entidades.Categoria;
import com.EggEducacion.Impresiones3d.repositorios.CategoriaRepositorio;

@Service
public class CategoriaServicio {
	@Autowired
	private CategoriaRepositorio CategoriaRepositorio;

	@Transactional
	public Categoria crearCategoria(String nombre, Integer porcentajeDeRelleno,
			Integer velocidadDelImpresion, Double diametroDeBoquilla, String material, Double alturaDeCapa,
			Boolean soporte) throws Exception {
		validar(nombre, porcentajeDeRelleno, velocidadDelImpresion, diametroDeBoquilla, material, alturaDeCapa, soporte);
		Categoria Categoria = new Categoria();
		Categoria.setNombre(nombre);
		Categoria.setAlturaDeCapa(alturaDeCapa);
		Categoria.setDiametroDeBoquilla(diametroDeBoquilla);
		Categoria.setPorcentajeDeRelleno(porcentajeDeRelleno);
		Categoria.setSoporte(soporte);
		Categoria.setVelocidadDelImpresion(velocidadDelImpresion);
		Categoria.setMaterial(material);
		return CategoriaRepositorio.save(Categoria);
	}

	@Transactional
	public void modificarCategoria(String id, String nombre, Integer porcentajeDeRelleno, Integer velocidadDelImpresion,
			Double diametroDeBoquilla, String material, Double alturaDeCapa, Boolean soporte) throws Exception {
		Optional<Categoria> respuesta = CategoriaRepositorio.findById(id);
		if (respuesta.isPresent()) {
			Categoria Categoria = respuesta.get();
			Categoria.setId(id);
			Categoria.setNombre(nombre);
			Categoria.setAlturaDeCapa(alturaDeCapa);
			Categoria.setDiametroDeBoquilla(diametroDeBoquilla);
			Categoria.setPorcentajeDeRelleno(porcentajeDeRelleno);
			Categoria.setSoporte(soporte);
			Categoria.setVelocidadDelImpresion(velocidadDelImpresion);
			Categoria.setMaterial(material);
			CategoriaRepositorio.save(Categoria);
		} else {
			throw new Exception("error en el modificar-Categoria");
		}
	}

	@Transactional
	public List<Categoria> listarTodos() {
		return CategoriaRepositorio.findAll();
	}
	
	public Categoria buscarPorId(String id) throws Exception {
		Optional<Categoria> respuesta = CategoriaRepositorio.findById(id);
		if (respuesta.isPresent()) {
			return respuesta.get();
		} else {
			throw new Exception("error");
		}
	}
	
	public void validar(String nombre, Integer porcentajeDeRelleno, Integer velocidadDelImpresion,
			Double diametroDeBoquilla, String material, Double alturaDeCapa, Boolean soporte) throws Exception {

		if (porcentajeDeRelleno == null) {
			throw new Exception("Debe tener un porcentaje de relleno valido");
		}
		
		if (velocidadDelImpresion == null) {
			throw new Exception("Debe tener una velocidad de impresion valida");
		}	
		
		if (nombre == null || nombre.isEmpty() || nombre.contains("  ")) {
			throw new Exception("Debe tener un nombre valido");
		}

		if (alturaDeCapa == null) {
			throw new Exception("Debe tener una altura de capa valida");
		}

		if (material == null || material.isEmpty() || material.contains("  ")) {
			throw new Exception("Debe tener un material valido");
		}

		if (diametroDeBoquilla == null) {
			throw new Exception("Debe tener un diametro valido");
		}
		
		if (soporte == null) {
			throw new Exception("Debe tener un soporte valido");
		}
		

	}

}