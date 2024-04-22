package com.fmt.notelock.controllers.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RequestCadernoDTO(
        @NotBlank() String nome,
        @NotNull Long idCadastro) {
}
