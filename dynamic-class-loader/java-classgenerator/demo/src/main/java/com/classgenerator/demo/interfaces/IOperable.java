package com.classgenerator.demo.interfaces;

public interface IOperable<O, I> {

    public O ejecutar(I input);

    public String whoami();

}
