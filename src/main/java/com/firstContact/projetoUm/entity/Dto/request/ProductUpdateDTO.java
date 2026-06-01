package com.firstContact.projetoUm.entity.Dto.request;

import java.util.List;
import java.util.Set;

import jakarta.validation.constraints.Positive;

public record ProductUpdateDTO(

        String name,
        String description,

        @Positive(message = "O preço deve ser maior que zero") Double price,

        Set<Long> categoryIds,

        List<ProductImageDTO> images

) {
}
