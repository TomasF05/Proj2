package com.example.projeto2.Services;

import com.example.projeto2.Repo.LinhaEncFornecedorRepository;
import com.example.projeto2.Tables.LinhaEncFornecedor;
import com.example.projeto2.Tables.LinhaEncFornecedorId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class LinhaEncFornecedorService {

    @Autowired
    private LinhaEncFornecedorRepository linhaEncFornecedorRepository;

    // Método para salvar uma LinhaEncFornecedor
    @Transactional
    public LinhaEncFornecedor saveLinhaEncFornecedor(LinhaEncFornecedor linhaEncFornecedor) {
        return linhaEncFornecedorRepository.save(linhaEncFornecedor);
    }

    // Método para retornar todas as LinhaEncFornecedor
    @Transactional
    public List<LinhaEncFornecedor> getAllLinhasEncFornecedor() {
        return linhaEncFornecedorRepository.findAll();
    }

    // Método para retornar uma LinhaEncFornecedor por ID composto
    @Transactional
    public LinhaEncFornecedor getLinhaEncFornecedorById(LinhaEncFornecedorId id) {
        return linhaEncFornecedorRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("LinhaEncFornecedor não encontrada: " + id));
    }

    // Método para excluir uma LinhaEncFornecedor por ID composto
    @Transactional
    public void deleteLinhaEncFornecedor(LinhaEncFornecedorId id) {
        linhaEncFornecedorRepository.deleteById(id);
    }

    // Método para atualizar uma LinhaEncFornecedor
    @Transactional
    public LinhaEncFornecedor updateLinhaEncFornecedor(LinhaEncFornecedorId id, LinhaEncFornecedor linhaEncFornecedor) {
        LinhaEncFornecedor existingLinhaEncFornecedor = getLinhaEncFornecedorById(id);

        // Atualiza os campos
        existingLinhaEncFornecedor.setQtd(linhaEncFornecedor.getQtd());
        existingLinhaEncFornecedor.setValorTotal(linhaEncFornecedor.getValorTotal());

        // Salva as alterações
        return linhaEncFornecedorRepository.save(existingLinhaEncFornecedor);
    }
}
