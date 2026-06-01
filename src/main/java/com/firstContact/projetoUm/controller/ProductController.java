package com.firstContact.projetoUm.controller;

import com.firstContact.projetoUm.entity.Product;
import com.firstContact.projetoUm.entity.Dto.request.ProductRequestDTO;
import com.firstContact.projetoUm.entity.Dto.request.ProductUpdateDTO;
import com.firstContact.projetoUm.services.ProductService;
import jakarta.validation.Valid;
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
    public ResponseEntity<Product> insert(@Valid @RequestBody ProductRequestDTO productDto) {
        Product product = service.createProduct(productDto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(product.getId())
                .toUri();
        return ResponseEntity.created(uri).body(product);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @Valid @RequestBody ProductUpdateDTO reqDTO) {
        Product product = service.update(id, reqDTO);
        return ResponseEntity.ok().body(product);
    }

    @GetMapping
    public ResponseEntity<List<Product>> findAll() {
        List<Product> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Product> findById(@PathVariable Long id) {
        Product objeto = service.findById(id);
        return ResponseEntity.ok().body(objeto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
