package br.univesp.pi.service.impl;

import br.univesp.pi.domain.dto.ProdutoCreateDTO;
import br.univesp.pi.domain.dto.ProdutoUpdateDTO;
import br.univesp.pi.domain.model.Familia;
import br.univesp.pi.domain.model.Fornecedor;
import br.univesp.pi.domain.model.Produto;
import br.univesp.pi.repository.FamiliaRepository;
import br.univesp.pi.repository.FornecedorRepository;
import br.univesp.pi.repository.ProdutoRepository;
import br.univesp.pi.service.ProdutoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
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
    public Produto salvarProduto(@Valid ProdutoCreateDTO produtoCreateDTO) {

        Fornecedor fornecedor = fornecedorRepository.findByCpfOuCnpj(produtoCreateDTO.getFornecedor())
                .orElseThrow(() -> new IllegalArgumentException("Fornecedor não encontrado com CPF/CNPJ: " + produtoCreateDTO.getFornecedor()));

        Familia familia = null;
        if (produtoCreateDTO.getFamilia() != null) {
            familia = familiaRepository.findById(produtoCreateDTO.getFamilia())
                    .orElseThrow(() -> new IllegalArgumentException("Família de produtos não encontrada"));
        }

        Produto produto = new Produto();
        produto.setFamilia(familia);
        produto.setNome(produtoCreateDTO.getNome());
        produto.setDescricao(produtoCreateDTO.getDescricao());
        produto.setPreco(produtoCreateDTO.getPreco());
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

    @Transactional
    @Override
    public Produto atualizarProduto(Long codigo, ProdutoUpdateDTO produtoCreateDTO) {
        Produto produtoExistente = produtoRepository.findById(codigo).orElseThrow(
                () -> new IllegalArgumentException("Produto não encontrado com código: " + codigo)
        );

        if (produtoCreateDTO.getNome() != null) {
            produtoExistente.setNome(produtoCreateDTO.getNome());
        }

        if (produtoCreateDTO.getDescricao() != null) {
            produtoExistente.setDescricao(produtoCreateDTO.getDescricao());
        }

        if (produtoCreateDTO.getPreco() != null) {
            produtoExistente.setPreco(produtoCreateDTO.getPreco());
        }

        if (produtoCreateDTO.getFornecedor() != null) {
            Fornecedor fornecedor = fornecedorRepository.findByCpfOuCnpj(produtoCreateDTO.getFornecedor())
                    .orElseThrow(() -> new IllegalArgumentException("Fornecedor não encontrado com CPF/CNPJ: " + produtoCreateDTO.getFornecedor()));
            produtoExistente.setFornecedor(fornecedor);
        }

        if (produtoCreateDTO.getFamilia() != null) {
            Familia familia = familiaRepository.findById(produtoCreateDTO.getFamilia())
                    .orElseThrow(() -> new IllegalArgumentException("Família não encontrada"));
            produtoExistente.setFamilia(familia);
        }

        return produtoRepository.save(produtoExistente);
    }

    @Transactional
    @Override
    public void deletarProduto(Long codigo) {
        produtoRepository.deleteById(codigo);
    }

}
