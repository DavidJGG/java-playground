package com.example.testingjpa;

import com.example.testingjpa.repository.CategoriaRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class TestingjpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestingjpaApplication.class, args);
	}

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@PostConstruct
	void algo(){
		var sql = """
				create table categoria(
				    codigo bigint GENERATED ALWAYS AS IDENTITY,
				    nombre text,
				    primary key(codigo)
				);
				    
				create table producto(
				    codigo bigint GENERATED ALWAYS AS IDENTITY,
				    nombre text,
				    precio numeric(8,2),
				    codigo_categoria bigint,
				    primary key (codigo),
				    foreign key (codigo_categoria) references categoria(codigo)
				);
				    
				create table cliente(
				    codigo bigint GENERATED ALWAYS AS IDENTITY,
				    nombre text,
				    primary key (codigo)
				);
				    
				create table compra(
				    fecha timestamp,
				    codigo_producto bigint,
				    codigo_cliente bigint,
				    primary key (fecha, codigo_producto, codigo_cliente),
				    foreign key (codigo_producto) references producto(codigo),
				    foreign key (codigo_cliente) references cliente(codigo)
				);
				    
				    
				    
				    
				insert into CATEGORIA (NOMBRE)
				values ('Cat A'),
				       ('Cat B'),
				       ('Cat C'),
				       ('Cat D'),
				       ('Cat E');
				    
				insert into CLIENTE (NOMBRE)
				values ('Cliente A'),
				       ('Cliente B'),
				       ('Cliente C'),
				       ('Cliente D'),
				       ('Cliente E');
				    
				    
				insert into PRODUCTO (NOMBRE, PRECIO, CODIGO_CATEGORIA)
				values ('Producto A cA', 5.8, 1), ('Producto A cB', 5.8, 2), ('Producto A cC', 5.8, 3), ('Producto A cD', 5.8, 4), ('Producto A cE', 5.8, 5),
				       ('Producto B cA', 5.8, 1), ('Producto B cB', 5.8, 2), ('Producto B cC', 5.8, 3), ('Producto B cD', 5.8, 4), ('Producto B cE', 5.8, 5),
				       ('Producto C cA', 5.8, 1), ('Producto C cB', 5.8, 2), ('Producto C cC', 5.8, 3), ('Producto C cD', 5.8, 4), ('Producto C cE', 5.8, 5),
				       ('Producto D cA', 5.8, 1), ('Producto D cB', 5.8, 2), ('Producto D cC', 5.8, 3), ('Producto D cD', 5.8, 4), ('Producto D cE', 5.8, 5),
				       ('Producto E cA', 5.8, 1), ('Producto E cB', 5.8, 2), ('Producto E cC', 5.8, 3), ('Producto E cD', 5.8, 4), ('Producto E cE', 5.8, 5)
				;
				    
				insert into PRODUCTO (NOMBRE, PRECIO, CODIGO_CATEGORIA)
				values ('Producto A cA', 5.8, 1), ('Producto A cB', 5.8, 2), ('Producto A cC', 5.8, 3), ('Producto A cD', 5.8, 4), ('Producto A cE', 5.8, 5),
				       ('Producto B cA', 5.8, 1), ('Producto B cB', 5.8, 2), ('Producto B cC', 5.8, 3), ('Producto B cD', 5.8, 4), ('Producto B cE', 5.8, 5),
				       ('Producto C cA', 5.8, 1), ('Producto C cB', 5.8, 2), ('Producto C cC', 5.8, 3), ('Producto C cD', 5.8, 4), ('Producto C cE', 5.8, 5),
				       ('Producto D cA', 5.8, 1), ('Producto D cB', 5.8, 2), ('Producto D cC', 5.8, 3), ('Producto D cD', 5.8, 4), ('Producto D cE', 5.8, 5),
				       ('Producto E cA', 5.8, 1), ('Producto E cB', 5.8, 2), ('Producto E cC', 5.8, 3), ('Producto E cD', 5.8, 4), ('Producto E cE', 5.8, 5)
				;
				    
				insert into COMPRA(FECHA, CODIGO_CLIENTE, CODIGO_PRODUCTO)
				values
				    (current_timestamp, 1, 1),
				    (current_timestamp, 1, 2),
				    (current_timestamp, 1, 3),
				    (current_timestamp, 1, 4),
				    (current_timestamp, 1, 5),
				    (current_timestamp, 2, 6),
				    (current_timestamp, 2, 7),
				    (current_timestamp, 2, 8),
				    (current_timestamp, 2, 9),
				    (current_timestamp, 2, 10),
				    (current_timestamp, 3, 11),
				    (current_timestamp, 3, 12),
				    (current_timestamp, 3, 13),
				    (current_timestamp, 3, 14),
				    (current_timestamp, 3, 15),
				    (current_timestamp, 4, 16),
				    (current_timestamp, 4, 17),
				    (current_timestamp, 4, 18),
				    (current_timestamp, 4, 19),
				    (current_timestamp, 4, 20),
				    (current_timestamp, 5, 21),
				    (current_timestamp, 5, 22),
				    (current_timestamp, 5, 23),
				    (current_timestamp, 5, 24),
				    (current_timestamp, 5, 25);
				    
				-- generate inserts
				    
				""";
		jdbcTemplate.execute(sql);
		System.out.println("Cargado");
	}

}
