package com.example.testingjpa.models1;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

//@Getter
//@Setter
//@Entity
//@Table(name = "CATEGORIA")
public class Categoria {
    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name = "CODIGO", nullable = false)
    private Long id;

    //@Lob
    //@Column(name = "NOMBRE")
    private String nombre;

    //@OneToMany(mappedBy = "codigoCategoria")
    private Set<Producto> productos = new LinkedHashSet<>();

}