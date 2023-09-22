package com.ajgestion.gestionpedidos.repository;

import com.ajgestion.gestionpedidos.model.DetallePedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Long> {
    @Query("SELECT new map(dp as detallePedido, p.numPedido as numPedido, p.fabrica as fabrica, p.fechaServicio as fechaServicio) " +
            "FROM DetallePedido dp " +
            "JOIN dp.pedido p " +
            "WHERE dp.cantidadEntregada < dp.cantidad * 0.9")
    List<Map<String, Object>> findIncompleteDetallePedidosWithPedidoNum();
}
