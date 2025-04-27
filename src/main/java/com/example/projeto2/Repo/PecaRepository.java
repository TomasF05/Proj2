package com.example.projeto2.Repo;

import com.example.projeto2.Tables.Peca;
import org.springframework.data.jpa.repository.JpaRepository;
import javafx.collections.ObservableList;
import java.math.BigDecimal;
import java.util.List;

public interface PecaRepository extends JpaRepository<Peca, BigDecimal> {
    List<Peca> findByQtdLessThanEqual(int threshold);
}