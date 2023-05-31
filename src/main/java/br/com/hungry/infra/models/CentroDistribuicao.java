package br.com.hungry.infra.models;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "HUNGRY_T_CENTRO_DIST", uniqueConstraints = {
        @UniqueConstraint(name = "un_t_centro_dist", columnNames = {"ds_email"})
})
public class CentroDistribuicao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_centro_dist")
    private Long id;

    @Column(name = "nm_centro_dist", nullable = false, length = 150)
    private String nome;

    @Column(name = "ds_centro_dist", length = 150)
    private String descricao;

    @Column(name = "ds_email", nullable = false, length = 150, unique = true)
    private String email;

    @Column(name = "ds_capacidade", nullable = false, length = 10)
    private String capacidade;

    @Column(name = "ds_armazenamento", nullable = false, length = 150)
    private String armazenamento;

    @Column(name = "ds_funcionamento", nullable = false, length = 20)
    private String funcionamento;

    @Column(name = "ds_ativo", nullable = false)
    private boolean ativo;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_endereco", nullable = false)
    private Endereco endereco;

}