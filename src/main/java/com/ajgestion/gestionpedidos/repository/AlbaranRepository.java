package com.ajgestion.gestionpedidos.repository;

import com.ajgestion.gestionpedidos.model.Albaran;
import com.ajgestion.gestionpedidos.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlbaranRepository extends JpaRepository<Albaran, Long> {
    List<Albaran> findByPedido(Pedido pedido);
}
