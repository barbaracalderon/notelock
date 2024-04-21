package com.fmt.notelock.interfaces;

import com.fmt.notelock.controllers.dtos.requests.RequestCadernoDTO;
import com.fmt.notelock.controllers.dtos.responses.ResponseCadernoDTO;
import com.fmt.notelock.datasource.entities.CadastroEntity;
import com.fmt.notelock.datasource.entities.CadernoEntity;

import java.util.List;

public interface CadernoInterface {
    CadernoEntity criarCaderno(CadernoEntity cadernoEntity);

    CadernoEntity criarCadernoEntity(RequestCadernoDTO requestCadernoDTO);

    ResponseCadernoDTO criarResponseCadernoDTO(CadernoEntity cadernoEntitySalvo);

    CadastroEntity buscarCadastroId(RequestCadernoDTO requestCadernoDTO);

    List<CadernoEntity> listarCadernos();

    List<ResponseCadernoDTO> criarResponseCadernoDTO(List<CadernoEntity> cadernoEntityList);

    CadernoEntity buscarCadernoPorId(Long id);

    CadernoEntity atualizarCaderno(Long id, RequestCadernoDTO requestCadernoDTO);

    Void deletarCaderno(Long id);
}
