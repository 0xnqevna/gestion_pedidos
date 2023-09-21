package com.ajgestion.gestionpedidos.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "albaran")
public class Albaran {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long albaranId;

    @Column(name = "num_albaran")
    private Integer numAlbaran;
    @Column(name = "fecha_albaran")
    private Date fechaAlbaran;

    @OneToMany(mappedBy = "albaran", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<DetalleAlbaran> detallesAlbaran = new HashSet<>();

    public Long getAlbaranId() {
        return albaranId;
    }

    public void setAlbaranId(Long albaranId) {
        this.albaranId = albaranId;
    }

    public Date getFechaAlbaran() {
        return fechaAlbaran;
    }

    public void setFechaAlbaran(Date fechaAlbaran) {
        this.fechaAlbaran = fechaAlbaran;
    }

    public Set<DetalleAlbaran> getDetallesAlbaran() {
        return detallesAlbaran;
    }

    public void setDetallesAlbaran(Set<DetalleAlbaran> detallesAlbaran) {
        this.detallesAlbaran = detallesAlbaran;
    }
    public Integer getNumAlbaran() {
        return numAlbaran;
    }

    public void setNumAlbaran(Integer numAlbaran) {
        this.numAlbaran = numAlbaran;
    }
}

