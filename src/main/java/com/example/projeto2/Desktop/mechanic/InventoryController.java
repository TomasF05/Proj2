package com.example.projeto2.Desktop.mechanic;

import com.example.projeto2.Services.PecaService;
import com.example.projeto2.Tables.Peca;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


import com.example.projeto2.Desktop.MainController;
import com.example.projeto2.Desktop.SceneManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
@Component
public class InventoryController {

    @FXML
    private VBox inventoryList;

    @FXML
    private Button dashboardButton;
    @FXML
    private Button servicesButton;
    @FXML
    private Button inventoryButton;
    @FXML
    private Button ordersButton;
    @FXML
    private Button logoutButton;

    private final PecaService pecaService;
    private final ApplicationContext applicationContext;

    @Autowired
    public InventoryController(PecaService pecaService, ApplicationContext applicationContext) {
        this.pecaService = pecaService;
        this.applicationContext = applicationContext;
    }

    @FXML
    public void initialize() {
        loadInventoryItems();
    }

    private void loadInventoryItems() {
        List<Peca> allPecas = pecaService.getAllPecas();

        inventoryList.getChildren().clear();

        allPecas.forEach(peca -> {
            HBox inventoryItem = new HBox(10);
            inventoryItem.setStyle("-fx-padding: 10px; -fx-spacing: 10px; -fx-background-color: #2d2d2d; -fx-background-radius: 10;");
            inventoryItem.setPrefHeight(60.0);

            VBox itemInfo = new VBox(5);
            Label itemName = new Label(peca.getNome());
            itemName.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");

            int stockQuantity = peca.getQtd() != null ? peca.getQtd().intValue() : 0;
            Label stockInfo = new Label(String.format("Stock: %d unidades restantes", stockQuantity));
            stockInfo.setStyle("-fx-text-fill: #ff6b35; -fx-font-size: 12px;");

            itemInfo.getChildren().addAll(itemName, stockInfo);
            HBox.setHgrow(itemInfo, javafx.scene.layout.Priority.ALWAYS);

            Button orderButton = new Button("Pedir Peça");
            orderButton.setStyle("-fx-background-color: linear-gradient(to right, #ff6b35, #ff9a00); -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 5 15 5 15; -fx-background-radius: 5;");
            orderButton.setOnAction(event -> handleOrderPart(peca));

            inventoryItem.getChildren().addAll(itemInfo, orderButton);
            inventoryList.getChildren().add(inventoryItem);
        });
    }

    private void handleOrderPart(Peca peca) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mechanic/order-part.fxml"));
            loader.setControllerFactory(applicationContext::getBean);
            Parent modalContent = loader.load();

            OrderPartController controller = loader.getController();
            Stage modalStage = new Stage();
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.setTitle("Encomendar Peça");
            modalStage.setScene(new Scene(modalContent));
            controller.setDialogStage(modalStage);
            controller.setPartName(peca.getNome());

            modalStage.showAndWait();

            if (controller.isOrderPlaced()) {
                System.out.println("Order placed for part: " + peca.getNome());
            } else {
                System.out.println("Order cancelled for part: " + peca.getNome());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Sidebar button actions
    @FXML
    public void onDashboardButtonClick() {
        try {
            SceneManager.switchScene("/mechanic/dashboard.fxml", "Dashboard", dashboardButton);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onInventoryButtonClick() {
        try {
            SceneManager.switchScene("/mechanic/inventory.fxml", "Inventory", inventoryButton);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onServicesButtonClick() {
        try {
            SceneManager.switchScene("/mechanic/services.fxml", "Services", servicesButton);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onOrdersButtonClick() {
        try {
            SceneManager.switchScene("/mechanic/order-part.fxml", "Orders", ordersButton);
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