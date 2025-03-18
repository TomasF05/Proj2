package com.example.projeto2.Services;

import com.example.projeto2.Repo.FaturaFornecedorRepository;
import com.example.projeto2.Tables.FaturaFornecedor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class FaturaFornecedorService {

    @Autowired
    private FaturaFornecedorRepository faturaFornecedorRepository;

    // Método para salvar uma fatura de fornecedor
    @Transactional
    public FaturaFornecedor saveFaturaFornecedor(FaturaFornecedor faturaFornecedor) {
        return faturaFornecedorRepository.save(faturaFornecedor);
    }

    // Método para retornar todas as faturas de fornecedores
    @Transactional
    public List<FaturaFornecedor> getAllFaturasFornecedores() {
        return faturaFornecedorRepository.findAll();
    }

    // Método para retornar uma fatura de fornecedor por ID
    @Transactional
    public FaturaFornecedor getFaturaFornecedorById(BigDecimal id) {
        return faturaFornecedorRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("FaturaFornecedor não encontrada: " + id));
    }

    // Método para excluir uma fatura de fornecedor por ID
    @Transactional
    public void deleteFaturaFornecedor(BigDecimal id) {
        faturaFornecedorRepository.deleteById(id);
    }

    // Método para atualizar uma fatura de fornecedor
    @Transactional
    public FaturaFornecedor updateFaturaFornecedor(BigDecimal id, FaturaFornecedor faturaFornecedor) {
        FaturaFornecedor existingFatura = getFaturaFornecedorById(id); // Verifica se a fatura existe

        // Atualiza os campos da fatura de fornecedor
        existingFatura.setData(faturaFornecedor.getData());
        existingFatura.setValorTotal(faturaFornecedor.getValorTotal());
        existingFatura.setIdEncFornecedor(faturaFornecedor.getIdEncFornecedor());

        // Salva as alterações
        return faturaFornecedorRepository.save(existingFatura);
    }
}
