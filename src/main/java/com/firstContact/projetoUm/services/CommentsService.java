package com.firstContact.projetoUm.services;

import com.firstContact.projetoUm.entity.Comments;
import com.firstContact.projetoUm.entity.Dto.request.CommentsDTO;
import com.firstContact.projetoUm.entity.Dto.request.CommentsUpdateDTO;
import com.firstContact.projetoUm.entity.Product;
import com.firstContact.projetoUm.entity.User;
import com.firstContact.projetoUm.repositories.CommentsRepository;
import com.firstContact.projetoUm.repositories.ProductRepository;
import com.firstContact.projetoUm.repositories.UserRepository;
import com.firstContact.projetoUm.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class CommentsService {

    @Autowired
    private CommentsRepository commentsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    public Comments createComment(CommentsDTO data) {
        User client = userRepository.findById(data.client_id()).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
        Product produto = productRepository.findById(data.product_id()).orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));
        Comments comments = new Comments();
        comments.setProducts(produto);
        comments.setClient(client);
        comments.setContent(data.content());
        comments.setMoment(Instant.now());
        return commentsRepository.save(comments);
    }

    public void deleteComment(Long id) {
        Comments comments = commentsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comentário não encontrado"));
        commentsRepository.deleteById(id);
    }

    public Comments findById(Long id) {
        Comments comments = commentsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comentário não encontrado"));
        return comments;
    }
}
