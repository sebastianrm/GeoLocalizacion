package cl.mobilLoyalti.geoLocation.bean;

import java.sql.Timestamp;

public class Bencinas {

	private String descripcion;
	private float precios;
	private Timestamp fechaUlmtimaModificacion;
	private ServiCentro serviCentro;
	
		

	public Timestamp getFechaUlmtimaModificacion() {
		return fechaUlmtimaModificacion;
	}

	public void setFechaUlmtimaModificacion(Timestamp fechaUlmtimaModificacion) {
		this.fechaUlmtimaModificacion = fechaUlmtimaModificacion;
	}

	public ServiCentro getServiCentro() {
		return serviCentro;
	}

	public void setServiCentro(ServiCentro serviCentro) {
		this.serviCentro = serviCentro;
	}
	
	
	
	
	public float getPrecios() {
		return precios;
	}

	public void setPrecios(float precios) {
		this.precios = precios;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
