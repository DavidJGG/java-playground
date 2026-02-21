package com.classgenerator.demo;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.classgenerator.demo.interfaces.IOperable;
import com.classgenerator.demo.models.WorkValue;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@PostConstruct
	public void init() throws InterruptedException, IOException {
		/*
		 * var file = new File(
		 * "C:\Users\DGG\Documents\java-playground\dynamic-class-loader\java-classgenerator\demo\target\classes"
		 * );
		 */
		var urls = new URL[] {
				// file.toURI().toURL()
				URI.create(
						"https://raw.githubusercontent.com/DavidJGG/java-playground/main/dynamic-class-loader/java-classgenerator/demo/target/classes/")
						.toURL()
		};

		try {
			while (true) {
				ejecutar(urls, "com.classgenerator.demo.generados.Sumador");
				ejecutar(urls, "com.classgenerator.demo.generados.Restador");
				ejecutar(urls, "com.classgenerator.demo.generados2.Multiplicador");
				ejecutar(urls, "com.classgenerator.demo.generados2.Divisor");
				System.out.println();
				System.out.println();
				System.out.println();
				Thread.sleep(2000);
			}
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		} finally {
			classLoader.close();
		}

	}

	private void ejecutar(URL[] urls, String className)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {
		URLClassLoader classLoader = getClassLoader(urls);
		var sumadorClass = classLoader.loadClass(className);
		var sumador = (IOperable<? extends Number, WorkValue>) sumadorClass.getDeclaredConstructor()
				.newInstance();
		System.out.println("-----------------------------------------");
		System.out.println(sumador.whoami());
		System.out.println(sumador.ejecutar(new WorkValue(3, 5)));
		System.out.println("-----------------------------------------");
	}

	private URLClassLoader classLoader = null;

	private URLClassLoader getClassLoader(URL[] urls) {
		try {
			if (classLoader == null) {
				this.classLoader = new URLClassLoader(urls, IOperable.class.getClassLoader());
				return this.classLoader;
			}

			var refreshFilePath = "C:\\Users\\" + System.getProperty("user.name")
					+ "\\Documents\\java-playground\\dynamic-class-loader\\java-classgenerator-importer\\demo\\refrescar";
			var lines = Files.readAllLines(Path.of(refreshFilePath), StandardCharsets.UTF_8);
			return lines.stream().filter(line -> !line.isBlank() && line.startsWith("refrescar=")).map(line -> {
				if (line.split("=")[1].equals("true")) {
					try {
						this.classLoader.close();
						// Eliminando refrescar=true del archivo
						Files.delete(Path.of(refreshFilePath));
						// Agregando refrescar=false al archivo
						Files.write(Path.of(refreshFilePath), "refrescar=false".getBytes());
					} catch (IOException e) {
						e.printStackTrace();
					}
					System.out.println("*** Refrescando ClassLoader ***");
					this.classLoader = new URLClassLoader(urls, IOperable.class.getClassLoader());
				}
				return this.classLoader;
			}).findFirst().orElse(this.classLoader);

		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

}
