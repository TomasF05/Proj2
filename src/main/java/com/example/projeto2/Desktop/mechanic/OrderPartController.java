package com.example.projeto2.Desktop.mechanic;

import com.example.projeto2.Services.EncomendaFornecedorService;
import com.example.projeto2.Services.FornecedorService;
import com.example.projeto2.Services.PecaService; // Import PecaService
import com.example.projeto2.Tables.EncomendaFornecedor;
import com.example.projeto2.Tables.Fornecedor;
import com.example.projeto2.Tables.Peca; // Import Peca
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.Optional; // Import Optional
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
public class OrderPartController implements Initializable {

    @FXML
    private TextField partNameField;

    @FXML
    private TextField quantityField;

    @FXML
    private ComboBox<Fornecedor> supplierComboBox;


    private final EncomendaFornecedorService encomendaFornecedorService;
    private final FornecedorService fornecedorService;
    private final PecaService pecaService;
    private Stage dialogStage;
    private boolean orderPlaced = false;

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

    @Autowired
    public OrderPartController(EncomendaFornecedorService encomendaFornecedorService, FornecedorService fornecedorService, PecaService pecaService) {
        this.encomendaFornecedorService = encomendaFornecedorService;
        this.fornecedorService = fornecedorService;
        this.pecaService = pecaService;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadSuppliers();
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setPartName(String partName) {
        this.partNameField.setText(partName);
        this.partNameField.setEditable(false);
    }

    public boolean isOrderPlaced() {
        return orderPlaced;
    }

    private void loadSuppliers() {
        List<Fornecedor> suppliers = fornecedorService.getAllFornecedores();
        ObservableList<Fornecedor> supplierList = FXCollections.observableArrayList(suppliers);
        supplierComboBox.setItems(supplierList);
        supplierComboBox.setPromptText("Select Supplier");
    }

    @FXML
    protected void handleOrderPart() {
        String partName = partNameField.getText();
        String quantityText = quantityField.getText();
        Fornecedor selectedSupplier = supplierComboBox.getSelectionModel().getSelectedItem();

        if (partName.isEmpty() || quantityText.isEmpty() || selectedSupplier == null) {
            showAlert("Error", "Order Part Failed", "All fields are required and a supplier must be selected.");
            return;
        }

        try {
            BigDecimal quantity = new BigDecimal(quantityText);
            if (quantity.compareTo(BigDecimal.ZERO) <= 0) {
                showAlert("Error", "Invalid Quantity", "Quantity must be a positive number.");
                return;
            }

            Optional<Peca> partOptional = pecaService.getPecaByNome(partName);

            if (!partOptional.isPresent()) {
                showAlert("Error", "Part Not Found", "The specified part does not exist in the inventory.");
                return;
            }

            Peca partToOrder = partOptional.get();

            EncomendaFornecedor newOrder = encomendaFornecedorService.createSupplierOrder(selectedSupplier);

            encomendaFornecedorService.addPartToSupplierOrder(newOrder, partToOrder, quantity);

            orderPlaced = true;
            showAlert("Success", "Part Ordered", "Part ordered successfully.");
            if (dialogStage != null) {
                dialogStage.close();
            }

        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid Quantity", "Please enter a valid number for quantity.");
        } catch (Exception e) {
            showAlert("Error", "Order Failed", "An error occurred while placing the order.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCancel() {
        orderPlaced = false;
        if (dialogStage != null) {
            dialogStage.close();
        }
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
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