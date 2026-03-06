package com.pluginstest.demo.suma.plugin;

import org.pf4j.Plugin;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PluginSuma extends Plugin {

    @Override
    public void start() {
        log.info("PluginSuma ha iniciado");
    }

    @Override
    public void stop() {
        log.info("PluginSuma se ha detenido");
    }

    @Override
    public void delete() {
        log.info("PluginSuma ha sido eliminado");
    }

}
