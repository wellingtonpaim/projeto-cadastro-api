package br.univesp.pi.controller;

import br.univesp.pi.domain.dto.ProdutoDTO;
import br.univesp.pi.domain.model.Produto;
import br.univesp.pi.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @PostMapping
    public ResponseEntity<Produto> salvarProduto(@Valid @RequestBody ProdutoDTO produto) {
        Produto produtoSalvo = produtoService.salvarProduto(produto);
        return ResponseEntity.ok(produtoSalvo);
    }

    @GetMapping
    public ResponseEntity<List<Produto>> listarProdutos() {
        List<Produto> produtos = produtoService.listarProdutos();
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Produto> buscarProdutoPorCodigo(@PathVariable Long codigo) {
        Produto produto = produtoService.buscarProdutoPorCodigo(codigo);
        return ResponseEntity.ok(produto);
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> deletarProduto(@PathVariable Long codigo) {
        produtoService.deletarProduto(codigo);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<Produto> atualizarProduto(@PathVariable Long codigo, @RequestBody Produto produto) {
        Produto produtoAtualizado = produtoService.atualizarProduto(codigo, produto);
        return ResponseEntity.ok(produtoAtualizado);
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<Produto>> buscarPorNome(@PathVariable String nome) {
        return ResponseEntity.ok(produtoService.buscarPorNome(nome));
    }

    @GetMapping("/descricao/{descricao}")
    public ResponseEntity<List<Produto>> buscarPorDescricao(@PathVariable String descricao) {
        return ResponseEntity.ok(produtoService.buscarPorDescricao(descricao));
    }

    @GetMapping("/familia/{codigoFamilia}")
    public ResponseEntity<List<Produto>> buscarPorFamilia(@PathVariable Long codigoFamilia) {
        return ResponseEntity.ok(produtoService.buscarPorFamilia(codigoFamilia));
    }
}
