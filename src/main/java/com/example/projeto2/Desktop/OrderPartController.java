package com.example.projeto2.Desktop;

import com.example.projeto2.Services.EncomendaFornecedorService;
import com.example.projeto2.Tables.EncomendaFornecedor;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class OrderPartController {

    @FXML
    private TextField partNameField;

    @FXML
    private TextField quantityField;

    private final EncomendaFornecedorService encomendaFornecedorService;

    @Autowired
    public OrderPartController(EncomendaFornecedorService encomendaFornecedorService) {
        this.encomendaFornecedorService = encomendaFornecedorService;
    }

    @FXML
    protected void handleOrderPart() {
        String partName = partNameField.getText();
        String quantity = quantityField.getText();

        if (partName.isEmpty() || quantity.isEmpty()) {
            showAlert("Error", "Order Part Failed", "All fields are required.");
            return;
        }

        EncomendaFornecedor newOrder = new EncomendaFornecedor();
        // Remove calls to non-existent setters; pass extra values directly to the service
        encomendaFornecedorService.saveOrderPart(newOrder, partName, new BigDecimal(quantity));

        showAlert("Success", "Part Ordered", "Part ordered successfully.");

        // Clear fields after successful ordering
        partNameField.clear();
        quantityField.clear();
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}