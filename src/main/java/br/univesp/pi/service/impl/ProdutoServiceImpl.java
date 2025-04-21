package br.univesp.pi.service.impl;

import br.univesp.pi.domain.model.Produto;
import br.univesp.pi.repository.ProdutoRepository;
import br.univesp.pi.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoServiceImpl implements ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Override
    public Produto salvarProduto(Produto produto) {
        return produtoRepository.save(produto);
    }

    @Override
    public List<Produto> listarProdutos() {
        return produtoRepository.findAll();
    }

    @Override
    public Produto buscarProdutoPorId(Long codigo) {
        return produtoRepository.findById(codigo).orElse(null);
    }

    @Override
    public void deletarProduto(Long codigo) {
        produtoRepository.deleteById(codigo);
    }

    @Override
    public Produto atualizarProduto(Long codigo, Produto produto) {
        Produto produtoExistente = produtoRepository.findById(codigo).orElse(null);
        if (produtoExistente != null) {
            produto.setCodigo(codigo);
            return produtoRepository.save(produto);
        }
        return null;
    }
}
