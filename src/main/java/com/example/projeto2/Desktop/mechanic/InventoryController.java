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

@Component
public class InventoryController {

    @FXML
    private VBox inventoryList;

    private final PecaService pecaService;
    private final ApplicationContext applicationContext; // Inject ApplicationContext

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

        inventoryList.getChildren().clear(); // Clear existing items

        allPecas.forEach(peca -> {
            HBox inventoryItem = new HBox(10);
            inventoryItem.setStyle("-fx-padding: 10px; -fx-spacing: 10px; -fx-background-color: #2d2d2d; -fx-background-radius: 10;");
            inventoryItem.setPrefHeight(60.0); // Adjust height as needed

            VBox itemInfo = new VBox(5);
            Label itemName = new Label(peca.getNome());
            itemName.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");

            // Convert BigDecimal to integer
            int stockQuantity = peca.getQtd() != null ? peca.getQtd().intValue() : 0;
            Label stockInfo = new Label(String.format("Stock: %d unidades restantes", stockQuantity));
            stockInfo.setStyle("-fx-text-fill: #ff6b35; -fx-font-size: 12px;");

            itemInfo.getChildren().addAll(itemName, stockInfo);
            HBox.setHgrow(itemInfo, javafx.scene.layout.Priority.ALWAYS);

            Button orderButton = new Button("Pedir Peça");
            orderButton.setStyle("-fx-background-color: linear-gradient(to right, #ff6b35, #ff9a00); -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 5 15 5 15; -fx-background-radius: 5;");
            orderButton.setOnAction(event -> handleOrderPart(peca)); // Add action for the button

            inventoryItem.getChildren().addAll(itemInfo, orderButton);
            inventoryList.getChildren().add(inventoryItem);
        });
    }

    // Method to handle ordering a part
    private void handleOrderPart(Peca peca) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mechanic/order-part.fxml"));
            loader.setControllerFactory(applicationContext::getBean); // Use Spring context for controller factory
            Parent modalContent = loader.load();

            OrderPartController controller = loader.getController();
            Stage modalStage = new Stage();
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.setTitle("Encomendar Peça");
            modalStage.setScene(new Scene(modalContent));
            controller.setDialogStage(modalStage);
            controller.setPartName(peca.getNome()); // Pass the part name to the modal

            modalStage.showAndWait();

            // After the modal is closed, you can check if an order was placed
            if (controller.isOrderPlaced()) {
                System.out.println("Order placed for part: " + peca.getNome());
                // You might want to refresh the inventory list or update the UI
                // loadInventoryItems();
            } else {
                System.out.println("Order cancelled for part: " + peca.getNome());
            }

        } catch (IOException e) {
            e.printStackTrace();
            // Handle error loading modal
        }
    }
}