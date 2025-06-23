package com.example.projeto2.Desktop;

import com.example.projeto2.Tables.CodPostal;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.control.Label;

public class PostalCodeListCell extends ListCell<CodPostal> {
    private final HBox hbox = new HBox(10);
    private final Label label = new Label();
    private final Button selectButton = new Button("Selecionar");
    private final PostalCodeModalController controller;

    public PostalCodeListCell(PostalCodeModalController controller) {
        super();
        this.controller = controller;
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        hbox.getChildren().addAll(label, spacer, selectButton);
        
        selectButton.setOnAction(event -> {
            if (getItem() != null) {
                controller.selectPostalCode(getItem());
            }
        });
    }

    @Override
    protected void updateItem(CodPostal codPostal, boolean empty) {
        super.updateItem(codPostal, empty);
        if (empty || codPostal == null) {
            setText(null);
            setGraphic(null);
        } else {
            label.setText(codPostal.getCodPostal() + " - " + codPostal.getDescricao());
            setGraphic(hbox);
        }
    }
}