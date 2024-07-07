package com.example.testingjpa;

import com.example.testingjpa.models.Producto;
import com.example.testingjpa.repository.CategoriaRepository;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class Controller {

    @Autowired
    CategoriaRepository categoriaRepository;

    @Autowired
    ProductoRepository productoRepository;

    @GetMapping("/algo")
    public Object generic(){
        var prod = productoRepository.findById(50L).get();
        System.out.println("ANTES");
        System.out.println(prod.getId());
        System.out.println(prod.getNombre());
        System.out.println(prod.getPrecio());
        prod.setPrecio(BigDecimal.valueOf(87.84));
        prod.setNombre(prod.getNombre() + " " + System.currentTimeMillis());
        productoRepository.saveAndFlush(prod);
        return "algo";
    }

    @GetMapping("/algo2")
    public Object generic2() throws JsonProcessingException {
        var prod = productoRepository.findById(50L).get();
        return new ObjectMapper().setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY).writeValueAsString(prod);
    }

}

@Repository
interface ProductoRepository extends JpaRepository<Producto, Long> {
}