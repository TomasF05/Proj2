package com.example.projeto2.Desktop.mechanic;

import com.example.projeto2.Services.EncomendaFornecedorService;
import com.example.projeto2.Services.LinhaEncFornecedorService;
import com.example.projeto2.Services.PecaService;
import com.example.projeto2.Tables.EncomendaFornecedor;
import com.example.projeto2.Tables.LinhaEncFornecedor;
import com.example.projeto2.Tables.Peca;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class ReceiveOrderPartController implements Initializable {

    @FXML
    private TableView<LinhaEncFornecedor> orderedPartsTable;

    @FXML
    private TableColumn<LinhaEncFornecedor, BigDecimal> orderIdColumn;

    @FXML
    private TableColumn<LinhaEncFornecedor, String> supplierColumn;

    @FXML
    private TableColumn<LinhaEncFornecedor, String> orderDateColumn;

    @FXML
    private TableColumn<LinhaEncFornecedor, String> partNameColumn;

    @FXML
    private TableColumn<LinhaEncFornecedor, BigDecimal> quantityOrderedColumn;

    @FXML
    private TableColumn<LinhaEncFornecedor, BigDecimal> quantityReceivedColumn;

    private final EncomendaFornecedorService encomendaFornecedorService;
    private final LinhaEncFornecedorService linhaEncFornecedorService;
    private final PecaService pecaService;

    @Autowired
    public ReceiveOrderPartController(EncomendaFornecedorService encomendaFornecedorService, LinhaEncFornecedorService linhaEncFornecedorService, PecaService pecaService) {
        this.encomendaFornecedorService = encomendaFornecedorService;
        this.linhaEncFornecedorService = linhaEncFornecedorService;
        this.pecaService = pecaService;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeColumns();
        loadOrderedParts();
    }

    private void initializeColumns() {
        orderIdColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getId().getIdEncFornecedor()));
        supplierColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEncomendaFornecedor().getFornecedor().getNome()));
        orderDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEncomendaFornecedor().getData().toString()));
        partNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPeca().getNome()));
        quantityOrderedColumn.setCellValueFactory(new PropertyValueFactory<>("qtd"));
        quantityReceivedColumn.setCellValueFactory(new PropertyValueFactory<>("qtdRecebida"));
    }

    private void loadOrderedParts() {
        List<EncomendaFornecedor> unreceivedOrders = encomendaFornecedorService.getUnreceivedOrders();
        ObservableList<LinhaEncFornecedor> observableList = FXCollections.observableArrayList();
        for (EncomendaFornecedor order : unreceivedOrders) {
            if (order.getLinhasEncFornecedor() != null) {
                observableList.addAll(order.getLinhasEncFornecedor());
            }
        }
        orderedPartsTable.setItems(observableList);
    }

    @FXML
    private void handleSaveReceivedQuantities() {
        // Iterate through the table view rows
        for (LinhaEncFornecedor linhaEncFornecedor : orderedPartsTable.getItems()) {
            // Get the "Quantity Received" value for each part
            BigDecimal quantityReceived = linhaEncFornecedor.getQtdRecebida(); // Assuming you have this property

            // Update the stock quantity of the part in the database
            Peca part = linhaEncFornecedor.getPeca();
            BigDecimal currentStock = part.getQtd() != null ? part.getQtd() : BigDecimal.ZERO;
            part.setQtd(currentStock.add(quantityReceived));
            pecaService.savePeca(part);

            // Potentially update the LinhaEncFornecedor with the quantity received
            linhaEncFornecedorService.saveLinhaEncFornecedor(linhaEncFornecedor);
        }

        // Display a success message
        showAlert(Alert.AlertType.INFORMATION, "Success", "Parts Received", "Parts received and stock updated successfully.");
    }

    @FXML
    private void handleCancel() {
        // Close the modal
        Stage stage = (Stage) orderedPartsTable.getScene().getWindow();
        stage.close();
    }

    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}