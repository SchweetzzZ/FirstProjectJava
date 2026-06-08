package com.firstContact.projetoUm.repositories;

import com.firstContact.projetoUm.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemsRepository extends JpaRepository<CartItem, Long> {
}
