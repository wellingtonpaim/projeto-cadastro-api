package br.univesp.pi.service.impl;

import br.univesp.pi.domain.model.Servico;
import br.univesp.pi.repository.ServicoRepository;
import br.univesp.pi.service.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicoServiceImpl implements ServicoService {

    @Autowired
    private ServicoRepository servicoRepository;

    @Override
    public Servico salvarServico(Servico servico) {
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
    public void deletarServico(Long codigo) {
        servicoRepository.deleteById(codigo);
    }

    @Override
    public Servico atualizarServico(Long codigo, Servico servico) {
        Servico servicoExistente = servicoRepository.findById(codigo).orElse(null);
        if (servicoExistente != null) {
            servico.setCodigo(codigo);
            return servicoRepository.save(servico);
        }
        return null;
    }
}
