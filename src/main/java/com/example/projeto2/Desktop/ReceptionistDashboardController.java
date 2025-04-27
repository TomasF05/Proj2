package com.example.projeto2.Desktop;

import com.example.projeto2.Services.AgendamentoService;
import com.example.projeto2.Services.ReparacaoService;
import com.example.projeto2.Tables.Agendamento;
import com.example.projeto2.Tables.Reparacao;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class ReceptionistDashboardController {

    @FXML
    private VBox appointmentsList;

    @FXML
    private VBox activeRepairsList;

    private final AgendamentoService agendamentoService;
    private final ReparacaoService reparacaoService;

    @Autowired
    public ReceptionistDashboardController(AgendamentoService agendamentoService, ReparacaoService reparacaoService) {
        this.agendamentoService = agendamentoService;
        this.reparacaoService = reparacaoService;
    }

    @FXML
    public void initialize() {
        loadTodayAppointments();
        loadActiveRepairs();
    }

    private void loadTodayAppointments() {
        List<Agendamento> todayAppointments = agendamentoService.getTodayAppointments();

        todayAppointments.forEach(appointment -> {
            HBox appointmentItem = new HBox(10);
            appointmentItem.setStyle("-fx-padding: 10px; -fx-spacing: 10px;");

            VBox itemInfo = new VBox(5);
            // Using getCliente() to retrieve client name remains unchanged.
            Label clientName = new Label(appointment.getCliente().getNome());
            clientName.setStyle("-fx-text-fill: white;");
            // Replace non-existent getDescricao() with getObservacoes() and use getLocalTime()
            Label serviceInfo = new Label(appointment.getObservacoes() + " | " + appointment.getLocalTime());
            serviceInfo.setStyle("-fx-text-fill: #cccccc;");

            itemInfo.getChildren().addAll(clientName, serviceInfo);

            Rectangle statusBar = new Rectangle(150, 10);
            statusBar.setFill(javafx.scene.paint.Color.valueOf("#ff6b35"));

            appointmentItem.getChildren().addAll(itemInfo, statusBar);
            appointmentsList.getChildren().add(appointmentItem);
        });
    }

    private void loadActiveRepairs() {
        List<Reparacao> activeRepairs = reparacaoService.getActiveRepairs();

        activeRepairs.forEach(repair -> {
            HBox repairItem = new HBox(10);
            repairItem.setStyle("-fx-padding: 10px; -fx-spacing: 10px;");

            VBox itemInfo = new VBox(5);
            // Use getIdVeiculo() for vehicle info. Adjust the string as needed.
            Label vehicleInfo = new Label("Vehicle: " + repair.getIdVeiculo());
            vehicleInfo.setStyle("-fx-text-fill: white;");
            Label repairDescription = new Label(repair.getDescricao());
            repairDescription.setStyle("-fx-text-fill: #cccccc;");

            itemInfo.getChildren().addAll(vehicleInfo, repairDescription);

            Rectangle statusBar = new Rectangle(150, 10);
            switch (repair.getEstado()) {
                case "Concluída":
                    statusBar.setFill(javafx.scene.paint.Color.valueOf("#4CAF50"));
                    break;
                case "Em andamento":
                    statusBar.setFill(javafx.scene.paint.Color.valueOf("#2196F3"));
                    break;
                case "Pendente":
                    statusBar.setFill(javafx.scene.paint.Color.valueOf("#F44336"));
                    break;
                default:
                    statusBar.setFill(javafx.scene.paint.Color.valueOf("#ff6b35"));
                    break;
            }

            repairItem.getChildren().addAll(itemInfo, statusBar);
            activeRepairsList.getChildren().add(repairItem);
        });
    }
}