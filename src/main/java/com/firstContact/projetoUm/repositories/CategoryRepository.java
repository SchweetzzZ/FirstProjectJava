package com.firstContact.projetoUm.repositories;

import com.firstContact.projetoUm.entity.Category;
import com.firstContact.projetoUm.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {


}
