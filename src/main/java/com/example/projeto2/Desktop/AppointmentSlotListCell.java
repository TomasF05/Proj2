package com.example.projeto2.Desktop;

import com.example.projeto2.Tables.Agendamento;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.control.Label;
import java.time.format.DateTimeFormatter;

public class AppointmentSlotListCell extends ListCell<Agendamento> {
    private final HBox hbox = new HBox(10);
    private final Label label = new Label();
    private final Button selectButton = new Button("Selecionar");
    private final AppointmentSlotModalController controller;

    public AppointmentSlotListCell(AppointmentSlotModalController controller) {
        super();
        this.controller = controller;
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        hbox.getChildren().addAll(label, spacer, selectButton);
        
        selectButton.setOnAction(event -> {
            if (getItem() != null) {
                controller.selectAppointmentSlot(getItem());
            }
        });
    }

    @Override
    protected void updateItem(Agendamento agendamento, boolean empty) {
        super.updateItem(agendamento, empty);
        if (empty || agendamento == null) {
            setText(null);
            setGraphic(null);
        } else {
            // Format the date and time for display
            String formattedDateTime = agendamento.getDataHora().toInstant()
                    .atZone(java.time.ZoneId.systemDefault())
                    .toLocalDateTime()
                    .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
            label.setText(formattedDateTime + " - " + agendamento.getObservacoes());
            setGraphic(hbox);
        }
    }
}