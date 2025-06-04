package com.example.projeto2.Services;

import com.example.projeto2.Repo.PecaRepository;
import com.example.projeto2.Tables.Peca;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PecaService {

    @Autowired
    private PecaRepository pecaRepository;

    @Transactional
    public BigDecimal savePeca(Peca peca) {
        Peca savedPeca = pecaRepository.save(peca);
        return savedPeca.getIdPeca();
    }

    @Transactional
    public void deletePeca(BigDecimal id) {
        pecaRepository.deleteById(id);
    }

    @Transactional
    public void updatePeca(BigDecimal id, Peca peca) {
        Peca existingPeca = getPecaById(id);
        existingPeca.setNome(peca.getNome());
        existingPeca.setReferencia(peca.getReferencia());
        existingPeca.setPreco(peca.getPreco());
        existingPeca.setQtd(peca.getQtd());
        pecaRepository.save(existingPeca);
    }

    @Transactional
    public Peca getPecaById(BigDecimal id) {
        return pecaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Peça não encontrada para o id: " + id));
    }

    @Transactional
    public List<Peca> getAllPecas() {
        return pecaRepository.findAll();
    }
}
