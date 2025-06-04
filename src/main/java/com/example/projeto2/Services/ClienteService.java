package com.example.projeto2.Services;

import com.example.projeto2.Repo.ClienteRepository;
import com.example.projeto2.Tables.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Transactional
    public Cliente saveCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Transactional
    public List<Cliente> getAllClientes() {
        return clienteRepository.findAll();
    }

    @Transactional
    public Cliente getClienteById(BigDecimal id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Cliente não encontrado: " + id));
    }

    @Transactional
    public void deleteCliente(BigDecimal id) {
        clienteRepository.deleteById(id);
    }
}
