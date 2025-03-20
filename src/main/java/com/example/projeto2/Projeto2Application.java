package com.example.projeto2;

import com.example.projeto2.Tables.Cliente;
import com.example.projeto2.Services.ClienteService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class Projeto2Application {
    public static void main(String[] args) {
        // Inicializa o contexto Spring
        ApplicationContext context = SpringApplication.run(Projeto2Application.class, args);

        // Obtém o serviço de cliente
        ClienteService clienteService = context.getBean(ClienteService.class);

        Long idCliente = 1L;
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

    }
}
