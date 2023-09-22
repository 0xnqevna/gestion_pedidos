package com.ajgestion.gestionpedidos.service;


import com.ajgestion.gestionpedidos.model.DetallePedido;
import com.ajgestion.gestionpedidos.repository.DetallePedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class DetallePedidoService {
    private final DetallePedidoRepository detallePedidoRepository;

    @Autowired
    public DetallePedidoService(DetallePedidoRepository detallePedidoRepository){
        this.detallePedidoRepository = detallePedidoRepository;
    }

    public List<DetallePedido> obtenerTodos() {
        return detallePedidoRepository.findAll();
    }

    public Optional<DetallePedido> obtenerPorId(Long id) {
        return detallePedidoRepository.findById(id);
    }

    public DetallePedido guardar(DetallePedido detallePedido) {
        return detallePedidoRepository.save(detallePedido);
    }

    public void eliminar(Long id) throws EmptyResultDataAccessException {
        detallePedidoRepository.deleteById(id);
    }
    public List<Map<String, Object>> obtenerPendientes(){
        return detallePedidoRepository.findIncompleteDetallePedidosWithPedidoNum();
    }
}
