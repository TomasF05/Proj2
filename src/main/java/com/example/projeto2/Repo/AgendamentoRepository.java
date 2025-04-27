package com.example.projeto2.Repo;

import com.example.projeto2.Tables.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface AgendamentoRepository extends JpaRepository<Agendamento, BigDecimal> {
    List<Agendamento> findByDataHoraGreaterThanEqualAndDataHoraLessThanEqual(LocalDateTime start, LocalDateTime end);
}