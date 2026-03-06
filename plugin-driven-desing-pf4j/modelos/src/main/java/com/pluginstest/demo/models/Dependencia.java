package com.pluginstest.demo.models;

import java.util.HashMap;
import java.util.Map;

import com.pluginstest.demo.interfaces.IOperable;

import lombok.Data;

@Data
public class Dependencia {

    public Dependencia() {
        init();
    }

    public Dependencia(Map<String, IOperable> dependencias) {
        init();
        this.dependencias = dependencias;
    }

    private void init() {
        this.dependencias = new HashMap<>();
    }

    private Map<String, IOperable> dependencias;

}
