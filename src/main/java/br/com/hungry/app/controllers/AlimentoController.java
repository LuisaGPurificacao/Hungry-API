package br.com.hungry.app.controllers;

import br.com.hungry.domain.exceptions.RestNotFoundException;
import br.com.hungry.infra.db.models.Alimento;
import br.com.hungry.infra.db.repositories.AlimentoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@RestController
@RequestMapping("/hungry/api/alimentos")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Alimentos")
public class AlimentoController {

    @Autowired
    private AlimentoRepository repository;

    @PostMapping
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Alimento cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos, a validação falhou")
    })
    public ResponseEntity<Alimento> adicionar(@RequestBody @Valid Alimento alimento, UriComponentsBuilder uriBuilder) {
        log.info("Adicionando o alimento: {}", alimento);
        alimento = repository.save(alimento);
        var uri = uriBuilder.path("/hungry/api/alimentos/{id}").buildAndExpand(alimento.getId()).toUri();
        return ResponseEntity.created(uri).body(alimento);
    }

    @PutMapping("/{id}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Alimento atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos, a validação falhou"),
            @ApiResponse(responseCode = "404", description = "Alimento não encontrado")
    })
    public ResponseEntity<Alimento> atualizar(@PathVariable Long id, @RequestBody @Valid Alimento alimento) {
        log.info("Atualizando o alimento com id: {}", id);
        getAlimento(id);
        alimento.setId(id);
        alimento = repository.save(alimento);
        return ResponseEntity.ok(alimento);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Detalhes do alimento",
            description = "Retorna os dados de um alimento passado pelo parâmetro de path id"
    )
    public ResponseEntity<Alimento> listarPorId(@PathVariable Long id) {
        log.info("Procurando o alimento com id: {}", id);
        return ResponseEntity.ok(getAlimento(id));
    }

    @DeleteMapping("/{id}")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Alimento removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Alimento não encontrado")
    })
    public ResponseEntity<Alimento> remover(@PathVariable Long id) {
        log.info("Removendo o alimento com id: {}", id);
        repository.delete(getAlimento(id));
        return ResponseEntity.noContent().build();
    }

    private Alimento getAlimento(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RestNotFoundException("O alimento não foi encontrado."));
    }

}
