package com.firstContact.projetoUm.repositories;

import com.firstContact.projetoUm.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    @Override
    Optional<Cart> findById(Long aLong);

    Long id(Long id);
}
