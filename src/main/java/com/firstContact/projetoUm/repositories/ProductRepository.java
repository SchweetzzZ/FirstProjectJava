package com.firstContact.projetoUm.repositories;

import com.firstContact.projetoUm.entity.Category;
import com.firstContact.projetoUm.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {


}
