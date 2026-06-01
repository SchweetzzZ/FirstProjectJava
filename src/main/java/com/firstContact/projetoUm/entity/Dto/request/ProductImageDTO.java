package com.firstContact.projetoUm.entity.Dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductImageDTO(
        @NotBlank(message = "A chave da imagem é obrigatória") String image_Key,
        @NotBlank(message = "A URL da imagem é obrigatória") String image_Url,
        @NotNull(message = "A posição da imagem é obrigatória") Integer position) {
}