package com.example.projeto2.Repo;

import com.example.projeto2.Tables.FaturaCliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface FaturaClienteRepository extends JpaRepository<FaturaCliente, BigDecimal> {
}