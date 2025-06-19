package com.example.projeto2.Repo;

import com.example.projeto2.Tables.Reparacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ReparacaoRepository extends JpaRepository<Reparacao, Long> {
    int countByDataInicioLessThanEqualAndDataFimGreaterThanEqual(LocalDate date, LocalDate endDate);
    int countByDataFimLessThanEqualAndEstado(LocalDate date, String estado);
    List<Reparacao> findByDataInicioLessThanEqualAndDataFimGreaterThanEqual(LocalDate date, LocalDate endDate);
    List<Reparacao> findByEstadoNot(String estado);
}