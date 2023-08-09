package com.ajgestion.gestionpedidos.service;

import com.ajgestion.gestionpedidos.model.DetalleAlbaran;
import com.ajgestion.gestionpedidos.repository.DetalleAlbaranRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DetalleAlbaranService {


    private final DetalleAlbaranRepository detalleAlbaranRepository;

    public DetalleAlbaranService(DetalleAlbaranRepository detalleAlbaranRepository){
        this.detalleAlbaranRepository = detalleAlbaranRepository;
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

    public void eliminar(Long id) throws EmptyResultDataAccessException {
        detalleAlbaranRepository.deleteById(id);
    }
}
