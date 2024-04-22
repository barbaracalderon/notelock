package com.fmt.notelock.services;

import com.fmt.notelock.datasource.entities.CadastroEntity;
import com.fmt.notelock.interfaces.NotaInterface;
import com.fmt.notelock.controllers.dtos.requests.RequestNotaDTO;
import com.fmt.notelock.controllers.dtos.responses.ResponseNotaDTO;
import com.fmt.notelock.datasource.entities.NotaEntity;
import com.fmt.notelock.datasource.repositories.NotaRepository;
import com.fmt.notelock.infra.exceptions.NotaNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotaService implements NotaInterface {

    private final NotaRepository notaRepository;
    private final CadastroService cadastroService;

    @Autowired
    public NotaService(NotaRepository notaRepository,
                          CadastroService cadastroService) {
        this.notaRepository = notaRepository;
        this.cadastroService = cadastroService;
    }

    @Override
    public NotaEntity criarNota(NotaEntity notaEntity) {
        return notaRepository.save(notaEntity);
    }

    @Override
    public NotaEntity criarNotaEntity(RequestNotaDTO requestNotaDTO) {
        NotaEntity notaEntity = new NotaEntity();
        notaEntity.setTitulo(requestNotaDTO.titulo());
        notaEntity.setConteudo(requestNotaDTO.conteudo());
        notaEntity.setIdCadastro(requestNotaDTO.idCadastro());
        notaEntity.setIdCaderno(requestNotaDTO.idCaderno());
        return notaEntity;
    }

    @Override
    public ResponseNotaDTO criarResponseNotaDTO(NotaEntity notaEntitySalvo) {
        return new ResponseNotaDTO(
                notaEntitySalvo.getId(),
                notaEntitySalvo.getTitulo(),
                notaEntitySalvo.getConteudo(),
                notaEntitySalvo.getIdCaderno(),
                notaEntitySalvo.getIdCadastro()
        );
    }

    @Override
    public CadastroEntity buscarCadastroId(RequestNotaDTO requestNotaDTO) {
        return cadastroService.buscarCadastroPorId(requestNotaDTO.idCadastro());
    }

    @Override
    public List<NotaEntity> listarNotas() {
        return notaRepository.findAll();
    }

    @Override
    public List<ResponseNotaDTO> criarResponseNotaDTO(List<NotaEntity> notaEntityList) {
        return notaEntityList.stream()
                .map(this::criarResponseNotaDTO)
                .collect(Collectors.toList());
    }

    @Override
    public NotaEntity buscarNotaPorId(Long id) {
        return notaRepository.findById(id).orElseThrow(
                () -> new NotaNotFoundException("Id do Nota não encontrado: " + id));
    }

    @Override
    public NotaEntity atualizarNota(Long id, RequestNotaDTO requestNotaDTO) {
        return notaRepository.findById(id)
                .map(nota -> {
                    nota.setTitulo(requestNotaDTO.titulo());
                    nota.setConteudo(requestNotaDTO.conteudo());
                    nota.setIdCadastro(requestNotaDTO.idCadastro());
                    nota.setIdCaderno(requestNotaDTO.idCaderno());
                    return nota;
                })
                .orElseThrow(() -> new NotaNotFoundException("Id do Nota não encontrado para atualizar: " + id));
    }

    @Override
    public Void deletarNota(Long id) {
        notaRepository.findById(id)
                .orElseThrow(() -> new NotaNotFoundException("Id da Nota não encontrado para deletar: " + id));
        notaRepository.deleteById(id);
        return null;
    }

}
