package com.julia.controlefinanceiro.repository;

import com.julia.controlefinanceiro.model.Tipo;
import com.julia.controlefinanceiro.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
    List<Transacao> findByTipo(Tipo tipo);
    List<Transacao> findByData(LocalDate data);
    List<Transacao> findByDataBetween(LocalDate inicio, LocalDate fim);
}
