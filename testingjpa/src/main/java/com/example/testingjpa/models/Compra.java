package com.example.testingjpa.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "COMPRA")
public class Compra {
    @EmbeddedId
    private CompraId id;

    @MapsId("codigoProducto")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "CODIGO_PRODUCTO", nullable = false)
    private Producto codigoProducto;

    @MapsId("codigoCliente")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "CODIGO_CLIENTE", nullable = false)
    private Cliente codigoCliente;

}