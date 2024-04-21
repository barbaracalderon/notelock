package com.fmt.notelock.services;

import com.fmt.notelock.controllers.dtos.requests.RequestCadastroDTO;
import com.fmt.notelock.controllers.dtos.responses.ResponseCadastroDTO;
import com.fmt.notelock.datasource.entities.CadastroEntity;
import com.fmt.notelock.datasource.repositories.CadastroRepository;
import com.fmt.notelock.infra.exceptions.CadastroNotFoundException;
import com.fmt.notelock.interfaces.CadastroInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CadastroService implements CadastroInterface {

    private final CadastroRepository cadastroRepository;

    @Autowired
    public CadastroService(CadastroRepository cadastroRepository) {
        this.cadastroRepository = cadastroRepository;
    }

    @Override
    public CadastroEntity criarCadastro(RequestCadastroDTO requestCadastroDTO) {
        CadastroEntity cadastroEntityEncrypted = criarEntidadeCadastroEncrypted(requestCadastroDTO);
        return cadastroRepository.save(cadastroEntityEncrypted);
    }

    public CadastroEntity criarEntidadeCadastroEncrypted(RequestCadastroDTO requestCadastroDTO) {
        String encryptedPassword = new BCryptPasswordEncoder().encode(requestCadastroDTO.senha());
        return new CadastroEntity(requestCadastroDTO.nome(), requestCadastroDTO.login(), encryptedPassword, requestCadastroDTO.papel());
    }


    public ResponseCadastroDTO criarResponseCadastroDTO(CadastroEntity cadastroEntitySalvo) {
        return new ResponseCadastroDTO(cadastroEntitySalvo.getId(), cadastroEntitySalvo.getNome(), cadastroEntitySalvo.getLogin(), cadastroEntitySalvo.getPapel());
    }

    public List<CadastroEntity> listarCadastros() {
        return cadastroRepository.findAll();
    }

    public List<ResponseCadastroDTO> criarResponseCadastroList(List<CadastroEntity> cadastroEntityList) {
        return cadastroEntityList.stream()
                .map(user -> new ResponseCadastroDTO(user.getId(), user.getNome(), user.getLogin(), user.getPapel()))
                .collect(Collectors.toList());    }

    @Override
    public Void deletarCadastro(Long id) {
        cadastroRepository.findById(id)
                .orElseThrow(() -> new CadastroNotFoundException("Id do Cadastro não encontrado para deletar: " + id));
        cadastroRepository.deleteById(id);
        return null;
    }

    public CadastroEntity buscarCadastroPorId(Long id) {
        return cadastroRepository.findById(id).orElseThrow(
                () -> new CadastroNotFoundException("Id do Cadastro não encontrado: " + id));
    }

}
