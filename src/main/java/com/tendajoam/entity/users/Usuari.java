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

	private boolean actiu = false;

	// 🏠 NOU CAMP PER A LA BASE DE DADES
	@Column(name = "direccio", length = 255)
	private String direccio;

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

	public boolean isActiu() {
		return actiu;
	}

	public void setActiu(boolean actiu) {
		this.actiu = actiu;
	}

	// ⚙️ GETTER I SETTER DE LA DIRECCIÓ
	public String getDireccio() {
		return direccio;
	}

	public void setDireccio(String direccio) {
		this.direccio = direccio;
	}
}