package com.ajgestion.gestionpedidos.repository;

import com.ajgestion.gestionpedidos.model.DetallePedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Long> {
}
