package br.univesp.pi.service.impl;

import br.univesp.pi.domain.dto.ProdutoDTO;
import br.univesp.pi.domain.model.Familia;
import br.univesp.pi.domain.model.Fornecedor;
import br.univesp.pi.domain.model.Pessoa;
import br.univesp.pi.domain.model.Produto;
import br.univesp.pi.enumeration.CategoriaPessoa;
import br.univesp.pi.repository.FamiliaRepository;
import br.univesp.pi.repository.FornecedorRepository;
import br.univesp.pi.repository.ProdutoRepository;
import br.univesp.pi.service.ProdutoService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoServiceImpl implements ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @Autowired
    private FamiliaRepository familiaRepository;

    @Transactional
    @Override
    public Produto salvarProduto(ProdutoDTO produtoDTO) {

        Fornecedor fornecedor = fornecedorRepository.findByCpfOuCnpj(produtoDTO.getFornecedor())
                .orElseThrow(() -> new IllegalArgumentException("Fornecedor não encontrado com CPF/CNPJ: " + produtoDTO.getFornecedor()));

        Familia familia = null;
        if (produtoDTO.getFamilia() != null) {
            familia = familiaRepository.findById(produtoDTO.getFamilia())
                    .orElseThrow(() -> new IllegalArgumentException("Família não encontrada"));
        }

        Produto produto = new Produto();
        produto.setFamilia(familia);
        produto.setNome(produtoDTO.getNome());
        produto.setDescricao(produtoDTO.getDescricao());
        produto.setPreco(produtoDTO.getPreco());
        produto.setFornecedor(fornecedor);

        return produtoRepository.save(produto);
    }

    @Override
    public List<Produto> listarProdutos() {
        return produtoRepository.findAll();
    }

    @Override
    public Produto buscarProdutoPorCodigo(Long codigo) {
        return produtoRepository.findByCodigo(codigo).orElse(null);
    }

    @Transactional
    @Override
    public void deletarProduto(Long codigo) {
        produtoRepository.deleteById(codigo);
    }

    @Transactional
    @Override
    public Produto atualizarProduto(Long codigo, Produto produto) {
        Produto produtoExistente = produtoRepository.findById(codigo).orElse(null);
        if (produtoExistente != null) {
            produto.setCodigo(codigo);
            return produtoRepository.save(produto);
        }
        return null;
    }

    @Override
    public List<Produto> buscarPorNome(String nome) {
        return produtoRepository.findByNomeContainingIgnoreCase(nome);
    }

    @Override
    public List<Produto> buscarPorDescricao(String descricao) {
        return produtoRepository.findByDescricaoContainingIgnoreCase(descricao);
    }

    @Override
    public List<Produto> buscarPorFamilia(Long codigoFamilia) {
        return produtoRepository.findByFamiliaCodigo(codigoFamilia);
    }
}
