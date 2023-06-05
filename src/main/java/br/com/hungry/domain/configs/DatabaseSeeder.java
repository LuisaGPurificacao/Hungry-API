package br.com.hungry.domain.configs;

import br.com.hungry.infra.db.models.Alimento;
import br.com.hungry.infra.db.models.CentroDistribuicao;
import br.com.hungry.infra.db.models.Empresa;
import br.com.hungry.infra.db.models.Endereco;
import br.com.hungry.infra.db.repositories.AlimentoRepository;
import br.com.hungry.infra.db.repositories.CentroDistribuicaoRepository;
import br.com.hungry.infra.db.repositories.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Configuration
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    EmpresaRepository empresaRepository;

    @Autowired
    CentroDistribuicaoRepository centroDistribuicaoRepository;

    @Autowired
    AlimentoRepository alimentoRepository;

    @Autowired
    PasswordEncoder encoder;

    @Override
    public void run(String... args) throws Exception {

        Empresa empresa1 = Empresa.builder()
                .nome("Agronegócio Co.")
                .nomeFantasia("Agronegócio")
                .cnpj(10923118823012L)
                .email("agronegocio@hotmail.com")
                .senha(encoder.encode("AgroNegociou"))
                .descricao("Empresa de agronegócio para distribuir alimentos")
                .endereco(Endereco.builder()
                        .cep(12911003)
                        .pais("Brasil").estado("SP").cidade("São Paulo")
                        .bairro("Vila Mariana").logradouro("Rua Tutóia").numero(1200)
                        .complemento("Do lado da farmácia")
                        .build())
                .build();
        Empresa empresa2 = Empresa.builder()
                .nome("Fazenda Animais Co.")
                .nomeFantasia("Fazenda Animais")
                .cnpj(98923132923055L)
                .email("fazenda_animais123@gmail.com")
                .senha(encoder.encode("AnimaisFaz"))
                .endereco(Endereco.builder()
                        .cep(20112033)
                        .pais("Brasil").estado("SP").cidade("São Paulo")
                        .bairro("Santana").logradouro("Rua Voluntários").numero(2511)
                        .build())
                .build();
        Empresa empresa3 = Empresa.builder()
                .nome("Fazenda Paulo")
                .cnpj(80773145823032L)
                .email("fazenda.paulo@gmail.com")
                .senha(encoder.encode("Frutas2004"))
                .descricao("Fazenda do Paulo de frutas")
                .endereco(Endereco.builder()
                        .cep(94532888)
                        .pais("Brasil").estado("SP").cidade("São Paulo")
                        .bairro("Butantan").logradouro("Rua Raposo").numero(9432)
                        .complemento("Nome da empresa no prédio")
                        .build())
                .build();

        empresaRepository.saveAll(List.of(empresa1, empresa2, empresa3));

        CentroDistribuicao centroDistribuicao1 = CentroDistribuicao.builder()
                .nome("Armazém do Zé")
                .email("armaze@hotmail.com")
                .descricao("Armazém do Zé - Armazé. Armazenamos e distribuímos alimentos")
                .capacidade("500kg")
                .armazenamento("231kg")
                .funcionamento("Aberto das 9:00 às 19:00 - segunda à sexta")
                .ativo(true)
                .senha(encoder.encode("ARMAZE1000"))
                .endereco(Endereco.builder()
                        .cep(45594222)
                        .pais("Brasil").estado("SP").cidade("São Paulo")
                        .bairro("Centro").logradouro("Avenida Paulista").numero(290)
                        .build())
                .build();

        CentroDistribuicao centroDistribuicao2 = CentroDistribuicao.builder()
                .nome("FooDDepósito")
                .email("fooddeposito@yahoo.com.br")
                .capacidade("790kg")
                .armazenamento("569kg")
                .funcionamento("Aberto das 8:00 às 20:00 - segunda à sábado")
                .ativo(true)
                .senha(encoder.encode("mandaqui820"))
                .endereco(Endereco.builder()
                        .cep(23221003)
                        .pais("Brasil").estado("SP").cidade("São Paulo")
                        .bairro("Mandaqui").logradouro("Avenida Santa Inês").numero(905)
                        .build())
                .build();

        CentroDistribuicao centroDistribuicao3 = CentroDistribuicao.builder()
                .nome("Lykke Centro de Distribuição")
                .email("lykke@gmail.com")
                .descricao("Centro de Distribuição de alimentos ♥ Lykke")
                .capacidade("900kg")
                .armazenamento("602kg")
                .funcionamento("Aberto das 7:30 às 19:30 - todos os dias da semana")
                .senha(encoder.encode("lykke123"))
                .ativo(true)
                .endereco(Endereco.builder()
                        .cep(45594222)
                        .pais("Brasil").estado("SP").cidade("São Paulo")
                        .bairro("Jardins").logradouro("Rua França").numero(204)
                        .complemento("Prédio Lykke")
                        .build())
                .build();

        centroDistribuicaoRepository.saveAll(List.of(centroDistribuicao1, centroDistribuicao2, centroDistribuicao3));

        Alimento alimento1 = Alimento.builder()
                .nome("Maçã")
                .quantidade("3kg")
                .categoria("Frutas")
                .dataValidade(LocalDate.now().plus(2, ChronoUnit.MONTHS))
                .centroDistribuicao(centroDistribuicao1)
                .empresa(empresa2)
                .build();

        Alimento alimento2 = Alimento.builder()
                .nome("Banana")
                .quantidade("5kg")
                .categoria("Frutas")
                .dataValidade(LocalDate.now().plus(2, ChronoUnit.MONTHS))
                .centroDistribuicao(centroDistribuicao2)
                .empresa(empresa3)
                .build();

        Alimento alimento3 = Alimento.builder()
                .nome("Morango")
                .quantidade("1kg")
                .categoria("Frutas")
                .dataValidade(LocalDate.now().plus(1, ChronoUnit.MONTHS))
                .centroDistribuicao(centroDistribuicao3)
                .empresa(empresa2)
                .build();

        Alimento alimento4 = Alimento.builder()
                .nome("Pão Francês")
                .quantidade("10kg")
                .categoria("Pães")
                .dataValidade(LocalDate.now().plus(7, ChronoUnit.DAYS))
                .centroDistribuicao(centroDistribuicao3)
                .empresa(empresa1)
                .build();

        Alimento alimento5 = Alimento.builder()
                .nome("Pão de forma")
                .quantidade("7.5kg")
                .categoria("Pães")
                .dataValidade(LocalDate.now().plus(1, ChronoUnit.MONTHS))
                .centroDistribuicao(centroDistribuicao2)
                .empresa(empresa1)
                .build();

        Alimento alimento6 = Alimento.builder()
                .nome("Pão Parmesão")
                .quantidade("3kg")
                .categoria("Pães")
                .dataValidade(LocalDate.now().plus(10, ChronoUnit.DAYS))
                .centroDistribuicao(centroDistribuicao1)
                .empresa(empresa3)
                .build();

        alimentoRepository.saveAll(List.of(alimento1, alimento2, alimento3, alimento4, alimento5, alimento6));

    }

}
