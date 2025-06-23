package com.example.projeto2.Desktop.mechanic;

import com.example.projeto2.Desktop.MainController;
import com.example.projeto2.Desktop.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SidebarController {

    @FXML
    private VBox sidebar;

    private final ApplicationContext context;
    private MainController mainController; // Reference to MainController
    private BorderPane mainLayout; // Reference to the main layout

    @Autowired
    public SidebarController(ApplicationContext context) {
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
        loadContent("/mechanic/dashboard.fxml");
    }

    @FXML
    public void onInventoryButtonClick() {
        loadContent("/mechanic/inventory.fxml"); // Assuming inventory is shared or a generic one
    }

    @FXML
    public void onRepairsButtonClick() {
        // "Reparações" maps to "Order Parts"
        loadContent("/mechanic/services.fxml");
    }

    @FXML
    public void onReceiveOrderPartButtonClick() {
        loadContent("/mechanic/receive-order-part.fxml");
    }

    @FXML
    public void onOrderPartButtonClick() {
        loadContent("/mechanic/order-part.fxml");
    }

    @FXML
    public void onLogoutButtonClick() {
        try {
            SceneManager.switchScene("/login.fxml", "Login", sidebar);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadContent(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            loader.setControllerFactory(context::getBean);
            Parent content = loader.load();
            if (mainController != null && mainController.getContentContainer() != null) {
                mainController.getContentContainer().getChildren().setAll(content);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}