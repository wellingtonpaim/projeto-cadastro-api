package br.univesp.pi.controller;

import br.univesp.pi.domain.dto.ClienteCreateDTO;
import br.univesp.pi.domain.dto.ClienteUpdateDTO;
import br.univesp.pi.domain.dto.response.ApiResponse;
import br.univesp.pi.domain.dto.response.ClienteResponseDTO;
import br.univesp.pi.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public ResponseEntity<ApiResponse<ClienteResponseDTO>> salvarCliente(@Valid @RequestBody ClienteCreateDTO clienteDTO) {
        ClienteResponseDTO cliente = clienteService.salvarCliente(clienteDTO);
        return ResponseEntity.ok(new ApiResponse<>(true, "Cliente salvo com sucesso", cliente));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ClienteResponseDTO>>> listarClientes() {
        List<ClienteResponseDTO> clientes = clienteService.listarClientes();
        return ResponseEntity.ok(new ApiResponse<>(true, "Clientes listados com sucesso", clientes));
    }

    @GetMapping("/{cpfOuCnpj}")
    public ResponseEntity<ApiResponse<ClienteResponseDTO>> buscarClientePorCpfOuCnpj(@PathVariable String cpfOuCnpj) {
        ClienteResponseDTO cliente = clienteService.buscarClientePorCpfOuCnpj(cpfOuCnpj);
        return ResponseEntity.ok(new ApiResponse<>(true, "Cliente encontrado", cliente));
    }

    @GetMapping("/nome/{nomeOuRazaoSocial}")
    public ResponseEntity<ApiResponse<List<ClienteResponseDTO>>> buscarClientePorNome(@PathVariable String nomeOuRazaoSocial) {
        List<ClienteResponseDTO> clientes = clienteService.buscarClientePorNomeOuRazaoSocial(nomeOuRazaoSocial);
        return ResponseEntity.ok(new ApiResponse<>(true, "Clientes encontrados por nome", clientes));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ApiResponse<List<ClienteResponseDTO>>> buscarClientePorEmail(@PathVariable String email) {
        List<ClienteResponseDTO> clientes = clienteService.buscarClientePorEmail(email);
        return ResponseEntity.ok(new ApiResponse<>(true, "Clientes encontrados por e-mail", clientes));
    }

    @PutMapping("/{cpfOuCnpj}")
    public ResponseEntity<ApiResponse<ClienteResponseDTO>> atualizarCliente(
            @PathVariable String cpfOuCnpj,
            @Valid @RequestBody ClienteUpdateDTO clienteDTO) {
        ClienteResponseDTO clienteAtualizado = clienteService.atualizarCliente(cpfOuCnpj, clienteDTO);
        return ResponseEntity.ok(new ApiResponse<>(true, "Cliente atualizado com sucesso", clienteAtualizado));
    }

    @DeleteMapping("/{cpfOuCnpj}")
    public ResponseEntity<ApiResponse<Void>> deletarCliente(@PathVariable String cpfOuCnpj) {
        clienteService.deletarCliente(cpfOuCnpj);
        return ResponseEntity.ok(new ApiResponse<>(true, "Cliente deletado com sucesso", null));
    }
}
