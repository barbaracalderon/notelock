package com.fmt.notelock.controllers;


import com.fmt.notelock.controllers.dtos.requests.RequestCadernoDTO;
import com.fmt.notelock.controllers.dtos.responses.ResponseCadastroDTO;
import com.fmt.notelock.controllers.dtos.responses.ResponseCadernoDTO;
import com.fmt.notelock.datasource.entities.CadastroEntity;
import com.fmt.notelock.datasource.entities.CadernoEntity;
import com.fmt.notelock.infra.exceptions.CadastroNotFoundException;
import com.fmt.notelock.infra.exceptions.CadernoNotFoundException;
import com.fmt.notelock.infra.exceptions.CadastroNotFoundException;

import com.fmt.notelock.services.CadernoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("cadernos")
public class CadernoController {

    @Autowired
    private CadernoService cadernoService;


    @PostMapping()
    public ResponseEntity<?> criarCaderno (@Valid @RequestBody RequestCadernoDTO requestCadernoDTO) {
            try {
                log.info("POST /cadernos ---> Chamada para o método.");
                CadastroEntity cadastroEntity = cadernoService.buscarCadastroId(requestCadernoDTO);
                CadernoEntity cadernoEntity = cadernoService.criarCadernoEntity(requestCadernoDTO);
                CadernoEntity cadernoEntitySalvo = cadernoService.criarCaderno(cadernoEntity);
                ResponseCadernoDTO responseCadastroDTO = cadernoService.criarResponseCadernoDTO(cadernoEntitySalvo);
                log.info("POST /cadernos ---> Sucesso.");
                return ResponseEntity.status(HttpStatus.CREATED).body(responseCadastroDTO);
            } catch (CadastroNotFoundException e) {
                log.error("STATUS 404 ---> Recurso não encontrado ---> {}", e.getMessage());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
        }

    @GetMapping()
    public ResponseEntity<?> listarCadernos() {
        log.info("GET /cadernos ---> Chamada para o método.");
        List<CadernoEntity> cadernoEntityList = cadernoService.listarCadernos();
        List<ResponseCadernoDTO> responseCadastroDTOsList = cadernoService.criarResponseCadernoDTO(cadernoEntityList);
        log.info("GET /cadernos ---> Sucesso.");
        return ResponseEntity.ok(responseCadastroDTOsList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarCadernoPorId(@PathVariable("id") Long id) {
        try {
            log.info("GET /cadernos ---> Chamada para o método.");
            CadernoEntity cadernoEntity = cadernoService.buscarCadernoPorId(id);
            ResponseCadernoDTO responseCadernoDTO = cadernoService.criarResponseCadernoDTO(cadernoEntity);
            log.info("GET /cadernos ---> Sucesso.");
            return ResponseEntity.status(HttpStatus.OK).body(responseCadernoDTO);
        } catch (CadernoNotFoundException e) {
            log.error("STATUS 404 ---> Recurso não encontrado ---> {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarCaderno(@PathVariable("id") Long id, @Valid @RequestBody RequestCadernoDTO requestCadernoDTO) {
        try {
            log.info("PUT /cadernos/{} ---> Chamada para o método.", id);
            CadastroEntity cadastroEntity = cadernoService.buscarCadastroId(requestCadernoDTO);
            CadernoEntity cadernoEntitySalvo = cadernoService.atualizarCaderno(id, requestCadernoDTO);
            ResponseCadernoDTO responseCadernoDTO = cadernoService.criarResponseCadernoDTO(cadernoEntitySalvo);
            log.info("PUT /cadernos/{} ---> Sucesso.", id);
            return ResponseEntity.status(HttpStatus.OK).body(responseCadernoDTO);
        } catch (CadernoNotFoundException | CadastroNotFoundException e) {
            log.error("STATUS 404 ---> Recurso não encontrado ---> {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarCaderno(@PathVariable("id") Long id) {
        try {
            log.info("DELETE /cadernos/{} ---> Chamada para o método.", id);
            cadernoService.deletarCaderno(id);
            log.info("DELETE /cadernos/{} ---> Sucesso.", id);
            return ResponseEntity.noContent().build();
        } catch (CadernoNotFoundException e) {
            log.error("STATUS 404 ---> Recurso não encontrado ---> {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
