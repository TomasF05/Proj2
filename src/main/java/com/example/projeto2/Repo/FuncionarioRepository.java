package com.example.projeto2.Repo;

import com.example.projeto2.Tables.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Optional;

public interface FuncionarioRepository extends JpaRepository<Funcionario, BigDecimal> {
    
    // Find by username
    Optional<Funcionario> findByUsername(String username);
    
    // Find by username and password
    @Query("SELECT f FROM Funcionario f WHERE f.username = :username AND f.password = :password")
    Optional<Funcionario> findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
}