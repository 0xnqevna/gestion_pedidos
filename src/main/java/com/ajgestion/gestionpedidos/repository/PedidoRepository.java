package com.ajgestion.gestionpedidos.repository;

import com.ajgestion.gestionpedidos.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByFechaServicioBetween(Date startDate, Date endDate);
    List<Pedido> findByFabricaContains(String fabrica);
    List<Pedido> findByNumPedido(Integer numPedido);
}
