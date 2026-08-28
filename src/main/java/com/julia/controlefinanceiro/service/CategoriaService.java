package com.julia.controlefinanceiro.service;

import com.julia.controlefinanceiro.dto.CategoriaRequestDTO;
import com.julia.controlefinanceiro.dto.CategoriaResponseDTO;
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

    public List<Categoria> listarCategorias(){
        return repository.findAll();
    }

    public Categoria buscarCategoria(Long id){
        return repository.findById(id).orElse(null);
    }

    public CategoriaResponseDTO adicionarCategoria(CategoriaRequestDTO novaCategoria){
        Categoria categoria = new Categoria();
        categoria.setNome(novaCategoria.getNome());

        Categoria salva = repository.save(categoria);

        CategoriaResponseDTO response = new CategoriaResponseDTO();
        response.setId(salva.getId());
        response.setNome(salva.getNome());

        return response;
    }

    public CategoriaResponseDTO alterarCategoria(Long id,
                                      CategoriaRequestDTO categoriaAlterada){
        Categoria categoria = repository.findById(id).orElse(null);
        if(categoria!=null){
            categoria.setNome(categoriaAlterada.getNome());
            Categoria atualizada = repository.save(categoria);

            CategoriaResponseDTO response = new CategoriaResponseDTO();

            response.setNome(atualizada.getNome());
            response.setId(atualizada.getId());

            return response;
        }
        return null;
    }

    public Categoria deletarCategoria(Long id){
        Categoria categoria = repository.findById(id).orElse(null);
        if(categoria!=null){
            repository.delete(categoria);
        }
        return categoria;
    }
}
