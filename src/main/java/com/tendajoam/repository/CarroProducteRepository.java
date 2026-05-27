package com.tendajoam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tendajoam.entity.cart.CarroProducte;
import com.tendajoam.entity.cart.CarroProducteId;

public interface CarroProducteRepository extends JpaRepository<CarroProducte, CarroProducteId> {
    
    @Modifying
    @Query(value = "DELETE FROM carro_producte WHERE id_carrito = :carroId AND id_producte = :prodId", nativeQuery = true)
    void deleteByNativeQuery(@Param("carroId") String carroId, @Param("prodId") String prodId);

	List<CarroProducte> findByCarro_IdCarrito(String idCarrito);
	
	@Modifying
	@Query(value = "DELETE FROM carro_producte WHERE id_carrito = :carroId", nativeQuery = true)
	void deleteByCarroId(@Param("carroId") String carroId);
}