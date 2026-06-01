package com.firstContact.projetoUm.entity.Dto.response;

import com.firstContact.projetoUm.entity.User;

public record UserResponseDTO(
        Long id,
        String name,
        String email,
        String phone
) {
    public UserResponseDTO(User user) {
        this(user.getId(), user.getName(), user.getEmail(), user.getPhone());
    }
}
