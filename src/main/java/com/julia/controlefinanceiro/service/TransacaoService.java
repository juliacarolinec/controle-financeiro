package com.julia.controlefinanceiro.service;

import com.julia.controlefinanceiro.dto.SaldoResponseDTO;
import com.julia.controlefinanceiro.dto.TransacaoRequestDTO;
import com.julia.controlefinanceiro.dto.TransacaoResponseDTO;
import com.julia.controlefinanceiro.model.Tipo;
import com.julia.controlefinanceiro.model.Transacao;
import com.julia.controlefinanceiro.repository.TransacaoRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class TransacaoService {

    private final TransacaoRepository repository;

    public TransacaoService(TransacaoRepository repository) {
        this.repository = repository;
    }

    public List<Transacao> listarTransacoes(){
        return repository.findAll();
    }

    public Transacao buscarTransacao(Long id){
        return repository.findById(id).orElse(null);
    }

    public TransacaoResponseDTO adicionarNovaTransacao(TransacaoRequestDTO novaTransacao) {

        Transacao transacao = new Transacao();

        transacao.setDescricao(novaTransacao.getDescricao());
        transacao.setValor(novaTransacao.getValor());
        transacao.setTipo(novaTransacao.getTipo());
        transacao.setData(novaTransacao.getData());

        Transacao salva = repository.save(transacao);

        TransacaoResponseDTO response = new TransacaoResponseDTO();

        response.setId(salva.getId());
        response.setDescricao(salva.getDescricao());
        response.setValor(salva.getValor());
        response.setTipo(salva.getTipo());
        response.setData(salva.getData());

        return response;
    }

    public TransacaoResponseDTO editarTransacao(
            Long id,
            TransacaoRequestDTO transacaoAlterada) {

        Transacao transacao = repository.findById(id).orElse(null);

        if (transacao != null) {

            transacao.setData(transacaoAlterada.getData());
            transacao.setDescricao(transacaoAlterada.getDescricao());
            transacao.setTipo(transacaoAlterada.getTipo());
            transacao.setValor(transacaoAlterada.getValor());

            Transacao atualizada = repository.save(transacao);

            TransacaoResponseDTO response = new TransacaoResponseDTO();

            response.setId(atualizada.getId());
            response.setDescricao(atualizada.getDescricao());
            response.setValor(atualizada.getValor());
            response.setTipo(atualizada.getTipo());
            response.setData(atualizada.getData());

            return response;
        }

        return null;
    }

    public Transacao deletarTransacao(Long id){
        Transacao transacao = repository.findById(id).orElse(null);
        if(transacao != null){
            repository.delete(transacao);
            return transacao;
        }
        return null;
    }

    public List<Transacao> pesquisarPorTipo(Tipo tipo){
        return repository.findByTipo(tipo);
    }

    public List<Transacao> pesquisarPorData(LocalDate data){
        return repository.findByData(data);
    }

    public SaldoResponseDTO calcularSaldo(){
        List<Transacao> receitas = repository.findByTipo(Tipo.RECEITA);
        List<Transacao> despesas = repository.findByTipo(Tipo.DESPESA);
        double totalReceitas = 0;
        double totalDespesas = 0;

        for(Transacao transacao : receitas){
            totalReceitas += transacao.getValor();
        }

        for(Transacao transacao : despesas){
            totalDespesas += transacao.getValor();
        }

        double saldo = totalReceitas - totalDespesas;

        SaldoResponseDTO response = new SaldoResponseDTO();

        response.setReceitas(totalReceitas);
        response.setDespesas(totalDespesas);
        response.setSaldo(saldo);

        return response;
    }
}