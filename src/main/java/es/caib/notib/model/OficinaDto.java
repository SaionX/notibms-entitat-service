package es.caib.notib.model;

import java.io.Serializable;

public class OficinaDto implements Serializable{

	private String codi;
	private String nom;
	
	public String getCodi() {
		return codi;
	}
	public void setCodi(
			String codi) {
		this.codi = codi;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(
			String nom) {
		this.nom = nom;
	}
	
	private static final long serialVersionUID = -3831959843313056718L;
	
}
