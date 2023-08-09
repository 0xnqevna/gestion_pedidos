package com.ajgestion.gestionpedidos.controller;

import com.ajgestion.gestionpedidos.model.Pedido;
import com.ajgestion.gestionpedidos.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {
    private final PedidoService pedidoService;

    @Autowired
    public PedidoController(PedidoService pedidoService){
        this.pedidoService = pedidoService;
    }

    @GetMapping
    public List<Pedido> obtenerTodos(){
        return pedidoService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> obtenerPorId(@PathVariable Long id){
        Optional<Pedido> optionalPedido = pedidoService.obtenerPorId(id);
        if(optionalPedido.isPresent())
            return new ResponseEntity<>(optionalPedido.get(), HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PostMapping
    public ResponseEntity<Pedido> crearPedido(@RequestBody Pedido pedido){
        return new ResponseEntity<>(pedidoService.guardar(pedido), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pedido> actualizarPedido(@PathVariable Long id, @RequestBody Pedido pedido){
        Optional<Pedido> optionalPedido = pedidoService.obtenerPorId(id);

        if(!optionalPedido.isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Pedido pedidoModificado = pedidoService.guardar(pedido);
        return new ResponseEntity<>(pedidoModificado, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPedido(@PathVariable Long id){
        try{
            pedidoService.eliminar(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (EmptyResultDataAccessException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
