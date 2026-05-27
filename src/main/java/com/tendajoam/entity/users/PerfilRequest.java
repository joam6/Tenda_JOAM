package com.tendajoam.entity.users;

public class PerfilRequest {
    private String nom;
    private String direccio;

    // Constructor buit (Obligatori per a Jackson/Spring)
    public PerfilRequest() {
    }

    // Getters i Setters
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDireccio() {
        return direccio;
    }

    public void setDireccio(String direccio) {
        this.direccio = direccio;
    }
}