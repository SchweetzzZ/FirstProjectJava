package com.firstContact.projetoUm.services;

import com.firstContact.projetoUm.entity.Product;
import com.firstContact.projetoUm.entity.ProductImages;
import com.firstContact.projetoUm.repositories.ProductRepository;
import com.firstContact.projetoUm.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {



    @Autowired
    private ProductRepository repository;

    public Product createProduct(Product product) {
        Product createdProduct = repository.save(product);
        return createdProduct;
    }

    public Product update(Long id, Product product) {
        try{
            Product entity = repository.getReferenceById(id);

            updateData(entity, product);

            return repository.save(entity);
        } catch (jakarta.persistence.EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(Product entity, Product obj) {
        entity.setName(obj.getName());
        entity.setDescription(obj.getDescription());
        entity.setPrice(obj.getPrice());
        entity.setImgUrl(obj.getImgUrl());

        entity.getImages().clear();

        if(obj.getImages() != null){
            for(ProductImages img: obj.getImages() ){
                entity.addImages(img);
            }
        }
    }


    public List<Product> findAll() {
        return repository.findAll();
    }
    public Product findById(Long id) {
        Optional<Product> obj = repository.findById(id);
        return obj.get();
    }
}