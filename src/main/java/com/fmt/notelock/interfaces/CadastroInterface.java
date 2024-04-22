package com.fmt.notelock.interfaces;

import com.fmt.notelock.controllers.dtos.requests.RequestCadastroDTO;
import com.fmt.notelock.controllers.dtos.responses.ResponseCadastroDTO;
import com.fmt.notelock.datasource.entities.CadastroEntity;

import java.util.List;

public interface CadastroInterface {
    CadastroEntity criarCadastro(RequestCadastroDTO requestCadastroDTO);

    List<CadastroEntity> listarCadastros();

    Void deletarCadastro(Long id);

}
