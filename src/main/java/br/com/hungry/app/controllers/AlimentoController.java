package br.com.hungry.app.controllers;

import br.com.hungry.domain.exceptions.RestNotFoundException;
import br.com.hungry.infra.db.models.Alimento;
import br.com.hungry.infra.db.repositories.AlimentoRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@RestController
@RequestMapping("/hungry/api/alimentos")
public class AlimentoController {

    @Autowired
    private AlimentoRepository repository;

    @PostMapping
    public ResponseEntity<Alimento> adicionar(@RequestBody @Valid Alimento alimento, UriComponentsBuilder uriBuilder) {
        log.info("Adicionando o alimento: {}", alimento);
        alimento = repository.save(alimento);
        var uri = uriBuilder.path("/hungry/api/alimentos/{id}").buildAndExpand(alimento.getId()).toUri();
        return ResponseEntity.created(uri).body(alimento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Alimento> atualizar(@PathVariable Long id, @RequestBody @Valid Alimento alimento) {
        log.info("Atualizando o alimento com id: {}", id);
        getAlimento(id);
        alimento.setId(id);
        alimento = repository.save(alimento);
        return ResponseEntity.ok(alimento);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Alimento> listarPorId(@PathVariable Long id) {
        log.info("Procurando o alimento com id: {}", id);
        return ResponseEntity.ok(getAlimento(id));
    }

    @DeleteMapping("/{id}")
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
