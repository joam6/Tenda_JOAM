package com.tendajoam.security;

public class LoginResponse {

    private String token;
    private String nom;
    private String rol;

    public LoginResponse(String token, String nom, String rol) {
        this.token = token;
        this.nom = nom;
        this.rol = rol;
    }

    public String getToken() {
        return token;
    }

    public String getNom() {
        return nom;
    }

    public String getRol() {
        return rol;
    }
}
