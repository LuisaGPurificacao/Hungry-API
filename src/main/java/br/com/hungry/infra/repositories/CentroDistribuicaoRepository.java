package br.com.hungry.infra.repositories;

import br.com.hungry.infra.models.CentroDistribuicao;
import br.com.hungry.infra.models.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CentroDistribuicaoRepository extends JpaRepository<CentroDistribuicao, Long> {
}
