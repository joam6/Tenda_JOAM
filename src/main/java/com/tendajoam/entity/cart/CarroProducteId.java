package com.tendajoam.entity.cart;

import jakarta.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class CarroProducteId implements Serializable {

    private String idCarrito;
    private String idProducte;

    public CarroProducteId() {}

    public CarroProducteId(String idCarrito, String idProducte) {
        this.idCarrito = idCarrito;
        this.idProducte = idProducte;
    }

    public String getIdCarrito() {
        return idCarrito;
    }

    public void setIdCarrito(String idCarrito) {
        this.idCarrito = idCarrito;
    }

    public String getIdProducte() {
        return idProducte;
    }

    public void setIdProducte(String idProducte) {
        this.idProducte = idProducte;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CarroProducteId)) return false;
        CarroProducteId that = (CarroProducteId) o;
        return idCarrito.equals(that.idCarrito) &&
               idProducte.equals(that.idProducte);
    }

    @Override
    public int hashCode() {
        return idCarrito.hashCode() + idProducte.hashCode();
    }
}
