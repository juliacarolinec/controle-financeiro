package com.julia.controlefinanceiro.service;

import com.julia.controlefinanceiro.dto.CategoriaResponseDTO;
import com.julia.controlefinanceiro.dto.SaldoResponseDTO;
import com.julia.controlefinanceiro.dto.TransacaoRequestDTO;
import com.julia.controlefinanceiro.dto.TransacaoResponseDTO;
import com.julia.controlefinanceiro.exception.CategoriaNotFoundException;
import com.julia.controlefinanceiro.exception.TransacaoNotFoundException;
import com.julia.controlefinanceiro.model.Categoria;
import com.julia.controlefinanceiro.model.Tipo;
import com.julia.controlefinanceiro.model.Transacao;
import com.julia.controlefinanceiro.repository.CategoriaRepository;
import com.julia.controlefinanceiro.repository.TransacaoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class TransacaoService {

    private final TransacaoRepository repository;

    private final CategoriaRepository categoriaRepository;

    public TransacaoService(TransacaoRepository repository, CategoriaRepository categoriaRepository) {
        this.repository = repository;
        this.categoriaRepository = categoriaRepository;
    }

    public Page<TransacaoResponseDTO> listarTransacoes(Pageable pageable) {
        Page<Transacao> transacoes = repository.findAll(pageable);

        return transacoes.map(transacao -> {

            TransacaoResponseDTO response = new TransacaoResponseDTO();

            response.setId(transacao.getId());
            response.setDescricao(transacao.getDescricao());
            response.setValor(transacao.getValor());
            response.setTipo(transacao.getTipo());
            response.setData(transacao.getData());

            CategoriaResponseDTO categoriaResponse = new CategoriaResponseDTO();
            categoriaResponse.setId(transacao.getCategoria().getId());
            categoriaResponse.setNome(transacao.getCategoria().getNome());

            response.setCategoria(categoriaResponse);

            return response;
        });
    }

    public TransacaoResponseDTO buscarTransacao(Long id) {
        Transacao transacao = repository.findById(id).orElseThrow(() -> new TransacaoNotFoundException("Transação não encontrada."));

        TransacaoResponseDTO response = new TransacaoResponseDTO();

        response.setId(transacao.getId());
        response.setDescricao(transacao.getDescricao());
        response.setValor(transacao.getValor());
        response.setTipo(transacao.getTipo());
        response.setData(transacao.getData());

        CategoriaResponseDTO categoriaResponse = new CategoriaResponseDTO();
        categoriaResponse.setId(transacao.getCategoria().getId());
        categoriaResponse.setNome(transacao.getCategoria().getNome());

        response.setCategoria(categoriaResponse);

        return response;
    }

    public TransacaoResponseDTO adicionarNovaTransacao(TransacaoRequestDTO novaTransacao) {

        Transacao transacao = new Transacao();

        transacao.setDescricao(novaTransacao.getDescricao());
        transacao.setValor(novaTransacao.getValor());
        transacao.setTipo(novaTransacao.getTipo());
        transacao.setData(novaTransacao.getData());
        Categoria categoria = categoriaRepository
                .findById(novaTransacao.getCategoriaId())
                .orElseThrow(() -> new CategoriaNotFoundException("Categoria não encontrada."));
        transacao.setCategoria(categoria);

        Transacao salva = repository.save(transacao);

        TransacaoResponseDTO response = new TransacaoResponseDTO();
        response.setId(salva.getId());
        response.setDescricao(salva.getDescricao());
        response.setValor(salva.getValor());
        response.setTipo(salva.getTipo());
        response.setData(salva.getData());

        CategoriaResponseDTO categoriaResponse = new CategoriaResponseDTO();
        categoriaResponse.setId(salva.getCategoria().getId());
        categoriaResponse.setNome(salva.getCategoria().getNome());

        response.setCategoria(categoriaResponse);
        return response;
    }

    public TransacaoResponseDTO editarTransacao(
            Long id,
            TransacaoRequestDTO transacaoAlterada) {

        Transacao transacao = repository.findById(id).orElseThrow(() -> new TransacaoNotFoundException("Transação não encontrada."));

        transacao.setData(transacaoAlterada.getData());
        transacao.setDescricao(transacaoAlterada.getDescricao());
        transacao.setTipo(transacaoAlterada.getTipo());
        transacao.setValor(transacaoAlterada.getValor());
        Categoria categoria = categoriaRepository.findById(transacaoAlterada.getCategoriaId())
                .orElseThrow(() -> new CategoriaNotFoundException("Categoria não encontrada"));
        transacao.setCategoria(categoria);

        Transacao atualizada = repository.save(transacao);

        TransacaoResponseDTO response = new TransacaoResponseDTO();

        response.setId(atualizada.getId());
        response.setDescricao(atualizada.getDescricao());
        response.setValor(atualizada.getValor());
        response.setTipo(atualizada.getTipo());
        response.setData(atualizada.getData());

        CategoriaResponseDTO categoriaResponse = new CategoriaResponseDTO();

        categoriaResponse.setId(atualizada.getCategoria().getId());
        categoriaResponse.setNome(atualizada.getCategoria().getNome());

        response.setCategoria(categoriaResponse);
        return response;
    }

    public Transacao deletarTransacao(Long id) {
        Transacao transacao = repository.findById(id).orElseThrow(() -> new TransacaoNotFoundException("Transação não encontrada."));
        repository.delete(transacao);
        return transacao;

    }

    public List<Transacao> pesquisarPorTipo(Tipo tipo) {
        return repository.findByTipo(tipo);
    }

    public List<Transacao> pesquisarPorData(LocalDate data) {
        return repository.findByData(data);
    }

    public List<Transacao> pesquisarPorPeriodo(LocalDate inicio, LocalDate fim) {
        if (inicio.isAfter(fim)) {
            throw new IllegalArgumentException(
                    "A data de início não pode ser maior que a data de fim."
            );
        }
        return repository.findByDataBetween(inicio, fim);
    }

    public SaldoResponseDTO calcularSaldo() {
        List<Transacao> receitas = repository.findByTipo(Tipo.RECEITA);
        List<Transacao> despesas = repository.findByTipo(Tipo.DESPESA);
        BigDecimal totalReceitas = BigDecimal.ZERO;
        BigDecimal totalDespesas = BigDecimal.ZERO;

        for (Transacao transacao : receitas) {
            totalReceitas = totalReceitas.add(transacao.getValor());
        }

        for (Transacao transacao : despesas) {
            totalDespesas = totalDespesas.add(transacao.getValor());
        }

        BigDecimal saldo =  totalReceitas.subtract(totalDespesas);

        SaldoResponseDTO response = new SaldoResponseDTO();

        response.setReceitas(totalReceitas);
        response.setDespesas(totalDespesas);
        response.setSaldo(saldo);

        return response;
    }

    public SaldoResponseDTO calcularSaldoPorPeriodo(LocalDate inicio, LocalDate fim) {
        if (inicio.isAfter(fim)) {
            throw new IllegalArgumentException(
                    "A data de início não pode ser maior que a data de fim."
            );
        }
        List<Transacao> transacoes = repository.findByDataBetween(inicio, fim);
        BigDecimal totalReceitas = BigDecimal.ZERO;
        BigDecimal totalDespesas = BigDecimal.ZERO;

        for (Transacao transacao : transacoes) {
            if (transacao.getTipo() == Tipo.RECEITA) {
                totalReceitas = totalReceitas.add(transacao.getValor());
            }

            if (transacao.getTipo() == Tipo.DESPESA) {
                totalDespesas = totalDespesas.add(transacao.getValor());
            }
        }

        BigDecimal saldo = totalReceitas.subtract(totalDespesas);

        SaldoResponseDTO response = new SaldoResponseDTO();
        response.setReceitas(totalReceitas);
        response.setDespesas(totalDespesas);
        response.setSaldo(saldo);

        return response;
    }

    public Page<Transacao> pesquisarPorTipoEPeriodo(Tipo tipo,
                                                    LocalDate inicio,
                                                    LocalDate fim,
                                                    Pageable pageable) {
        if (inicio.isAfter(fim)) {
            throw new IllegalArgumentException(
                    "A data de início não pode ser maior que a data de fim."
            );
        }
        return repository.findByTipoAndDataBetween(tipo, inicio, fim, pageable);
    }
}