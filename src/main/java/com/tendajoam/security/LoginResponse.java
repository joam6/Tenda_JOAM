package com.tendajoam.security;

public class LoginResponse {

    private String token;
    private String nom;
    private String rol;
    private String idUsuari;
    private String email; 
    private String direccio;

    public LoginResponse(String token, String nom, String rol, String idUsuari, String email, String direccio) {
        this.token = token;
        this.nom = nom;
        this.rol = rol;
        this.idUsuari = idUsuari;
        this.email = email;
        this.direccio = direccio; 
    }

    // Getters i Setters
    public String getToken() {
        return token;
    }

    public String getNom() {
        return nom;
    }

    public String getRol() {
        return rol;
    }

    public String getIdUsuari() {
        return idUsuari;
    }

    public void setIdUsuari(String idUsuari) {
        this.idUsuari = idUsuari;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

	public String getDireccio() {
		return direccio;
	}

	public void setDireccio(String direccio) {
		this.direccio = direccio;
	}
}