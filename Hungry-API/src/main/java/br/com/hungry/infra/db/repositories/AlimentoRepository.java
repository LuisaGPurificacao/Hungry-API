package br.com.hungry.infra.db.repositories;

import br.com.hungry.infra.db.models.Alimento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlimentoRepository extends JpaRepository<Alimento, Long> {
}
