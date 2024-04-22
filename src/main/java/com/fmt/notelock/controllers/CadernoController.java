package com.fmt.notelock.controllers;


import com.fmt.notelock.controllers.dtos.requests.RequestCadernoDTO;
import com.fmt.notelock.controllers.dtos.responses.ResponseCadernoDTO;
import com.fmt.notelock.datasource.entities.CadastroEntity;
import com.fmt.notelock.datasource.entities.CadernoEntity;
import com.fmt.notelock.infra.exceptions.CadastroNotFoundException;
import com.fmt.notelock.infra.exceptions.CadernoNotFoundException;

import com.fmt.notelock.infra.exceptions.UnauthorizedAccessException;
import com.fmt.notelock.services.CadastroService;
import com.fmt.notelock.services.CadernoService;
import com.fmt.notelock.services.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("cadernos")
public class CadernoController {

    @Autowired
    private CadernoService cadernoService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private CadastroService cadastroService;


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
    public ResponseEntity<?> buscarCadernoPorId(@PathVariable("id") Long id, HttpServletRequest request) {
        try {
            log.info("GET /cadernos ---> Chamada para o método.");
            log.info("GET /cadernos ---> Validando token e cadastroId.");

            String token = tokenService.extractToken(request);
            String loginCadastro = tokenService.validateToken(token);
            CadastroEntity cadastroEntity = cadastroService.buscarCadastroPorLogin(loginCadastro);

            try {
                CadernoEntity cadernoEntity = cadernoService.buscarCadernoPorId(id);
                if (hasAccessPermission(cadastroEntity, cadernoEntity)) {
                    try {
                        ResponseCadernoDTO responseCadernoDTO = cadernoService.criarResponseCadernoDTO(cadernoEntity);
                        log.info("GET /cadernos ---> Sucesso.");
                        return ResponseEntity.status(HttpStatus.OK).body(responseCadernoDTO);
                    } catch (CadernoNotFoundException e) {
                        log.error("STATUS 404 ---> Recurso não encontrado ---> {}", e.getMessage());
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
                    }
                } else {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso não autorizado para este id: " + id);
                }
            } catch (CadernoNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Caderno não encontrado para este id: " + id);
            }
        }  catch (UnauthorizedAccessException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso não autorizado para este recurso.");
    }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarCaderno(@PathVariable("id") Long id, @Valid @RequestBody RequestCadernoDTO requestCadernoDTO, HttpServletRequest request) {
        try {
            log.info("GET /cadernos ---> Chamada para o método.");
            log.info("GET /cadernos ---> Validando token e cadastroId.");

            String token = tokenService.extractToken(request);
            String loginCadastro = tokenService.validateToken(token);
            CadastroEntity cadastroEntity = cadastroService.buscarCadastroPorLogin(loginCadastro);

            try {
                CadernoEntity cadernoEntity = cadernoService.buscarCadernoPorId(id);
                if (hasAccessPermission(cadastroEntity, cadernoEntity)) {
                    try {
                        log.info("PUT /cadernos/{} ---> Chamada para o método.", id);
                        CadernoEntity cadernoEntitySalvo = cadernoService.atualizarCaderno(id, requestCadernoDTO);
                        ResponseCadernoDTO responseCadernoDTO = cadernoService.criarResponseCadernoDTO(cadernoEntitySalvo);
                        log.info("PUT /cadernos/{} ---> Sucesso.", id);
                        return ResponseEntity.status(HttpStatus.OK).body(responseCadernoDTO);
                    } catch (CadernoNotFoundException | CadastroNotFoundException e) {
                        log.error("STATUS 404 ---> Recurso não encontrado ---> {}", e.getMessage());
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
                    }
                } else {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso não autorizado para este id: " + id);
                }
            } catch (CadernoNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Caderno não encontrado para este id: " + id);
            }
        }  catch (UnauthorizedAccessException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso não autorizado para este recurso.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarCaderno(@PathVariable("id") Long id)
    {
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

    private boolean hasAccessPermission(CadastroEntity cadastroEntity, CadernoEntity cadernoEntity) {
        return Objects.equals(cadastroEntity.getPapel(), "ADMIN") || cadernoEntity.getIdCadastro().equals(cadastroEntity.getId());
    }

}
