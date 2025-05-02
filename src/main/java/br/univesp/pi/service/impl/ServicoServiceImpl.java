package br.univesp.pi.service.impl;

import br.univesp.pi.domain.dto.ServicoCreateDTO;
import br.univesp.pi.domain.dto.ServicoUpdateDTO;
import br.univesp.pi.domain.dto.response.FamiliaResponseDTO;
import br.univesp.pi.domain.dto.response.ServicoResponseDTO;
import br.univesp.pi.domain.model.Cliente;
import br.univesp.pi.domain.model.Familia;
import br.univesp.pi.domain.model.Produto;
import br.univesp.pi.domain.model.Servico;
import br.univesp.pi.enumeration.TipoDesconto;
import br.univesp.pi.exception.ApiIllegalArgumentException;
import br.univesp.pi.repository.ClienteRepository;
import br.univesp.pi.repository.ProdutoRepository;
import br.univesp.pi.repository.ServicoRepository;
import br.univesp.pi.service.ServicoService;
import br.univesp.pi.util.MapperUtil;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicoServiceImpl implements ServicoService {

    @Autowired
    private ServicoRepository servicoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

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
                        dto.getCliente()
                ));

        List<Produto> produtos = produtoRepository.findAllById(dto.getProdutos());

        Servico servico = new Servico();
        servico.setCliente(cliente);
        servico.setMaoDeObra(dto.getMaoDeObra());
        servico.setProdutos(produtos);
        servico.setDesconto(dto.getDesconto());

        calcularTotais(servico);
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
        Servico servico = servicoRepository.findById(codigo).orElse(null);
        if (servico == null) {
            throw new ApiIllegalArgumentException(
                    "Serviço não encontrado com o código: " + codigo,
                    "Servico",
                    "codigo",
                    String.valueOf(codigo)
            );
        }
        return mapperUtil.map(servico, ServicoResponseDTO.class);
    }

    @Override
    public List<ServicoResponseDTO> buscarServicosPorClienteId(String cpfOuCnpj) {
        List<Servico> servicos = servicoRepository.findByCliente_CpfOuCnpj(cpfOuCnpj);
        if (servicos == null || servicos.isEmpty()) {
            throw new ApiIllegalArgumentException(
                    "Nenhum serviço encontrado para o cliente com CPF/CNPJ: " + cpfOuCnpj,
                    "Servico",
                    "cpfOuCnpj",
                    cpfOuCnpj
            );
        }
        return mapperUtil.mapList(servicos, ServicoResponseDTO.class);
    }

    @Transactional
    @Override
    public ServicoResponseDTO atualizarServico(Long codigo, ServicoUpdateDTO dto) {
        Servico servicoExistente = servicoRepository.findByCodigo(codigo)
                .orElseThrow(() -> new ApiIllegalArgumentException(
                        "Serviço não encontrado",
                        "Servico",
                        "codigo",
                        String.valueOf(codigo)
                ));

        if (dto.getCliente() != null) {
            Cliente cliente = clienteRepository.findById(dto.getCliente())
                    .orElseThrow(() -> new ApiIllegalArgumentException(
                            "Cliente não encontrado",
                            "Cliente",
                            "cpfOuCnpj",
                            dto.getCliente()
                    ));
            servicoExistente.setCliente(cliente);
        }

        if (dto.getMaoDeObra() != null) {
            servicoExistente.setMaoDeObra(dto.getMaoDeObra());
        }

        List<Long> codigosProdutos = dto.getProdutos();
        List<Produto> produtos = produtoRepository.findAllById(codigosProdutos);
        if (produtos.size() != codigosProdutos.size()) {
            throw new ApiIllegalArgumentException(
                    "Um ou mais produtos não foram encontrados",
                    "Produto",
                    "ids",
                    codigosProdutos.toString()
            );
        }
        servicoExistente.setProdutos(produtos);

        if (dto.getDesconto() != null) {
            servicoExistente.setDesconto(dto.getDesconto());
        }

        calcularTotais(servicoExistente);
        Servico atualizado = servicoRepository.save(servicoExistente);

        return mapperUtil.map(atualizado, ServicoResponseDTO.class);
    }

    @Transactional
    @Override
    public void deletarServico(Long codigo) {
        servicoRepository.deleteById(codigo);
    }

    // =======================
    // MÉTODOS AUXILIARES
    // =======================

    private void calcularTotais(Servico servico) {
        calcularPrecoTotalProdutos(servico);
        calcularPrecoTotal(servico);
        aplicarDesconto(servico);
        calcularPrecoTotalComDesconto(servico);
    }

    private void calcularPrecoTotalProdutos(Servico servico) {
        if (servico.getProdutos() != null && !servico.getProdutos().isEmpty()) {
            double totalProdutos = servico.getProdutos().stream()
                    .mapToDouble(Produto::getPreco)
                    .sum();
            servico.setPrecoTotalProdutos(totalProdutos);
        } else {
            servico.setPrecoTotalProdutos(0.0);
        }
    }

    private void calcularPrecoTotal(Servico servico) {
        double precoMaoDeObra = (servico.getMaoDeObra() != null && servico.getMaoDeObra().getPreco() != null)
                ? servico.getMaoDeObra().getPreco()
                : 0.0;

        double precoTotal = servico.getPrecoTotalProdutos() + precoMaoDeObra;
        servico.setPrecoTotal(precoTotal);
    }

    private void aplicarDesconto(Servico servico) {
        if (servico.getDesconto() != null && servico.getDesconto().getValor() != null) {
            if (servico.getDesconto().getTipo() == TipoDesconto.PORCENTAGEM) {
                double porcentagem = servico.getDesconto().getValor() / 100;
                double valorDesconto = servico.getPrecoTotal() * porcentagem;
                servico.getDesconto().setValor(valorDesconto);
            }
            // Se for TipoDesconto.VALOR, não faz nada.
        }
    }

    private void calcularPrecoTotalComDesconto(Servico servico) {
        double desconto = (servico.getDesconto() != null && servico.getDesconto().getValor() != null)
                ? servico.getDesconto().getValor()
                : 0.0;

        double precoComDesconto = servico.getPrecoTotal() - desconto;
        servico.setPrecoTotalComDesconto(precoComDesconto);
    }
}
