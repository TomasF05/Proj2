// java
package com.example.projeto2.Desktop;

import com.example.projeto2.Services.ClienteService;
import com.example.projeto2.Tables.Cliente;
import com.example.projeto2.Tables.CodPostal;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateClientController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField nifField;

    @FXML
    private TextField contactField;

    @FXML
    private TextField postalCodeField;

    @FXML
    private TextField vehicleField;

    private final ClienteService clienteService;

    @Autowired
    public CreateClientController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @FXML
    protected void handleCreateClient() {
        String name = nameField.getText();
        String nif = nifField.getText();
        String contact = contactField.getText();
        String postalCode = postalCodeField.getText();
        String vehicle = vehicleField.getText();

        if (name.isEmpty() || nif.isEmpty() || contact.isEmpty() || postalCode.isEmpty() || vehicle.isEmpty()) {
            showAlert("Error", "Create Client Failed", "All fields are required.");
            return;
        }

        Cliente newClient = new Cliente();
        newClient.setNome(name);
        newClient.setNif(nif);
        newClient.setContacto(contact);

        // Create a new CodPostal setting its codPostal field from the string
        CodPostal cp = new CodPostal();
        cp.setCodPostal(postalCode);
        newClient.setCodPostal(cp);

        clienteService.saveCliente(newClient);

        showAlert("Success", "Client Created", "Client created successfully.");

        // Clear fields after successful creation
        nameField.clear();
        nifField.clear();
        contactField.clear();
        postalCodeField.clear();
        vehicleField.clear();
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}