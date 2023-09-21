package com.ajgestion.gestionpedidos.repository;

import com.ajgestion.gestionpedidos.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByFechaServicioBetween(Date startDate, Date endDate);
    List<Pedido> findByFabrica(String fabrica);
    List<Pedido> findByNumPedido(Integer numPedido);
    List<Pedido> findByFabricaContainsAndFechaServicioBetween(String fabrica, Date startDate, Date endDate);
    Optional<Pedido> findByFabricaAndNumPedido(String fabrica, Integer numPedido);
}
