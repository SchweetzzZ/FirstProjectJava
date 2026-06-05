package com.firstContact.projetoUm.repositories;

import com.firstContact.projetoUm.entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentsRepository extends JpaRepository<Comments, Long> {

    List<Comments> id(Long id);
}
