package com.example.projeto2.Desktop;

import com.example.projeto2.Tables.Servico;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.control.Label;

public class ServicoListCell extends ListCell<Servico> {
    private final HBox hbox = new HBox(10);
    private final Label label = new Label();
    private final Button selectButton = new Button("Selecionar");
    private final ServicoModalController controller;

    public ServicoListCell(ServicoModalController controller) {
        super();
        this.controller = controller;
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        hbox.getChildren().addAll(label, spacer, selectButton);
        
        selectButton.setOnAction(event -> {
            if (getItem() != null) {
                controller.selectServico(getItem());
            }
        });
    }

    @Override
    protected void updateItem(Servico servico, boolean empty) {
        super.updateItem(servico, empty);
        if (empty || servico == null) {
            setText(null);
            setGraphic(null);
        } else {
            label.setText(servico.getNome() + " - " + servico.getPreco() + "€");
            setGraphic(hbox);
        }
    }
}