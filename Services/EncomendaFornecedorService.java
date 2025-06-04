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

    // Método para salvar uma encomenda fornecedor
    @Transactional
    public EncomendaFornecedor saveEncomendaFornecedor(EncomendaFornecedor encomendaFornecedor) {
        return encomendaFornecedorRepository.save(encomendaFornecedor);
    }

    // Método para retornar todas as encomendas fornecedor
    @Transactional
    public List<EncomendaFornecedor> getAllEncomendasFornecedores() {
        return encomendaFornecedorRepository.findAll();
    }

    // Método para retornar uma encomenda fornecedor por ID
    @Transactional
    public EncomendaFornecedor getEncomendaFornecedorById(BigDecimal id) {
        return encomendaFornecedorRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("EncomendaFornecedor não encontrado: " + id));
    }

    // Método para excluir uma encomenda fornecedor por ID
    @Transactional
    public void deleteEncomendaFornecedor(BigDecimal id) {
        encomendaFornecedorRepository.deleteById(id);
    }

    // Método para atualizar uma encomenda fornecedor
    @Transactional
    public EncomendaFornecedor updateEncomendaFornecedor(BigDecimal id, EncomendaFornecedor encomendaFornecedor) {
        EncomendaFornecedor existingEncomenda = getEncomendaFornecedorById(id); // Verifica se a encomenda fornecedor existe

        // Atualiza os campos da encomenda fornecedor
        existingEncomenda.setData(encomendaFornecedor.getData());
        existingEncomenda.setValorTotal(encomendaFornecedor.getValorTotal());
        existingEncomenda.setIdFornecedor(encomendaFornecedor.getIdFornecedor());

        // Salva as alterações
        return encomendaFornecedorRepository.save(existingEncomenda);
    }
}
