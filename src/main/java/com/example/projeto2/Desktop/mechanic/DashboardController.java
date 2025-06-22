package com.example.projeto2.Desktop.mechanic;

import com.example.projeto2.Desktop.MainController;
import com.example.projeto2.Services.AgendamentoService;
import com.example.projeto2.Services.PecaService;
import com.example.projeto2.Services.ReparacaoService;
import com.example.projeto2.Tables.Agendamento;
import com.example.projeto2.Tables.Peca;
import com.example.projeto2.Tables.Reparacao;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.StrokeLineCap;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import javafx.scene.paint.CycleMethod;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class DashboardController {


    @FXML
    private Arc progressArc;

    @FXML
    private Label progressLabel;

    @FXML
    private VBox stockList;

    private final ReparacaoService reparacaoService;
    private final PecaService pecaService;
    private final ApplicationContext context;

    @Autowired
    private MainController mainController; // Inject MainController

    @Autowired
    public DashboardController(ReparacaoService reparacaoService, PecaService pecaService, ApplicationContext context) {
        this.reparacaoService = reparacaoService;
        this.pecaService = pecaService;
        this.context = context;
    }

    @FXML
    public void initialize() {
        loadRepairProgress();
        loadStockInformation();
    }


    @FXML
    public void onVerReparacoesButtonClick() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/mechanic/services.fxml"));
            fxmlLoader.setControllerFactory(context::getBean); // Ensure Spring manages the controller
            Parent servicesView = fxmlLoader.load();
            mainController.getContentContainer().getChildren().setAll(servicesView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadRepairProgress() {
        int totalRepairs = reparacaoService.countTodayRepairs();
        int completedRepairs = reparacaoService.countCompletedRepairsToday();

        double progressPercentage;
        if (totalRepairs > 0) {
            progressPercentage = (double) completedRepairs / totalRepairs * 100;
            progressLabel.setText(String.format("%.1f%% das reparações de hoje concluídas", progressPercentage));
        } else {
            progressPercentage = 50.0; // Placeholder for demonstration
            progressLabel.setText(String.format("%.1f%% das reparações de hoje concluídas", progressPercentage));
        }

        // Customize the arc based on progress
        progressArc.setRadiusX(40);
        progressArc.setRadiusY(40);
        progressArc.setType(ArcType.ROUND); // Changed to ROUND
        progressArc.setStartAngle(90); // Start from the top
        progressArc.setLength(360 * (progressPercentage / 100)); // Full circle progress
        progressArc.setStrokeLineCap(StrokeLineCap.ROUND); // Rounded ends for the arc

        Stop[] stops = new Stop[]{new Stop(0, Color.valueOf("#ff6b35")), new Stop(1, Color.valueOf("#ff9a00"))};
        LinearGradient gradient = new LinearGradient(0,0,1,0, true, CycleMethod.NO_CYCLE, stops);
        progressArc.setStroke(gradient);
        progressArc.setStrokeWidth(10);
        progressArc.setFill(null);
        progressArc.setVisible(true); // Ensure arc is always visible
    }

    private void loadStockInformation() {
        List<Peca> lowStockPecas = pecaService.findLowStockPecas();

        lowStockPecas.forEach(peca -> {
            HBox stockItem = new HBox(10);
            Label pecaName = new Label(peca.getNome());
            pecaName.setStyle("-fx-text-fill: white;");
            Label stockInfo = new Label(String.format("Stock: %d unidades restantes", peca.getQtd()));
            stockInfo.setStyle("-fx-text-fill: #ff6b35;");

            Region spacer = new Region();
            HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);

            Region stockBar = new Region();
            stockBar.setStyle("-fx-background-color: linear-gradient(to right, #ff6b35, #ff9a00);");
            stockBar.setMinHeight(5);
            stockBar.setMaxHeight(5);

            stockItem.getChildren().addAll(pecaName, spacer, stockInfo);
            HBox barContainer = new HBox(stockBar);
            barContainer.setPadding(new javafx.geometry.Insets(5, 0, 5, 0));

            stockList.getChildren().addAll(stockItem, barContainer);
        });
    }
}