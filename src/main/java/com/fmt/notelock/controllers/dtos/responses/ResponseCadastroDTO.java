package com.fmt.notelock.controllers.dtos.responses;

public record ResponseCadastroDTO(
        Long id,
        String nome,
        String login,
        String papel) {

}
