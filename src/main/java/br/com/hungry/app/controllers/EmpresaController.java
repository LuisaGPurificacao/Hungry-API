package br.com.hungry.app.controllers;

import br.com.hungry.domain.exceptions.RestAlreadyExistsException;
import br.com.hungry.domain.exceptions.RestNotFoundException;
import br.com.hungry.infra.db.models.Empresa;
import br.com.hungry.infra.db.repositories.EmpresaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/hungry/api/empresas")
@Tag(name = "Empresas")
public class EmpresaController {

    @Autowired
    private EmpresaRepository repository;

    @Autowired
    PasswordEncoder encoder;

    @PostMapping
    @Tag(name = "auth")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Empresa cadastrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos, a validação falhou"),
            @ApiResponse(responseCode = "409", description = "Empresa com esse CNPJ já está cadastrada no nosso sistema")
    })
    public ResponseEntity<Empresa> adicionar(@RequestBody @Valid Empresa empresa, UriComponentsBuilder uriBuilder) {
        log.info("Adicionando a empresa: {}", empresa);
        if (repository.existsByCnpj(empresa.getCnpj()))
            throw new RestAlreadyExistsException("Uma empresa com esse CNPJ já está cadastrada no nosso sistema.");
        empresa.setSenha(encoder.encode(empresa.getSenha()));
        empresa = repository.save(empresa);
        var uri = uriBuilder.path("/hungry/api/empresas/{id}").buildAndExpand(empresa.getId()).toUri();
        return ResponseEntity.created(uri).body(empresa);
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "bearer-key")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Empresa atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos, a validação falhou"),
            @ApiResponse(responseCode = "404", description = "Empresa não encontrada")
    })
    public ResponseEntity<Empresa> atualizar(@PathVariable Long id, @RequestBody @Valid Empresa empresa) {
        log.info("Atualizando a empresa com id: {}", id);
        Empresa empresaAntiga = getEmpresa(id);
        empresa.setId(id);
        empresa.setCnpj(empresaAntiga.getCnpj());
        empresa.setSenha(encoder.encode(empresa.getSenha()));
        empresa = repository.save(empresa);
        return ResponseEntity.ok(empresa);
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "bearer-key")
    @Operation(
            summary = "Detalhes da empresa",
            description = "Retorna os dados de uma empresa passada pelo parâmetro de path id"
    )
    public ResponseEntity<Empresa> listarPorId(@PathVariable Long id) {
        log.info("Procurando a empresa com id: {}", id);
        return ResponseEntity.ok(getEmpresa(id));
    }

    @GetMapping()
    @SecurityRequirement(name = "bearer-key")
    @Operation(
            summary = "Retornar uma empresa por e-mail",
            description = "Retorna os dados de uma empresa passada pelo parâmetro de query e-mail"
    )
    public ResponseEntity<Empresa> listarPorEmail(@RequestParam String email) {
        log.info("Procurando a empresa com e-mail: {}", email);
        Optional<Empresa> optionalEmpresa = repository.findByEmail(email);
        if (optionalEmpresa.isEmpty())
            throw new RestNotFoundException("A empresa com esse e-mail não foi encontrada.");
        return ResponseEntity.ok(optionalEmpresa.get());
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearer-key")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Empresa removida com sucesso"),
            @ApiResponse(responseCode = "404", description = "Empresa não encontrada")
    })
    public ResponseEntity<Empresa> remover(@PathVariable Long id) {
        log.info("Removendo a empresa com id: {}", id);
        Empresa empresa = getEmpresa(id);
        empresa.getAlimentos().forEach(a -> a.setEmpresa(null));
        repository.delete(empresa);
        return ResponseEntity.noContent().build();
    }

    private Empresa getEmpresa(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RestNotFoundException("A empresa não foi encontrada."));
    }


}
