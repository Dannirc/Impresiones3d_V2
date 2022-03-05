package com.EggEducacion.Impresiones3d.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.EggEducacion.Impresiones3d.entidades.Compra;


@Repository
public interface CompraRepositorio extends JpaRepository<Compra, String> {

}
