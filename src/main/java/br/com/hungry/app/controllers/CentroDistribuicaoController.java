package br.com.hungry.app.controllers;

import br.com.hungry.app.dtos.Credencial;
import br.com.hungry.domain.exceptions.RestAlreadyExistsException;
import br.com.hungry.domain.exceptions.RestNotFoundException;
import br.com.hungry.domain.services.TokenService;
import br.com.hungry.infra.db.models.CentroDistribuicao;
import br.com.hungry.infra.db.repositories.CentroDistribuicaoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/hungry/api/centros")
@Tag(name = "Centros de Distribuição")
public class CentroDistribuicaoController {

    @Autowired
    private CentroDistribuicaoRepository repository;

    @Autowired
    AuthenticationManager manager;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    TokenService tokenService;

    @GetMapping
    @SecurityRequirement(name = "bearer-key")
    @Operation(
            summary = "Listar todos os centros de distribuição",
            description = "Retorna os dados dos centros de distribuição com paginação, e podendo receber parâmetros para buscas"
    )
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
    @Tag(name = "auth")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Centro de distribuição cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos, a validação falhou"),
            @ApiResponse(responseCode = "409", description = "Centro de distribuição com esse e-mail já está cadastrado no nosso sistema")
    })
    public ResponseEntity<CentroDistribuicao> adicionar(@RequestBody @Valid CentroDistribuicao centroDistribuicao, UriComponentsBuilder uriBuilder) {
        log.info("Adicionando o centro de distribuição: {}", centroDistribuicao);
        if (repository.existsByEmail(centroDistribuicao.getEmail()))
            throw new RestAlreadyExistsException("Um centro de distribuição com esse e-mail já está cadastrado no nosso sistema.");
        centroDistribuicao.setSenha(encoder.encode(centroDistribuicao.getSenha()));
        centroDistribuicao = repository.save(centroDistribuicao);
        var uri = uriBuilder.path("/hungry/api/centroDistribuicaos/{id}").buildAndExpand(centroDistribuicao.getId()).toUri();
        return ResponseEntity.created(uri).body(centroDistribuicao);
    }

    @PostMapping("/login")
    @Tag(name = "auth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Centro de distribuição logado com sucesso"),
            @ApiResponse(responseCode = "403", description = "Dados inválidos, usuário não cadastrado no sistema"),
    })
    public ResponseEntity<Object> login(@RequestBody @Valid Credencial credencial) {
        manager.authenticate(credencial.toAuthentication());
        var token = tokenService.generateToken(credencial);
        return ResponseEntity.ok(token);
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "bearer-key")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Centro de distribuição atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos, a validação falhou"),
            @ApiResponse(responseCode = "404", description = "Centro de distribuição não encontrado")
    })
    public ResponseEntity<CentroDistribuicao> atualizar(@PathVariable Long id, @RequestBody @Valid CentroDistribuicao centroDistribuicao) {
        log.info("Atualizando o centro de distribuição com id: {}", id);
        CentroDistribuicao centroDistribuicaoAntigo = getCentroDistribuicao(id);
        centroDistribuicao.setId(id);
        centroDistribuicao.setEmail(centroDistribuicaoAntigo.getEmail());
        centroDistribuicao = repository.save(centroDistribuicao);
        return ResponseEntity.ok(centroDistribuicao);
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "bearer-key")
    @Operation(
            summary = "Detalhes do centro de distribuição",
            description = "Retorna os dados de um centro de distribuição passado pelo parâmetro de path id"
    )
    public ResponseEntity<CentroDistribuicao> listarPorId(@PathVariable Long id) {
        log.info("Procurando o centro de distribuição com id: {}", id);
        return ResponseEntity.ok(getCentroDistribuicao(id));
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearer-key")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Centro de distribuição removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Centro de distribuição não encontrado")
    })
    public ResponseEntity<CentroDistribuicao> remover(@PathVariable Long id) {
        log.info("Removendo o centro de distribuição com id: {}", id);
        repository.delete(getCentroDistribuicao(id));
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    @SecurityRequirement(name = "bearer-key")
    @Operation(
            summary = "Atualizar o status do centro de distribuição como ativo/inativo",
            description = "Atualiza o status do centro de distribuição para ATIVO caso esteja INATIVO, e se o status estiver INATIVO, ele é atualizado para ATIVO"
    )
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
