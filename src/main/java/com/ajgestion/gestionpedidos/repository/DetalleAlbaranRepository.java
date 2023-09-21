package com.ajgestion.gestionpedidos.repository;

import com.ajgestion.gestionpedidos.model.DetalleAlbaran;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetalleAlbaranRepository extends JpaRepository<DetalleAlbaran, Long> {
    List<DetalleAlbaran> findByDetallePedido_DetalleId(Long detalleId);
}