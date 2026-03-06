package com.pluginstest.demo.resta.plugin;

import org.pf4j.Plugin;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PluginResta extends Plugin {

    @Override
    public void start() {
        log.info("PluginResta ha iniciado");
    }

    @Override
    public void stop() {
        log.info("PluginResta se ha detenido");
    }

    @Override
    public void delete() {
        log.info("PluginResta ha sido eliminado");
    }

}
