package com.ajgestion.gestionpedidos.model;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "detalle_albaran")
public class DetalleAlbaran {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long detalleAlbaranId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "albaran_id")
    @JsonBackReference
    private Albaran albaran;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "detalle_pedido_id")
    private DetallePedido detallePedido;

    public DetallePedido getDetallePedido() {
        return detallePedido;
    }

    public void setDetallePedido(DetallePedido detallePedido) {
        this.detallePedido = detallePedido;
    }

    @ManyToOne
    @JoinColumn(name = "articulo_id")
    private Articulo articulo;


    @Column(name = "cantidad_entregada")
    private Integer cantidadEntregada;
    @Transient
    private Map<String, Object> additionalProperties = new HashMap<>();
    @Transient
    private Integer numeroAlbaran;
    @JsonAnySetter
    public void setAdditionalProperties(String name, Object value){
        additionalProperties.put(name, value);
    }
    public Object getAdditionalProperty(String name) {
        return this.additionalProperties.get(name);
    }

    public Long getDetalleAlbaranId() {
        return detalleAlbaranId;
    }

    public void setDetalleAlbaranId(Long detalleAlbaranId) {
        this.detalleAlbaranId = detalleAlbaranId;
    }

    public Albaran getAlbaran() {
        return albaran;
    }

    public void setAlbaran(Albaran albaran) {
        this.albaran = albaran;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public Integer getCantidadEntregada() {
        return cantidadEntregada;
    }

    public void setCantidadEntregada(Integer cantidadEntregada) {
        this.cantidadEntregada = cantidadEntregada;
    }

    public Integer getNumeroAlbaran() {
        return numeroAlbaran;
    }

    public void setNumeroAlbaran(Integer numeroAlbaran) {
        this.numeroAlbaran = numeroAlbaran;
    }
}

