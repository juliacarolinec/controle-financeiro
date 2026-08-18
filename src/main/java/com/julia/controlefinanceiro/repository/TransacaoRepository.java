package com.julia.controlefinanceiro.repository;

import com.julia.controlefinanceiro.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
}
