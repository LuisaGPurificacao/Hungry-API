package br.com.hungry.app.controllers;

import br.com.hungry.app.dtos.Credencial;
import br.com.hungry.app.dtos.Token;
import br.com.hungry.domain.exceptions.RestNotFoundException;
import br.com.hungry.domain.services.TokenService;
import br.com.hungry.infra.db.models.Empresa;
import br.com.hungry.infra.db.repositories.CentroDistribuicaoRepository;
import br.com.hungry.infra.db.repositories.EmpresaRepository;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/hungry/api/login")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "auth")
public class LoginController {

    @Autowired
    AuthenticationManager manager;

    @Autowired
    TokenService tokenService;

    @Autowired
    CentroDistribuicaoRepository centroRepository;

    @Autowired
    EmpresaRepository empresaRepository;

    @Autowired
    PasswordEncoder encoder;

    @PostMapping
    @Tag(name = "auth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário logado com sucesso"),
            @ApiResponse(responseCode = "403", description = "Dados inválidos, usuário não cadastrado no sistema"),
    })
    public ResponseEntity<Object> login(@RequestBody @Valid Credencial credencial) {
        Token token = null;
        Optional<Empresa> optionalEmpresa = empresaRepository.findByEmail(credencial.email());
        if (centroRepository.findByEmail(credencial.email()).isPresent()) {
            log.info("Logando como centro de distribuição: {}", credencial.email());
            manager.authenticate(credencial.toAuthentication());
            token = tokenService.generateToken(credencial, "CENTRO");
        } else if (optionalEmpresa.isPresent() && encoder.matches(credencial.senha(), optionalEmpresa.get().getSenha())) {
            log.info("Logando como empresa: {}", credencial.email());
            token = tokenService.generateToken(credencial, "EMPRESA");
        } else {
            throw new RestNotFoundException("Nenhum usuário encontrado. Cheque para ver se as informações estão corretas.");
        }
        return ResponseEntity.ok(token);
    }


}
