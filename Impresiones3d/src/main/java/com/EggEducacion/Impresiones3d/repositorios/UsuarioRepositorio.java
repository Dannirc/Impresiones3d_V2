package com.EggEducacion.Impresiones3d.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.EggEducacion.Impresiones3d.entidades.Usuario;


@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, String> {

	@Query("SELECT a from Usuario a WHERE a.email LIKE :email")
	public Usuario buscarPorEmail(@Param("email") String email);
	
	@Query("SELECT c FROM Usuario c WHERE c.id = :id")
	public Usuario buscarUsuarioPorId(@Param("id") String id);
}


