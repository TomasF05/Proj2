package com.example.projeto2.Repo;

import com.example.projeto2.Tables.Servico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface ServicoRepository extends JpaRepository<Servico, BigDecimal> {
}