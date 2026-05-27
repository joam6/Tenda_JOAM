package com.tendajoam.entity.cart;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tendajoam.entity.product.Producte;
import jakarta.persistence.*;

@Entity
@Table(name = "carro_producte")
public class CarroProducte {


    @EmbeddedId
    private CarroProducteId id;

    @ManyToOne
    @MapsId("idCarrito")
    @JoinColumn(name = "id_carrito")
    @JsonIgnore 
    private Carro carro;

    @ManyToOne
    @MapsId("idProducte")
    @JoinColumn(name = "id_producte")
    private Producte producte;


    private int quantitat;

    public CarroProducte() {}

    public CarroProducte(Carro carro, Producte producte, int quantitat) {
        this.carro = carro;
        this.producte = producte;
        this.quantitat = quantitat;
        this.id = new CarroProducteId(carro.getIdCarrito(), producte.getIdProducte());
    }

    public CarroProducteId getId() {
        return id;
    }

    public void setId(CarroProducteId id) {
        this.id = id;
    }

    public Carro getCarro() {
        return carro;
    }

    public void setCarro(Carro carro) {
        this.carro = carro;
    }

    public Producte getProducte() {
        return producte;
    }

    public void setProducte(Producte producte) {
        this.producte = producte;
    }

    public int getQuantitat() {
        return quantitat;
    }

    public void setQuantitat(int quantitat) {
        this.quantitat = quantitat;
    }
}
