package br.univesp.pi.controller;

import br.univesp.pi.domain.dto.ProdutoCreateDTO;
import br.univesp.pi.domain.dto.ProdutoUpdateDTO;
import br.univesp.pi.domain.dto.response.ProdutoResponseDTO;
import br.univesp.pi.domain.model.Produto;
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
    public ResponseEntity<ProdutoResponseDTO> salvarProduto(@Valid @RequestBody ProdutoCreateDTO produto) {
        ProdutoResponseDTO produtoSalvo = produtoService.salvarProduto(produto);
        return ResponseEntity.ok(produtoSalvo);
    }

    @GetMapping
    public ResponseEntity<List<ProdutoResponseDTO>> listarProdutos() {
        List<ProdutoResponseDTO> produtos = produtoService.listarProdutos();
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<ProdutoResponseDTO> buscarProdutoPorCodigo(@PathVariable Long codigo) {
        ProdutoResponseDTO produto = produtoService.buscarProdutoPorCodigo(codigo);
        return ResponseEntity.ok(produto);
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<ProdutoResponseDTO>> buscarPorNome(@PathVariable String nome) {
        return ResponseEntity.ok(produtoService.buscarPorNome(nome));
    }

    @GetMapping("/descricao/{descricao}")
    public ResponseEntity<List<ProdutoResponseDTO>> buscarPorDescricao(@PathVariable String descricao) {
        return ResponseEntity.ok(produtoService.buscarPorDescricao(descricao));
    }

    @GetMapping("/familia/{codigoFamilia}")
    public ResponseEntity<List<ProdutoResponseDTO>> buscarPorFamilia(@PathVariable Long codigoFamilia) {
        return ResponseEntity.ok(produtoService.buscarPorFamilia(codigoFamilia));
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<ProdutoResponseDTO> atualizarProduto(@PathVariable Long codigo, @Valid @RequestBody ProdutoUpdateDTO produto) {
        ProdutoResponseDTO produtoAtualizado = produtoService.atualizarProduto(codigo, produto);
        return ResponseEntity.ok(produtoAtualizado);
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> deletarProduto(@PathVariable Long codigo) {
        produtoService.deletarProduto(codigo);
        return ResponseEntity.noContent().build();
    }
}
