package br.univesp.pi.service.impl;

import br.univesp.pi.domain.dto.*;
import br.univesp.pi.domain.dto.response.ServicoResponseDTO;
import br.univesp.pi.domain.model.*;
import br.univesp.pi.exception.ApiIllegalArgumentException;
import br.univesp.pi.repository.*;
import br.univesp.pi.service.ServicoService;
import br.univesp.pi.util.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ServicoServiceImpl implements ServicoService {

    @Autowired
    private ServicoRepository servicoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ItemServicoRepository itemServicoRepository;

    @Autowired
    private MapperUtil mapperUtil;

    @Transactional
    @Override
    public ServicoResponseDTO salvarServico(ServicoCreateDTO dto) {
        Cliente cliente = clienteRepository.findByCpfOuCnpj(dto.getCliente())
                .orElseThrow(() -> new ApiIllegalArgumentException(
                        "Cliente não encontrado",
                        "Cliente",
                        "cpfOuCnpj",
                        dto.getCliente(),
                        HttpStatus.NOT_FOUND
                ));

        Servico servico = new Servico();
        servico.setCliente(cliente);
        servico.setMaoDeObra(dto.getMaoDeObra());
        servico.setDesconto(dto.getDesconto());

        // Adiciona os itens ao serviço
        adicionarItensAoServico(servico, dto.getItens());

        // Calcula os totais
        servico.calcularTotais();

        Servico saved = servicoRepository.save(servico);
        return mapperUtil.map(saved, ServicoResponseDTO.class);
    }

    @Override
    public List<ServicoResponseDTO> listarServicos() {
        List<Servico> servicos = servicoRepository.findAll();
        return mapperUtil.mapList(servicos, ServicoResponseDTO.class);
    }

    @Override
    public ServicoResponseDTO buscarServicoPorId(Long codigo) {
        Servico servico = servicoRepository.findById(codigo)
                .orElseThrow(() -> new ApiIllegalArgumentException(
                        "Serviço não encontrado com o código: " + codigo,
                        "Servico",
                        "codigo",
                        String.valueOf(codigo),
                        HttpStatus.NOT_FOUND
                ));
        return mapperUtil.map(servico, ServicoResponseDTO.class);
    }

    @Override
    public List<ServicoResponseDTO> buscarServicosPorClienteId(String cpfOuCnpj) {
        List<Servico> servicos = servicoRepository.findByCliente_CpfOuCnpj(cpfOuCnpj);
        if (servicos.isEmpty()) {
            throw new ApiIllegalArgumentException(
                    "Nenhum serviço encontrado para o cliente com CPF/CNPJ: " + cpfOuCnpj,
                    "Servico",
                    "cpfOuCnpj",
                    cpfOuCnpj,
                    HttpStatus.NOT_FOUND
            );
        }
        return mapperUtil.mapList(servicos, ServicoResponseDTO.class);
    }

    @Transactional
    @Override
    public ServicoResponseDTO atualizarServico(Long codigo, ServicoUpdateDTO dto) {
        Servico servico = servicoRepository.findById(codigo)
                .orElseThrow(() -> new ApiIllegalArgumentException(
                        "Serviço não encontrado",
                        "Servico",
                        "codigo",
                        String.valueOf(codigo),
                        HttpStatus.NOT_FOUND
                ));

        if (dto.getCliente() != null) {
            Cliente cliente = clienteRepository.findByCpfOuCnpj(dto.getCliente())
                    .orElseThrow(() -> new ApiIllegalArgumentException(
                            "Cliente não encontrado",
                            "Cliente",
                            "cpfOuCnpj",
                            dto.getCliente(),
                            HttpStatus.NOT_FOUND
                    ));
            servico.setCliente(cliente);
        }

        if (dto.getMaoDeObra() != null) {
            servico.setMaoDeObra(dto.getMaoDeObra());
        }

        // Atualiza os itens do serviço
        if (dto.getItens() != null) {
            // Limpa os itens existentes de forma segura
            servico.getItens().clear();

            // Necessário para sincronizar com o banco de dados
            itemServicoRepository.flush();

            adicionarItensAoServico(servico, dto.getItens());
        }

        if (dto.getDesconto() != null) {
            servico.setDesconto(dto.getDesconto());
        }

        // Recalcula os totais
        servico.calcularTotais();

        Servico atualizado = servicoRepository.save(servico);
        return mapperUtil.map(atualizado, ServicoResponseDTO.class);
    }

    @Transactional
    @Override
    public void deletarServico(Long codigo) {
        if (!servicoRepository.existsById(codigo)) {
            throw new ApiIllegalArgumentException(
                    "Não foi possível deletar. Serviço não encontrado com codigo: " + codigo,
                    "Servico",
                    "codigo",
                    codigo,
                    HttpStatus.NOT_FOUND
            );
        }
        // Remove os itens primeiro para evitar problemas de constraint
        itemServicoRepository.deleteByServicoCodigo(codigo);
        servicoRepository.deleteById(codigo);
    }

    // ============= MÉTODOS AUXILIARES =============

    private void adicionarItensAoServico(Servico servico, Set<ServicoItemDTO> itensDTO) {
        // Validação inicial
        if (itensDTO == null || itensDTO.isEmpty()) {
            return;
        }

        // Obtém todos os IDs de produtos
        Set<Long> produtoIds = itensDTO.stream()
                .map(ServicoItemDTO::getProdutoId)
                .collect(Collectors.toSet());

        // Busca todos os produtos
        List<Produto> produtos = produtoRepository.findAllById(produtoIds);

        // Verifica produtos não encontrados
        if (produtos.size() != produtoIds.size()) {
            Set<Long> produtosEncontrados = produtos.stream()
                    .map(Produto::getCodigo)
                    .collect(Collectors.toSet());

            Set<Long> produtosNaoEncontrados = produtoIds.stream()
                    .filter(id -> !produtosEncontrados.contains(id))
                    .collect(Collectors.toSet());

            throw new ApiIllegalArgumentException(
                    "Produtos não encontrados: " + produtosNaoEncontrados,
                    "Produto",
                    "codigo",
                    produtosNaoEncontrados.toString(),
                    HttpStatus.NOT_FOUND
            );
        }

        // Validação de quantidades
        itensDTO.forEach(itemDTO -> {
            if (itemDTO.getQuantidade() <= 0) {
                throw new ApiIllegalArgumentException(
                        "Quantidade deve ser maior que zero",
                        "ServicoItemDTO",
                        "quantidade",
                        itemDTO.getQuantidade().toString(),
                        HttpStatus.BAD_REQUEST
                );
            }
        });

        // Adiciona itens ao serviço
        itensDTO.forEach(itemDTO -> {
            Produto produto = produtos.stream()
                    .filter(p -> p.getCodigo().equals(itemDTO.getProdutoId()))
                    .findFirst()
                    .orElseThrow();

            // Cria ou atualiza item existente
            Optional<ItemServico> itemExistente = servico.getItens().stream()
                    .filter(i -> i.getProduto().getCodigo().equals(itemDTO.getProdutoId()))
                    .findFirst();

            if (itemExistente.isPresent()) {
                // Atualiza item existente (para casos de atualização)
                ItemServico item = itemExistente.get();
                item.setQuantidade(itemDTO.getQuantidade());
                item.setPrecoUnitario(produto.getPreco());
                item.setPrecoTotalItem(produto.getPreco() * itemDTO.getQuantidade());
            } else {
                // Adiciona novo item (para criação ou novos itens na atualização)
                servico.adicionarItem(produto, itemDTO.getQuantidade());
            }
        });
    }
}