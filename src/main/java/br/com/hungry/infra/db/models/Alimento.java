package br.com.hungry.infra.db.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "HUNGRY_T_ALIMENTO")
public class Alimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_alimento")
    private Long id;

    @Column(name = "nm_alimento", nullable = false, length = 50)
    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 50, message = "O nome não deve passar de 50 caracteres")
    private String nome;

    @Column(name = "qtd_alimento", nullable = false, length = 15)
    @NotBlank(message = "A quantidade é obrigatória")
    @Size(max = 15, message = "A quantidade não deve passar de 15 caracteres")
    private String quantidade;

    @Column(name = "ds_categoria", nullable = false, length = 30)
    @NotBlank(message = "A categoria é obrigatória")
    @Size(max = 30, message = "A categoria não deve passar de 30 caracteres")
    private String categoria;

    @Column(name = "dt_validade", nullable = false)
    @NotNull(message = "A data de validade é obrigatória")
    @JsonProperty(value = "data_validade")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataValidade;

    @ManyToOne
    @JoinColumn(name = "id_centro_dist", nullable = false)
    @NotNull(message = "O centro de distribuição é obrigatório")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY, value = "centro_distribuicao")
    private CentroDistribuicao centroDistribuicao;

    @ManyToOne
    @JoinColumn(name = "id_empresa", nullable = false)
    @NotNull(message = "A empresa é obrigatória")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY, value = "empresa")
    private Empresa empresa;

}
