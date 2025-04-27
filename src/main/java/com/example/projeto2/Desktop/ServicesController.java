package com.example.projeto2.Desktop;

import com.example.projeto2.Services.*;
import com.example.projeto2.Services.ServicoService;
import com.example.projeto2.Tables.Reparacao;
import com.example.projeto2.Tables.Servico;
import com.example.projeto2.Tables.Veiculo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Component
public class ServicesController {
    @Autowired
    private VeiculoService veiculoService;

    @FXML
    private VBox repairsList;

    private final ReparacaoService reparacaoService;

    @Autowired
    public ServicesController(ReparacaoService reparacaoService) {
        this.reparacaoService = reparacaoService;
    }

    @FXML
    public void initialize() {
        loadTodayRepairs();
    }

    private void loadTodayRepairs() {
        List<Reparacao> todayRepairs = reparacaoService.getTodayRepairs();

        todayRepairs.forEach(reparacao -> {
            HBox repairItem = new HBox(10);
            repairItem.setStyle("-fx-padding: 10px; -fx-spacing: 10px;");

            Label timeLabel = new Label(reparacao.getDataInicio().toLocalDate().toString() + " (" + reparacao.getEstado() + ")");
            timeLabel.setStyle("-fx-text-fill: white;");

            VBox vehicleInfo = new VBox(5);
            Veiculo veiculo = veiculoService.getVeiculoById(reparacao.getIdVeiculo());
            Label vehicleName = new Label(veiculo.getModelo());
            vehicleName.setStyle("-fx-text-fill: white;");
            Label repairDescription = new Label(reparacao.getDescricao());
            repairDescription.setStyle("-fx-text-fill: #cccccc;");

            vehicleInfo.getChildren().addAll(vehicleName, repairDescription);

            Rectangle progressIndicator = new Rectangle(150, 10);
            switch (reparacao.getEstado()) {
                case "Concluída":
                    progressIndicator.setFill(Color.valueOf("#4CAF50"));
                    break;
                case "Em andamento":
                    progressIndicator.setFill(Color.valueOf("#2196F3"));
                    break;
                case "Pendente":
                    progressIndicator.setFill(Color.valueOf("#F44336"));
                    break;
                default:
                    progressIndicator.setFill(Color.valueOf("#ff6b35"));
                    break;
            }

            repairItem.getChildren().addAll(timeLabel, vehicleInfo, progressIndicator);
            repairsList.getChildren().add(repairItem);
        });
    }
}