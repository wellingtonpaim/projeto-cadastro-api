package br.univesp.pi.service;

import br.univesp.pi.domain.model.Produto;

import java.util.List;

public interface ProdutoService {

    Produto salvarProduto(Produto produto);
    List<Produto> listarProdutos();
    Produto buscarProdutoPorId(Long codigo);
    void deletarProduto(Long codigo);
    Produto atualizarProduto(Long codigo, Produto produto);
}
