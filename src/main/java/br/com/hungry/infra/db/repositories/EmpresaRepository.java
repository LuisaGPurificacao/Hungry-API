package br.com.hungry.infra.db.repositories;

import br.com.hungry.infra.db.models.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

    @Query("select (count(e) > 0) from Empresa e where e.cnpj = :cnpj")
    boolean existsByCnpj(Long cnpj);

}
