package com.EggEducacion.Impresiones3d.entidades;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Archivo {
	
	//ATRIBUTOS
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	
	@Column(length = 512, nullable = false, unique = true)
	private String nombre;
	
	private String mime;
		
	private Long size;
	
	private Date tiempoCarga; 
	
	@Lob @Basic(fetch=FetchType.LAZY)
	private byte[] contenido;
	
	
	//CONSTRUCTORES
	
	public Archivo(String id, String nombre, Date tiempoCarga) {
		this.id = id;
		this.nombre = nombre;
		this.tiempoCarga = tiempoCarga;
	}
	
	public Archivo() {
		
	}
		
	//GETTERs & SETTERs
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getMime() {
		return mime;
	}

	public void setMime(String mime) {
		this.mime = mime;
	}

	public byte[] getContenido() {
		return contenido;
	}

	public void setContenido(byte[] contenido) {
		this.contenido = contenido;
	}
	
	public Long getSize() {
		return size;
	}
	
	public void setSize(Long size) {
		this.size = size;
	}
	
	public Date getTiempoCarga() {
		return tiempoCarga;
	}
	
	public void setTiempoCarga(Date tiempoCarga) {
		this.tiempoCarga = tiempoCarga;
	}

	@Override
	public String toString() {
		return  id ;
	}
	
	
	
}
