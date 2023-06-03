package br.com.hungry.infra.db.repositories;

import br.com.hungry.infra.db.models.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

    boolean existsByCnpj(Long cnpj);

}
