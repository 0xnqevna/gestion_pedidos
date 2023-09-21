package com.ajgestion.gestionpedidos.service;

import com.ajgestion.gestionpedidos.model.Pedido;
import com.ajgestion.gestionpedidos.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {
    private PedidoRepository pedidoRepository;

    @Autowired
    public PedidoService(PedidoRepository pedidoRepository){
        this.pedidoRepository = pedidoRepository;
    }

    public List<Pedido> obtenerTodos() {
        return pedidoRepository.findAll();
    }

    public Optional<Pedido> obtenerPorId(Long id) {
        return pedidoRepository.findById(id);
    }

    public Pedido guardar(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    public void eliminar(Long id) throws EmptyResultDataAccessException {
        pedidoRepository.deleteById(id);
    }

    public List<Pedido> obtenerRangoFechasServicio(Date ini, Date fin){
        return pedidoRepository.findByFechaServicioBetween(ini, fin);
    }

    public List<Pedido> obtenerPedidosFabrica(String fabrica){
        return pedidoRepository.findByFabrica(fabrica);
    }

    public List<Pedido> obtenerPedidosNum(Integer numPedido){
        return pedidoRepository.findByNumPedido(numPedido);
    }

    public List<Pedido> obtenerPedidosFabricaRangoFecha(String fabrica, Date startDate, Date endDate){
        return pedidoRepository.findByFabricaContainsAndFechaServicioBetween(fabrica, startDate, endDate);
    }
    public Optional<Pedido> obtenerPedidoFabricaNumPedido(String fabrica, Integer numPedido){
        return pedidoRepository.findByFabricaAndNumPedido(fabrica, numPedido);
    }
}
