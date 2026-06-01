package com.firstContact.projetoUm.entity.Dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.List;
import java.util.Set;

public record ProductRequestDTO(
        @NotBlank(message = "O nome do produto é obrigatório") String name,
        @NotBlank(message = "A descrição do produto é obrigatória") String description,
        @NotNull(message = "O preço é obrigatório") @Positive(message = "O preço deve ser maior que zero") Double price,
        Set<Long> categoryIds,
        List<ProductImageDTO> images) {
}
