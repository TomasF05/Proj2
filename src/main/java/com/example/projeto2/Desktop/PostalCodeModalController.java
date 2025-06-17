package com.example.projeto2.Desktop;

import com.example.projeto2.Services.CodPostalService;
import com.example.projeto2.Tables.CodPostal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class PostalCodeModalController implements Initializable {

    @FXML
    private TextField searchPostalCodeField;

    @FXML
    private ListView postalCodeListView;

    @FXML
    private Button addNewPostalCodeButton;

    @FXML
    private Button selectButton;

    @FXML
    private TextField postalCodeField;

    @FXML
    private TextField descriptionField;

    @Autowired
    private CodPostalService codPostalService;

    @Autowired
    private ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadPostalCodes();
    }

    private void loadPostalCodes() {
        List<CodPostal> postalCodes = codPostalService.getAllCodPostais();
        ObservableList<CodPostal> observableList = FXCollections.observableArrayList(postalCodes);
        postalCodeListView.setItems(observableList);
    }

    @FXML
    private void handleAddNewPostalCode() {
        String postalCode = postalCodeField.getText();
        String description = descriptionField.getText();

        if (postalCode != null && !postalCode.isEmpty() && description != null && !description.isEmpty()) {
            CodPostal newPostalCode = new CodPostal();
            newPostalCode.setCodPostal(postalCode);
            newPostalCode.setDescricao(description);
            codPostalService.saveCodPostal(newPostalCode);
            loadPostalCodes(); // Refresh the list
        } else {
            System.out.println("Please enter postal code and description");
        }
    }

    @FXML
    private void handleClose() {
        Stage stage = (Stage) selectButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleSelect() {
        CodPostal selectedPostalCode = (CodPostal) postalCodeListView.getSelectionModel().getSelectedItem();
        if (selectedPostalCode != null) {
            System.out.println("Selected postal code: " + selectedPostalCode);
            // Implement logic to pass the selected postal code back to the CreateClientController
            CreateClientController createClientController = applicationContext.getBean(CreateClientController.class);
            createClientController.setPostalCode(selectedPostalCode);

            // Close the modal
            Stage stage = (Stage) selectButton.getScene().getWindow();
            stage.close();
        } else {
            System.out.println("No postal code selected");
        }
    }
}