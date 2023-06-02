package br.com.hungry.infra.db.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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
    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 150, message = "O nome não pode passar de 150 caracteres")
    private String nome;

    @Column(name = "ds_centro_dist", length = 150)
    @Size(max = 150, message = "A descrição não pode passar de 150 caracteres")
    private String descricao;

    @Column(name = "ds_email", nullable = false, length = 150, unique = true)
    @NotBlank(message = "O e-mail é obrigatório")
    @Size(max = 150, message = "O e-mail não pode passar de 150 caracteres")
    private String email;

    @Column(name = "ds_capacidade", nullable = false, length = 10)
    @NotBlank(message = "A capacidade é obrigatória")
    @Size(max = 10, message = "A capacidade não pode passar de 10 caracteres")
    private String capacidade;

    @Column(name = "ds_armazenamento", nullable = false, length = 150)
    @NotBlank(message = "O armazenamento é obrigatório")
    @Size(max = 150, message = "O armazenamento não pode passar de 150 caracteres")
    private String armazenamento;

    @Column(name = "ds_funcionamento", nullable = false, length = 20)
    @NotBlank(message = "O funcionamento é obrigatório")
    @Size(max = 20, message = "O funcionamento não pode passar de 20 caracteres")
    private String funcionamento;

    @Column(name = "ds_ativo", nullable = false)
    @NotNull(message = "O ativo é obrigatório")
    private boolean ativo;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_endereco", nullable = false)
    @NotNull(message = "O endereço é obrigatório")
    @Valid
    private Endereco endereco;

    @OneToMany(mappedBy = "centroDistribuicao")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<Alimento> alimentos = new ArrayList<>();

}