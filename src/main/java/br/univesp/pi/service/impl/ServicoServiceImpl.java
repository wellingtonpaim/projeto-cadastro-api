package br.univesp.pi.service.impl;

import br.univesp.pi.domain.model.Pessoa;
import br.univesp.pi.domain.model.Produto;
import br.univesp.pi.domain.model.Servico;
import br.univesp.pi.enumeration.TipoDesconto;
import br.univesp.pi.repository.ServicoRepository;
import br.univesp.pi.service.ServicoService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicoServiceImpl implements ServicoService {

    @Autowired
    private ServicoRepository servicoRepository;

    @Transactional
    @Override
    public Servico salvarServico(Servico servico) {
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

    @Transactional
    @Override
    public void deletarServico(Long codigo) {
        servicoRepository.deleteById(codigo);
    }

    @Transactional
    @Override
    public Servico atualizarServico(Long codigo, Servico servico) {
        Servico servicoExistente = servicoRepository.findById(codigo).orElse(null);
        if (servicoExistente != null) {
            servico.setCodigo(codigo);
            calcularTotais(servico);
            return servicoRepository.save(servico);
        }
        return null;
    }

    @Override
    public List<Servico> buscarServicosPorCliente(Pessoa cliente) {
        return servicoRepository.findByCliente(cliente);
    }

    @Override
    public List<Servico> buscarServicosPorClienteId(String cpfOuCnpj) {
        return servicoRepository.findByCliente_CpfOuCnpj(cpfOuCnpj);
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
