package com.julia.controlefinanceiro.service;

import com.julia.controlefinanceiro.dto.CategoriaRequestDTO;
import com.julia.controlefinanceiro.dto.CategoriaResponseDTO;
import com.julia.controlefinanceiro.dto.SaldoResponseDTO;
import com.julia.controlefinanceiro.exception.CategoriaNotFoundException;
import com.julia.controlefinanceiro.model.Categoria;
import com.julia.controlefinanceiro.model.Tipo;
import com.julia.controlefinanceiro.model.Transacao;
import com.julia.controlefinanceiro.repository.CategoriaRepository;
import com.julia.controlefinanceiro.repository.TransacaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {
    private final CategoriaRepository repository;
    private final TransacaoRepository transacaoRepository;

    public CategoriaService(CategoriaRepository repository, TransacaoRepository transacaoRepository) {
        this.repository = repository;
        this.transacaoRepository = transacaoRepository;
    }


    public List<Categoria> listarCategorias() {
        return repository.findAll();
    }

    public Categoria buscarCategoria(Long id) {
        return repository.findById(id).orElseThrow(() -> new CategoriaNotFoundException("Categoria não encontrada"));
    }

    public CategoriaResponseDTO adicionarCategoria(CategoriaRequestDTO novaCategoria) {
        if (repository.existsByNomeIgnoreCase(novaCategoria.getNome())) {
            throw new IllegalArgumentException("Categoria já cadastrada.");
        }

        Categoria categoria = new Categoria();
        categoria.setNome(novaCategoria.getNome());

        Categoria salva = repository.save(categoria);

        CategoriaResponseDTO response = new CategoriaResponseDTO();
        response.setId(salva.getId());
        response.setNome(salva.getNome());

        return response;
    }

    public CategoriaResponseDTO alterarCategoria(Long id,
                                                 CategoriaRequestDTO categoriaAlterada) {
        Categoria categoria = repository.findById(id).orElseThrow(() ->
                new CategoriaNotFoundException("Categoria não encontrada."));

        if (repository.existsByNomeIgnoreCaseAndIdNot(
                categoriaAlterada.getNome(), id)) {

            throw new IllegalArgumentException("Categoria já cadastrada.");
        }


        categoria.setNome(categoriaAlterada.getNome());
        Categoria atualizada = repository.save(categoria);

        CategoriaResponseDTO response = new CategoriaResponseDTO();

        response.setNome(atualizada.getNome());
        response.setId(atualizada.getId());

        return response;

    }

    public void deletarCategoria(Long id) {
        Categoria categoria = repository.findById(id).orElseThrow(() ->
                new CategoriaNotFoundException("Categoria não encontrada."));
        if(transacaoRepository.existsByCategoriaId(id)){
            throw new IllegalArgumentException("Não é possível excluir uma categoria que possui transações.");
        }
        repository.delete(categoria);
    }

    public SaldoResponseDTO calcularSaldoPorCategoria(Long categoriaId){
        Categoria categoria = repository.findById(categoriaId)
                .orElseThrow(() ->
                        new CategoriaNotFoundException("Categoria não encontrada.")
                );
        List<Transacao> transacoes =
                transacaoRepository.findByCategoriaId(categoriaId);

        double totalReceitas = 0;
        double totalDespesas = 0;

        for(Transacao transacao : transacoes){
            if(transacao.getTipo() == Tipo.RECEITA){
                totalReceitas += transacao.getValor();
            }
            if(transacao.getTipo() == Tipo.DESPESA){
                totalDespesas += transacao.getValor();
            }
        }
        double saldo = totalReceitas - totalDespesas;

        SaldoResponseDTO response = new SaldoResponseDTO();

        response.setReceitas(totalReceitas);
        response.setDespesas(totalDespesas);
        response.setSaldo(saldo);

        return response;
    }
}
