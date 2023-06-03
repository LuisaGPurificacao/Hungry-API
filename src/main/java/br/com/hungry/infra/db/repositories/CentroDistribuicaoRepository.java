package br.com.hungry.infra.db.repositories;

import br.com.hungry.infra.db.models.CentroDistribuicao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;

import java.util.List;

public interface CentroDistribuicaoRepository extends JpaRepository<CentroDistribuicao, Long> {

    @Query("select (count(c) > 0) from CentroDistribuicao c where upper(c.email) = upper(:email)")
    boolean existsByEmail(String email);

    @Query("""
            select c from CentroDistribuicao c inner join c.alimentos alimentos
            where upper(c.nome) = upper(?1) and upper(c.endereco.cidade) = upper(?2) and upper(c.endereco.bairro) = upper(?3) and upper(alimentos.nome) = upper(?4) and upper(alimentos.categoria) = upper(?5)""")
    List<CentroDistribuicao> search(@Nullable String nome, @Nullable String cidade, @Nullable String bairro, @Nullable String nomeAlimento, @Nullable String categoriaAlimento);

}
