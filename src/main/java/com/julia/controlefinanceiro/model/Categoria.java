package com.julia.controlefinanceiro.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(schema = "public")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "categoria_seq")
    @SequenceGenerator(
            name = "categoria_seq",
            sequenceName = "categoria_seq",
            allocationSize = 1
    )
    private Long id;
    @NotBlank
    private String nome;
}
