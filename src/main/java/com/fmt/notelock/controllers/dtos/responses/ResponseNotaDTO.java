package com.fmt.notelock.controllers.dtos.responses;

public record ResponseNotaDTO(
        Long id,
        String titulo,
        String conteudo,
        Long idCaderno,
        Long idCadastro
) {
}
