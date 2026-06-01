package com.firstContact.projetoUm.services;

import com.firstContact.projetoUm.entity.Category;
import com.firstContact.projetoUm.entity.Product;
import com.firstContact.projetoUm.entity.ProductImages;
import com.firstContact.projetoUm.entity.Dto.request.ProductImageDTO;
import com.firstContact.projetoUm.entity.Dto.request.ProductRequestDTO;
import com.firstContact.projetoUm.entity.Dto.request.ProductUpdateDTO;
import com.firstContact.projetoUm.repositories.CategoryRepository;
import com.firstContact.projetoUm.repositories.ProductRepository;
import com.firstContact.projetoUm.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private S3Service s3Service;

    @Transactional
    public Product createProduct(ProductRequestDTO reqDTO) {
        Product product = new Product();
        product.setName(reqDTO.name());
        product.setDescription(reqDTO.description());
        product.setPrice(reqDTO.price());

        if (reqDTO.categoryIds() != null) {
            for (Long catId : reqDTO.categoryIds()) {
                Category category = categoryRepository.findById(catId)
                        .orElseThrow(
                                () -> new ResourceNotFoundException("Categoria não encontrada com o ID: " + catId));
                product.getCategories().add(category);
            }
        }

        Product createdProduct = repository.save(product);

        if (reqDTO.images() != null) {
            for (ProductImageDTO imgDto : reqDTO.images()) {
                ProductImages images = new ProductImages();
                images.setImage_Key(imgDto.image_Key());
                images.setImage_Url(imgDto.image_Url());
                images.setPosition(imgDto.position());
                createdProduct.addImages(images);
            }
            createdProduct = repository.save(createdProduct);
        }
        return createdProduct;
    }

    public Product update(Long id, ProductUpdateDTO dto) {
        try {
            Product entity = repository.getReferenceById(id);
            updateData(entity, dto);
            return repository.save(entity);
        } catch (jakarta.persistence.EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(Product entity, ProductUpdateDTO reqDTO) {
        if (reqDTO.name() != null) {
            entity.setName(reqDTO.name());
        }
        if (reqDTO.description() != null) {
            entity.setDescription(reqDTO.description());
        }
        if (reqDTO.price() != null) {
            entity.setPrice(reqDTO.price());
        }
        if (reqDTO.categoryIds() != null) {
            entity.getCategories().clear();
            for (Long catId : reqDTO.categoryIds()) {
                Category category = categoryRepository.findById(catId)
                        .orElseThrow(
                                () -> new ResourceNotFoundException("Categoria não encontrada com o ID: " + catId));
                entity.getCategories().add(category);

            }
            if (reqDTO.images() != null) {
                entity.getImages().clear();
                for (ProductImageDTO imgDto : reqDTO.images()) {
                    ProductImages images = new ProductImages();
                    images.setImage_Url(imgDto.image_Url());
                    images.setImage_Key(imgDto.image_Key());
                    images.setPosition(imgDto.position());
                    entity.addImages(images);
                }
            }

        }
    }

    @Transactional
    public void delete(Long id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
        try {
            if (product.getImages() != null) {
                for (ProductImages img : product.getImages()) {
                    s3Service.deleteFile(img.getImage_Key());
                }
            }
            repository.delete(product);
        } catch (DataIntegrityViolationException e) {
            // Proteção contra deletar produtos que estão vinculados a pedidos antigos
            // (Histórico de Vendas)
            throw new RuntimeException("Não é possível deletar o produto pois ele está associado a um pedido.");
        }
    }

    public List<Product> findAll() {
        return repository.findAll();
    }

    public Product findById(Long id) {
        Optional<Product> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id));
    }
}