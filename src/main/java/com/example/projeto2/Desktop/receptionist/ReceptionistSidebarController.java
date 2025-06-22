package com.example.projeto2.Desktop.receptionist;

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
public class ReceptionistSidebarController {

    @FXML
    private VBox sidebar;

    private final ApplicationContext context;
    private MainController mainController; 
    private BorderPane mainLayout;

    @Autowired
    public ReceptionistSidebarController(ApplicationContext context) {
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
    }

    @FXML
    public void onDashboardButtonClick() {
        loadContent("/receptionist-dashboard.fxml");
    }

    @FXML
    public void onCreateClientButtonClick() {
        loadContent("/create-client.fxml");
    }

    @FXML
    public void onCreateInvoiceButtonClick() {
        loadContent("/create-invoice.fxml");
    }

    @FXML
    public void onScheduleRepairButtonClick() {
        loadContent("/schedule-repair.fxml");
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