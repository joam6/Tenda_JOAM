package com.tendajoam.entity.order;

import com.tendajoam.entity.users.Cliente;
import jakarta.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "comanda")
public class Comanda {


    @Id
    private String idComanda;

    private Date data;
    private String estat;
    private double total;

    @ManyToOne
    @JoinColumn(name = "id_cliente", referencedColumnName = "id_usuari")
    private Cliente cliente;


    public Comanda() {}

    public String getIdComanda() {
        return idComanda;
    }

    public void setIdComanda(String idComanda) {
        this.idComanda = idComanda;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getEstat() {
        return estat;
    }

    public void setEstat(String estat) {
        this.estat = estat;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
