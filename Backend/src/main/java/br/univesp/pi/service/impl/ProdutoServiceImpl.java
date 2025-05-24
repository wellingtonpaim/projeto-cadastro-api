package br.univesp.pi.service.impl;

import br.univesp.pi.domain.dto.ProdutoCreateDTO;
import br.univesp.pi.domain.dto.ProdutoUpdateDTO;
import br.univesp.pi.domain.dto.response.ProdutoResponseDTO;
import br.univesp.pi.domain.model.Familia;
import br.univesp.pi.domain.model.Fornecedor;
import br.univesp.pi.domain.model.Produto;
import br.univesp.pi.exception.ApiIllegalArgumentException;
import br.univesp.pi.repository.FamiliaRepository;
import br.univesp.pi.repository.FornecedorRepository;
import br.univesp.pi.repository.ProdutoRepository;
import br.univesp.pi.service.ProdutoService;
import br.univesp.pi.util.MapperUtil;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
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

    @Autowired
    private MapperUtil mapperUtil;

    @Transactional
    @Override
    public ProdutoResponseDTO salvarProduto(@Valid ProdutoCreateDTO dto) {

        Fornecedor fornecedor = null;
        if (dto.getFornecedor() != null) {
            fornecedor = fornecedorRepository.findByCpfOuCnpj(dto.getFornecedor())
                    .orElseThrow(() -> new ApiIllegalArgumentException(
                            "Não existe fornecedor cadastrado com o CPF/CNPJ: " + dto.getFornecedor(),
                            "Fornecedor",
                            "cpfCnpj",
                            dto.getDescricao(),
                            HttpStatus.NOT_FOUND
                    ));
        }

        Familia familia = familiaRepository.findById(dto.getFamilia())
                    .orElseThrow(() -> new ApiIllegalArgumentException(
                            "Família de produtos não encontrada",
                            "Família",
                            "codigo",
                            dto.getFamilia(),
                            HttpStatus.NOT_FOUND
                    ));

        Produto produto = new Produto();
        produto.setFamilia(familia);
        produto.setNome(dto.getNome());
        produto.setDescricao(dto.getDescricao());
        produto.setPreco(dto.getPreco());
        produto.setFornecedor(fornecedor);

        Produto saved = produtoRepository.save(produto);
        return mapperUtil.map(saved, ProdutoResponseDTO.class);
    }

    @Override
    public List<ProdutoResponseDTO> listarProdutos() {
        List<Produto> produtos = produtoRepository.findAll();
        return mapperUtil.mapList(produtos, ProdutoResponseDTO.class);
    }

    @Override
    public ProdutoResponseDTO buscarProdutoPorCodigo(Long codigo) {
        Produto produto = produtoRepository.findByCodigo(codigo)
                .orElseThrow(() -> new ApiIllegalArgumentException(
                    "Produto não encontrado com código: " + codigo,
                    "Produto",
                    "codigo",
                    String.valueOf(codigo),
                    HttpStatus.NOT_FOUND
            ));

        return mapperUtil.map(produto, ProdutoResponseDTO.class);
    }

    @Override
    public List<ProdutoResponseDTO> buscarPorNome(String nome) {
        List<Produto> produtos = produtoRepository.findByNomeContainingIgnoreCase(nome);
        if (produtos == null || produtos.isEmpty()) {
            throw new ApiIllegalArgumentException(
                    "Nenhum produto encontrado com nome contendo: " + nome,
                    "Produto",
                    "nome",
                    nome,
                    HttpStatus.NOT_FOUND
            );
        }
        return mapperUtil.mapList(produtos, ProdutoResponseDTO.class);
    }

    @Override
    public List<ProdutoResponseDTO> buscarPorDescricao(String descricao) {
        List<Produto> produtos = produtoRepository.findByDescricaoContainingIgnoreCase(descricao);
        if (produtos == null || produtos.isEmpty()) {
            throw new ApiIllegalArgumentException(
                    "Nenhum produto encontrado com descrição contendo: " + descricao,
                    "Produto",
                    "descricao",
                    descricao,
                    HttpStatus.NOT_FOUND
            );
        }
        return mapperUtil.mapList(produtos, ProdutoResponseDTO.class);
    }

    @Override
    public List<ProdutoResponseDTO> buscarPorFamilia(Long codigoFamilia) {
        List<Produto> produtos = produtoRepository.findByFamiliaCodigo(codigoFamilia);
        if (produtos == null || produtos.isEmpty()) {
            throw new ApiIllegalArgumentException(
                    "Nenhum produto encontrado para a família com código: " + codigoFamilia,
                    "Produto",
                    "codigoFamilia",
                    String.valueOf(codigoFamilia),
                    HttpStatus.NOT_FOUND
            );
        }
        return mapperUtil.mapList(produtos, ProdutoResponseDTO.class);
    }

    @Transactional
    @Override
    public ProdutoResponseDTO atualizarProduto(Long codigo, ProdutoUpdateDTO produtoCreateDTO) {
        Produto produtoExistente = produtoRepository.findById(codigo)
                .orElseThrow(() -> new ApiIllegalArgumentException(
                        "Produto não encontrado com código: " + codigo,
                        "Produto",
                        "codigo",
                        codigo,
                        HttpStatus.NOT_FOUND
                ));

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
                    .orElseThrow(() -> new ApiIllegalArgumentException(
                            "Fornecedor não encontrado com CPF/CNPJ: " + produtoCreateDTO.getFornecedor(),
                            "Fornecedor",
                            "cpfCnpj",
                            produtoCreateDTO.getFornecedor(),
                            HttpStatus.NOT_FOUND
                    ));
            produtoExistente.setFornecedor(fornecedor);
        }

        if (produtoCreateDTO.getFamilia() != null) {
            Familia familia = familiaRepository.findById(produtoCreateDTO.getFamilia())
                    .orElseThrow(() -> new ApiIllegalArgumentException(
                            "Família não encontrada",
                            "Família",
                            "codigo",
                            produtoCreateDTO.getFamilia(),
                            HttpStatus.NOT_FOUND
                    ));
            produtoExistente.setFamilia(familia);
        }

        Produto saved = produtoRepository.save(produtoExistente);
        return mapperUtil.map(saved, ProdutoResponseDTO.class);
    }

    @Transactional
    @Override
    public void deletarProduto(Long codigo) {
        if (!produtoRepository.existsById(codigo)) {
            throw new ApiIllegalArgumentException(
                    "Produto não encontrado com código: " + codigo,
                    "Produto",
                    "codigo",
                    codigo,
                    HttpStatus.NOT_FOUND
            );
        }
        produtoRepository.deleteById(codigo);
    }
}
