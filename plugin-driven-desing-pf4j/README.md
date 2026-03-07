# Prueba de Concepto - PF4J

Este documento describe la estructura de la **Prueba de Concepto (POC)** actual con PF4J, cuyo objetivo es analizar el comportamiento del framework al gestionar más de un nivel de dependencias en un entorno con Spring Boot.

## 🌳 Árbol Estructural

```text
plugin-driven-desing-pf4j/
│
├── modelos/              ➔ 🧱 Nivel de Dependencia Extra
│   ├── pom.xml             
│   └── src/main/java/      
│       └── IOperable.java  
│
├── applicacion/          ➔ 🏢 Host Application
│   ├── pom.xml             
│   └── src/main/java/      
│
├── plugin-suma/          ➔ 🧩 Plugin 1
│   ├── pom.xml             (Depende de 'modelos' y 'pf4j' con scope 'provided')
│   └── src/main/java/      
│
├── plugin-resta/         ➔ 🧩 Plugin 2
│   ├── pom.xml
│   └── src/main/java/
│
├── plugin-division/      ➔ 🧩 Plugin 3
│   ├── pom.xml
│   └── src/main/java/
│
└── plugins/              ➔ ⚙️ Directorio de Ejecución
    ├── plugin-suma.jar     
    ├── plugin-resta.jar
    └── plugin-division.jar
```

---

## 📌 Topología

### 🧱 `modelos/` (Prueba de Dependencia Transitiva)
Aísla las interfaces y modelos en un módulo separado. Se utiliza exclusivamente para validar **si el sistema y el empaquetado son capaces de resolver dependencias de segundo nivel o múltiples niveles** y cargarlas correctamente en tiempo de ejecución. 

### 🏢 `applicacion/` (Host de Evaluación)
Es el punto donde se inicializa el `PluginManager`. Permite verificar en la práctica que las dependencias requeridas por los plugins hayan cargado de modo correcto y realizar las llamadas necesarias.

### 🧩 Carpetas de Plugins (`plugin-suma`, `plugin-resta`, `plugin-division`)
Son submódulos independientes. Fuerzan un escenario donde múltiples plugins apuntan hacia un mismo contrato base ubicado en el módulo `modelos/`.

### ⚙️ `plugins/` (Directorio de Validación en Ejecución)
Es el contenedor físico final hacia donde se envían los artefactos compilados (`.jar`). Aquí es donde PF4J explora, valida y extrae las clases en tiempo de ejecución.

---

## 🔑 Puntos Clave de Implementación

Al implementar esta topología, existen reglas técnicas críticas para que el classloader de PF4J funcione de manera correcta:

1. **Uso de `<scope>provided</scope>` en los Plugins:**
   En los `pom.xml` de los plugins (como `plugin-suma`), las dependencias hacia `pf4j` y `modelos` **deben** estar marcadas como `<scope>provided</scope>`. 
   * **¿Por qué?** Porque cuando el plugin se ejecute, será impulsado por el *Host* (`applicacion`), quien *ya tiene cargados en memoria* a PF4J y los modelos. Si los plugins compilaran estas dependencias adentro de su JAR, habría conflictos de ClassLoader (la aplicación no reconocería que el `IOperable` del host es el mismo `IOperable` empaquetado en el plugin).

2. **Configuración del Manifiesto de PF4J:**
   Los `pom.xml` de los plugins utilizan el `maven-jar-plugin` para configurar un `MANIFEST.MF` específico. Ahí se declaran las variables esenciales que PF4J usa para identificar el plugin al leer el archivo `.jar` (ej. `Plugin-Class`, `Plugin-Id`, `Plugin-Version`).

3. **Inhibición del Fat JAR de Spring Boot en los Plugins:**
   Dado que los plugins no son una aplicación de Spring Boot autónoma (sino una librería que el host va a consumir), en sus propiedades de Maven se indica `<spring-boot.repackage.skip>true</spring-boot.repackage.skip>`, forzando a que se genere un JAR estándar de Java en lugar de un JAR ejecutable por Spring.
