package com.example.projeto2.Services;

import com.example.projeto2.Repo.CodPostalRepository;
import com.example.projeto2.Tables.CodPostal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CodPostalService {

    @Autowired
    private CodPostalRepository codPostalRepository;

    // Método para salvar um código postal
    @Transactional
    public CodPostal saveCodPostal(CodPostal codPostal) {
        return codPostalRepository.save(codPostal);
    }

    // Método para retornar todos os códigos postais
    @Transactional
    public List<CodPostal> getAllCodPostais() {
        return codPostalRepository.findAll();
    }

    // Método para retornar um código postal por ID
    @Transactional
    public CodPostal getCodPostalById(String id) {
        return codPostalRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Código Postal não encontrado: " + id));
    }

    // Método para excluir um código postal por ID
    @Transactional
    public void deleteCodPostal(String id) {
        codPostalRepository.deleteById(id);
    }

    // Método para atualizar um código postal
    @Transactional
    public CodPostal updateCodPostal(String id, CodPostal codPostal) {
        CodPostal existingCodPostal = getCodPostalById(id); // Verifica se o código postal existe

        // Atualiza os campos do código postal
        existingCodPostal.setCodPostal(codPostal.getCodPostal());
        existingCodPostal.setDescricao(codPostal.getDescricao()); // Atualiza o campo "descricao" conforme necessário

        // Salva as alterações
        return codPostalRepository.save(existingCodPostal);
    }
}
