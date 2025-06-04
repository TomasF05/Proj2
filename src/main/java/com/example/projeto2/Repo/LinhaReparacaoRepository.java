package com.example.projeto2.Repo;

import com.example.projeto2.Tables.LinhaReparacao;
import com.example.projeto2.Tables.LinhaReparacaoId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinhaReparacaoRepository extends JpaRepository<LinhaReparacao, LinhaReparacaoId> {
}