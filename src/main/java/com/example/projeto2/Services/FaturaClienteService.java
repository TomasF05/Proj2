package com.example.projeto2.Services;

import com.example.projeto2.Repo.FaturaClienteRepository;
import com.example.projeto2.Tables.FaturaCliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class FaturaClienteService {

    @Autowired
    private FaturaClienteRepository faturaClienteRepository;

    // Método para salvar uma fatura de cliente
    @Transactional
    public FaturaCliente saveFaturaCliente(FaturaCliente faturaCliente) {
        return faturaClienteRepository.save(faturaCliente);
    }

    // Método para retornar todas as faturas de clientes
    @Transactional
    public List<FaturaCliente> getAllFaturasClientes() {
        return faturaClienteRepository.findAll();
    }

    // Método para retornar uma fatura de cliente por ID
    @Transactional
    public FaturaCliente getFaturaClienteById(BigDecimal id) {
        return faturaClienteRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("FaturaCliente não encontrado: " + id));
    }

    // Método para excluir uma fatura de cliente por ID
    @Transactional
    public void deleteFaturaCliente(BigDecimal id) {
        faturaClienteRepository.deleteById(id);
    }

    // Método para atualizar uma fatura de cliente
    @Transactional
    public FaturaCliente updateFaturaCliente(BigDecimal id, FaturaCliente faturaCliente) {
        FaturaCliente existingFatura = getFaturaClienteById(id); // Verifica se a fatura existe

        // Atualiza os campos da fatura de cliente
        existingFatura.setData(faturaCliente.getData());
        existingFatura.setValorTotal(faturaCliente.getValorTotal());
        existingFatura.setIdCliente(faturaCliente.getIdCliente());

        // Salva as alterações
        return faturaClienteRepository.save(existingFatura);
    }
    @Transactional
    public List<FaturaCliente> getAllFaturasCliente() {
        return faturaClienteRepository.findAll();
    }

}
