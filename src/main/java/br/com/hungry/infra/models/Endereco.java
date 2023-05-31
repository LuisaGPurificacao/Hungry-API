package br.com.hungry.infra.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "HUNGRY_T_ENDERECO")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_endereco")
    private Long id;

    @Column(name = "nr_cep", nullable = false, precision = 8)
    private Integer cep;

    @Column(name = "nm_pais", nullable = false, length = 8)
    private String pais;

    @Column(name = "nm_estado", nullable = false, length = 100)
    private String estado;

    @Column(name = "nm_cidade", nullable = false, length = 150)
    private String cidade;

    @Column(name = "nm_bairro", nullable = false, length = 150)
    private String bairro;

    @Column(name = "nm_logradouro", nullable = false, length = 150)
    private String logradouro;

    @Column(name = "nr_rua", nullable = false, precision = 6)
    private String numero;

    @Column(name = "ds_complemento")
    private String complemento;

    @OneToOne(mappedBy = "endereco")
    @JsonIgnore
    private Empresa empresa;

    @OneToOne(mappedBy = "endereco")
    @JsonIgnore
    private CentroDistribuicao centroDistribuicao;

}