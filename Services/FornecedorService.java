package com.example.projeto2.Services;

import com.example.projeto2.Repo.FornecedorRepository;
import com.example.projeto2.Tables.Fornecedor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class FornecedorService {

    @Autowired
    private FornecedorRepository fornecedorRepository;

    // Método para salvar um fornecedor
    @Transactional
    public Fornecedor saveFornecedor(Fornecedor fornecedor) {
        return fornecedorRepository.save(fornecedor);
    }

    // Método para retornar todos os fornecedores
    @Transactional
    public List<Fornecedor> getAllFornecedores() {
        return fornecedorRepository.findAll();
    }

    // Método para retornar um fornecedor por ID
    @Transactional
    public Fornecedor getFornecedorById(BigDecimal id) {
        return fornecedorRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Fornecedor não encontrado: " + id));
    }

    // Método para excluir um fornecedor por ID
    @Transactional
    public void deleteFornecedor(BigDecimal id) {
        fornecedorRepository.deleteById(id);
    }

    // Método para atualizar um fornecedor
    @Transactional
    public Fornecedor updateFornecedor(BigDecimal id, Fornecedor fornecedor) {
        Fornecedor existingFornecedor = getFornecedorById(id); // Verifica se o fornecedor existe

        // Atualiza os campos do fornecedor
        existingFornecedor.setNome(fornecedor.getNome());
        existingFornecedor.setNif(fornecedor.getNif());
        existingFornecedor.setContacto(fornecedor.getContacto());
        existingFornecedor.setCodPostal(fornecedor.getCodPostal());

        // Salva as alterações
        return fornecedorRepository.save(existingFornecedor);
    }
}
