package com.example.projeto2.Desktop;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class ReceptionistDashboardController implements Initializable {

    @FXML
    private VBox sidebar;

    @FXML
    private Button createClientButton;

    @Autowired
    private ApplicationContext applicationContext;

    @FXML
    private Button scheduleRepairButton;

    @FXML
    private Button createReceiptButton;

    @FXML
    private Button orderPartsButton;

    @FXML
    private ListView partsListView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Load parts from database and populate the list view
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mechanic/sidebar.fxml"));
            loader.setControllerFactory(applicationContext::getBean);
            Parent sidebarContent = loader.load();
            sidebar.getChildren().add(sidebarContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCreateClient() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/create-client.fxml"));
        loader.setControllerFactory(applicationContext::getBean);
        Parent root = loader.load();
        //Stage stage = new Stage();
        //stage.setScene(new Scene(root));
        //stage.show();
    }

    @FXML
    private void handleScheduleRepair() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/schedule-repair.fxml"));
        loader.setControllerFactory(applicationContext::getBean);
        Parent root = loader.load();
        //Stage stage = new Stage();
        //stage.setScene(new Scene(root));
        //stage.show();
    }

    @FXML
    private void handleCreateReceipt() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/create-invoice.fxml"));
        loader.setControllerFactory(applicationContext::getBean);
        Parent root = loader.load();
        //Stage stage = new Stage();
        //stage.setScene(new Scene(root));
        //stage.show();
    }

    @FXML
    private void handleOrderParts() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/order-part.fxml"));
        loader.setControllerFactory(applicationContext::getBean);
        Parent root = loader.load();
        //Stage stage = new Stage();
        //stage.setScene(new Scene(root));
        //stage.show();
    }
}