package com.ajgestion.gestionpedidos.service;

import com.ajgestion.gestionpedidos.model.DetalleAlbaran;
import com.ajgestion.gestionpedidos.model.DetallePedido;
import com.ajgestion.gestionpedidos.repository.DetalleAlbaranRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DetalleAlbaranService {


    private final DetalleAlbaranRepository detalleAlbaranRepository;
    private final DetallePedidoService detallePedidoService;
    @Autowired
    public DetalleAlbaranService(DetalleAlbaranRepository detalleAlbaranRepository, DetallePedidoService detallePedidoService){
        this.detalleAlbaranRepository = detalleAlbaranRepository;
        this.detallePedidoService = detallePedidoService;
    }

    public List<DetalleAlbaran> obtenerTodos() {
        return detalleAlbaranRepository.findAll();
    }

    public Optional<DetalleAlbaran> obtenerPorId(Long id) {
        return detalleAlbaranRepository.findById(id);
    }

    public DetalleAlbaran guardar(DetalleAlbaran detalleAlbaran) {
        return detalleAlbaranRepository.save(detalleAlbaran);
    }

    @Transactional
    public void eliminar(Long id) {
        Optional<DetalleAlbaran> detalleAlbaran = detalleAlbaranRepository.findById(id);
        if (detalleAlbaran.isPresent()) {
            DetallePedido detallePedido = detalleAlbaran.get().getDetallePedido();
            detallePedido.setCantidadEntregada(detallePedido.getCantidadEntregada() - detalleAlbaran.get().getCantidadEntregada());
            detallePedidoService.guardar(detallePedido);
            detalleAlbaranRepository.deleteById(id);
        } else {
            throw new EmptyResultDataAccessException(1);
        }
    }
    public List<DetalleAlbaran> obtenerPorDetallePedido(Long id){
        return detalleAlbaranRepository.findByDetallePedido_DetalleId(id);
    }
}
