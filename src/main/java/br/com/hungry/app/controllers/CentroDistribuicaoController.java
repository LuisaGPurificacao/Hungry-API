package br.com.hungry.app.controllers;

import br.com.hungry.domain.exceptions.RestAlreadyExistsException;
import br.com.hungry.domain.exceptions.RestNotFoundException;
import br.com.hungry.infra.db.models.CentroDistribuicao;
import br.com.hungry.infra.db.repositories.CentroDistribuicaoRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@RestController
@RequestMapping("/hungry/api/centros")
public class CentroDistribuicaoController {

    @Autowired
    private CentroDistribuicaoRepository repository;

    @PostMapping
    public ResponseEntity<CentroDistribuicao> adicionar(@RequestBody @Valid CentroDistribuicao centroDistribuicao, UriComponentsBuilder uriBuilder) {
        log.info("Adicionando o centro de distribuição: {}", centroDistribuicao);
        if (repository.existsByEmail(centroDistribuicao.getEmail()))
            throw new RestAlreadyExistsException("Um centro de distribuição com esse e-mail já está cadastrado no nosso sistema.");
        centroDistribuicao = repository.save(centroDistribuicao);
        var uri = uriBuilder.path("/hungry/api/centroDistribuicaos/{id}").buildAndExpand(centroDistribuicao.getId()).toUri();
        return ResponseEntity.created(uri).body(centroDistribuicao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CentroDistribuicao> atualizar(@PathVariable Long id, @RequestBody @Valid CentroDistribuicao centroDistribuicao) {
        log.info("Atualizando o centro de distribuição com id: {}", id);
        CentroDistribuicao centroDistribuicaoAntigo = getCentroDistribuicao(id);
        centroDistribuicao.setId(id);
        centroDistribuicao.setEmail(centroDistribuicaoAntigo.getEmail());
        centroDistribuicao = repository.save(centroDistribuicao);
        return ResponseEntity.ok(centroDistribuicao);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CentroDistribuicao> listarPorId(@PathVariable Long id) {
        log.info("Procurando o centro de distribuição com id: {}", id);
        return ResponseEntity.ok(getCentroDistribuicao(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CentroDistribuicao> remover(@PathVariable Long id) {
        log.info("Removendo o centro de distribuição com id: {}", id);
        repository.delete(getCentroDistribuicao(id));
        return ResponseEntity.noContent().build();
    }

    private CentroDistribuicao getCentroDistribuicao(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RestNotFoundException("O centro de distribuição não foi encontrado."));
    }


}
