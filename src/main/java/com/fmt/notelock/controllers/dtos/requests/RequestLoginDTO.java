package com.fmt.notelock.controllers.dtos.requests;

import jakarta.validation.constraints.NotBlank;

public record RequestLoginDTO(
    @NotBlank() String login,
    @NotBlank() String senha) {
    }
