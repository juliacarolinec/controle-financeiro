package com.julia.controlefinanceiro.controller;

import com.julia.controlefinanceiro.dto.SaldoResponseDTO;
import com.julia.controlefinanceiro.dto.TransacaoRequestDTO;
import com.julia.controlefinanceiro.dto.TransacaoResponseDTO;
import com.julia.controlefinanceiro.model.Tipo;
import com.julia.controlefinanceiro.model.Transacao;
import com.julia.controlefinanceiro.service.TransacaoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
public class TransacaoController {

    private final TransacaoService service;

    public TransacaoController(TransacaoService service) {
        this.service = service;
    }

    @GetMapping("/transacoes")
    public ResponseEntity<Page<Transacao>> listarTransacoes(Pageable pageable){
        Page<Transacao> transacoes = service.listarTransacoes(pageable);
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
    public ResponseEntity<TransacaoResponseDTO> adicionarNovaTransacao(@Valid @RequestBody TransacaoRequestDTO novaTransacao){
        TransacaoResponseDTO transacao = service.adicionarNovaTransacao(novaTransacao);

        return ResponseEntity.status(HttpStatus.CREATED).body(transacao);
    }

    @PutMapping("/transacoes/{id}")
    public ResponseEntity<TransacaoResponseDTO> editarTransacao(@PathVariable Long id,
                                    @Valid @RequestBody TransacaoRequestDTO transacaoAlterada){
        TransacaoResponseDTO transacao = service.editarTransacao(id, transacaoAlterada);
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

    @GetMapping("/transacoes/tipo/{tipo}")
    public List<Transacao> pesquisarPorTipo(@PathVariable Tipo tipo){
        return service.pesquisarPorTipo(tipo);
    }

    @GetMapping("/transacoes/data")
    public List<Transacao> pesquisarPorData(@RequestParam LocalDate data){
        return service.pesquisarPorData(data);
    }

    @GetMapping("/transacoes/periodo")
    public List<Transacao> pesquisarPorPeriodo(@RequestParam LocalDate inicio, @RequestParam LocalDate fim){
        return service.pesquisarPorPeriodo(inicio, fim);
    }

    @GetMapping("/transacoes/saldo")
    public SaldoResponseDTO calcularSaldo(){
        return service.calcularSaldo();
    }

    @GetMapping("/transacoes/saldo/periodo")
    public SaldoResponseDTO calcularSaldoPorPeriodo(@RequestParam LocalDate inicio,
                                                    @RequestParam LocalDate fim){
        return service.calcularSaldoPorPeriodo(inicio, fim);
    }
}
