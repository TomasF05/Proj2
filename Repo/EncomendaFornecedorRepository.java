package com.example.projeto2.Repo;

import com.example.projeto2.Tables.EncomendaFornecedor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface EncomendaFornecedorRepository extends JpaRepository<EncomendaFornecedor, BigDecimal> {
}