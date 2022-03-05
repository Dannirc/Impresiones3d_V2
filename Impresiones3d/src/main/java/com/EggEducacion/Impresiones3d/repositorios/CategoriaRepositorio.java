package com.EggEducacion.Impresiones3d.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.EggEducacion.Impresiones3d.entidades.Categoria;


@Repository
public interface CategoriaRepositorio extends JpaRepository<Categoria, String> {

}
