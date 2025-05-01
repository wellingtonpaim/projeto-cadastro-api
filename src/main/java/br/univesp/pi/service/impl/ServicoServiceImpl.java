package br.univesp.pi.service.impl;

import br.univesp.pi.domain.dto.ServicoCreateDTO;
import br.univesp.pi.domain.dto.ServicoUpdateDTO;
import br.univesp.pi.domain.model.Cliente;
import br.univesp.pi.domain.model.Produto;
import br.univesp.pi.domain.model.Servico;
import br.univesp.pi.enumeration.TipoDesconto;
import br.univesp.pi.repository.ClienteRepository;
import br.univesp.pi.repository.ProdutoRepository;
import br.univesp.pi.repository.ServicoRepository;
import br.univesp.pi.service.ServicoService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
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

    @Transactional
    @Override
    public Servico salvarServico(ServicoCreateDTO dto) {

        Cliente cliente = clienteRepository.findByCpfOuCnpj(dto.getCliente())
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));

        List<Produto> produtos = produtoRepository.findAllByCodigo(dto.getProdutos());

        Servico servico = new Servico();
        servico.setCliente(cliente);
        servico.setMaoDeObra(dto.getMaoDeObra());
        servico.setProdutos(produtos);
        servico.setDesconto(dto.getDesconto());

        calcularTotais(servico);
        return servicoRepository.save(servico);
    }

    @Override
    public List<Servico> listarServicos() {
        return servicoRepository.findAll();
    }

    @Override
    public Servico buscarServicoPorId(Long codigo) {
        return servicoRepository.findById(codigo).orElse(null);
    }

    @Override
    public List<Servico> buscarServicosPorClienteId(String cpfOuCnpj) {
        return servicoRepository.findByCliente_CpfOuCnpj(cpfOuCnpj);
    }

    @Transactional
    @Override
    public Servico atualizarServico(Long codigo, ServicoUpdateDTO dto) {
        Servico servicoExistente = servicoRepository.findByCodigo(codigo)
                .orElseThrow(() -> new EntityNotFoundException("Serviço não encontrado"));

        if (dto.getCliente() != null) {
            Cliente cliente = clienteRepository.findById(dto.getCliente())
                    .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));
            servicoExistente.setCliente(cliente);
        }

        if (dto.getMaoDeObra() != null) {
            servicoExistente.setMaoDeObra(dto.getMaoDeObra());
        }

        if (dto.getProdutos() != null && !dto.getProdutos().isEmpty()) {
            List<Produto> produtos = produtoRepository.findAllById(dto.getProdutos());
            servicoExistente.setProdutos(produtos);
        }

        if (dto.getDesconto() != null) {
            servicoExistente.setDesconto(dto.getDesconto());
        }

        calcularTotais(servicoExistente);
        return servicoRepository.save(servicoExistente);
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
