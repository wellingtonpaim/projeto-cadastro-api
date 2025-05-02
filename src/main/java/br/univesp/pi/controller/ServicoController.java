package br.univesp.pi.controller;

import br.univesp.pi.domain.dto.ServicoCreateDTO;
import br.univesp.pi.domain.dto.ServicoUpdateDTO;
import br.univesp.pi.domain.dto.response.ServicoResponseDTO;
import br.univesp.pi.domain.model.Servico;
import br.univesp.pi.service.ServicoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servico")
public class ServicoController {

    @Autowired
    private ServicoService servicoService;

    @PostMapping
    public ResponseEntity<ServicoResponseDTO> salvarServico(@Valid @RequestBody ServicoCreateDTO servico) {
        ServicoResponseDTO servicoSalvo = servicoService.salvarServico(servico);
        return ResponseEntity.ok(servicoSalvo);
    }

    @GetMapping
    public ResponseEntity<List<ServicoResponseDTO>> listarServicos() {
        List<ServicoResponseDTO> servicos = servicoService.listarServicos();
        return ResponseEntity.ok(servicos);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<ServicoResponseDTO> buscarServicoPorId(@PathVariable Long codigo) {
        ServicoResponseDTO servico = servicoService.buscarServicoPorId(codigo);
        return ResponseEntity.ok(servico);
    }

    @GetMapping("/cliente/{cpfOuCnpj}")
    public ResponseEntity<List<ServicoResponseDTO>> buscarServicosPorCliente(@PathVariable String cpfOuCnpj) {
        List<ServicoResponseDTO> servicos = servicoService.buscarServicosPorClienteId(cpfOuCnpj);
        return ResponseEntity.ok(servicos);
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<ServicoResponseDTO> atualizarServico(@PathVariable Long codigo, @Valid @RequestBody ServicoUpdateDTO servico) {
        ServicoResponseDTO servicoAtualizado = servicoService.atualizarServico(codigo, servico);
        return ResponseEntity.ok(servicoAtualizado);
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> deletarServico(@PathVariable Long codigo) {
        servicoService.deletarServico(codigo);
        return ResponseEntity.noContent().build();
    }

}
