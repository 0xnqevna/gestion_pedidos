package com.ajgestion.gestionpedidos.controller;

import com.ajgestion.gestionpedidos.model.DetalleAlbaran;
import com.ajgestion.gestionpedidos.model.DetallePedido;
import com.ajgestion.gestionpedidos.service.DetalleAlbaranService;
import com.ajgestion.gestionpedidos.service.DetallePedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/detalles_albaran")
public class DetalleAlbaranController {


    private DetalleAlbaranService detalleAlbaranService;
    private DetallePedidoService detallePedidoService;
    @Autowired
    public DetalleAlbaranController(DetalleAlbaranService detalleAlbaranService, DetallePedidoService detallePedidoService){
        this.detalleAlbaranService = detalleAlbaranService;
        this.detallePedidoService = detallePedidoService;
    }
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
    @GetMapping("/detalles/{id}")
    public ResponseEntity<List<DetalleAlbaran>> obtenerDetallesAlbaran(@PathVariable Long id) {
        List<DetalleAlbaran> detallesAlbaran = detalleAlbaranService.obtenerPorDetallePedido(id);
        if (detallesAlbaran != null && !detallesAlbaran.isEmpty()) {
            for (DetalleAlbaran detalle : detallesAlbaran) {
                if (detalle.getAlbaran() != null) {
                    detalle.setNumeroAlbaran(detalle.getAlbaran().getNumAlbaran());
                }
            }
            return new ResponseEntity<>(detallesAlbaran, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
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
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDetalleAlbaran(@PathVariable Long id) {
        try {
            Optional<DetalleAlbaran> detalleAlbaran = detalleAlbaranService.obtenerPorId(id);
            if(!detalleAlbaran.isPresent())
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            DetallePedido detallePedido = detalleAlbaran.get().getDetallePedido();
            detallePedido.setCantidadEntregada(detallePedido.getCantidadEntregada() - detalleAlbaran.get().getCantidadEntregada());
            detallePedidoService.guardar(detallePedido);
            detalleAlbaranService.eliminar(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
