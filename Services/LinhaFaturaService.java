package com.example.projeto2.Services;

import com.example.projeto2.Repo.LinhaFaturaRepository;
import com.example.projeto2.Tables.LinhaFatura;
import com.example.projeto2.Tables.LinhaFaturaId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class LinhaFaturaService {

    @Autowired
    private LinhaFaturaRepository linhaFaturaRepository;

    @Transactional
    public LinhaFatura saveLinhaFatura(LinhaFatura linhaFatura) {
        return linhaFaturaRepository.save(linhaFatura);
    }

    @Transactional
    public List<LinhaFatura> getAllLinhaFaturas() {
        return linhaFaturaRepository.findAll();
    }

    @Transactional
    public LinhaFatura getLinhaFaturaById(LinhaFaturaId id) {
        return linhaFaturaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Linha de fatura não encontrada: " + id));
    }

    @Transactional
    public void deleteLinhaFatura(LinhaFaturaId id) {
        linhaFaturaRepository.deleteById(id);
    }

    @Transactional
    public void updateLinhaFatura(LinhaFaturaId id, LinhaFatura linhaFatura) {
        LinhaFatura existingLinhaFatura = getLinhaFaturaById(id);
        existingLinhaFatura.setQtd(linhaFatura.getQtd());  // Atualizando a quantidade
        existingLinhaFatura.setValorTotal(linhaFatura.getValorTotal());  // Atualizando o valor total
        linhaFaturaRepository.save(existingLinhaFatura);
    }
}
