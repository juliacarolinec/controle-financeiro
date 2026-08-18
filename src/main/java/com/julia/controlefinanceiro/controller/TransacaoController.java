package com.julia.controlefinanceiro.controller;

import com.julia.controlefinanceiro.model.Transacao;
import com.julia.controlefinanceiro.service.TransacaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TransacaoController {

    private final TransacaoService service;

    public TransacaoController(TransacaoService service) {
        this.service = service;
    }

    @GetMapping("/transacoes")
    public ResponseEntity<List<Transacao>> listarTransacoes(){
        List<Transacao> transacoes = service.listarTransacoes();
        if(!transacoes.isEmpty()){
            return ResponseEntity.ok(transacoes);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/transacoes/{id}")
    public ResponseEntity<Transacao> buscarTransacao(@PathVariable Long id){

        Transacao transacao = service.buscarTransacao(id);
        if(transacao!=null){
            return ResponseEntity.ok(transacao);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/transacoes")
    public ResponseEntity<Transacao> adicionarNovaTransacao(@RequestBody Transacao novaTransacao){
        Transacao transacao = service.adicionarNovaTransacao(novaTransacao);

        return ResponseEntity.status(HttpStatus.CREATED).body(transacao);
    }

    @PutMapping("/transacoes/{id}")
    public ResponseEntity<Transacao> editarTransacao(@PathVariable Long id,
                                     @RequestBody Transacao transacaoAlterada){
        Transacao transacao = service.editarTransacao(id, transacaoAlterada);
        if(transacao!=null){
            return ResponseEntity.ok(transacao);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/transacoes/{id}")
    public ResponseEntity<Transacao> deletarTransacao(@PathVariable Long id){
        Transacao transacao = service.deletarTransacao(id);
        if(transacao!=null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
