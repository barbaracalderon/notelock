package com.fmt.notelock.services;

import com.fmt.notelock.datasource.entities.CadastroEntity;
import com.fmt.notelock.interfaces.CadernoInterface;
import com.fmt.notelock.controllers.dtos.requests.RequestCadernoDTO;
import com.fmt.notelock.controllers.dtos.responses.ResponseCadernoDTO;
import com.fmt.notelock.datasource.entities.CadernoEntity;
import com.fmt.notelock.datasource.repositories.CadernoRepository;
import com.fmt.notelock.infra.exceptions.CadernoNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CadernoService implements CadernoInterface {

    private final CadernoRepository cadernoRepository;
    private final CadastroService cadastroService;

    @Autowired
    public CadernoService(CadernoRepository cadernoRepository,
                          CadastroService cadastroService) {
        this.cadernoRepository = cadernoRepository;
        this.cadastroService = cadastroService;
    }

    @Override
    public CadernoEntity criarCaderno(CadernoEntity cadernoEntity) {
        return cadernoRepository.save(cadernoEntity);
    }

    @Override
    public CadernoEntity criarCadernoEntity(RequestCadernoDTO requestCadernoDTO) {
        CadernoEntity cadernoEntity = new CadernoEntity();
        cadernoEntity.setNome(requestCadernoDTO.nome());
        cadernoEntity.setIdCadastro(requestCadernoDTO.idCadastro());
        return cadernoEntity;
    }

    @Override
    public ResponseCadernoDTO criarResponseCadernoDTO(CadernoEntity cadernoEntitySalvo) {
        return new ResponseCadernoDTO(
                cadernoEntitySalvo.getId(),
                cadernoEntitySalvo.getNome(),
                cadernoEntitySalvo.getIdCadastro()
        );
    }

    @Override
    public CadastroEntity buscarCadastroId(RequestCadernoDTO requestCadernoDTO) {
        return cadastroService.buscarCadastroPorId(requestCadernoDTO.idCadastro());
    }

    @Override
    public List<CadernoEntity> listarCadernos() {
        return cadernoRepository.findAll();
    }

    @Override
    public List<ResponseCadernoDTO> criarResponseCadernoDTO(List<CadernoEntity> cadernoEntityList) {
        return cadernoEntityList.stream()
                .map(this::criarResponseCadernoDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CadernoEntity buscarCadernoPorId(Long id) {
        return cadernoRepository.findById(id).orElseThrow(
                () -> new CadernoNotFoundException("Id do Caderno não encontrado: " + id));
    }

    @Override
    public CadernoEntity atualizarCaderno(Long id, RequestCadernoDTO requestCadernoDTO) {
        return cadernoRepository.findById(id)
                .map(caderno -> {
                    caderno.setNome(requestCadernoDTO.nome());
                    caderno.setIdCadastro(requestCadernoDTO.idCadastro());
                    return caderno;
                })
                .orElseThrow(() -> new CadernoNotFoundException("Id do Caderno não encontrado para atualizar: " + id));
    }

    @Override
    public Void deletarCaderno(Long id) {
        cadernoRepository.findById(id)
                .orElseThrow(() -> new CadernoNotFoundException("Id da Caderno não encontrado para deletar: " + id));
        cadernoRepository.deleteById(id);
        return null;
    }

}
