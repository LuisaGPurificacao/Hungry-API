package br.com.hungry.infra.db.repositories;

import br.com.hungry.infra.db.models.CentroDistribuicao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CentroDistribuicaoRepository extends JpaRepository<CentroDistribuicao, Long> {

    boolean existsByEmail(String email);

}
