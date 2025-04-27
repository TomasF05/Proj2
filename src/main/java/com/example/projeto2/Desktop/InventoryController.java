package com.example.projeto2.Desktop;

import com.example.projeto2.Services.PecaService;
import com.example.projeto2.Tables.Peca;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class InventoryController {

    @FXML
    private VBox inventoryList;

    private final PecaService pecaService;

    @Autowired
    public InventoryController(PecaService pecaService) {
        this.pecaService = pecaService;
    }

    @FXML
    public void initialize() {
        loadInventoryItems();
    }

    private void loadInventoryItems() {
        List<Peca> allPecas = pecaService.getAllPecas();

        allPecas.forEach(peca -> {
            HBox inventoryItem = new HBox(10);
            inventoryItem.setStyle("-fx-padding: 10px; -fx-spacing: 10px;");

            VBox itemInfo = new VBox(5);
            Label itemName = new Label(peca.getNome());
            itemName.setStyle("-fx-text-fill: white;");

            // Convert BigDecimal to integer
            int stockQuantity = peca.getQtd() != null ? peca.getQtd().intValue() : 0;
            Label stockInfo = new Label(String.format("Stock: %d unidades restantes", stockQuantity));
            stockInfo.setStyle("-fx-text-fill: #cccccc;");

            itemInfo.getChildren().addAll(itemName, stockInfo);

            inventoryItem.getChildren().addAll(itemInfo);
            inventoryList.getChildren().add(inventoryItem);
        });
    }
}