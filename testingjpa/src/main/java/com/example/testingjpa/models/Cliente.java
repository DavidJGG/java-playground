package com.example.testingjpa.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "CLIENTE")
public class Cliente {
    @Id
    @Column(name = "CODIGO", nullable = false)
    private Long id;

    @Lob
    @Column(name = "NOMBRE")
    private String nombre;

}