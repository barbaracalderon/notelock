package com.fmt.notelock.controllers;

import com.fmt.notelock.controllers.dtos.requests.RequestLoginDTO;
import com.fmt.notelock.controllers.dtos.responses.ResponseLoginDTO;
import com.fmt.notelock.datasource.entities.CadastroEntity;
import com.fmt.notelock.services.TokenService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("login")
public class LoginController {

    @Autowired
    public AuthenticationManager authenticationManager;

    @Autowired
    public TokenService tokenService;

    @PostMapping()
    public ResponseEntity login(@Valid @RequestBody RequestLoginDTO body) {
        try{
            log.info("POST /login ---> Chamada para o método.");
            var usernamePassword = new UsernamePasswordAuthenticationToken(body.login(), body.senha());
            var auth = this.authenticationManager.authenticate(usernamePassword);
            var token = tokenService.generateToken((CadastroEntity) auth.getPrincipal());
            return ResponseEntity.ok(new ResponseLoginDTO(token));
        } catch (AuthenticationException e) {
            log.error("STATUS 401 ---> Acesso não autorizado ---> {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login ou senha inválidos.");
        }
    }

}
