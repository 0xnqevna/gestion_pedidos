package com.ajgestion.gestionpedidos.controller;

import com.ajgestion.gestionpedidos.model.Albaran;
import com.ajgestion.gestionpedidos.model.Pedido;
import com.ajgestion.gestionpedidos.service.AlbaranService;
import com.ajgestion.gestionpedidos.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/albaranes")
public class AlbaranController {

    private AlbaranService albaranService;
    private final PedidoService pedidoService;

    @Autowired
    public AlbaranController(AlbaranService albaranService, PedidoService pedidoService){
        this.pedidoService = pedidoService;
        this.albaranService = albaranService;
    }
    @GetMapping
    public List<Albaran> obtenerTodos() {
        return albaranService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Albaran> obtenerPorId(@PathVariable Long id) {
        Optional<Albaran> optionalAlbaran = albaranService.obtenerPorId(id);
        if (optionalAlbaran.isPresent())
            return new ResponseEntity<>(optionalAlbaran.get(), HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/pedido/{pedidoId}")
    public ResponseEntity<List<Albaran>> obtenerPorNumPedido(@PathVariable Long pedidoId) {
        Optional<Pedido> pedido = pedidoService.obtenerPorId(pedidoId);
        if(!pedido.isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        List<Albaran> albaranes = albaranService.obtenerPorPedido(pedido.get());
        return new ResponseEntity<>(albaranes, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Albaran> crearAlbaran(@RequestBody Albaran albaran) {
        Albaran nuevoAlbaran = albaranService.guardar(albaran);
        return new ResponseEntity<>(nuevoAlbaran, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Albaran> actualizarAlbaran(@PathVariable Long id, @RequestBody Albaran albaran) {
        if (!albaranService.obtenerPorId(id).isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Albaran albaranActualizado = albaranService.guardar(albaran);
        return new ResponseEntity<>(albaranActualizado, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAlbaran(@PathVariable Long id) {
        try {
            albaranService.eliminar(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
