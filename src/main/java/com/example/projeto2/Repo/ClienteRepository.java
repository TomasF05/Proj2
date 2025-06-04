package com.example.projeto2.Repo;

import com.example.projeto2.Tables.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface ClienteRepository extends JpaRepository<Cliente, BigDecimal> {
}