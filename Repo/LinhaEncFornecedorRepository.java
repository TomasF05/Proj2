package com.example.projeto2.Repo;

import com.example.projeto2.Tables.LinhaEncFornecedor;
import com.example.projeto2.Tables.LinhaEncFornecedorId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinhaEncFornecedorRepository extends JpaRepository<LinhaEncFornecedor, LinhaEncFornecedorId> {
}