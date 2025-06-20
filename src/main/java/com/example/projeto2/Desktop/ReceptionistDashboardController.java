package com.example.projeto2.Desktop;

import com.example.projeto2.Services.PecaService;
import com.example.projeto2.Desktop.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import com.example.projeto2.Services.PecaService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Button;

@Component
public class ReceptionistDashboardController implements Initializable {

    @FXML
    private ListView partsListView;

    @FXML
    private Button dashboardButton;
    @FXML
    private Button appointmentsButton;
    @FXML
    private Button inventoryButton;
    @FXML
    private Button createClientButton;
    @FXML
    private Button createInvoiceButton;
    @FXML
    private Button logoutButton;

    private final ApplicationContext applicationContext;
    private final PecaService pecaService;

    @Autowired
    public ReceptionistDashboardController(ApplicationContext applicationContext, PecaService pecaService) {
        this.applicationContext = applicationContext;
        this.pecaService = pecaService;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Load parts from database and populate the list view
        // This part remains, but sidebar loading is removed
    }

    @FXML
    private void handleCreateClient() {
        try {
            SceneManager.switchScene("/create-client.fxml", "Criar Cliente", createClientButton);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleScheduleRepair() {
        try {
            SceneManager.switchScene("/schedule-repair.fxml", "Agendar Reparação", appointmentsButton);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCreateReceipt() {
        try {
            SceneManager.switchScene("/create-invoice.fxml", "Criar Fatura", createInvoiceButton);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleOrderParts() {
        try {
            SceneManager.switchScene("/mechanic/order-part.fxml", "Encomendar Peças", inventoryButton); // Assuming inventory button is used for ordering parts
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Sidebar button actions
    @FXML
    public void onDashboardButtonClick() {
        try {
            SceneManager.switchScene("/receptionist-dashboard.fxml", "Dashboard", dashboardButton);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onAppointmentsButtonClick() {
        try {
            SceneManager.switchScene("/schedule-repair.fxml", "Agendamentos", appointmentsButton);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onInventoryButtonClick() {
        try {
            SceneManager.switchScene("/mechanic/inventory.fxml", "Inventário", inventoryButton);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onCreateClientButtonClick() {
        try {
            SceneManager.switchScene("/create-client.fxml", "Criar Cliente", createClientButton);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onCreateInvoiceButtonClick() {
        try {
            SceneManager.switchScene("/create-invoice.fxml", "Criar Fatura", createInvoiceButton);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onLogoutButtonClick() {
        try {
            SceneManager.switchScene("/login.fxml", "Login", logoutButton);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}