package com.example.projeto2.Desktop;

import com.example.projeto2.Services.PecaService;
import com.example.projeto2.Desktop.MainController;
import com.example.projeto2.Services.PecaService;
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
    private MainController mainController; // Inject MainController

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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/create-client.fxml"));
            fxmlLoader.setControllerFactory(applicationContext::getBean);
            Parent createClientView = fxmlLoader.load();
            mainController.getContentContainer().getChildren().setAll(createClientView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleScheduleRepair() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/schedule-repair.fxml"));
            fxmlLoader.setControllerFactory(applicationContext::getBean);
            Parent scheduleRepairView = fxmlLoader.load();
            mainController.getContentContainer().getChildren().setAll(scheduleRepairView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCreateReceipt() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/create-invoice.fxml"));
            fxmlLoader.setControllerFactory(applicationContext::getBean);
            Parent createInvoiceView = fxmlLoader.load();
            mainController.getContentContainer().getChildren().setAll(createInvoiceView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleOrderParts() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/mechanic/order-part.fxml"));
            fxmlLoader.setControllerFactory(applicationContext::getBean);
            Parent orderPartsView = fxmlLoader.load();
            mainController.getContentContainer().getChildren().setAll(orderPartsView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Sidebar button actions
    @FXML
    public void onDashboardButtonClick() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/receptionist-dashboard.fxml"));
            fxmlLoader.setControllerFactory(applicationContext::getBean);
            Parent dashboardView = fxmlLoader.load();
            mainController.getContentContainer().getChildren().setAll(dashboardView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onAppointmentsButtonClick() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/schedule-repair.fxml"));
            fxmlLoader.setControllerFactory(applicationContext::getBean);
            Parent appointmentsView = fxmlLoader.load();
            mainController.getContentContainer().getChildren().setAll(appointmentsView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onInventoryButtonClick() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/mechanic/inventory.fxml"));
            fxmlLoader.setControllerFactory(applicationContext::getBean);
            Parent inventoryView = fxmlLoader.load();
            mainController.getContentContainer().getChildren().setAll(inventoryView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onCreateClientButtonClick() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/create-client.fxml"));
            fxmlLoader.setControllerFactory(applicationContext::getBean);
            Parent createClientView = fxmlLoader.load();
            mainController.getContentContainer().getChildren().setAll(createClientView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onCreateInvoiceButtonClick() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/create-invoice.fxml"));
            fxmlLoader.setControllerFactory(applicationContext::getBean);
            Parent createInvoiceView = fxmlLoader.load();
            mainController.getContentContainer().getChildren().setAll(createInvoiceView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onLogoutButtonClick() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/login.fxml"));
            fxmlLoader.setControllerFactory(applicationContext::getBean);
            Parent loginView = fxmlLoader.load();
            mainController.getContentContainer().getChildren().setAll(loginView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}