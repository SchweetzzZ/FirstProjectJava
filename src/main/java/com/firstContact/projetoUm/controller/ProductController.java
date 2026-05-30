package com.firstContact.projetoUm.controller;

import com.firstContact.projetoUm.entity.Product;
import com.firstContact.projetoUm.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/Products")
public class ProductController {

    @Autowired
    private ProductService service;

    @PostMapping
    public ResponseEntity<Product> insert(@RequestBody Product product) {
        product = service.createProduct(product);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("q{id}")
                .buildAndExpand(product.getId())
                .toUri();
        return ResponseEntity.created(uri).body(product);
    }

    @PutMapping
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product product) {
        product = service.update(id, product);
        return ResponseEntity.ok().body(product);
    }


    @GetMapping
    public ResponseEntity<List<Product>>  findAll(){
        List<Product> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<Product> findById(@PathVariable Long id){
        Product objeto = service.findById(id);
        return ResponseEntity.ok().body(objeto);
    }
}
