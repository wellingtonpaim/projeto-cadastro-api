package br.univesp.pi.service;

import br.univesp.pi.domain.dto.FornecedorCreateDTO;
import br.univesp.pi.domain.dto.FornecedorUpdateDTO;
import br.univesp.pi.domain.dto.response.FornecedorResponseDTO;

import java.util.List;

public interface FornecedorService {

    FornecedorResponseDTO salvarFornecedor(FornecedorCreateDTO fornecedor);
    List<FornecedorResponseDTO> listarFornecedores();
    FornecedorResponseDTO buscarFornecedorPorCpfOuCnpj(String cpfOuCnpj);
    List<FornecedorResponseDTO> buscarFornecedorPorNomeOuRazaoSocial(String nomeOuRazaoSocial);
    List<FornecedorResponseDTO> buscarFornecedorPorEmail(String email);
    FornecedorResponseDTO atualizarFornecedor(String cpfOuCnpj, FornecedorUpdateDTO fornecedorAtualizado);
    void deletarFornecedor(String cpfOuCnpj);
}

