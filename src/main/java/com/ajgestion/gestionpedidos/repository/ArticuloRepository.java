package com.ajgestion.gestionpedidos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ajgestion.gestionpedidos.model.Articulo;

public interface ArticuloRepository extends JpaRepository<Articulo, Integer> {
    boolean deleteByIdArticulo(Integer idArticulo);
}
