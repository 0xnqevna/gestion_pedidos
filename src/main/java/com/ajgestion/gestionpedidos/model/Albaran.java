package com.ajgestion.gestionpedidos.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "albaran")
public class Albaran {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long albaranId;

    @ManyToOne
    @JoinColumn(name = "pedido_id", referencedColumnName = "pedidoId")
    private Pedido pedido;

    @Column(name = "fecha_albaran")
    private Date fechaAlbaran;

    @OneToMany(mappedBy = "albaran")
    private List<DetalleAlbaran> detallesAlbaran;

    public Long getAlbaranId() {
        return albaranId;
    }

    public void setAlbaranId(Long albaranId) {
        this.albaranId = albaranId;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Date getFechaAlbaran() {
        return fechaAlbaran;
    }

    public void setFechaAlbaran(Date fechaAlbaran) {
        this.fechaAlbaran = fechaAlbaran;
    }

    public List<DetalleAlbaran> getDetallesAlbaran() {
        return detallesAlbaran;
    }

    public void setDetallesAlbaran(List<DetalleAlbaran> detallesAlbaran) {
        this.detallesAlbaran = detallesAlbaran;
    }
}

