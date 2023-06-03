package br.com.hungry.app.controllers;

import br.com.hungry.domain.exceptions.RestAlreadyExistsException;
import br.com.hungry.domain.exceptions.RestNotFoundException;
import br.com.hungry.infra.db.models.CentroDistribuicao;
import br.com.hungry.infra.db.repositories.CentroDistribuicaoRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/hungry/api/centros")
public class CentroDistribuicaoController {

    @Autowired
    private CentroDistribuicaoRepository repository;

    @GetMapping
    public ResponseEntity<Page<CentroDistribuicao>> index(@RequestParam(required = false) String nome,
                                                          @RequestParam(required = false) String cidade,
                                                          @RequestParam(required = false) String bairro,
                                                          @RequestParam(required = false) String nomeAlimento,
                                                          @RequestParam(required = false) String categoriaAlimento,
                                                          @PageableDefault(size = 5, sort = "nome", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<CentroDistribuicao> centros = null;
        if (nome == null && cidade == null && bairro == null && nomeAlimento == null && categoriaAlimento == null)
            centros = repository.findAll(pageable);
        else {
            List<CentroDistribuicao> centrosList = repository.search(nome, cidade, bairro, nomeAlimento, categoriaAlimento);
            centros = new PageImpl<CentroDistribuicao>(centrosList, pageable, centrosList.size());
        }
        return ResponseEntity.ok(centros);
    }

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

    @PatchMapping("/{id}")
    public ResponseEntity<CentroDistribuicao> atualizarStatus(@PathVariable Long id) {
        log.info("Atualizando o status do centro de distribuição com id: {}", id);
        CentroDistribuicao centroDistribuicao = getCentroDistribuicao(id);
        centroDistribuicao.setId(id);
        centroDistribuicao.setAtivo(!centroDistribuicao.isAtivo());
        centroDistribuicao = repository.save(centroDistribuicao);
        return ResponseEntity.ok(centroDistribuicao);
    }

    private CentroDistribuicao getCentroDistribuicao(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RestNotFoundException("O centro de distribuição não foi encontrado."));
    }


}
