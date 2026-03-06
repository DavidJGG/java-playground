package com.pluginstest.demo.interfaces;

import org.pf4j.ExtensionPoint;

import com.pluginstest.demo.models.Dependencia;
import com.pluginstest.demo.models.Entrada;

public interface IOperable extends ExtensionPoint {

    public Number operar(Entrada entrada);

    public Dependencia getDependencias();

    public String whoAmI();

}
