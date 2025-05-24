package br.univesp.pi.controller;

import br.univesp.pi.domain.dto.ServicoCreateDTO;
import br.univesp.pi.domain.dto.ServicoUpdateDTO;
import br.univesp.pi.domain.dto.response.ApiResponse;
import br.univesp.pi.domain.dto.response.ServicoResponseDTO;
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
    public ResponseEntity<ApiResponse<ServicoResponseDTO>> salvarServico(@Valid @RequestBody ServicoCreateDTO servico) {
        ServicoResponseDTO servicoSalvo = servicoService.salvarServico(servico);
        return ResponseEntity.ok(new ApiResponse<>(true, "Serviço salvo com sucesso", servicoSalvo));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ServicoResponseDTO>>> listarServicos() {
        List<ServicoResponseDTO> servicos = servicoService.listarServicos();
        return ResponseEntity.ok(new ApiResponse<>(true, "Serviços listados com sucesso", servicos));
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<ApiResponse<ServicoResponseDTO>> buscarServicoPorId(@PathVariable Long codigo) {
        ServicoResponseDTO servico = servicoService.buscarServicoPorId(codigo);
        return ResponseEntity.ok(new ApiResponse<>(true, "Serviço encontrado", servico));
    }

    @GetMapping("/cliente/{cpfOuCnpj}")
    public ResponseEntity<ApiResponse<List<ServicoResponseDTO>>> buscarServicosPorCliente(@PathVariable String cpfOuCnpj) {
        List<ServicoResponseDTO> servicos = servicoService.buscarServicosPorClienteId(cpfOuCnpj);
        return ResponseEntity.ok(new ApiResponse<>(true, "Serviços encontrados para o cliente", servicos));
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<ApiResponse<ServicoResponseDTO>> atualizarServico(
            @PathVariable Long codigo,
            @Valid @RequestBody ServicoUpdateDTO servico) {
        ServicoResponseDTO servicoAtualizado = servicoService.atualizarServico(codigo, servico);
        return ResponseEntity.ok(new ApiResponse<>(true, "Serviço atualizado com sucesso", servicoAtualizado));
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<ApiResponse<Void>> deletarServico(@PathVariable Long codigo) {
        servicoService.deletarServico(codigo);
        return ResponseEntity.ok(new ApiResponse<>(true, "Serviço deletado com sucesso", null));
    }
}
