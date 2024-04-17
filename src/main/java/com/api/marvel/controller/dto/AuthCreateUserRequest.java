package com.api.marvel.controller.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record AuthCreateUserRequest(
        @NotBlank String username,
        @NotBlank String password,
        @Valid AuthCreateRoleRequest authCreateRoleRequest) {
}