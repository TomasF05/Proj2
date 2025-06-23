package com.example.projeto2.Desktop;

import com.example.projeto2.Tables.Reparacao;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.control.Label;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.time.Instant;

public class ServicoListCell extends ListCell<Reparacao> {
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
                controller.selectReparacao(getItem());
            }
        });
    }

    @Override
    protected void updateItem(Reparacao reparacao, boolean empty) {
        super.updateItem(reparacao, empty);
        if (empty || reparacao == null) {
            setText(null);
            setGraphic(null);
        } else {
            String formattedDate = "";
            if (reparacao.getDataInicio() != null) {
                formattedDate = Instant.ofEpochMilli(reparacao.getDataInicio().getTime())
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()
                        .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            }
            label.setText(reparacao.getDescricao() + " - " + formattedDate + " (" + reparacao.getEstado() + ")");
            setGraphic(hbox);
        }
    }
}