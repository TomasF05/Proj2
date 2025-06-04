package com.example.projeto2.Services;

import com.example.projeto2.Repo.TipoFuncionarioRepository;
import com.example.projeto2.Tables.TipoFuncionario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TipoFuncionarioService {

    @Autowired
    private TipoFuncionarioRepository tipoFuncionarioRepository;

    // Salva ou atualiza um tipo de funcionário
    @Transactional
    public BigDecimal saveTipoFuncionario(TipoFuncionario tipoFuncionario) {
        TipoFuncionario savedTipoFuncionario = tipoFuncionarioRepository.save(tipoFuncionario);
        return savedTipoFuncionario.getIdTipo();  // Retorna o ID do tipo de funcionário salvo
    }

    // Deleta um tipo de funcionário pelo ID
    @Transactional
    public void deleteTipoFuncionario(BigDecimal id) {
        tipoFuncionarioRepository.deleteById(id);
    }

    // Atualiza os dados de um tipo de funcionário existente
    @Transactional
    public void updateTipoFuncionario(BigDecimal id, TipoFuncionario tipoFuncionario) {
        TipoFuncionario existingTipoFuncionario = getTipoFuncionarioById(id);
        existingTipoFuncionario.setTipoFuncionario(tipoFuncionario.getTipoFuncionario());
        tipoFuncionarioRepository.save(existingTipoFuncionario);
    }

    // Retorna um tipo de funcionário pelo ID
    @Transactional
    public TipoFuncionario getTipoFuncionarioById(BigDecimal id) {
        return tipoFuncionarioRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Tipo de funcionário não encontrado para o id: " + id));
    }

    // Retorna todos os tipos de funcionários
    @Transactional
    public List<TipoFuncionario> getAllTipoFuncionarios() {
        return tipoFuncionarioRepository.findAll();
    }
}
