package com.fmt.notelock.interfaces;

import com.fmt.notelock.controllers.dtos.requests.RequestNotaDTO;
import com.fmt.notelock.controllers.dtos.responses.ResponseNotaDTO;
import com.fmt.notelock.datasource.entities.CadastroEntity;
import com.fmt.notelock.datasource.entities.NotaEntity;

import java.util.List;

public interface NotaInterface {
    NotaEntity criarNota(NotaEntity notaEntity);

    NotaEntity criarNotaEntity(RequestNotaDTO requestNotaDTO);

    ResponseNotaDTO criarResponseNotaDTO(NotaEntity notaEntitySalvo);

    CadastroEntity buscarCadastroId(RequestNotaDTO requestNotaDTO);

    List<NotaEntity> listarNotas();

    List<ResponseNotaDTO> criarResponseNotaDTO(List<NotaEntity> notaEntityList);

    NotaEntity buscarNotaPorId(Long id);

    NotaEntity atualizarNota(Long id, RequestNotaDTO requestNotaDTO);

    Void deletarNota(Long id);
}
