package com.example.projeto2.Desktop.mechanic;

import com.example.projeto2.Desktop.LoginController;
import com.example.projeto2.Services.*;
import com.example.projeto2.Services.ServicoService;
import com.example.projeto2.Tables.Funcionario;
import com.example.projeto2.Tables.Reparacao;
import com.example.projeto2.Tables.Servico;
import com.example.projeto2.Tables.Veiculo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.time.ZoneId;


import com.example.projeto2.Desktop.MainController;
import com.example.projeto2.Desktop.SceneManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
@Component
public class ServicesController {
    @Autowired
    private VeiculoService veiculoService;

    @Autowired
    private FuncionarioService funcionarioService;

    @FXML
    private VBox repairsList;

    @FXML
    private Button dashboardButton;
    @FXML
    private Button servicesButton;
    @FXML
    private Button inventoryButton;
    @FXML
    private Button ordersButton;
    @FXML
    private Button logoutButton;

    private final ReparacaoService reparacaoService;
    private final ApplicationContext applicationContext;
    private Funcionario loggedInFuncionario;

    @Autowired
    public ServicesController(ReparacaoService reparacaoService, ApplicationContext applicationContext, FuncionarioService funcionarioService, VeiculoService veiculoService) {
        this.reparacaoService = reparacaoService;
        this.applicationContext = applicationContext;
        this.funcionarioService = funcionarioService;
    }

    @FXML
    public void initialize() {
        loadTodayRepairs();
    }

