package com.pluginstest.demo.config;

import java.nio.file.Path;

import org.pf4j.JarPluginManager;
import org.pf4j.PluginManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class PluginConfig {

    private PluginManager pluginManager;

    @Value("${pf4j.plugins-dir:../plugins}")
    private String pluginsDir;

    @Bean
    public PluginManager pluginManager() {
        Path pluginsPath = Path.of(pluginsDir);
        log.info("Cargando plugins desde: {}", pluginsPath.toAbsolutePath());

        pluginManager = new JarPluginManager(pluginsPath);
        pluginManager.loadPlugins();
        pluginManager.startPlugins();

        log.info("Plugins cargados: {}", pluginManager.getPlugins().size());
        pluginManager.getPlugins()
                .forEach(plugin -> log.info("  - {} ({})", plugin.getPluginId(), plugin.getPluginState()));

        return pluginManager;
    }

    @PreDestroy
    public void cleanup() {
        if (pluginManager != null) {
            log.info("Deteniendo plugins...");
            pluginManager.stopPlugins();
        }
    }
}
