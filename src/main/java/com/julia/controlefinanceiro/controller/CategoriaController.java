package com.julia.controlefinanceiro.controller;

import com.julia.controlefinanceiro.dto.CategoriaRequestDTO;
import com.julia.controlefinanceiro.dto.CategoriaResponseDTO;
import com.julia.controlefinanceiro.dto.SaldoResponseDTO;
import com.julia.controlefinanceiro.model.Categoria;
import com.julia.controlefinanceiro.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CategoriaController {
    private final CategoriaService service;

    public CategoriaController(CategoriaService service) {
        this.service = service;
    }

    @GetMapping("/categorias")
    public ResponseEntity<List<Categoria>> listarCategorias(){
       List<Categoria> categorias = service.listarCategorias();
        if(!categorias.isEmpty()){
            return ResponseEntity.ok(categorias);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/categorias/{id}")
    public ResponseEntity<Categoria> buscarCategoria(@PathVariable Long id){
        Categoria categoria = service.buscarCategoria(id);
        if(categoria!=null){
            return ResponseEntity.ok(categoria);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/categorias")
    public ResponseEntity<CategoriaResponseDTO> adicionarCategoria(@Valid @RequestBody CategoriaRequestDTO categoria){
        CategoriaResponseDTO novaCategoria = service.adicionarCategoria(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaCategoria);
    }

    @PutMapping("/categorias/{id}")
    public ResponseEntity<CategoriaResponseDTO> alterarCategoria(@PathVariable Long id,
                                      @Valid @RequestBody CategoriaRequestDTO categoriaAlterada){
        CategoriaResponseDTO categoria = service.alterarCategoria(id, categoriaAlterada);
        return ResponseEntity.ok(categoria);
    }

    @DeleteMapping("/categorias/{id}")
    public ResponseEntity<Void> deletarCategoria(@PathVariable Long id){
        service.deletarCategoria(id);
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/categorias/{id}/saldo")
    public ResponseEntity<SaldoResponseDTO> calcularSaldoPorId(@PathVariable Long id){
        SaldoResponseDTO saldo = service.calcularSaldoPorCategoria(id);
        return ResponseEntity.ok(saldo);
    }
}
