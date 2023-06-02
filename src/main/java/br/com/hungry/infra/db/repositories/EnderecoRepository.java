package br.com.hungry.infra.db.repositories;

import br.com.hungry.infra.db.models.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
}
