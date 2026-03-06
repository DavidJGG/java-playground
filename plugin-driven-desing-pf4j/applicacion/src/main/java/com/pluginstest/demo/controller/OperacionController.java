package com.pluginstest.demo.controller;

import java.util.List;

import org.pf4j.PluginManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pluginstest.demo.interfaces.IOperable;
import com.pluginstest.demo.models.Entrada;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OperacionController {

    private final PluginManager pluginManager;

    @PostMapping("/operar")
    public ResponseEntity<Number> operar(@RequestBody Entrada entrada) {
        List<IOperable> extensions = pluginManager.getExtensions(IOperable.class);

        log.info("Extensiones IOperable encontradas: {}", extensions.size());

        for (IOperable extension : extensions) {
            try {
                Number resultado = extension.operar(entrada);
                log.info("Resultado de {}: {}", extension.getClass().getSimpleName(), resultado);
                return ResponseEntity.ok(resultado);
            } catch (IllegalArgumentException e) {
                log.debug("{} no soporta la operación: {}", extension.getClass().getSimpleName(), e.getMessage());
            }
        }

        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/plugins")
    public ResponseEntity<List<String>> listarPlugins() {
        List<String> plugins = pluginManager.getPlugins().stream()
                .map(p -> p.getPluginId() + " v" + p.getDescriptor().getVersion() + " [" + p.getPluginState() + "]")
                .toList();
        return ResponseEntity.ok(plugins);
    }

    @GetMapping("/extensiones")
    public ResponseEntity<List<String>> listarExtensiones() {
        List<String> extensiones = pluginManager.getExtensions(IOperable.class).stream()
                .map(e -> e.getClass().getName())
                .toList();
        return ResponseEntity.ok(extensiones);
    }
}
