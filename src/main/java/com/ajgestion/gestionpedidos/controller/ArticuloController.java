package com.ajgestion.gestionpedidos.controller;

import com.ajgestion.gestionpedidos.model.Articulo;
import com.ajgestion.gestionpedidos.service.ArticuloService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/articulos")
public class ArticuloController {
    private final ArticuloService articuloService;

    @Autowired
    public ArticuloController(ArticuloService articuloService){
        this.articuloService = articuloService;
    }

    @GetMapping
    public List<Articulo> obtenerTodos(){
        return articuloService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Articulo> obtenerPorId(@PathVariable Integer id){
        Optional<Articulo> optArticulo = articuloService.obtenerPorId(id);
        if(optArticulo.isPresent())
            return new ResponseEntity<>(optArticulo.get(), HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Articulo> crearArticulo(@RequestBody Articulo articulo){
        Articulo nuevoArticulo = articuloService.guardar(articulo);
        return new ResponseEntity<>(nuevoArticulo, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Articulo> actualizarArticulo(@PathVariable Integer id, @RequestBody Articulo articulo){
        Optional<Articulo> articuloExistente = articuloService.obtenerPorId(id);
        if(articuloExistente.isPresent()){
            Articulo articuloModificado = articuloService.guardar(articulo);
            return new ResponseEntity<>(articuloModificado, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Articulo> eliminarArticulo(@PathVariable Integer id){
        try {
            articuloService.eliminar(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
