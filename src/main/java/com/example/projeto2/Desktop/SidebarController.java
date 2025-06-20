package com.example.projeto2.Desktop;

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
    private BorderPane mainLayout;

    @Autowired
    public SidebarController(ApplicationContext context) {
        this.context = context;
    }

    public void setMainLayout(BorderPane mainLayout) {
        this.mainLayout = mainLayout;
    }

    @FXML
    public void onDashboardButtonClick() {
        loadContent("/receptionist-dashboard.fxml");
    }

    @FXML
    public void onAppointmentsButtonClick() {
        // Assuming "Agendamentos" maps to appointments
        loadContent("/schedule-repair.fxml"); // Or a dedicated appointments FXML
    }

    @FXML
    public void onInventoryButtonClick() {
        loadContent("/mechanic/inventory.fxml"); // Assuming inventory is shared or a generic one
    }

    @FXML
    public void onRepairsButtonClick() {
        // "Reparações" maps to "Order Parts"
        loadContent("/order-part.fxml");
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
            if (mainLayout != null) {
                mainLayout.setCenter(content);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}