package com.fmt.notelock.controllers;


import com.fmt.notelock.controllers.dtos.requests.RequestNotaDTO;
import com.fmt.notelock.controllers.dtos.responses.ResponseCadastroDTO;
import com.fmt.notelock.controllers.dtos.responses.ResponseNotaDTO;
import com.fmt.notelock.datasource.entities.CadastroEntity;
import com.fmt.notelock.datasource.entities.NotaEntity;
import com.fmt.notelock.infra.exceptions.CadastroNotFoundException;
import com.fmt.notelock.infra.exceptions.NotaNotFoundException;
import com.fmt.notelock.infra.exceptions.CadastroNotFoundException;

import com.fmt.notelock.services.NotaService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("notas")
public class NotaController {

    @Autowired
    private NotaService notaService;


    @PostMapping()
    public ResponseEntity<?> criarNota (@Valid @RequestBody RequestNotaDTO requestNotaDTO) {
        try {
            log.info("POST /notas ---> Chamada para o método.");
            CadastroEntity cadastroEntity = notaService.buscarCadastroId(requestNotaDTO);
            NotaEntity notaEntity = notaService.criarNotaEntity(requestNotaDTO);
            NotaEntity notaEntitySalvo = notaService.criarNota(notaEntity);
            ResponseNotaDTO responseCadastroDTO = notaService.criarResponseNotaDTO(notaEntitySalvo);
            log.info("POST /notas ---> Sucesso.");
            return ResponseEntity.status(HttpStatus.CREATED).body(responseCadastroDTO);
        } catch (CadastroNotFoundException e) {
            log.error("STATUS 404 ---> Recurso não encontrado ---> {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping()
    public ResponseEntity<?> listarNotas() {
        log.info("GET /notas ---> Chamada para o método.");
        List<NotaEntity> notaEntityList = notaService.listarNotas();
        List<ResponseNotaDTO> responseCadastroDTOsList = notaService.criarResponseNotaDTO(notaEntityList);
        log.info("GET /notas ---> Sucesso.");
        return ResponseEntity.ok(responseCadastroDTOsList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarNotaPorId(@PathVariable("id") Long id) {
        try {
            log.info("GET /notas ---> Chamada para o método.");
            NotaEntity notaEntity = notaService.buscarNotaPorId(id);
            ResponseNotaDTO responseNotaDTO = notaService.criarResponseNotaDTO(notaEntity);
            log.info("GET /notas ---> Sucesso.");
            return ResponseEntity.status(HttpStatus.OK).body(responseNotaDTO);
        } catch (NotaNotFoundException e) {
            log.error("STATUS 404 ---> Recurso não encontrado ---> {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarNota(@PathVariable("id") Long id, @Valid @RequestBody RequestNotaDTO requestNotaDTO) {
        try {
            log.info("PUT /notas/{} ---> Chamada para o método.", id);
            CadastroEntity cadastroEntity = notaService.buscarCadastroId(requestNotaDTO);
            NotaEntity notaEntitySalvo = notaService.atualizarNota(id, requestNotaDTO);
            ResponseNotaDTO responseNotaDTO = notaService.criarResponseNotaDTO(notaEntitySalvo);
            log.info("PUT /notas/{} ---> Sucesso.", id);
            return ResponseEntity.status(HttpStatus.OK).body(responseNotaDTO);
        } catch (NotaNotFoundException | CadastroNotFoundException e) {
            log.error("STATUS 404 ---> Recurso não encontrado ---> {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarNota(@PathVariable("id") Long id) {
        try {
            log.info("DELETE /notas/{} ---> Chamada para o método.", id);
            notaService.deletarNota(id);
            log.info("DELETE /notas/{} ---> Sucesso.", id);
            return ResponseEntity.noContent().build();
        } catch (NotaNotFoundException e) {
            log.error("STATUS 404 ---> Recurso não encontrado ---> {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
