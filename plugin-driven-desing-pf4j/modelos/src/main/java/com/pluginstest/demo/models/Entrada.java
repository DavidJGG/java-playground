package com.pluginstest.demo.models;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Entrada {

    private List<Number> valores;

    public Entrada() {
        init();
    }

    public Entrada(List<Number> valores) {
        init();
        this.valores = valores;
    }

    private void init() {
        this.valores = new ArrayList<>();
    }

    public enum UnEnum {
        OPERATORIA_SUMA,
        OPERATORIA_RESTA,
        OPERATORIA_DIVISION,
        OPERATORIA_MULTIPLICACION
    }
}
