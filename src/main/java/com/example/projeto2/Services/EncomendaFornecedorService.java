// java
package com.example.projeto2.Services;

import com.example.projeto2.Repo.EncomendaFornecedorRepository;
import com.example.projeto2.Tables.EncomendaFornecedor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class EncomendaFornecedorService {

    @Autowired
    private EncomendaFornecedorRepository encomendaFornecedorRepository;

    @Transactional
    public EncomendaFornecedor saveEncomendaFornecedor(EncomendaFornecedor encomendaFornecedor) {
        return encomendaFornecedorRepository.save(encomendaFornecedor);
    }

    @Transactional
    public List<EncomendaFornecedor> getAllEncomendasFornecedores() {
        return encomendaFornecedorRepository.findAll();
    }

    @Transactional
    public EncomendaFornecedor getEncomendaFornecedorById(BigDecimal id) {
        return encomendaFornecedorRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("EncomendaFornecedor not found: " + id));
    }

    @Transactional
    public List<EncomendaFornecedor> getAllEncomendasFornecedor() {
        return encomendaFornecedorRepository.findAll();
    }

    @Transactional
    public void deleteEncomendaFornecedor(BigDecimal id) {
        encomendaFornecedorRepository.deleteById(id);
    }

    @Transactional
    public EncomendaFornecedor updateEncomendaFornecedor(BigDecimal id, EncomendaFornecedor encomendaFornecedor) {
        EncomendaFornecedor existingEncomenda = getEncomendaFornecedorById(id);
        existingEncomenda.setData(encomendaFornecedor.getData());
        existingEncomenda.setValorTotal(encomendaFornecedor.getValorTotal());
        existingEncomenda.setIdFornecedor(encomendaFornecedor.getIdFornecedor());
        return encomendaFornecedorRepository.save(existingEncomenda);
    }

    // New method to handle extra parameters for order parts.
    @Transactional
    public EncomendaFornecedor saveOrderPart(EncomendaFornecedor order, String partName, BigDecimal quantity) {
        // Handle extra parameters as needed (e.g., logging or additional business logic)
        return saveEncomendaFornecedor(order);
    }
}