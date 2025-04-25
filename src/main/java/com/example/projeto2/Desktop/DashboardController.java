package com.example.projeto2.Desktop;

import com.example.projeto2.Services.AgendamentoService;
import com.example.projeto2.Services.ReparacaoService;
import com.example.projeto2.Tables.Agendamento;
import com.example.projeto2.Tables.Reparacao;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class DashboardController implements Initializable {

    private final AgendamentoService agendamentoService;
    private final ReparacaoService reparacaoService;

    @FXML
    private Button servicesButton;

    @FXML
    private Button inventoryButton;

    @Autowired
    public DashboardController(AgendamentoService agendamentoService, ReparacaoService reparacaoService) {
        this.agendamentoService = agendamentoService;
        this.reparacaoService = reparacaoService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("DashboardController initializing...");
        
        try {
            // Set up button actions if buttons are not null
            if (servicesButton != null) {
                System.out.println("Setting up services button action");
                servicesButton.setOnAction(event -> navigateToServices());
            } else {
                System.out.println("Services button is null!");
            }
            
            if (inventoryButton != null) {
                System.out.println("Setting up inventory button action");
                inventoryButton.setOnAction(event -> navigateToInventory());
            } else {
                System.out.println("Inventory button is null!");
            }
            
            // Initialize dashboard data
            loadDashboardData();
            System.out.println("Dashboard data loaded successfully");
        } catch (Exception e) {
            System.out.println("Error initializing DashboardController: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadDashboardData() {
        // This is where you'd load data from your services to display on the dashboard
        // For example:
        List<Agendamento> todaysAppointments = agendamentoService.getAllAgendamentos();
        List<Reparacao> activeRepairs = reparacaoService.getAllReparacoes();
        
        // Then update your UI elements with this data
        // For example: appointmentsLabel.setText("Today's appointments: " + todaysAppointments.size());
    }

    private void navigateToServices() {
        try {
            // Use the SceneManager to open the services window
            SceneManager.openNewWindow("/services.fxml", "AutoPro Services");
            
            // Optionally, close the current window
            // ((Stage) servicesButton.getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void navigateToInventory() {
        try {
            // Use the SceneManager to open the inventory window
            SceneManager.openNewWindow("/inventory.fxml", "AutoPro Inventory");
            
            // Optionally, close the current window
            // ((Stage) inventoryButton.getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}