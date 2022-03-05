

package com.EggEducacion.Impresiones3d.servicios;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.EggEducacion.Impresiones3d.entidades.Archivo;
import com.EggEducacion.Impresiones3d.repositorios.ArchivoRepositorio;

@Service
public class ArchivoServicio {

	@Autowired
	private ArchivoRepositorio ArchivoRepositorio;

	public Archivo guardar(MultipartFile file) throws Exception {
		try {
			if (file != null) {
				Archivo archivo = new Archivo();
				archivo.setMime(file.getContentType());
				archivo.setNombre(file.getName());
				archivo.setContenido(file.getBytes());
				return ArchivoRepositorio.save(archivo);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return null;
	}

	public Archivo actualizar(String idArchivo, MultipartFile file) throws Exception {
		if (file != null) {
			try {
				Archivo archivo = new Archivo();
				if (idArchivo != null) {
					Optional<Archivo> respuesta = ArchivoRepositorio.findById(idArchivo);
					if (respuesta.isPresent()) {
						archivo = respuesta.get();
					}
				}
				archivo.setMime(file.getContentType());
				archivo.setNombre(file.getName());
				archivo.setContenido(file.getBytes());
				return ArchivoRepositorio.save(archivo);
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
		return null;
	}
}
