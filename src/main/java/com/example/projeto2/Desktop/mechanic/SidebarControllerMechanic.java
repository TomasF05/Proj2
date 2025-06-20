package com.example.projeto2.Desktop.mechanic;

import com.example.projeto2.Desktop.MainController;
import com.example.projeto2.Desktop.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

@Component
public class SidebarControllerMechanic {

    @FXML
    private VBox sidebar;

    private final ApplicationContext context;
    private MainController mainController; // Reference to MainController
    private BorderPane mainLayout; // Reference to the main layout

    @Autowired
    public SidebarControllerMechanic(ApplicationContext context) {
        this.context = context;
    }

    public void setMainLayout(BorderPane mainLayout) {
        this.mainLayout = mainLayout;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void initialize() {
        // DO NOT load content here. This method is called too early.
        // The MainController will handle loading the initial dashboard.
    }

    @FXML
    public void onDashboardButtonClick() {
        loadDashboard();
    }

    @FXML
    public void onInventoryButtonClick() {
        loadInventory();
    }

    @FXML
    public void onServicesButtonClick() {
        loadServices();
    }

    @FXML
    public void onOrdersButtonClick() {
        loadOrders();
    }

    private void loadDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mechanic/dashboard.fxml"));
            loader.setControllerFactory(context::getBean);
            Parent dashboardContent = loader.load();
            mainController.getContentContainer().getChildren().setAll(dashboardContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadInventory() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mechanic/inventory.fxml"));
            loader.setControllerFactory(context::getBean);
            Parent inventoryContent = loader.load();
            mainController.getContentContainer().getChildren().setAll(inventoryContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadServices() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mechanic/services.fxml"));
            loader.setControllerFactory(context::getBean);
            Parent servicesContent = loader.load();
            mainController.getContentContainer().getChildren().setAll(servicesContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadOrders() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mechanic/order-part.fxml"));
            loader.setControllerFactory(context::getBean);
            Parent ordersContent = loader.load();
            mainController.getContentContainer().getChildren().setAll(ordersContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onLogoutButtonClick() {
        try {
            SceneManager.switchScene("/login.fxml", "Login", sidebar);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}