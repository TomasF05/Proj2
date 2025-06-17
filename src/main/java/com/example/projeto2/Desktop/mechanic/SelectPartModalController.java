package com.example.projeto2.Desktop.mechanic;


import com.example.projeto2.Services.PecaService;
import com.example.projeto2.Tables.Peca;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class SelectPartModalController {

    @FXML
    private TextField searchField;

    @FXML
    private ListView<Peca> partListView;

    private final PecaService pecaService;
    private final ApplicationContext applicationContext; // Inject ApplicationContext
    private ObservableList<Peca> allParts;
    private Stage dialogStage;
    private List<PartQuantity> selectedPartsWithQuantity = new ArrayList<>(); // List to hold selected parts with quantity
    private List<PartQuantity> partsToReturn = new ArrayList<>();

    @Autowired
    public SelectPartModalController(PecaService pecaService, ApplicationContext applicationContext) {
        this.pecaService = pecaService;
        this.applicationContext = applicationContext;
    }

    @FXML
    public void initialize() {
        allParts = FXCollections.observableArrayList(pecaService.getAllPecas());
        partListView.setItems(allParts);
        partListView.getSelectionModel().setSelectionMode(javafx.scene.control.SelectionMode.MULTIPLE);

        // Set custom cell factory
        partListView.setCellFactory(param -> new ListCell<Peca>() {
            @Override
            protected void updateItem(Peca item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    // Create a label to display the part name
                    Label partNameLabel = new Label(item.getNome());

                    // Create a TextField for quantity input
                    TextField quantityTextField = new TextField();
                    quantityTextField.setPromptText("Quantity");
                    quantityTextField.setId("quantityTextField_" + item.getIdPeca());

                    // Create an HBox to hold the label and text field
                    HBox hbox = new HBox(10); // 10 pixels spacing
                    hbox.getChildren().addAll(partNameLabel, quantityTextField);
                    hbox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

                    // Set the graphic of the list cell to the HBox
                    setGraphic(hbox);
                }
            }
        });
        searchField.textProperty().addListener((observable, oldValue, newValue) -> filterParts(newValue));
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    // Change return type to List<PartQuantity>
    public List<PartQuantity> getSelectedPartsWithQuantity() {
        return selectedPartsWithQuantity;
    }

    private void filterParts(String searchText) {
        if (searchText == null || searchText.isEmpty()) {
            partListView.setItems(allParts);
        } else {
            ObservableList<Peca> filteredList = allParts.stream()
                    .filter(peca -> peca.getNome().toLowerCase().contains(searchText.toLowerCase()))
                    .collect(Collectors.toCollection(FXCollections::observableArrayList));
            partListView.setItems(filteredList);
        }
    }

    @FXML
    private void handleCancel() {
        selectedPartsWithQuantity = null; // Indicate cancellation
        dialogStage.close();
    }

    @FXML
    private void handleAddSelectedParts() {
        selectedPartsWithQuantity.clear(); // Clear previous selections
// Iterate through the visible cells to get selected parts and their quantities
        for (javafx.scene.Node node : partListView.lookupAll(".list-cell")) {
        if (node instanceof ListCell) {
            ListCell<Peca> cell = (ListCell<Peca>) node;
            if (cell.getItem() != null && cell.isSelected()) {
                // Check if the cell's controller is of type PartQuantityListCellController
                if (PartQuantityListCellController.class.isInstance(cell)) {
                    PartQuantityListCellController controller = (PartQuantityListCellController) cell;
                    BigDecimal quantity = controller.getQuantity();
                    Peca part = controller.getPart();

                    if (quantity != null && quantity.compareTo(BigDecimal.ZERO) > 0) {
                        selectedPartsWithQuantity.add(new PartQuantity(part, quantity));
                    } else {
                        showAlert(Alert.AlertType.WARNING, "Invalid Quantity", "Zero Quantity", "Ignoring part with zero quantity: " + part.getNome());
                        System.out.println("Ignoring part with zero quantity: " + part.getNome());
                    }
                }
            }
        }
    } // Closing brace for the for loop

        // Handle parts that were not in the initial list but were created/ordered
        // This part of the logic needs to be integrated with the CreateOrderPartModalController
        // Currently, the CreateOrderPartModalController doesn't return the created part object.
        // We need to modify CreateOrderPartModalController to return the created Peca object
        // and then add it to selectedPartsWithQuantity here.

        // For now, the logic for opening the create/order modal for non-existing parts
        // is still in this method, but it should ideally be triggered from the search
        // functionality or a separate "Add New Part" button.
        // I will keep the existing logic for opening the create/order modal for now,
        // but the workflow needs refinement.

        List<Peca> selected = partListView.getSelectionModel().getSelectedItems();
         for (Peca selectedPeca : selected) {
             Optional<Peca> existingPeca = pecaService.getPecaByNome(selectedPeca.getNome());
             if (!existingPeca.isPresent()) {
                 // Part does not exist, open create and order modal
                 try {
                     FXMLLoader loader = new FXMLLoader(getClass().getResource("/mechanic/create-order-part-modal.fxml"));
                     loader.setControllerFactory(applicationContext::getBean);
                     Parent modalContent = loader.load();

                     CreateOrderPartModalController controller = loader.getController();
                     Stage modalStage = new Stage();
                     modalStage.initModality(Modality.APPLICATION_MODAL);
                     modalStage.setTitle("Create and Order Part");
                     modalStage.setScene(new Scene(modalContent));
                     controller.setDialogStage(modalStage);
                     controller.setPartName(selectedPeca.getNome()); // Pre-fill part name

                     modalStage.showAndWait();

                     if (controller.isCreatedAndOrdered()) {
                         // Get the newly created part from the modal controller
                         Peca newlyCreatedPart = controller.getCreatedPart();
                         BigDecimal orderedQuantity = new BigDecimal(controller.getQuantity().toString()); // Get the ordered quantity

                         if (newlyCreatedPart != null && orderedQuantity != null && orderedQuantity.compareTo(BigDecimal.ZERO) > 0) {
                             // Add the newly created part with its ordered quantity to the list
                             selectedPartsWithQuantity.add(new PartQuantity(newlyCreatedPart, orderedQuantity));
                             System.out.println("Part created and ordered: " + newlyCreatedPart.getNome() + " Quantity: " + orderedQuantity);
                         } else {
                             showAlert(Alert.AlertType.ERROR, "Part Creation Failed", "Invalid Quantity", "Part creation and ordering failed or quantity is zero for: " + selectedPeca.getNome());
                             System.out.println("Part creation and ordering failed or quantity is zero for: " + selectedPeca.getNome());
                         }
                     } else {
                         System.out.println("Part creation and ordering cancelled for: " + selectedPeca.getNome());
                     }
                 } catch (IOException e) {
                     e.printStackTrace();
                     showAlert(Alert.AlertType.ERROR, "Modal Load Error", "Failed to Load Modal", "Error loading create/order part modal: " + e.getMessage());
                     // Handle error loading modal
                 } catch (NumberFormatException e) {
                     System.err.println("Invalid quantity from CreateOrderPartModalController for part: " + selectedPeca.getNome());
                     showAlert(Alert.AlertType.ERROR, "Invalid Quantity", "Invalid Quantity", "Invalid quantity from CreateOrderPartModalController for part: " + selectedPeca.getNome());
                     // Handle invalid quantity from the create/order modal
                 }
             }
         }

        dialogStage.close(); // Close the select parts modal
    }
    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
