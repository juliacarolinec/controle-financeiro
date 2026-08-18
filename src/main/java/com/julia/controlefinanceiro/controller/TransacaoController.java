package com.julia.controlefinanceiro.controller;

import com.julia.controlefinanceiro.model.Transacao;
import com.julia.controlefinanceiro.service.TransacaoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TransacaoController {

    private final TransacaoService service;

    public TransacaoController(TransacaoService service) {
        this.service = service;
    }

    @GetMapping("/transacoes")
    public List<Transacao> listarTransacoes(){
        return service.listarTransacoes();
    }

    @GetMapping("/transacoes/{id}")
    public Transacao buscarTransacao(@PathVariable Long id){
        return service.buscarTransacao(id);
    }

    @PostMapping("/transacoes")
    public Transacao adicionarNovaTransacao(@RequestBody Transacao novaTransacao){
        return service.adicionarNovaTransacao(novaTransacao);
    }

    @PutMapping("/transacoes/{id}")
    public Transacao editarTransacao(@PathVariable Long id,
                                     @RequestBody Transacao transacaoAlterada){
        return service.editarTransacao(id, transacaoAlterada);
    }

    @DeleteMapping("/transacoes/{id}")
    public Transacao deletarTransacao(@PathVariable Long id){
        return service.deletarTransacao(id);
    }
}
