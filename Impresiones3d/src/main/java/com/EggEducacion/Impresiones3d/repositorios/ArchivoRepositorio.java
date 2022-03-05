package com.EggEducacion.Impresiones3d.repositorios;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.EggEducacion.Impresiones3d.entidades.Archivo;


@Repository
public interface ArchivoRepositorio extends JpaRepository<Archivo, String> {
	
	
	@Query("SELECT new Archivo(a.id, a.nombre, a.tiempoCarga) FROM Archivo a ORDER BY a.tiempoCarga DESC")
	List<Archivo> buscarTodos();
	

}