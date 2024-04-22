package com.fmt.notelock.controllers.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RequestNotaDTO(
        @NotBlank() String titulo,
        @NotBlank() String conteudo,
        @NotNull() Long idCaderno,
        @NotNull() Long idCadastro) {
}
