package com.example.projeto2.Desktop.mechanic;

import com.example.projeto2.Tables.Peca;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.math.BigDecimal;

public class PartQuantityListCellController extends ListCell<Peca> {

    @FXML
    private HBox hBox; // Root element of the list cell FXML

    @FXML
    private Label partNameLabel;

    @FXML
    private Label stockLabel;

    @FXML
    private TextField quantityField;

    private FXMLLoader mLLoader;

    @Override
    protected void updateItem(Peca peca, boolean empty) {
        super.updateItem(peca, empty);

        if (empty || peca == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("/mechanic/part-quantity-list-cell.fxml"));
                mLLoader.setController(this);

                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            partNameLabel.setText(peca.getNome());
            stockLabel.setText(String.format("Stock: %d unidades restantes", peca.getQtd() != null ? peca.getQtd().intValue() : 0));

            // Initialize quantity field with 1 or the current value if already set
            if (quantityField.getText().isEmpty()) {
                 quantityField.setText("1");
            }

            // Store the Peca object in the quantity field's properties for easy retrieval later
            quantityField.getProperties().put("peca", peca);

            setText(null);
            setGraphic(hBox);
        }
    }

    // Method to get the quantity entered in the text field
    public BigDecimal getQuantity() {
        try {
            return new BigDecimal(quantityField.getText());
        } catch (NumberFormatException e) {
            return BigDecimal.ZERO; // Return zero for invalid input
        }
    }

     // Method to get the associated Peca object
    public Peca getPart() {
        return (Peca) quantityField.getProperties().get("peca");
    }
}