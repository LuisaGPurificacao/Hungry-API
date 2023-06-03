package br.com.hungry.app.controllers;

import br.com.hungry.domain.exceptions.RestAlreadyExistsException;
import br.com.hungry.domain.exceptions.RestNotFoundException;
import br.com.hungry.infra.db.models.Empresa;
import br.com.hungry.infra.db.repositories.EmpresaRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@RestController
@RequestMapping("/hungry/api/empresas")
public class EmpresaController {

    @Autowired
    private EmpresaRepository repository;

    @PostMapping
    public ResponseEntity<Empresa> adicionar(@RequestBody @Valid Empresa empresa, UriComponentsBuilder uriBuilder) {
        log.info("Adicionando a empresa: {}", empresa);
        if (repository.existsByCnpj(empresa.getCnpj()))
            throw new RestAlreadyExistsException("Uma empresa com esse CNPJ já está cadastrada no nosso sistema.");
        empresa = repository.save(empresa);
        var uri = uriBuilder.path("/hungry/api/empresas/{id}").buildAndExpand(empresa.getId()).toUri();
        return ResponseEntity.created(uri).body(empresa);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Empresa> atualizar(@PathVariable Long id, @RequestBody @Valid Empresa empresa) {
        log.info("Atualizando a empresa com id: {}", id);
        Empresa empresaAntiga = getEmpresa(id);
        empresa.setId(id);
        empresa.setCnpj(empresaAntiga.getCnpj());
        empresa = repository.save(empresa);
        return ResponseEntity.ok(empresa);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Empresa> listarPorId(@PathVariable Long id) {
        log.info("Procurando a empresa com id: {}", id);
        return ResponseEntity.ok(getEmpresa(id));
    }

    @DeleteMapping("/{id}")
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
