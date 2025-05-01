package br.univesp.pi.service;

import br.univesp.pi.domain.dto.ProdutoCreateDTO;
import br.univesp.pi.domain.dto.ProdutoUpdateDTO;
import br.univesp.pi.domain.model.Produto;

import java.util.List;

public interface ProdutoService {

    Produto salvarProduto(ProdutoCreateDTO produto);
    List<Produto> listarProdutos();
    Produto buscarProdutoPorCodigo(Long codigo);
    List<Produto> buscarPorNome(String nome);
    List<Produto> buscarPorDescricao(String descricao);
    List<Produto> buscarPorFamilia(Long codigoFamilia);
    Produto atualizarProduto(Long codigo, ProdutoUpdateDTO produto);
    void deletarProduto(Long codigo);
}
