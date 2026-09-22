package com.julia.controlefinanceiro.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoriaRequestDTO {
    @NotBlank
    private String nome;
}
