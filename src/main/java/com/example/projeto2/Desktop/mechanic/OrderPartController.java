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

import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.Optional; // Import Optional
import java.util.ResourceBundle;

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
    private final PecaService pecaService; // Inject PecaService
    private Stage dialogStage;
    private boolean orderPlaced = false;

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
        this.partNameField.setEditable(false); // Part name is pre-filled and not editable
    }

    public boolean isOrderPlaced() {
        return orderPlaced;
    }

    private void loadSuppliers() {
        List<Fornecedor> suppliers = fornecedorService.getAllFornecedores();
        ObservableList<Fornecedor> supplierList = FXCollections.observableArrayList(suppliers);
        supplierComboBox.setItems(supplierList);
        // Set a cell factory or string converter if you want to display something other than the default toString()
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

            // Find the part by name
            Optional<Peca> partOptional = pecaService.getPecaByNome(partName);

            if (!partOptional.isPresent()) {
                showAlert("Error", "Part Not Found", "The specified part does not exist in the inventory.");
                return;
            }

            Peca partToOrder = partOptional.get();

            // Create a new supplier order header
            EncomendaFornecedor newOrder = encomendaFornecedorService.createSupplierOrder(selectedSupplier);

            // Add the part as a line item to the order
            encomendaFornecedorService.addPartToSupplierOrder(newOrder, partToOrder, quantity);

            orderPlaced = true;
            showAlert("Success", "Part Ordered", "Part ordered successfully.");
            dialogStage.close(); // Close the modal after successful order

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
        dialogStage.close();
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}