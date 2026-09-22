package com.julia.controlefinanceiro.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(schema = "public")
@EntityListeners(AuditingEntityListener.class)
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transacao_seq")
    @SequenceGenerator(
            name = "transacao_seq",
            sequenceName = "transacao_seq",
            allocationSize = 1
    )
    private Long id;
    @NotBlank
    private String descricao;
    @Positive
    @NotNull
    private BigDecimal valor;
    @Enumerated(EnumType.STRING)
    @NotNull
    private Tipo tipo;
    @NotNull
    private LocalDate data;
    @ManyToOne
    @JoinColumn(name="categoria_id")
    private Categoria categoria;
    @CreatedDate
    @Column(name="data_cadastro")
    private LocalDateTime criadoEm;
    @LastModifiedDate
    @Column(name="data_atualizacao")
    private LocalDateTime atualizadoEm;
    public Transacao() {
    }

}
