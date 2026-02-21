package com.classgenerator.demo.generados2;

import com.classgenerator.demo.interfaces.IOperable;
import com.classgenerator.demo.models.WorkValue;

public class Divisor implements IOperable<Double, WorkValue> {

    @Override
    public Double ejecutar(WorkValue input) {
        if (input.getVal2() == 0) {
            throw new ArithmeticException("No se puede dividir por cero");
        }
        return (double) ((double) input.getVal1() / (double) input.getVal2());
    }

    @Override
    public String whoami() {
        return "Soy el objeto divisor MODIFICADO";
    }

}
