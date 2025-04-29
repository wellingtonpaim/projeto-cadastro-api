package br.univesp.pi.service;

import br.univesp.pi.domain.dto.ProdutoDTO;
import br.univesp.pi.domain.model.Produto;

import java.util.List;

public interface ProdutoService {

    Produto salvarProduto(ProdutoDTO produto);
    List<Produto> listarProdutos();
    Produto buscarProdutoPorCodigo(Long codigo);
    void deletarProduto(Long codigo);
    Produto atualizarProduto(Long codigo, Produto produto);
    List<Produto> buscarPorNome(String nome);
    List<Produto> buscarPorDescricao(String descricao);
    List<Produto> buscarPorFamilia(Long codigoFamilia);
}
