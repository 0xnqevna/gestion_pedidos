package com.ajgestion.gestionpedidos.controller;

import com.ajgestion.gestionpedidos.model.DetallePedido;
import com.ajgestion.gestionpedidos.model.Pedido;
import com.ajgestion.gestionpedidos.service.DetallePedidoService;
import com.ajgestion.gestionpedidos.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {
    private final PedidoService pedidoService;
    private final DetallePedidoService detallePedidoService;
    @Value("${file.upload-dir}")
    private String uploadDir;
    @Autowired
    public PedidoController(PedidoService pedidoService, DetallePedidoService detallePedidoService){
        this.pedidoService = pedidoService;
        this.detallePedidoService = detallePedidoService;
    }

    @GetMapping
    public List<Pedido> obtenerTodos(){
        return pedidoService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> obtenerPorId(@PathVariable Long id){
        Optional<Pedido> optionalPedido = pedidoService.obtenerPorId(id);
        return optionalPedido.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/numero")
    public List<Pedido> obtenerPedidosByNumPedido(@RequestParam Integer numPedido) {
        return pedidoService.obtenerPedidosNum(numPedido);
    }

    @GetMapping("/fabrica")
    public List<Pedido> obtenerPedidosFabrica(@RequestParam String fabrica) {
        return pedidoService.obtenerPedidosFabrica(fabrica);
    }

    @GetMapping("/fecha-servicio")
    public List<Pedido> obtenerRangoFechasServicio(
            @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate) {
        return pedidoService.obtenerRangoFechasServicio(startDate, endDate);
    }

    @PostMapping
    public ResponseEntity<Pedido> crearPedido(@RequestBody Pedido pedido){
        return ResponseEntity.ok(pedidoService.guardar(pedido));
    }
    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<Pedido> actualizarPedido(@PathVariable Long id, @RequestBody Pedido inputPedido) {
        Optional<Pedido> optionalPedido = pedidoService.obtenerPorId(id);
        if (!optionalPedido.isPresent())
            return ResponseEntity.notFound().build();

        Pedido existingPedido = optionalPedido.get();
        existingPedido.setFechaPedido(inputPedido.getFechaPedido());
        existingPedido.setFechaServicio(inputPedido.getFechaServicio());
        existingPedido.setFabrica(inputPedido.getFabrica());
        existingPedido.setNumPedido(inputPedido.getNumPedido());

        Set<DetallePedido> detallesExistentes = existingPedido.getDetalles();
        Set<DetallePedido> inputDetalles = inputPedido.getDetalles();

        // Eliminar detalles que ya no están presentes en los detalles nuevos
        detallesExistentes.removeIf(detalleExistente ->
                inputDetalles.stream().noneMatch(detalleNuevo ->
                        detalleNuevo.getArticulo().getId() == detalleExistente.getArticulo().getId()
                )
        );

        for (DetallePedido inputDetalle : inputDetalles) {
            Optional<DetallePedido> optDetalleExistente = detallesExistentes.stream()
                    .filter(detalleExistente -> detalleExistente.getArticulo().getId() == inputDetalle.getArticulo().getId())
                    .findFirst();

            if (optDetalleExistente.isPresent()) {
                DetallePedido detalleExistente = optDetalleExistente.get();
                detalleExistente.setCantidad(inputDetalle.getCantidad());
            } else {
                inputDetalle.setPedido(existingPedido);
                detallesExistentes.add(inputDetalle);
            }
        }

        existingPedido.setDetalles(detallesExistentes);
        Pedido pedidoModificado = pedidoService.guardar(existingPedido);
        return ResponseEntity.ok(pedidoModificado);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPedido(@PathVariable Long id){
        try{
            pedidoService.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        catch (EmptyResultDataAccessException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Pedido>> obtenerPedidosPorFabricaYRangoFecha(
            @RequestParam String fabrica,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {

        List<Pedido> pedidos = pedidoService.obtenerPedidosFabricaRangoFecha(fabrica, startDate, endDate);
        return ResponseEntity.ok(pedidos);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> subidaArchivos(@RequestParam MultipartFile file,
                                                 @RequestParam String pedidoId) {
        try {
            String originalFileName = file.getOriginalFilename();
            String targetDirectory = uploadDir + pedidoId + "\\";
            Path targetPath = Paths.get(targetDirectory + originalFileName);

            if (!Files.exists(Paths.get(targetDirectory)))
                Files.createDirectories(Paths.get(targetDirectory));

            byte[] bytes = file.getBytes();
            Files.write(targetPath, bytes, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

            return new ResponseEntity<>(targetPath.toString(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @GetMapping("archivos/{id}")
    public ResponseEntity<List<String>> obtenerListadoArchivos(@PathVariable Long id) {
        try {
            Path dirPath = Paths.get(uploadDir + id);

            if (!Files.exists(dirPath) || !Files.isDirectory(dirPath)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            List<String> fileList = Files.list(dirPath)
                    .filter(Files::isRegularFile)
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .collect(Collectors.toList());

            return new ResponseEntity<>(fileList, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/download/{pedidoId}/{filename}")
    public ResponseEntity<Resource> download(@PathVariable String pedidoId, @PathVariable String filename) {
        try {
            Path filePath = Paths.get(uploadDir + pedidoId + "\\" + filename);
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}