package com.example.projeto2.Repo;

import com.example.projeto2.Tables.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface VeiculoRepository extends JpaRepository<Veiculo, BigDecimal> {
}