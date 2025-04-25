package com.example.projeto2;

import com.example.projeto2.Tables.*;
import com.example.projeto2.Services.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import java.math.BigDecimal;
import java.util.List;

@SpringBootApplication(scanBasePackages = {"com.example.projeto2", "com.example.projeto2.Desktop"})
public class Projeto2Application extends Application {
    
    private static ConfigurableApplicationContext springContext;
    private static String[] savedArgs;
    
    // Static accessor for the Spring context
    public static ConfigurableApplicationContext getSpringContext() {
        return springContext;
    }
    
    // This is needed for running with JAR
    public static void main(String[] args) {
        savedArgs = args;
        Application.launch(Projeto2Application.class, args);
    }
    
    @Override
    public void init() throws Exception {
        springContext = SpringApplication.run(Projeto2Application.class, savedArgs);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            // Create a sample funcionario for testing if none exists
            createSampleFuncionarioIfNeeded();
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
            loader.setControllerFactory(springContext::getBean);
            Parent root = loader.load();
            
            primaryStage.setTitle("AutoPro Workshop Management");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    private void createSampleFuncionarioIfNeeded() {
        FuncionarioService funcionarioService = springContext.getBean(FuncionarioService.class);
        
        // Check if any funcionarios exist
        if (funcionarioService.getAllFuncionarios().isEmpty()) {
            System.out.println("Creating sample funcionario for testing...");
            
            // Create a sample funcionario
            Funcionario sampleFuncionario = new Funcionario();
            sampleFuncionario.setIdFuncionario(new BigDecimal(1));
            sampleFuncionario.setNome("Admin");
            sampleFuncionario.setTipo(new BigDecimal(1)); // Admin type
            sampleFuncionario.setUsername("admin");
            sampleFuncionario.setPassword("admin");
            
            // Save to database
            funcionarioService.saveFuncionario(sampleFuncionario);
            
            System.out.println("Sample funcionario created successfully. Use username 'admin' and password 'admin' to login.");
        } else {
            System.out.println("Funcionarios already exist in the database. No need to create samples.");
            
            // Debug: Print all funcionarios
            List<Funcionario> funcionarios = funcionarioService.getAllFuncionarios();
            System.out.println("Available funcionarios:");
            for (Funcionario f : funcionarios) {
                System.out.println("Username: " + f.getUsername() + ", Password: " + f.getPassword());
            }
        }
    }
    
    @Override
    public void stop() {
        springContext.close();
        Platform.exit();
    }
    
    // Alternative main method without JavaFX to run database examples
    public static void runConsoleMode(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Projeto2Application.class, args);
        runDatabaseExamples(context);
        context.close();
    }
    
    // This method can be used to run database examples separately
    private static void runDatabaseExamples(ApplicationContext context) {
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
    }
}
