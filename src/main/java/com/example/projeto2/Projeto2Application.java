package com.example.projeto2;

import com.example.projeto2.Tables.*;
import com.example.projeto2.Services.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class Projeto2Application {
    public static void main(String[] args) {
        // Inicializa o contexto Spring
        ApplicationContext context = SpringApplication.run(Projeto2Application.class, args);

        // Obtém o serviço de cliente
        ClienteService clienteService = context.getBean(ClienteService.class);

        BigDecimal idCliente = BigDecimal.valueOf(1);
        String nome = "Tone";
        String nif = "999999999";
        String contacto = "999999999";

        // Criando um novo cliente com os dados inseridos pelo usuário
        Cliente novoCliente = new Cliente();
        novoCliente.setIdCliente(idCliente);  // Atribui manualmente o ID
        novoCliente.setNome(nome);
        novoCliente.setNif(nif);
        novoCliente.setContacto(contacto);

        // Inserindo o cliente no banco de dados
        clienteService.saveCliente(novoCliente);
        System.out.println("Novo cliente adicionado com sucesso!");

        // Busca todos os clientes no banco de dados
        List<Cliente> clientes = clienteService.getAllClientes();

        // Exibe os clientes no console
        System.out.println("\nLista de Clientes:");
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente encontrado.");
        } else {
            for (Cliente cliente : clientes) {
                System.out.println("Cliente ID: " + cliente.getIdCliente() +
                        " | Nome: " + cliente.getNome() +
                        " | NIF: " + cliente.getNif() +
                        " | Contato: " + cliente.getContacto());
            }
        }
        // ---------------- Peca Example (Parts) ----------------
        PecaService pecaService = context.getBean(PecaService.class);
        Peca novaPeca = new Peca();
        novaPeca.setIdPeca(BigDecimal.valueOf(1));
        novaPeca.setNome("Filtro de Ar");
        novaPeca.setReferencia("FA-123");
        novaPeca.setPreco(new BigDecimal("50.00"));
        novaPeca.setQtd(BigDecimal.valueOf(20));

        // Inserindo a peça no banco de dados
        pecaService.savePeca(novaPeca);
        System.out.println("Nova peça adicionada com sucesso!");

        // Atualizando a peça (ex.: alterando preço e quantidade)
        novaPeca.setPreco(new BigDecimal("55.00"));
        novaPeca.setQtd(BigDecimal.valueOf(25));
        pecaService.updatePeca(novaPeca.getIdPeca(), novaPeca);
        System.out.println("Peça atualizada com sucesso!");

        // Deletando a peça
        pecaService.deletePeca(novaPeca.getIdPeca());
        System.out.println("Peça deletada com sucesso!");

        // ---------------- Veiculo Example (Vehicle) ----------------
        VeiculoService veiculoService = context.getBean(VeiculoService.class);
        Veiculo novoVeiculo = new Veiculo();
        novoVeiculo.setIdVeiculo(BigDecimal.valueOf(1));
        novoVeiculo.setMatricula("12-34-AB");
        novoVeiculo.setMarca("Toyota");
        novoVeiculo.setModelo("Corolla");
        novoVeiculo.setAno(BigDecimal.valueOf(2020));
        novoVeiculo.setIdCliente(BigDecimal.valueOf(1));  // assumindo que o cliente existe

        // Inserindo o veículo no banco de dados
        veiculoService.saveVeiculo(novoVeiculo);
        System.out.println("Novo veículo adicionado com sucesso!");

        // Atualizando o veículo (ex.: alterando o modelo)
        novoVeiculo.setModelo("Camry");
        veiculoService.updateVeiculo(novoVeiculo.getIdVeiculo(), novoVeiculo);
        System.out.println("Veículo atualizado com sucesso!");

        // Deletando o veículo
        veiculoService.deleteVeiculo(novoVeiculo.getIdVeiculo());
        System.out.println("Veículo deletado com sucesso!");

        // ---------------- Fornecedor Example (Supplier) ----------------
        FornecedorService fornecedorService = context.getBean(FornecedorService.class);
        Fornecedor novoFornecedor = new Fornecedor();
        novoFornecedor.setId(BigDecimal.valueOf(1));
        novoFornecedor.setNome("Fornecedor A");
        novoFornecedor.setNif(BigDecimal.valueOf(123456789L));
        novoFornecedor.setContacto(BigDecimal.valueOf(987654321L));

        // Inserindo o fornecedor no banco de dados
        fornecedorService.saveFornecedor(novoFornecedor);
        System.out.println("Novo fornecedor adicionado com sucesso!");

        // Atualizando o fornecedor (ex.: alterando o nome)
        novoFornecedor.setNome("Fornecedor A Atualizado");
        fornecedorService.updateFornecedor(novoFornecedor.getId(), novoFornecedor);
        System.out.println("Fornecedor atualizado com sucesso!");

        // Deletando o fornecedor
        fornecedorService.deleteFornecedor(novoFornecedor.getId());
        System.out.println("Fornecedor deletado com sucesso!");

    }
}
