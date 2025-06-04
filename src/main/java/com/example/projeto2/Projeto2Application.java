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

        // Criação de um scanner para ler a entrada do usuário
        Scanner scanner = new Scanner(System.in);

        // Solicitar e ler os dados do cliente na linha de comando
        System.out.print("Digite o ID do cliente: ");
        Long idCliente = scanner.nextLong();  // Lê o ID do cliente

        scanner.nextLine();  // Limpar o buffer após ler o número

        System.out.print("Digite o nome do cliente: ");
        String nome = scanner.nextLine();  // Lê o nome do cliente

        System.out.print("Digite o NIF do cliente: ");
        String nif = scanner.nextLine();  // Lê o NIF do cliente

        System.out.print("Digite o contacto do cliente: ");
        String contacto = scanner.nextLine();  // Lê o contacto do cliente

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

        // Fechar o scanner
        scanner.close();
    }
}
