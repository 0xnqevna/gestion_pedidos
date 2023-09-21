package com.ajgestion.gestionpedidos.controller;

import com.ajgestion.gestionpedidos.model.Albaran;
import com.ajgestion.gestionpedidos.model.DetalleAlbaran;
import com.ajgestion.gestionpedidos.model.DetallePedido;
import com.ajgestion.gestionpedidos.model.Pedido;
import com.ajgestion.gestionpedidos.service.AlbaranService;
import com.ajgestion.gestionpedidos.service.DetalleAlbaranService;
import com.ajgestion.gestionpedidos.service.DetallePedidoService;
import com.ajgestion.gestionpedidos.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/albaranes")
public class AlbaranController {

    private AlbaranService albaranService;
    private final PedidoService pedidoService;
    private final DetallePedidoService detallePedidoService;
    private final DetalleAlbaranService detalleAlbaranService;

    @Autowired
    public AlbaranController(AlbaranService albaranService, PedidoService pedidoService, DetallePedidoService detallePedidoService, DetalleAlbaranService detalleAlbaranService){
        this.pedidoService = pedidoService;
        this.albaranService = albaranService;
        this.detallePedidoService = detallePedidoService;
        this.detalleAlbaranService = detalleAlbaranService;
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

    @PostMapping
    @Transactional
    public ResponseEntity<Albaran> crearAlbaran(@RequestBody Albaran albaran) {
        try {
            for (DetalleAlbaran detalleAlbaran : albaran.getDetallesAlbaran()) {
                String fabrica = (String) detalleAlbaran.getAdditionalProperty("fabrica");
                Integer pedidoId = Integer.parseInt(detalleAlbaran.getAdditionalProperty("numPedido").toString());

                if (fabrica == null || pedidoId == null)
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);


                Optional<Pedido> pedido = pedidoService.obtenerPedidoFabricaNumPedido(fabrica, pedidoId);

                if (!pedido.isPresent())
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);


                Map<Integer, DetallePedido> detallePedidoMap = pedido.get().getDetalles().stream()
                        .collect(Collectors.toMap(detalle -> detalle.getArticulo().getId(), Function.identity()));

                DetallePedido detallePedido = detallePedidoMap.get(detalleAlbaran.getArticulo().getId());

                if (detallePedido == null)
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);

                detalleAlbaran.setDetallePedido(detallePedido);
                detallePedido.setCantidadEntregada(detallePedido.getCantidadEntregada() + detalleAlbaran.getCantidadEntregada());
                detallePedidoService.guardar(detallePedido);
            }

            Albaran nuevoAlbaran = albaranService.guardar(albaran);
            return new ResponseEntity<>(nuevoAlbaran, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Albaran> actualizarAlbaran(@PathVariable Long id, @RequestBody Albaran albaran) {
        if (!albaranService.obtenerPorId(id).isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Albaran albaranActualizado = albaranService.guardar(albaran);
        return new ResponseEntity<>(albaranActualizado, HttpStatus.OK);
    }
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAlbaran(@PathVariable Long id) {
        try {
            Optional<Albaran> albaran = albaranService.obtenerPorId(id);
            if(!albaran.isPresent())
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            for (DetalleAlbaran detalle: albaran.get().getDetallesAlbaran()) {
                detalleAlbaranService.eliminar(detalle.getDetalleAlbaranId());
            }
            albaranService.eliminar(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
