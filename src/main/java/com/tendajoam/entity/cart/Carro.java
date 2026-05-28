package com.tendajoam.entity.cart;

import java.util.List;

import com.tendajoam.entity.users.Cliente;
import jakarta.persistence.*;

@Entity
@Table(name = "carro")
public class Carro {

	@Id
	@Column(name = "idCarrito")
	private String idCarrito;

	@ManyToOne
	@JoinColumn(name = "id_cliente", referencedColumnName = "id_usuari")
	private Cliente cliente;

	@OneToMany(mappedBy = "carro", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CarroProducte> carroProductes;

	private double total;

	public Carro() {
	}

	public String getIdCarrito() {
		return idCarrito;
	}

	public void setIdCarrito(String idCarrito) {
		this.idCarrito = idCarrito;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public List<CarroProducte> getCarroProductes() {
		return carroProductes;
	}

	public void setCarroProductes(List<CarroProducte> cp) {
		this.carroProductes = cp;
	}
}
