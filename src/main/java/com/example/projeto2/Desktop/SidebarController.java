package com.example.projeto2.Desktop;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import javafx.fxml.FXMLLoader;

@Component
public class SidebarController {

    @FXML
    private VBox sidebar;

    private final ApplicationContext context;
    private BorderPane mainLayout;

    @Autowired
    public SidebarController(ApplicationContext context) {
        this.context = context;
    }

    @FXML
    public void initialize() {
        // Set up button actions
        setupButtonActions();
    }

    public void setMainLayout(BorderPane mainLayout) {
        this.mainLayout = mainLayout;
    }

    private void setupButtonActions() {
        // Load initial content
        if (mainLayout != null) {
            loadDashboard();
        }
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dashboard.fxml"));
            loader.setControllerFactory(context::getBean);
            Parent dashboardContent = loader.load();
            mainLayout.setCenter(dashboardContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadInventory() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/inventory.fxml"));
            loader.setControllerFactory(context::getBean);
            Parent inventoryContent = loader.load();
            mainLayout.setCenter(inventoryContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadServices() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/services.fxml"));
            loader.setControllerFactory(context::getBean);
            Parent servicesContent = loader.load();
            mainLayout.setCenter(servicesContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadOrders() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/orders.fxml"));
            loader.setControllerFactory(context::getBean);
            Parent ordersContent = loader.load();
            mainLayout.setCenter(ordersContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}