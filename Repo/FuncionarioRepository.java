package com.example.projeto2.Repo;

import com.example.projeto2.Tables.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface FuncionarioRepository extends JpaRepository<Funcionario, BigDecimal> {
}