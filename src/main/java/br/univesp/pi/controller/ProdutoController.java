package br.univesp.pi.controller;

import br.univesp.pi.domain.model.Produto;
import br.univesp.pi.service.ProdutoService;
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
    public ResponseEntity<Produto> salvarProduto(@RequestBody Produto produto) {
        Produto produtoSalvo = produtoService.salvarProduto(produto);
        return ResponseEntity.ok(produtoSalvo);
    }

    @GetMapping
    public ResponseEntity<List<Produto>> listarProdutos() {
        List<Produto> produtos = produtoService.listarProdutos();
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Produto> buscarProdutoPorId(@PathVariable Long codigo) {
        Produto produto = produtoService.buscarProdutoPorId(codigo);
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
}
