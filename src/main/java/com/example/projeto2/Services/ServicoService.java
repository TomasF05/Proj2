package com.example.projeto2.Services;

import com.example.projeto2.Repo.ServicoRepository;
import com.example.projeto2.Tables.Servico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ServicoService {

    @Autowired
    private ServicoRepository servicoRepository;

    // Salva ou atualiza um serviço
    @Transactional
    public BigDecimal saveServico(Servico servico) {
        Servico savedServico = servicoRepository.save(servico);
        return savedServico.getIdServico();  // Retorna o ID do serviço salvo
    }

    // Deleta um serviço pelo ID
    @Transactional
    public void deleteServico(BigDecimal id) {
        servicoRepository.deleteById(id);
    }

    // Atualiza os dados de um serviço existente
    @Transactional
    public void updateServico(BigDecimal id, Servico servico) {
        Servico existingServico = getServicoById(id);
        existingServico.setNome(servico.getNome());
        existingServico.setDescricao(servico.getDescricao());
        servicoRepository.save(existingServico);
    }

    // Retorna um serviço pelo ID
    @Transactional
    public Servico getServicoById(BigDecimal id) {
        return servicoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Serviço não encontrado para o id: " + id));
    }

    // Retorna todos os serviços
    @Transactional
    public List<Servico> getAllServicos() {
        return servicoRepository.findAll();
    }

}
