package com.example.projeto2.Services;

import com.example.projeto2.Repo.FuncionarioRepository;
import com.example.projeto2.Tables.Funcionario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    // Método para salvar um funcionário
    @Transactional
    public Funcionario saveFuncionario(Funcionario funcionario) {
        return funcionarioRepository.save(funcionario);
    }

    // Método para retornar todos os funcionários
    @Transactional
    public List<Funcionario> getAllFuncionarios() {
        return funcionarioRepository.findAll();
    }

    // Método para retornar um funcionário por ID
    @Transactional
    public Funcionario getFuncionarioById(BigDecimal id) {
        return funcionarioRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Funcionário não encontrado: " + id));
    }

    // Método para excluir um funcionário por ID
    @Transactional
    public void deleteFuncionario(BigDecimal id) {
        funcionarioRepository.deleteById(id);
    }

    // Método para atualizar um funcionário
    @Transactional
    public Funcionario updateFuncionario(BigDecimal id, Funcionario funcionario) {
        Funcionario existingFuncionario = getFuncionarioById(id); // Verifica se o funcionário existe

        // Atualiza os campos do funcionário
        existingFuncionario.setNome(funcionario.getNome());
        existingFuncionario.setTipo(funcionario.getTipo());
        existingFuncionario.setUsername(funcionario.getUsername());
        existingFuncionario.setPassword(funcionario.getPassword());

        // Salva as alterações
        return funcionarioRepository.save(existingFuncionario);
    }
}
