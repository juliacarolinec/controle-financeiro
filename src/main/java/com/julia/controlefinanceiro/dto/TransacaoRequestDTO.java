package com.julia.controlefinanceiro.dto;

import com.julia.controlefinanceiro.model.Tipo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TransacaoRequestDTO {

    @NotBlank
    private String descricao;
    @Positive
    @NotNull
    private BigDecimal valor;
    @NotNull
    private Tipo tipo;
    @NotNull
    private LocalDate data;
    @NotNull
    private Long categoriaId;

}
