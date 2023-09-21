package com.ajgestion.gestionpedidos.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@Table(name = "detalles_pedido")
public class DetallePedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long detalleId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id", nullable = false)
    @JsonBackReference
    private Pedido pedido;
    @ManyToOne
    @JoinColumn(name = "articulo_id", nullable = false)
    private Articulo articulo;
    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;
    @Column(name = "cantidad_entregada", nullable = false)
    private Integer cantidadEntregada = 0;

    public Long getDetalleId() {
        return detalleId;
    }

    public void setDetalleId(Long detalleId) {
        this.detalleId = detalleId;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getCantidadEntregada() {
        return cantidadEntregada;
    }

    public void setCantidadEntregada(Integer cantidadEntregada) {
        this.cantidadEntregada = cantidadEntregada;
    }

    public BigDecimal calcularImporteEsperado(){
        return calcularImporteEsperado(this.cantidad);
    }

    public BigDecimal calcularImporteEsperado(Integer cantidad){
        if(cantidad < 250)
            return new BigDecimal(cantidad).multiply(articulo.getPrecioUnitario1());
        if(cantidad < 500)
            return new BigDecimal(cantidad).multiply(articulo.getPrecioUnitario2());
        return new BigDecimal(cantidad).multiply(articulo.getPrecioUnitario3()).setScale(2, RoundingMode.HALF_UP);
    }


    public BigDecimal calcularImporteReal(){
        if(cantidadEntregada == 0)
            return calcularImporteEsperado(cantidad);
        return calcularImporteEsperado(cantidadEntregada);
    }
}
