package com.example.projeto2.Repo;

import com.example.projeto2.Tables.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface AgendamentoRepository extends JpaRepository<Agendamento, BigDecimal> {
}