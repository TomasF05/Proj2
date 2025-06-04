package com.example.projeto2.Repo;

import com.example.projeto2.Tables.LinhaFatura;
import com.example.projeto2.Tables.LinhaFaturaId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinhaFaturaRepository extends JpaRepository<LinhaFatura, LinhaFaturaId> {
}