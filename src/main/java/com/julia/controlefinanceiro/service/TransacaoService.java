package com.julia.controlefinanceiro.service;

import com.julia.controlefinanceiro.model.Transacao;
import com.julia.controlefinanceiro.repository.TransacaoRepository;
import org.springframework.stereotype.Service;

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

    public Transacao adicionarNovaTransacao(Transacao novaTransacao){
        return repository.save(novaTransacao);
    }

    public Transacao editarTransacao(Long id,
                                     Transacao transacaoAlterada){
        Transacao transacao = repository.findById(id).orElse(null);
        if(transacao!=null){
            transacao.setData(transacaoAlterada.getData());
            transacao.setDescricao(transacaoAlterada.getDescricao());
            transacao.setTipo(transacaoAlterada.getTipo());
            transacao.setValor(transacaoAlterada.getValor());

            return repository.save(transacao);
        }
        return null;
    }

    public Transacao deletarTransacao(Long id){
        Transacao transacao = repository.findById(id).orElse(null);
        if(transacao!=null){
            repository.delete(transacao);
            return transacao;
        }
        return null;
    }
}
