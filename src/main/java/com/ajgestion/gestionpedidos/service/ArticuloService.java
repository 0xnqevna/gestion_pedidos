package com.ajgestion.gestionpedidos.service;

import com.ajgestion.gestionpedidos.model.Articulo;
import com.ajgestion.gestionpedidos.repository.ArticuloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticuloService {
    private final ArticuloRepository articuloRepository;

    @Autowired
    public ArticuloService(ArticuloRepository articuloRepository) {
        this.articuloRepository = articuloRepository;
    }
    public List<Articulo> obtenerTodos() {
        return articuloRepository.findAll();
    }

    public Optional<Articulo> obtenerPorId(Integer id) {
        return articuloRepository.findById(id);
    }

    public Articulo guardar(Articulo articulo) {
        return articuloRepository.save(articulo);
    }

    public void eliminar(Integer id) throws EmptyResultDataAccessException {
        articuloRepository.deleteById(id);
    }
}
