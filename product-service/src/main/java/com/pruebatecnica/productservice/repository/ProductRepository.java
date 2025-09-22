package com.pruebatecnica.productservice.repository;

import com.pruebatecnica.productservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Le indica a Spring que esta es una interfaz para acceder a datos.
public interface ProductRepository extends JpaRepository<Product, Long> {
    // ¡Eso es todo!
}