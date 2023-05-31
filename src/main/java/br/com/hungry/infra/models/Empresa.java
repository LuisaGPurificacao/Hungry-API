package br.com.hungry.infra.models;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "HUNGRY_T_EMPRESA", uniqueConstraints = {
        @UniqueConstraint(name = "un_t_empresa", columnNames = {"nr_cnpj"})
})
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_empresa")
    private Long id;

    @Column(name = "nm_empresa", nullable = false, length = 150)
    private String nome;

    @Column(name = "nm_fantasia", length = 150)
    private String nomeFantasia;

    @Column(name = "nr_cnpj", precision = 14, unique = true, updatable = false)
    private Long cnpj;

    @Column(name = "ds_email", length = 150)
    private String email;

    @Column(name = "ds_empresa")
    private String descricao;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_endereco", nullable = false)
    private Endereco endereco;

}
