package com.example.projeto2.Desktop;

import com.example.projeto2.Services.CodPostalService;
import com.example.projeto2.Tables.CodPostal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Component
public class PostalCodeModalController implements Initializable {

    @FXML
    private ListView<CodPostal> postalCodeListView;

    @FXML
    private TextField searchPostalCodeField;

    @FXML
    private TextField postalCodeField;

    @FXML
    private TextField descriptionField;

    private CodPostal selectedPostalCode;
    private Stage dialogStage;

    @Autowired
    private CodPostalService codPostalService;

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        postalCodeListView.setCellFactory(param -> new PostalCodeListCell(this));
        loadPostalCodes();
        searchPostalCodeField.textProperty().addListener((observable, oldValue, newValue) -> filterPostalCodes(newValue));
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public CodPostal getSelectedPostalCode() {
        return selectedPostalCode;
    }

    private void loadPostalCodes() {
        List<CodPostal> postalCodes = codPostalService.getAllCodPostais();
        ObservableList<CodPostal> observableList = FXCollections.observableArrayList(postalCodes);
        postalCodeListView.setItems(observableList);
    }

    private void filterPostalCodes(String searchText) {
        List<CodPostal> allPostalCodes = codPostalService.getAllCodPostais();
        List<CodPostal> filteredList = allPostalCodes.stream()
                .filter(cp -> cp.getCodPostal().toLowerCase().contains(searchText.toLowerCase()) ||
                        cp.getDescricao().toLowerCase().contains(searchText.toLowerCase()))
                .collect(Collectors.toList());
        postalCodeListView.setItems(FXCollections.observableArrayList(filteredList));
    }

    public void selectPostalCode(CodPostal codPostal) {
        this.selectedPostalCode = codPostal;
        if (dialogStage != null) {
            dialogStage.close();
        }
    }

    @FXML
    private void handleAddNewPostalCode() {
        String newPostalCode = postalCodeField.getText();
        String newDescription = descriptionField.getText();

        if (newPostalCode.isEmpty() || newDescription.isEmpty()) {
            showAlert("Error", "Missing Information", "Please enter both postal code and description.");
            return;
        }

        CodPostal codPostal = new CodPostal();
        codPostal.setCodPostal(newPostalCode);
        codPostal.setDescricao(newDescription);

        try {
            codPostalService.saveCodPostal(codPostal);
            showAlert("Success", "Postal Code Added", "New postal code added successfully.");
            loadPostalCodes(); // Refresh the list
            postalCodeField.clear();
            descriptionField.clear();
        } catch (Exception e) {
            showAlert("Error", "Failed to Add", "Error adding postal code: " + e.getMessage());
        }
    }

    @FXML
    private void handleClose() {
        selectedPostalCode = null; // Ensure no selection is returned on close
        if (dialogStage != null) {
            dialogStage.close();
        }
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}