package com.example.projeto2.Desktop;

import com.example.projeto2.Projeto2Application;
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
import com.example.projeto2.Desktop.SceneManager;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Component
public class LoginController {
    private static String loggedInUsername;

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

        boolean authenticated = false;

        try {
            Optional<Funcionario> funcionarioOpt = funcionarioService.authenticateFuncionario(username, password);

            if (funcionarioOpt.isPresent()) {
                authenticated = true;
            } else {
                List<Funcionario> funcionarios = funcionarioService.getAllFuncionarios();

                for (Funcionario funcionario : funcionarios) {
                    if (funcionario.getUsername() != null && funcionario.getUsername().equalsIgnoreCase(username) &&
                            funcionario.getPassword() != null && funcionario.getPassword().equals(password)) {
                        authenticated = true;
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
                // Determine the user type
                Funcionario usuario = funcionarioService.findByUsername(username);
                LoginController.setLoggedInUsername(username);

                if (usuario.getTipo().equals(new BigDecimal(1))) {
                    // Load mechanic dashboard
                    SceneManager.switchScene("/mechanic/dashboard.fxml", "Mechanic Dashboard", (Node) event.getSource());
                } else if (usuario.getTipo().equals(new BigDecimal(2))) {
                    // Load receptionist dashboard
                    SceneManager.switchScene("/receptionist-dashboard.fxml", "Receptionist Dashboard", (Node) event.getSource());
                } else {
                    // Default to main page
                    SceneManager.switchScene("/main.fxml", "Main Page", (Node) event.getSource());
                }
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

    public static String getLoggedInUsername() {
        return loggedInUsername;
    }

    public static void setLoggedInUsername(String loggedInUsername) {
        LoginController.loggedInUsername = loggedInUsername;
    }
}