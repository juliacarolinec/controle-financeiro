package com.julia.controlefinanceiro.dto;

import com.julia.controlefinanceiro.model.Tipo;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TransacaoResponseDTO {
    private Long id;
    private String descricao;
    private BigDecimal valor;
    private Tipo tipo;
    private LocalDate data;
    private CategoriaResponseDTO categoria;

}
