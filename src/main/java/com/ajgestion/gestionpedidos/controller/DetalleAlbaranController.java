package com.ajgestion.gestionpedidos.controller;

import com.ajgestion.gestionpedidos.model.DetalleAlbaran;
import com.ajgestion.gestionpedidos.service.DetalleAlbaranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/detalles_albaran")
public class DetalleAlbaranController {

    @Autowired
    private DetalleAlbaranService detalleAlbaranService;

    @GetMapping
    public List<DetalleAlbaran> obtenerTodos() {
        return detalleAlbaranService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleAlbaran> obtenerPorId(@PathVariable Long id) {
        Optional<DetalleAlbaran> optionalDetalle = detalleAlbaranService.obtenerPorId(id);
        if (!optionalDetalle.isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(optionalDetalle.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<DetalleAlbaran> crearDetalleAlbaran(@RequestBody DetalleAlbaran detalleAlbaran) {
        DetalleAlbaran nuevoDetalle = detalleAlbaranService.guardar(detalleAlbaran);
        return new ResponseEntity<>(nuevoDetalle, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetalleAlbaran> actualizarDetalleAlbaran(@PathVariable Long id, @RequestBody DetalleAlbaran detalleAlbaran) {
        Optional<DetalleAlbaran> detalleExistente = detalleAlbaranService.obtenerPorId(id);
        if (!detalleExistente.isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        DetalleAlbaran detalleActualizado = detalleAlbaranService.guardar(detalleAlbaran);
        return new ResponseEntity<>(detalleActualizado, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDetalleAlbaran(@PathVariable Long id) {
        try {
            detalleAlbaranService.eliminar(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
