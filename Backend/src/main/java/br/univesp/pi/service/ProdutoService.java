package br.univesp.pi.service;

import br.univesp.pi.domain.dto.ProdutoCreateDTO;
import br.univesp.pi.domain.dto.ProdutoUpdateDTO;
import br.univesp.pi.domain.dto.response.ProdutoResponseDTO;

import java.util.List;

public interface ProdutoService {

    ProdutoResponseDTO salvarProduto(ProdutoCreateDTO produto);
    List<ProdutoResponseDTO> listarProdutos();
    ProdutoResponseDTO buscarProdutoPorCodigo(Long codigo);
    List<ProdutoResponseDTO> buscarPorNome(String nome);
    List<ProdutoResponseDTO> buscarPorDescricao(String descricao);
    List<ProdutoResponseDTO> buscarPorFamilia(Long codigoFamilia);
    ProdutoResponseDTO atualizarProduto(Long codigo, ProdutoUpdateDTO produto);
    void deletarProduto(Long codigo);
}
