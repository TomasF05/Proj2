package com.example.projeto2.Services;

import com.example.projeto2.Repo.EncomendaFornecedorRepository;
import com.example.projeto2.Repo.LinhaEncFornecedorRepository;
import com.example.projeto2.Tables.EncomendaFornecedor;
import com.example.projeto2.Tables.Fornecedor;
import com.example.projeto2.Tables.LinhaEncFornecedor;
import com.example.projeto2.Tables.LinhaEncFornecedorId;
import com.example.projeto2.Tables.Peca;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class EncomendaFornecedorService {

    @Autowired
    private EncomendaFornecedorRepository encomendaFornecedorRepository;

    @Autowired
    private LinhaEncFornecedorRepository linhaEncFornecedorRepository;

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

    // Method to create a new supplier order
    @Transactional
    public EncomendaFornecedor createSupplierOrder(Fornecedor supplier) {
        EncomendaFornecedor newOrder = new EncomendaFornecedor();
        newOrder.setData(Date.valueOf(LocalDate.now())); // Set current date
        newOrder.setIdFornecedor(supplier.getId()); // Set supplier ID
        newOrder.setValorTotal(BigDecimal.ZERO); // Initialize total value to zero
        return encomendaFornecedorRepository.save(newOrder);
    }

    // Method to add a part line item to a supplier order
    @Transactional
    public LinhaEncFornecedor addPartToSupplierOrder(EncomendaFornecedor order, Peca part, BigDecimal quantity) {
        if (order == null || part == null || quantity == null || quantity.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Invalid order, part, or quantity.");
        }

        LinhaEncFornecedor linha = new LinhaEncFornecedor();
        LinhaEncFornecedorId linhaId = new LinhaEncFornecedorId(part.getIdPeca(), order.getIdEncFornecedor());
        linha.setId(linhaId);
        linha.setQtd(quantity);

        // Calculate line item total (assuming Peca has a getPreco() method)
        BigDecimal lineTotal = part.getPreco().multiply(quantity);
        linha.setValorTotal(lineTotal);

        // Save the line item
        LinhaEncFornecedor savedLinha = linhaEncFornecedorRepository.save(linha);

        // Update the total value of the order header
        order.setValorTotal(order.getValorTotal().add(lineTotal));
        encomendaFornecedorRepository.save(order); // Save the updated order header

        return savedLinha;
    }

    public List<EncomendaFornecedor> getUnreceivedOrders() {
        // Implement logic to get all EncomendaFornecedor that are not fully received
        // This might involve checking if the quantity received for each LinhaEncFornecedor
        // is less than the quantity ordered.
        List<EncomendaFornecedor> allOrders = encomendaFornecedorRepository.findAll();
        return allOrders.stream()
                .filter(order -> order.getLinhasEncFornecedor() != null && order.getLinhasEncFornecedor().stream()
                        .anyMatch(linha -> linha.getQtdRecebida() == null || linha.getQtdRecebida().compareTo(linha.getQtd()) < 0))
                .collect(Collectors.toList());
    }
}