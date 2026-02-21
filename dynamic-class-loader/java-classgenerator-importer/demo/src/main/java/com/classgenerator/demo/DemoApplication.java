package com.classgenerator.demo;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

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
	public void init() throws MalformedURLException, InterruptedException {
		Thread.sleep(2000);
		var file = new File(
				"C:\\Users\\DGG\\Documents\\java-playground\\dynamic-class-loader\\java-classgenerator\\demo\\target\\classes");
		var urls = new URL[] {
				file.toURI().toURL()
		};

		ejecutar(urls, "com.classgenerator.demo.generados.Sumador");
		ejecutar(urls, "com.classgenerator.demo.generados.Restador");
		ejecutar(urls, "com.classgenerator.demo.generados2.Multiplicador");
		ejecutar(urls, "com.classgenerator.demo.generados2.Divisor");

	}

	private void ejecutar(URL[] urls, String className) {
		try (URLClassLoader classLoader = new URLClassLoader(urls, IOperable.class.getClassLoader())) {
			var sumadorClass = classLoader.loadClass(className);
			var sumador = (IOperable<? extends Number, WorkValue>) sumadorClass.getDeclaredConstructor()
					.newInstance();
			System.out.println("-----------------------------------------");
			System.out.println(sumador.whoami());
			System.out.println(sumador.ejecutar(new WorkValue(3, 5)));
			System.out.println("-----------------------------------------");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
