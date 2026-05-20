package com.tendajoam.entity.product;

import com.tendajoam.entity.users.Cliente;
import jakarta.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "review")
public class Review {


    @Id
    private String idReview;

    private String comentari;
    private int score;
    private Date data;

    @ManyToOne
    @JoinColumn(name = "id_cliente", referencedColumnName = "id_usuari")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "idProducte")
    private Producte producte;

    public Review() {}

    public String getIdReview() {
        return idReview;
    }

    public void setIdReview(String idReview) {
        this.idReview = idReview;
    }

    public String getComentari() {
        return comentari;
    }

    public void setComentari(String comentari) {
        this.comentari = comentari;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Producte getProducte() {
        return producte;
    }

    public void setProducte(Producte producte) {
        this.producte = producte;
    }
}
