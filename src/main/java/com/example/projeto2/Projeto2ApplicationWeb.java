package com.example.projeto2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(scanBasePackages = {
        "com.example.projeto2.Web",
        "com.example.projeto2.config",
        "com.example.projeto2.Services",
        "com.example.projeto2.Tables",
        "com.example.projeto2.Repo"
})
public class Projeto2ApplicationWeb {
    private static ConfigurableApplicationContext springContext;

    public static ConfigurableApplicationContext getSpringContext() {
        return springContext;
    }

    public static void main(String[] args) {
        springContext = SpringApplication.run(Projeto2ApplicationWeb.class, args);
        System.out.println("Aplicação web iniciada!");
        System.out.println("Acesse: http://localhost:8080/login.html");
    }
}
