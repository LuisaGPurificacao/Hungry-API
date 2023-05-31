package br.com.hungry.infra.repositories;

import br.com.hungry.infra.models.Alimento;
import br.com.hungry.infra.models.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlimentoRepository extends JpaRepository<Alimento, Long> {
}
