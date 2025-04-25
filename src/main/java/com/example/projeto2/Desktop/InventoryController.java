package com.example.projeto2.Desktop;

import com.example.projeto2.Services.PecaService;
import com.example.projeto2.Tables.Peca;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class InventoryController implements Initializable {

    @FXML
    private TableView<Peca> inventoryTable;
    
    private final PecaService pecaService;
    
    @Autowired
    public InventoryController(PecaService pecaService) {
        this.pecaService = pecaService;
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize the columns in the table
        setupTableColumns();
        
        // Load inventory data from the database
        loadInventoryData();
    }
    
    private void setupTableColumns() {
        // Clear existing columns
        inventoryTable.getColumns().clear();
        
        // Create and configure the Name column
        TableColumn<Peca, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        nameColumn.setPrefWidth(200);
        
        // Create and configure the Reference column
        TableColumn<Peca, String> referenceColumn = new TableColumn<>("Reference");
        referenceColumn.setCellValueFactory(new PropertyValueFactory<>("referencia"));
        referenceColumn.setPrefWidth(100);
        
        // Create and configure the Price column
        TableColumn<Peca, String> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("preco"));
        priceColumn.setPrefWidth(100);
        
        // Create and configure the Stock column
        TableColumn<Peca, String> stockColumn = new TableColumn<>("Stock");
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("qtd"));
        stockColumn.setPrefWidth(100);
        
        // Add the columns to the table
        inventoryTable.getColumns().addAll(nameColumn, referenceColumn, priceColumn, stockColumn);
    }
    
    private void loadInventoryData() {
        // Get all parts from the database
        List<Peca> pecas = pecaService.getAllPecas();
        
        // Convert the list to an ObservableList
        ObservableList<Peca> data = FXCollections.observableArrayList(pecas);
        
        // Set the data to the table
        inventoryTable.setItems(data);
    }
    
    // You can add methods for adding, updating, and deleting parts here
}