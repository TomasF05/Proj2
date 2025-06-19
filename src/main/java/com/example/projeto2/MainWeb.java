package com.example.projeto2;

/**
 * Classe principal para iniciar apenas a aplicação web
 */
public class MainWeb {
    public static void main(String[] args) {
        System.setProperty("java.awt.headless", "true"); // Força modo headless (sem GUI)
        Projeto2ApplicationWeb.main(args);
    }
}
