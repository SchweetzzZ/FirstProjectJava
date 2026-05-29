package com.firstContact.projetoUm.repositories;

import com.firstContact.projetoUm.entity.OrderItem;
import com.firstContact.projetoUm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {


}
