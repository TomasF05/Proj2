// File: src/main/java/com/example/projeto2/Desktop/CreateInvoiceController.java
package com.example.projeto2.Desktop;

import com.example.projeto2.Services.FaturaClienteService;
import com.example.projeto2.Tables.FaturaCliente;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Date;

@Component
public class CreateInvoiceController {

    @FXML
    private TextField invoiceDateField;

    @FXML
    private TextField invoiceNumberField;

    @FXML
    private TextField clientIdField;

    // Removed extra UI fields that were mapped to non-existent columns

    private final FaturaClienteService faturaClienteService;

    @Autowired
    public CreateInvoiceController(FaturaClienteService faturaClienteService) {
        this.faturaClienteService = faturaClienteService;
    }

    @FXML
    protected void handleCreateInvoice() {
        String invoiceDate = invoiceDateField.getText();
        String invoiceNumber = invoiceNumberField.getText();
        String clientIdValue = clientIdField.getText();

        if (invoiceDate.isEmpty() || invoiceNumber.isEmpty() || clientIdValue.isEmpty()) {
            showAlert("Error", "Create Invoice Failed", "All fields are required.");
            return;
        }

        FaturaCliente newInvoice = new FaturaCliente();
        try {
            // Map invoiceNumber and clientIdValue to BigDecimal
            newInvoice.setnFatura(new BigDecimal(invoiceNumber));
            newInvoice.setData(Date.valueOf(invoiceDate));
            newInvoice.setIdCliente(new BigDecimal(clientIdValue));
            // ValorTotal remains unset or set to a default if needed.
        } catch (Exception e) {
            showAlert("Error", "Invalid data", "Ensure the fields are in correct format.");
            return;
        }

        faturaClienteService.saveFaturaCliente(newInvoice);

        showAlert("Success", "Invoice Created", "Invoice created successfully.");

        // Clear fields after successful creation
        invoiceDateField.clear();
        invoiceNumberField.clear();
        clientIdField.clear();
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}