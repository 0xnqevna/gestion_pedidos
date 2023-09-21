package com.ajgestion.gestionpedidos.service;

import com.ajgestion.gestionpedidos.model.Albaran;
import com.ajgestion.gestionpedidos.model.Pedido;
import com.ajgestion.gestionpedidos.repository.AlbaranRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlbaranService {


    private final AlbaranRepository albaranRepository;

    @Autowired
    public AlbaranService(AlbaranRepository albaranRepository){
        this.albaranRepository = albaranRepository;
    }

    public List<Albaran> obtenerTodos() {
        return albaranRepository.findAll();
    }

    public Optional<Albaran> obtenerPorId(Long id) {
        return albaranRepository.findById(id);
    }

    public Albaran guardar(Albaran albaran) {
        return albaranRepository.save(albaran);
    }

    public void eliminar(Long id) throws EmptyResultDataAccessException {
        albaranRepository.deleteById(id);
    }
}
