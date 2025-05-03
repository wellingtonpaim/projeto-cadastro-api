package br.univesp.pi.controller;

import br.univesp.pi.domain.dto.ProdutoCreateDTO;
import br.univesp.pi.domain.dto.ProdutoUpdateDTO;
import br.univesp.pi.domain.dto.response.ApiResponse;
import br.univesp.pi.domain.dto.response.ProdutoResponseDTO;
import br.univesp.pi.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @PostMapping
    public ResponseEntity<ApiResponse<ProdutoResponseDTO>> salvarProduto(@Valid @RequestBody ProdutoCreateDTO produto) {
        ProdutoResponseDTO produtoSalvo = produtoService.salvarProduto(produto);
        return ResponseEntity.ok(new ApiResponse<>(true, "Produto salvo com sucesso", produtoSalvo));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProdutoResponseDTO>>> listarProdutos() {
        List<ProdutoResponseDTO> produtos = produtoService.listarProdutos();
        return ResponseEntity.ok(new ApiResponse<>(true, "Produtos listados com sucesso", produtos));
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<ApiResponse<ProdutoResponseDTO>> buscarProdutoPorCodigo(@PathVariable Long codigo) {
        ProdutoResponseDTO produto = produtoService.buscarProdutoPorCodigo(codigo);
        return ResponseEntity.ok(new ApiResponse<>(true, "Produto encontrado", produto));
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<ApiResponse<List<ProdutoResponseDTO>>> buscarPorNome(@PathVariable String nome) {
        List<ProdutoResponseDTO> produtos = produtoService.buscarPorNome(nome);
        return ResponseEntity.ok(new ApiResponse<>(true, "Produtos encontrados por nome", produtos));
    }

    @GetMapping("/descricao/{descricao}")
    public ResponseEntity<ApiResponse<List<ProdutoResponseDTO>>> buscarPorDescricao(@PathVariable String descricao) {
        List<ProdutoResponseDTO> produtos = produtoService.buscarPorDescricao(descricao);
        return ResponseEntity.ok(new ApiResponse<>(true, "Produtos encontrados por descrição", produtos));
    }

    @GetMapping("/familia/{codigoFamilia}")
    public ResponseEntity<ApiResponse<List<ProdutoResponseDTO>>> buscarPorFamilia(@PathVariable Long codigoFamilia) {
        List<ProdutoResponseDTO> produtos = produtoService.buscarPorFamilia(codigoFamilia);
        return ResponseEntity.ok(new ApiResponse<>(true, "Produtos encontrados por família", produtos));
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<ApiResponse<ProdutoResponseDTO>> atualizarProduto(
            @PathVariable Long codigo,
            @Valid @RequestBody ProdutoUpdateDTO produto) {
        ProdutoResponseDTO produtoAtualizado = produtoService.atualizarProduto(codigo, produto);
        return ResponseEntity.ok(new ApiResponse<>(true, "Produto atualizado com sucesso", produtoAtualizado));
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<ApiResponse<Void>> deletarProduto(@PathVariable Long codigo) {
        produtoService.deletarProduto(codigo);
        return ResponseEntity.ok(new ApiResponse<>(true, "Produto deletado com sucesso", null));
    }
}
