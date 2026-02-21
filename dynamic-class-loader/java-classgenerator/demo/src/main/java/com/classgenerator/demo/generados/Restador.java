package com.classgenerator.demo.generados;

import com.classgenerator.demo.interfaces.IOperable;
import com.classgenerator.demo.models.WorkValue;

public class Restador implements IOperable<Long, WorkValue> {

    @Override
    public Long ejecutar(WorkValue input) {
        return (long) (input.getVal1() - input.getVal2());
    }

    @Override
    public String whoami() {
        return "Soy el objeto restador";
    }

}
