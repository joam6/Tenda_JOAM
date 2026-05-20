package com.tendajoam.entity.product;

import com.tendajoam.entity.users.Venedor;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "producte")
public class Producte {

	@Id
	@Column(name = "id_producte")
	private String idProducte;

	private String nom;
	private double preu;
	private int stock;
	private String categoria;

	@Column(name = "imatge")
	private String imatge;

	@Column(name = "descripcio")
	private String descripcio;

	@ManyToOne
	@JoinColumn(name = "id_venedor")
	private Venedor venedor;

	public Producte() {
	}

	public String getIdProducte() {
		return idProducte;
	}

	public void setIdProducte(String idProducte) {
		this.idProducte = idProducte;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public double getPreu() {
		return preu;
	}

	public void setPreu(double preu) {
		this.preu = preu;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getImatge() {
		return imatge;
	}

	public void setImatge(String imatge) {
		this.imatge = imatge;
	}

	public String getDescripcio() {
		return descripcio;
	}

	public void setDescripcio(String descripcio) {
		this.descripcio = descripcio;
	}

	public Venedor getVenedor() {
		return venedor;
	}

	public void setVenedor(Venedor venedor) {
		this.venedor = venedor;
	}
}