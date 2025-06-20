package com.example.projeto2.Desktop;

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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class ReceptionistDashboardController implements Initializable {

    @FXML
    private ListView partsListView;

    private BorderPane mainLayout; // Injected from MainController

    private final ApplicationContext applicationContext;
    private final PecaService pecaService;

    @Autowired
    public ReceptionistDashboardController(ApplicationContext applicationContext, PecaService pecaService) {
        this.applicationContext = applicationContext;
        this.pecaService = pecaService;
    }

    public void setMainLayout(BorderPane mainLayout) {
        this.mainLayout = mainLayout;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Load parts from database and populate the list view
        // This part remains, but sidebar loading is removed
    }

    @FXML
    private void handleCreateClient() {
        loadContent("/create-client.fxml");
    }

    @FXML
    private void handleScheduleRepair() {
        loadContent("/schedule-repair.fxml");
    }

    @FXML
    private void handleCreateReceipt() {
        loadContent("/create-invoice.fxml");
    }

    @FXML
    private void handleOrderParts() {
        loadContent("/order-part.fxml");
    }

    private void loadContent(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            loader.setControllerFactory(applicationContext::getBean);
            Parent content = loader.load();
            if (mainLayout != null) {
                mainLayout.setCenter(content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}