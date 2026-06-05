package com.firstContact.projetoUm.entity.Dto.request;

import com.firstContact.projetoUm.entity.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CommentsDTO(
        @NotNull(message = "Deve ter um id de produto") Long product_id,
        @NotNull(message = "Deve ter um id de usuario") Long client_id,
        @NotBlank(message = "O comentario deve ter um conteúdo") String content){ }
