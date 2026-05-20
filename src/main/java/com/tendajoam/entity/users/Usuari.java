package com.tendajoam.entity.users;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuari {

	@Id
	@Column(name = "id_usuari")
	private String idUsuari;

	private String nom;

	@Column(unique = true, nullable = false)
	private String email;

	private String pass;

	@Enumerated(EnumType.STRING)
	private Rol rol;

	public Usuari() {
	}

	public String getIdUsuari() {
		return idUsuari;
	}

	public void setIdUsuari(String idUsuari) {
		this.idUsuari = idUsuari;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}
}