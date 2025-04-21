package br.univesp.pi.controller;

import br.univesp.pi.domain.model.Usuario;
import br.univesp.pi.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Usuario> salvarUsuario(@RequestBody Usuario usuario) {
        Usuario usuarioSalvo = usuarioService.salvarUsuario(usuario);
        return ResponseEntity.ok(usuarioSalvo);
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{cpfOuCnpj}")
    public ResponseEntity<Usuario> buscarUsuarioPorId(@PathVariable String cpfOuCnpj) {
        Usuario usuario = usuarioService.buscarUsuarioPorId(cpfOuCnpj);
        return ResponseEntity.ok(usuario);
    }

    @DeleteMapping("/{cpfOuCnpj}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable String cpfOuCnpj) {
        usuarioService.deletarUsuario(cpfOuCnpj);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{cpfOuCnpj}")
    public ResponseEntity<Usuario> atualizarUsuario(@PathVariable String cpfOuCnpj, @RequestBody Usuario usuario) {
        Usuario usuarioAtualizado = usuarioService.atualizarUsuario(cpfOuCnpj, usuario);
        return ResponseEntity.ok(usuarioAtualizado);
    }
}
