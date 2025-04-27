package com.example.projeto2.Desktop;

import com.example.projeto2.Services.ReparacaoService;
import com.example.projeto2.Tables.Reparacao;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
public class ScheduleRepairController {

    @FXML
    private TextField dateField;

    @FXML
    private TextField timeField;

    @FXML
    private TextField paymentStatusField;

    @FXML
    private TextField notesField;

    private final ReparacaoService reparacaoService;

    @Autowired
    public ScheduleRepairController(ReparacaoService reparacaoService) {
        this.reparacaoService = reparacaoService;
    }

    @FXML
    protected void handleScheduleRepair() {
        String date = dateField.getText();
        String time = timeField.getText();
        String paymentStatus = paymentStatusField.getText();
        String notes = notesField.getText();

        if (date.isEmpty() || time.isEmpty() || paymentStatus.isEmpty() || notes.isEmpty()) {
            showAlert("Error", "Schedule Repair Failed", "All fields are required.");
            return;
        }

        Reparacao newRepair = new Reparacao();
        newRepair.setDataInicio(Date.valueOf(date));
        newRepair.setEstado(paymentStatus);
        newRepair.setDescricao(notes);

        reparacaoService.saveReparacao(newRepair);

        showAlert("Success", "Repair Scheduled", "Repair scheduled successfully.");

        // Clear fields after successful scheduling
        dateField.clear();
        timeField.clear();
        paymentStatusField.clear();
        notesField.clear();
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}