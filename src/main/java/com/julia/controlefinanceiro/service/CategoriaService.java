package com.julia.controlefinanceiro.service;

import com.julia.controlefinanceiro.dto.CategoriaRequestDTO;
import com.julia.controlefinanceiro.dto.CategoriaResponseDTO;
import com.julia.controlefinanceiro.exception.CategoriaNotFoundException;
import com.julia.controlefinanceiro.model.Categoria;
import com.julia.controlefinanceiro.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {
    private final CategoriaRepository repository;

    public CategoriaService(CategoriaRepository repository) {
        this.repository = repository;
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

    public Categoria deletarCategoria(Long id) {
        Categoria categoria = repository.findById(id).orElseThrow(() ->
                new CategoriaNotFoundException("Categoria não encontrada."));
        repository.delete(categoria);
        return categoria;
    }
}
