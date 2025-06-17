package com.example.projeto2.Repo;

import com.example.projeto2.Tables.Peca;
import org.springframework.data.jpa.repository.JpaRepository;
import javafx.collections.ObservableList;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface PecaRepository extends JpaRepository<Peca, BigDecimal> {
    List<Peca> findByQtdLessThanEqual(int threshold);
    
    // Add method to find a Peca by name
    Optional<Peca> findByNome(String nome);
}