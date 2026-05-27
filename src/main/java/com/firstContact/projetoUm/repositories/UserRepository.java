package com.firstContact.projetoUm.repositories;

import com.firstContact.projetoUm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {


}
