package br.com.hungry.infra.repositories;

import br.com.hungry.infra.models.Empresa;
import br.com.hungry.infra.models.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
}
