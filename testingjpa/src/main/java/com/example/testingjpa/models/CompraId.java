package com.example.testingjpa.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.time.Instant;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class CompraId implements java.io.Serializable {
    private static final long serialVersionUID = -2731465475212572339L;
    @Column(name = "FECHA", nullable = false)
    private Instant fecha;

    @Column(name = "CODIGO_PRODUCTO", nullable = false)
    private Long codigoProducto;

    @Column(name = "CODIGO_CLIENTE", nullable = false)
    private Long codigoCliente;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CompraId entity = (CompraId) o;
        return Objects.equals(this.fecha, entity.fecha) &&
                Objects.equals(this.codigoCliente, entity.codigoCliente) &&
                Objects.equals(this.codigoProducto, entity.codigoProducto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fecha, codigoCliente, codigoProducto);
    }

}