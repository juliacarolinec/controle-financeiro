package com.julia.controlefinanceiro.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SaldoResponseDTO {
    private BigDecimal receitas;
    private BigDecimal despesas;
    private BigDecimal saldo;

}
