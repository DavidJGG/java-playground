package com.pluginstest.demo.division;

import org.pf4j.Extension;

import com.pluginstest.demo.interfaces.IOperable;
import com.pluginstest.demo.models.Dependencia;
import com.pluginstest.demo.models.Entrada;
import com.pluginstest.demo.models.Entrada.UnEnum;

import lombok.extern.slf4j.Slf4j;

@Extension
@Slf4j
public class Division1 implements IOperable {

    @Override
    public Dependencia getDependencias() {
        return new Dependencia();
    }

    @Override
    public Number operar(Entrada entrada) {
        return entrada.getValores().stream()
                .filter(x -> x != null)
                .map(x -> x.doubleValue())
                .reduce(0.0, (a, b) -> a / b);

    }

    @Override
    public String whoAmI() {
        return "Yo soy Division 1, " + UnEnum.OPERATORIA_DIVISION;
    }

}
