package com.example.testingjpa.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "PRODUCTO")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CODIGO", nullable = false)
    private Long id;

    @Lob
    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "PRECIO", precision = 8, scale = 2, updatable = true)
    private BigDecimal precio;


    /*@ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "CODIGO_CATEGORIA")
    private Categoria codigoCategoria;*/

}