package br.com.hungry.infra.db.repositories;

import br.com.hungry.infra.db.models.CentroDistribuicao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Optional;

public interface CentroDistribuicaoRepository extends JpaRepository<CentroDistribuicao, Long> {

    Optional<CentroDistribuicao> findByEmail(String email);

    @Query("select (count(c) > 0) from CentroDistribuicao c where upper(c.email) = upper(:email)")
    boolean existsByEmail(String email);

    @Query("""
            select c from CentroDistribuicao c inner join c.alimentos alimentos
            where upper(c.nome) like concat('%', upper(?1), '%') and upper(c.endereco.cidade) like concat('%', upper(?2), '%') and upper(c.endereco.bairro) like concat('%', upper(?3), '%') and upper(alimentos.nome) like concat('%', upper(?4), '%') and upper(alimentos.categoria) like concat('%', upper(?5), '%')""")
    List<CentroDistribuicao> search(String nome, String cidade, String bairro, String nomeAlimento, String categoriaAlimento);

}
