package com.example.projeto2.Services;

import com.example.projeto2.Repo.LinhaEncFornecedorRepository;
import com.example.projeto2.Tables.LinhaEncFornecedor;
import com.example.projeto2.Tables.LinhaEncFornecedorId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class LinhaEncFornecedorService {

    @Autowired
    private LinhaEncFornecedorRepository linhaEncFornecedorRepository;

    @Transactional
    public LinhaEncFornecedor saveLinhaEncFornecedor(LinhaEncFornecedor linhaEncFornecedor) {
        return linhaEncFornecedorRepository.save(linhaEncFornecedor);
    }

    @Transactional
    public List<LinhaEncFornecedor> getAllLinhaEncFornecedor() {
        return linhaEncFornecedorRepository.findAll();
    }

    @Transactional
    public LinhaEncFornecedor getLinhaEncFornecedorById(LinhaEncFornecedorId id) {
        return linhaEncFornecedorRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("LinhaEncFornecedor not found: " + id));
    }

    @Transactional
    public void deleteLinhaEncFornecedor(LinhaEncFornecedorId id) {
        linhaEncFornecedorRepository.deleteById(id);
    }
}
