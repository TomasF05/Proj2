package com.example.projeto2.Services;

import com.example.projeto2.Repo.ReparacaoRepository;
import com.example.projeto2.Tables.Reparacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ReparacaoService {

    @Autowired
    private ReparacaoRepository reparacaoRepository;

    public int countTodayRepairs() {
        LocalDate today = LocalDate.now();
        return reparacaoRepository.countByDataInicioLessThanEqualAndDataFimGreaterThanEqual(today, today);
    }

    public int countCompletedRepairsToday() {
        LocalDate today = LocalDate.now();
        return reparacaoRepository.countByDataFimLessThanEqualAndEstado(today, "Concluída");
    }
    public List<Reparacao> getTodayRepairs() {
        LocalDate today = LocalDate.now();
        return reparacaoRepository.findByDataInicioLessThanEqualAndDataFimGreaterThanEqual(today, today);
    }

    public List<Reparacao> getActiveRepairs() {
        return reparacaoRepository.findByEstadoNot("Concluída");
    }

    // Salva ou atualiza uma reparação
    @Transactional
    public BigDecimal saveReparacao(Reparacao reparacao) {
        Reparacao savedReparacao = reparacaoRepository.save(reparacao);
        return savedReparacao.getIdReparacao();  // Retorna o ID da reparação salva
    }

    // Deleta uma reparação pelo ID
    @Transactional
    public void deleteReparacao(BigDecimal id) {
        reparacaoRepository.deleteById(id);
    }

    // Atualiza os dados de uma reparação existente
    @Transactional
    public void updateReparacao(BigDecimal id, Reparacao reparacao) {
        Reparacao existingReparacao = getReparacaoById(id);
        existingReparacao.setDataInicio(reparacao.getDataInicio());
        existingReparacao.setDataFim(reparacao.getDataFim());
        existingReparacao.setEstado(reparacao.getEstado());
        existingReparacao.setDescricao(reparacao.getDescricao());
        existingReparacao.setValorTotal(reparacao.getValorTotal());
        existingReparacao.setnFatura(reparacao.getnFatura());
        existingReparacao.setIdVeiculo(reparacao.getIdVeiculo());
        existingReparacao.setIdFuncionario(reparacao.getIdFuncionario());
        existingReparacao.setIdServico(reparacao.getIdServico());
        reparacaoRepository.save(existingReparacao);
    }

    // Retorna uma reparação pelo ID
    @Transactional
    public Reparacao getReparacaoById(BigDecimal id) {
        return reparacaoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Reparação não encontrada para o id: " + id));
    }

    // Retorna todas as reparações
    @Transactional
    public List<Reparacao> getAllReparacoes() {
        return reparacaoRepository.findAll();
    }
}
