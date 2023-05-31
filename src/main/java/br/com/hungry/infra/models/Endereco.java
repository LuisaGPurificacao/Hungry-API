package br.com.hungry.infra.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @NotNull(message = "O CEP é obrigatório")
    private Integer cep;

    @Column(name = "nm_pais", nullable = false, length = 8)
    @NotBlank(message = "O país é obrigatório")
    @Size(max = 8, message = "O país não deve passar de 8 caracteres")
    private String pais;

    @Column(name = "nm_estado", nullable = false, length = 100)
    @NotBlank(message = "O estado é obrigatório")
    @Size(max = 100, message = "O estado não deve passar de 100 caracteres")
    private String estado;

    @Column(name = "nm_cidade", nullable = false, length = 150)
    @NotBlank(message = "A cidade é obrigatória")
    @Size(max = 150, message = "O estado não deve passar de 150 caracteres")
    private String cidade;

    @Column(name = "nm_bairro", nullable = false, length = 150)
    @NotBlank(message = "O bairro é obrigatório")
    @Size(max = 150, message = "O bairro não deve passar de 150 caracteres")
    private String bairro;

    @Column(name = "nm_logradouro", nullable = false, length = 150)
    @NotBlank(message = "O logradouro é obrigatório")
    @Size(max = 150, message = "O logradouro não deve passar de 150 caracteres")
    private String logradouro;

    @Column(name = "nr_rua", nullable = false, precision = 6)
    @NotNull(message = "O número é obrigatório")
    private Integer numero;

    @Column(name = "ds_complemento")
    @Size(max = 255, message = "O complemento não deve passar de 255 caracteres")
    private String complemento;

    @OneToOne(mappedBy = "endereco")
    @JsonIgnore
    private Empresa empresa;

    @OneToOne(mappedBy = "endereco")
    @JsonIgnore
    private CentroDistribuicao centroDistribuicao;

}