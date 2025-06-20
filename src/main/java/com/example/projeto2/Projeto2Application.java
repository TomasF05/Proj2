package com.example.projeto2;

import com.example.projeto2.Tables.*;
import com.example.projeto2.Services.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.math.BigDecimal;
import java.util.List;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.projeto2.Repo")
@EntityScan(basePackages = "com.example.projeto2.Tables")
public class Projeto2Application extends Application {

    private static ConfigurableApplicationContext springContext;
    private static String[] savedArgs;

    public static ConfigurableApplicationContext getSpringContext() {
        return springContext;
    }

    public static void main(String[] args) {
        savedArgs = args;
        Application.launch(Projeto2Application.class, args);
    }

    @Override
    public void init() throws Exception {
        if (savedArgs == null) {
            savedArgs = new String[]{};  // providencia um array vazio se não houver argumentos
        }
        springContext = new SpringApplicationBuilder(Projeto2Application.class)
                .headless(false)
                .web(org.springframework.boot.WebApplicationType.NONE)
                .run(savedArgs);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
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

        if (funcionarioService.getAllFuncionarios().isEmpty()) {
            System.out.println("Creating sample funcionario for testing...");

            Funcionario sampleFuncionario = new Funcionario();
            sampleFuncionario.setIdFuncionario(new BigDecimal(1));
            sampleFuncionario.setNome("Admin");
            sampleFuncionario.setTipo(new BigDecimal(1));
            sampleFuncionario.setUsername("admin");
            sampleFuncionario.setPassword("admin");

            funcionarioService.saveFuncionario(sampleFuncionario);
            System.out.println("Sample funcionario created. Use 'admin' / 'admin' to login.");
        }
    }

    @Override
    public void stop() {
        springContext.close();
        Platform.exit();
    }

    public static void runConsoleMode(String[] args) {
        ConfigurableApplicationContext context = org.springframework.boot.SpringApplication.run(Projeto2Application.class, args);
        runDatabaseExamples(context);
        context.close();
    }

    private static void runDatabaseExamples(ApplicationContext context) {
        ClienteService clienteService = context.getBean(ClienteService.class);

        BigDecimal idCliente = BigDecimal.valueOf(1);
        Cliente novoCliente = new Cliente();
        novoCliente.setIdCliente(idCliente);
        novoCliente.setNome("Tone");
        novoCliente.setNif("999999999");
        novoCliente.setContacto("999999999");

        clienteService.saveCliente(novoCliente);
        System.out.println("Novo cliente adicionado com sucesso!");

        List<Cliente> clientes = clienteService.getAllClientes();
        System.out.println("\nLista de Clientes:");
        for (Cliente cliente : clientes) {
            System.out.printf("ID: %s | Nome: %s | NIF: %s | Contato: %s%n",
                    cliente.getIdCliente(), cliente.getNome(), cliente.getNif(), cliente.getContacto());
        }
    }
}
