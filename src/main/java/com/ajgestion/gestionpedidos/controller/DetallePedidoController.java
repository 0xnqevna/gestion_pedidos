package com.ajgestion.gestionpedidos.controller;


import com.ajgestion.gestionpedidos.model.DetallePedido;
import com.ajgestion.gestionpedidos.service.DetallePedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/detalles_pedido")
public class DetallePedidoController {
    private final DetallePedidoService detallePedidoService;

    @Autowired
    public DetallePedidoController(DetallePedidoService detallePedidoService){
        this.detallePedidoService = detallePedidoService;
    }

    @GetMapping
    public List<DetallePedido> obtenerTodos() {
        return detallePedidoService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetallePedido> obtenerPorId(@PathVariable Long id) {
        Optional<DetallePedido> optionalDetalle = detallePedidoService.obtenerPorId(id);
        if (!optionalDetalle.isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(optionalDetalle.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<DetallePedido> crearDetallePedido(@RequestBody DetallePedido detallePedido) {
        DetallePedido nuevoDetalle = detallePedidoService.guardar(detallePedido);
        return new ResponseEntity<>(nuevoDetalle, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetallePedido> actualizarDetallePedido(@PathVariable Long id, @RequestBody DetallePedido detallePedido) {
        Optional<DetallePedido> detalleExistente = detallePedidoService.obtenerPorId(id);
        if (!detalleExistente.isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        DetallePedido detalleActualizado = detallePedidoService.guardar(detallePedido);
        return new ResponseEntity<>(detalleActualizado, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarDetallePedido(@PathVariable Long id) {
        try {
            detallePedidoService.eliminar(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
