package com.example.testingjpa.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

}