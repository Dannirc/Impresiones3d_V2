package com.EggEducacion.Impresiones3d.entidades;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Pedido {
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	@OneToOne
	private Producto producto;
	@OneToOne
	private Usuario usuario;
	@Temporal(TemporalType.DATE)
	private Date fecha;
	private Boolean envio;
	
	
	
	public Pedido() {
		super();
	}


	public Pedido(String id, Producto producto, Usuario usuario, Date fecha, Boolean envio) {
		super();
		this.id = id;
		this.producto = producto;
		this.usuario = usuario;
		this.fecha = fecha;
		this.envio = envio;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Boolean getEnvio(){
		return envio;
	}

	public void setEnvio(Boolean envio){
		this.envio = envio;
	}

	@Override
	public String toString() {
		return "Pedido [cliente=" + usuario + ", fecha=" + fecha + "]";
	}
	
}
