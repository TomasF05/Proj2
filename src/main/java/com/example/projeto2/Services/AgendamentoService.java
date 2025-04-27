package com.example.projeto2.Services;

import com.example.projeto2.Repo.AgendamentoRepository;
import com.example.projeto2.Tables.Agendamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AgendamentoService {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    // Método para salvar um agendamento
    @Transactional
    public Agendamento saveAgendamento(Agendamento agendamento) {
        return agendamentoRepository.save(agendamento);
    }

    // Método para retornar todos os agendamentos
    @Transactional
    public List<Agendamento> getAllAgendamentos() {
        return agendamentoRepository.findAll();
    }

    // Método para retornar um agendamento por ID
    @Transactional
    public Agendamento getAgendamentoById(BigDecimal id) {
        return agendamentoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Agendamento não encontrado: " + id));
    }

    // Método para excluir um agendamento por ID
    @Transactional
    public void deleteAgendamento(BigDecimal id) {
        agendamentoRepository.deleteById(id);
    }

    // Método para atualizar um agendamento
    @Transactional
    public Agendamento updateAgendamento(BigDecimal id, Agendamento agendamento) {
        Agendamento existingAgendamento = getAgendamentoById(id); // Verifica se o agendamento existe

        // Atualiza os campos do agendamento
        existingAgendamento.setDataHora(agendamento.getDataHora());
        existingAgendamento.setEstadoPagamento(agendamento.getEstadoPagamento());
        existingAgendamento.setObservacoes(agendamento.getObservacoes());
        existingAgendamento.setIdVeiculo(agendamento.getIdVeiculo());
        existingAgendamento.setIdFuncionario(agendamento.getIdFuncionario());

        // Salva as alterações
        return agendamentoRepository.save(existingAgendamento);
    }
    public List<Agendamento> getTodayAppointments() {
        LocalDate today = LocalDate.now();
        return agendamentoRepository.findByDataHoraGreaterThanEqualAndDataHoraLessThanEqual(today.atStartOfDay(), today.atTime(23, 59, 59));
    }
}
