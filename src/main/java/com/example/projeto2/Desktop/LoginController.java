package com.example.projeto2.Desktop;

import com.example.projeto2.Services.FuncionarioService;
import com.example.projeto2.Tables.Funcionario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private final FuncionarioService funcionarioService;

    @Autowired
    public LoginController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @FXML
    protected void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Login Failed", "Username and password cannot be empty.");
            return;
        }

        // Debug output
        System.out.println("Attempting login with username: " + username);
        System.out.println("Number of funcionarios in DB: " + funcionarioService.getAllFuncionarios().size());
        
        // Debug: Print all funcionarios from DB
        for (Funcionario f : funcionarioService.getAllFuncionarios()) {
            System.out.println("DB User: " + f.getUsername() + ", Password: " + f.getPassword());
        }
        
        // Try the direct authentication method first
        boolean authenticated = false;
        
        try {
            Optional<Funcionario> funcionarioOpt = funcionarioService.authenticateFuncionario(username, password);
            
            if (funcionarioOpt.isPresent()) {
                authenticated = true;
                System.out.println("Authentication successful using repository method!");
            } else {
                System.out.println("Authentication failed using repository method");
                
                // Fallback to manual authentication
                List<Funcionario> funcionarios = funcionarioService.getAllFuncionarios();
                
                for (Funcionario funcionario : funcionarios) {
                    System.out.println("Checking against: " + funcionario.getUsername());
                    
                    boolean usernameMatch = funcionario.getUsername() != null && funcionario.getUsername().equalsIgnoreCase(username);
                    boolean passwordMatch = funcionario.getPassword() != null && funcionario.getPassword().equals(password);
                    
                    System.out.println("Username match: " + usernameMatch + ", Password match: " + passwordMatch);
                    
                    if (usernameMatch && passwordMatch) {
                        authenticated = true;
                        System.out.println("Authentication successful using manual check!");
                        break;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error during authentication: " + e.getMessage());
            e.printStackTrace();
        }

        if (authenticated) {
            try {
                // Use the SceneManager to switch to the dashboard
                SceneManager.switchScene("/dashboard.fxml", "AutoPro Dashboard", (Node) event.getSource());
            } catch (IOException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error", "Navigation Error", 
                          "Failed to load dashboard: " + e.getMessage());
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Login Failed", "Invalid username or password.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}