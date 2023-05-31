package br.com.hungry.infra.models;

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
@Table(name = "HUNGRY_T_EMPRESA", uniqueConstraints = {
        @UniqueConstraint(name = "un_t_empresa", columnNames = {"nr_cnpj"})
})
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_empresa")
    private Long id;

    @Column(name = "nm_empresa", nullable = false, length = 150)
    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 150, message = "O nome não pode passar de 150 caracteres")
    private String nome;

    @Column(name = "nm_fantasia", length = 150)
    @Size(max = 150, message = "O nome fantasia não pode passar de 150 caracteres")
    @JsonProperty(value = "nome_fantasia")
    private String nomeFantasia;

    @Column(name = "nr_cnpj", precision = 14, nullable = false, unique = true, updatable = false)
    @NotNull(message = "O CNPJ é obrigatório")
    private Long cnpj;

    @Column(name = "ds_email", length = 150)
    @Size(max = 150, message = "O e-mail não pode passar de 150 caracteres")
    private String email;

    @Column(name = "ds_empresa")
    @Size(max = 255, message = "A descrição não pode passar de 255 caracteres")
    private String descricao;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_endereco", nullable = false)
    @NotNull(message = "O endereço é obrigatório")
    @Valid
    private Endereco endereco;

    @OneToMany(mappedBy = "empresa")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<Alimento> alimentos = new ArrayList<>();

}
