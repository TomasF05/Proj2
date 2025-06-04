package com.example.projeto2.Repo;

import com.example.projeto2.Tables.Peca;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface PecaRepository extends JpaRepository<Peca, BigDecimal> {
}