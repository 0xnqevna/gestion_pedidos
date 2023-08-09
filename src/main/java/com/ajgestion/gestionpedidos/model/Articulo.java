package com.ajgestion.gestionpedidos.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "articulos")
public class Articulo {
    @Id
    private int id;
    @Column(name = "descripcion", nullable = false)
    private String descripcion;
    @Column(name = "precio_unitario_1", nullable = false)
    private BigDecimal precioUnitario1;
    @Column(name = "precio_unitario_2", nullable = false)
    private BigDecimal precioUnitario2;
    @Column(name = "precio_unitario_3", nullable = false)
    private BigDecimal precioUnitario3;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecioUnitario1() {
        return precioUnitario1;
    }

    public void setPrecioUnitario1(BigDecimal precioUnitario1) {
        this.precioUnitario1 = precioUnitario1;
    }

    public BigDecimal getPrecioUnitario2() {
        return precioUnitario2;
    }

    public void setPrecioUnitario2(BigDecimal precioUnitario2) {
        this.precioUnitario2 = precioUnitario2;
    }

    public BigDecimal getPrecioUnitario3() {
        return precioUnitario3;
    }

    public void setPrecioUnitario3(BigDecimal precioUnitario3) {
        this.precioUnitario3 = precioUnitario3;
    }
}