    // Sidebar button actions
    @FXML
    public void onDashboardButtonClick() {
        try {
            SceneManager.switchScene("/mechanic/dashboard.fxml", "Dashboard", dashboardButton);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onInventoryButtonClick() {
        try {
            SceneManager.switchScene("/mechanic/inventory.fxml", "Inventory", inventoryButton);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onServicesButtonClick() {
        try {
            SceneManager.switchScene("/mechanic/services.fxml", "Services", servicesButton);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onOrdersButtonClick() {
        try {
            SceneManager.switchScene("/mechanic/order-part.fxml", "Orders", ordersButton);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onLogoutButtonClick() {
        try {
            SceneManager.switchScene("/login.fxml", "Login", logoutButton);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void loadTodayRepairs() {
        // Get the logged-in funcionario
        funcionarioService.getFuncionarioByUsername(LoginController.getLoggedInUsername()).ifPresent(funcionario -> loggedInFuncionario = funcionario);

        List<Reparacao> assignedRepairs = reparacaoService.getRepairsForMechanic(loggedInFuncionario.getIdFuncionario());
        List<Reparacao> availableRepairs = reparacaoService.getAvailableRepairs();

        // Combine and deduplicate
        List<Reparacao> allRepairs = new java.util.ArrayList<>(assignedRepairs);
        availableRepairs.forEach(repair -> {
            if (!allRepairs.contains(repair)) { // Assuming Reparacao has proper equals/hashCode
                allRepairs.add(repair);
            }
        });
        // Sort if necessary, e.g., by dataInicio
        allRepairs.sort(java.util.Comparator.comparing(Reparacao::getDataInicio));

        repairsList.getChildren().clear(); // Clear existing items

        allRepairs.forEach(reparacao -> {
            // Only display repairs that are not "Concluída"
            if (!"Concluída".equals(reparacao.getEstado())) {
                HBox repairItem = new HBox(20); // Increased spacing
                repairItem.setStyle("-fx-padding: 15px; -fx-spacing: 15px; -fx-background-color: #2d2d2d; -fx-background-radius: 10;");
                repairItem.setPrefHeight(80.0); // Adjust height as needed
                repairItem.setAlignment(javafx.geometry.Pos.CENTER_LEFT); // Align items to the left

                VBox dateTimeInfo = new VBox(5);
                Label timeLabel = new Label(Instant.ofEpochMilli(reparacao.getDataInicio().getTime()).atZone(ZoneId.systemDefault()).toLocalTime().toString() + " (" + reparacao.getEstado() + ")"); // Display time and status
                timeLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");

                dateTimeInfo.getChildren().add(timeLabel);

                VBox vehicleInfo = new VBox(5);
                Veiculo veiculo = veiculoService.getVeiculoById(reparacao.getIdVeiculo());
                Label vehicleName = new Label(veiculo.getModelo());
                vehicleName.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");
                Label repairDescription = new Label(reparacao.getDescricao());
                repairDescription.setStyle("-fx-text-fill: #cccccc; -fx-font-size: 12px;");

                vehicleInfo.getChildren().addAll(vehicleName, repairDescription);
                HBox.setHgrow(vehicleInfo, javafx.scene.layout.Priority.ALWAYS); // Allow vehicle info to take available space

                HBox buttonBox = new HBox(10); // Spacing between buttons
                buttonBox.setAlignment(javafx.geometry.Pos.CENTER_RIGHT); // Align buttons to the right

                // Determine buttons based on state and assignment
                if ("Pendente".equals(reparacao.getEstado()) && reparacao.getIdFuncionario() == null) {
                    // This is an available repair
                    Button startButton = new Button("Pick Up & Start");
                    startButton.setStyle("-fx-background-color: linear-gradient(to right, #ff6b35, #ff9a00); -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 5 15 5 15; -fx-background-radius: 5;");
                    startButton.setOnAction(event -> handleStartRepair(reparacao)); // This will now assign and start
                    buttonBox.getChildren().add(startButton);
                } else if ("Em andamento".equals(reparacao.getEstado()) && loggedInFuncionario != null && reparacao.getIdFuncionario() != null && reparacao.getIdFuncionario().equals(loggedInFuncionario.getIdFuncionario())) {
                    // This is an assigned and in-progress repair for the logged-in mechanic
                    Button addPartsButton = new Button("Add Parts");
                    addPartsButton.setStyle("-fx-background-color: linear-gradient(to right, #ff6b35, #ff9a00); -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 5 15 5 15; -fx-background-radius: 5;");
                    addPartsButton.setOnAction(event -> handleAddParts(reparacao));

                    Button finishButton = new Button("Finish");
                    finishButton.setStyle("-fx-background-color: linear-gradient(to right, #ff6b35, #ff9a00); -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 5 15 5 15; -fx-background-radius: 5;");
                    finishButton.setOnAction(event -> handleFinishRepair(reparacao));

                    buttonBox.getChildren().addAll(addPartsButton, finishButton);
                } else if ("Pendente".equals(reparacao.getEstado()) && loggedInFuncionario != null && reparacao.getIdFuncionario() != null && reparacao.getIdFuncionario().equals(loggedInFuncionario.getIdFuncionario())) {
                    // This is an assigned but not yet started repair for the logged-in mechanic
                    Button startButton = new Button("Start");
                    startButton.setStyle("-fx-background-color: linear-gradient(to right, #ff6b35, #ff9a00); -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 5 15 5 15; -fx-background-radius: 5;");
                    startButton.setOnAction(event -> handleStartRepair(reparacao));
                    buttonBox.getChildren().add(startButton);
                }
                // No default case needed for "Concluída" as they are filtered out.
                // For other states or if not assigned to current mechanic, no buttons.

                repairItem.getChildren().addAll(dateTimeInfo, vehicleInfo, buttonBox);
                repairsList.getChildren().add(repairItem);
            }
        });
    }

    private void createNewRepair() {
        Reparacao newReparacao = new Reparacao();
        newReparacao.setIdFuncionario(loggedInFuncionario.getIdFuncionario());
    }

    // Methods for button actions
    @FXML
    private void handleStartRepair(Reparacao reparacao) {
        System.out.println("Starting repair: " + reparacao.getIdReparacao());
        try {
            // If the repair is unassigned, assign it to the logged-in mechanic
            if (reparacao.getIdFuncionario() == null && loggedInFuncionario != null) {
                reparacao.setIdFuncionario(loggedInFuncionario.getIdFuncionario());
                reparacaoService.updateReparacao(reparacao.getIdReparacao(), reparacao); // Save the assignment
            }

            reparacaoService.startRepair(reparacao.getIdReparacao());
            loadTodayRepairs(); // Refresh the list
            showAlert(Alert.AlertType.INFORMATION, "Success", "Repair Started", "Repair marked as started.");
        } catch (Exception serviceException) {
            System.err.println("Error starting repair: " + serviceException.getMessage());
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to Start Repair", "An error occurred while starting the repair: " + serviceException.getMessage());
        } finally {
            if (reparacao != null) {
                reparacao.setDataInicio(java.sql.Date.valueOf(java.time.LocalDate.now()));
                reparacaoService.updateReparacao(reparacao.getIdReparacao(), reparacao);
            }
        }
    }

    @FXML
    private void handleAddParts(Reparacao reparacao) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mechanic/select-part-modal.fxml"));
            loader.setControllerFactory(applicationContext::getBean); // Use Spring context for controller factory
            Parent modalContent = loader.load();

            SelectPartModalController controller = loader.getController();
            Stage modalStage = new Stage();
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.setTitle("Select Parts");
            modalStage.setScene(new Scene(modalContent));
            controller.setDialogStage(modalStage);

            modalStage.showAndWait();

            List<PartQuantity> partsToAdd = controller.getSelectedPartsWithQuantity(); // Get list of PartQuantity
            if (partsToAdd != null && !partsToAdd.isEmpty()) {
                try {
                    // Call the service method to add parts to the repair
                    reparacaoService.addPartsToRepair(reparacao.getIdReparacao(), partsToAdd); // Pass repair ID and list of PartQuantity
                    System.out.println("Parts added to repair " + reparacao.getIdReparacao() + ":");
                    partsToAdd.forEach(pq -> System.out.println("- " + pq.getPart().getNome() + " (Qty: " + pq.getQuantity() + ")")); // Print part name and quantity

                    // Refresh the repairs list to reflect changes (e.g., updated total value)
                    loadTodayRepairs();
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Parts Added", "Selected parts have been added to the repair.");

                } catch (Exception serviceException) {
                    System.err.println("Error adding parts to repair: " + serviceException.getMessage());
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to Add Parts", "An error occurred while adding parts to the repair: " + serviceException.getMessage());
                }
            } else if (partsToAdd != null && partsToAdd.isEmpty()) {
                 System.out.println("No parts selected or created for repair " + reparacao.getIdReparacao());
                 showAlert(Alert.AlertType.INFORMATION, "Info", "No Parts Selected", "No parts were selected or created to add to the repair.");
            } else {
                 System.out.println("Part selection cancelled for repair " + reparacao.getIdReparacao());
                 // No alert needed for cancellation
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading select part modal: " + e.getMessage());
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to Load Modal", "An error occurred while loading the part selection modal.");
        }
    }

    @FXML
    private void handleFinishRepair(Reparacao reparacao) {
        System.out.println("Finishing repair: " + reparacao.getIdReparacao());
         try {
             reparacaoService.finishRepair(reparacao.getIdReparacao());
             loadTodayRepairs(); // Refresh the list
             showAlert(Alert.AlertType.INFORMATION, "Success", "Repair Finished", "Repair marked as completed.");
         } catch (Exception serviceException) {
             System.err.println("Error finishing repair: " + serviceException.getMessage());
             showAlert(Alert.AlertType.ERROR, "Error", "Failed to Finish Repair", "An error occurred while finishing the repair: " + serviceException.getMessage());
         }
    }

    // This method might not be needed anymore based on the new logic
    @FXML
    private void handleViewReport(Reparacao reparacao) {
        System.out.println("Viewing report for repair: " + reparacao.getIdReparacao());
        // Implement logic to open report view or modal
        showAlert(Alert.AlertType.INFORMATION, "Info", "View Report", "View Report functionality is not yet fully implemented.");
    }

    // This method might not be needed anymore based on the new logic
    @FXML
    private void handleViewDetails(Reparacao reparacao) {
        System.out.println("Viewing details for repair: " + reparacao.getIdReparacao());
        // Implement logic to open details view or modal
         showAlert(Alert.AlertType.INFORMATION, "Info", "View Details", "View Details functionality is not yet fully implemented.");
    }

    // Helper method to show alerts
    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}