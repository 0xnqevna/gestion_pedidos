package com.ajgestion.gestionpedidos.model;

import jakarta.persistence.*;

@Entity
@Table(name = "detalle_albaran")
public class DetalleAlbaran {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long detalleAlbaranId;

    @ManyToOne
    @JoinColumn(name = "albaran_id")
    private Albaran albaran;

    @ManyToOne
    @JoinColumn(name = "articulo_id")
    private Articulo articulo;

    @Column(name = "cantidad_entregada")
    private Integer cantidadEntregada;

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
}

