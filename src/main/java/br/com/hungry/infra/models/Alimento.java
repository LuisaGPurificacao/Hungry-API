package br.com.hungry.infra.models;

import jakarta.persistence.*;
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
    private String nome;

    @Column(name = "qtd_alimento", nullable = false, length = 15)
    private String quantidade;

    @Column(name = "ds_categoria", nullable = false, length = 30)
    private String categoria;

    @Column(name = "dt_validade", nullable = false)
    private LocalDate dataValidade;

}
