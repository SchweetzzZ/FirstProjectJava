package com.firstContact.projetoUm.controller;

import com.firstContact.projetoUm.entity.Comments;
import com.firstContact.projetoUm.entity.Dto.request.CommentsDTO;
import com.firstContact.projetoUm.services.CommentsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import software.amazon.awssdk.regions.servicemetadata.ServerlessrepoServiceMetadata;

import java.net.URI;

@RestController
@RequestMapping("/comments")
public class CommentsController {

    @Autowired
    private CommentsService service;

    @PostMapping
    public ResponseEntity<Comments> insert(@Valid @RequestBody CommentsDTO dto){
        Comments comments = service.createComment(dto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("{/id}")
                .buildAndExpand(comments.getId())
                .toUri();
        return ResponseEntity.created(uri).body(comments);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.deleteComment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Comments> findById(@PathVariable Long id){
        Comments comments = service.findById(id);
        return ResponseEntity.ok().body(comments);
    }
}
