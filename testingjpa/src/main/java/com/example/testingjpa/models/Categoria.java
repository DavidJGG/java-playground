package com.example.testingjpa.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "CATEGORIA")
public class Categoria {
    @Id
    @Column(name = "CODIGO", nullable = false)
    private Long id;

    @Lob
    @Column(name = "NOMBRE")
    private String nombre;

    @OneToMany(mappedBy = "codigoCategoria")
    private Set<Producto> productos = new LinkedHashSet<>();

}