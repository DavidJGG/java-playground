package com.pluginstest.demo.division.plugin;

import org.pf4j.Plugin;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PluginDivision extends Plugin {

    @Override
    public void start() {
        log.info("PluginDivision ha iniciado");
    }

    @Override
    public void stop() {
        log.info("PluginDivision se ha detenido");
    }

    @Override
    public void delete() {
        log.info("PluginDivision ha sido eliminado");
    }

}
