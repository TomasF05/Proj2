package com.example.projeto2.Desktop;

import com.example.projeto2.Services.ClienteService;
import com.example.projeto2.Tables.Cliente;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import com.example.projeto2.Services.CodPostalService;
import com.example.projeto2.Tables.CodPostal;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CreateClientController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField nifField;

    @FXML
    private TextField contactField;

    @FXML
    private Button postalCodeButton;

    @FXML
    private TextField postalCodeField;

    @FXML
    private Button dashboardButton;
    @FXML
    private Button appointmentsButton;
    @FXML
    private Button inventoryButton;
    @FXML
    private Button createClientButton;
    @FXML
    private Button createInvoiceButton;
    @FXML
    private Button logoutButton;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private CodPostalService codPostalService;

    private CodPostal selectedPostalCode;

    public void setPostalCode(CodPostal postalCode) {
        this.selectedPostalCode = postalCode;
        this.postalCodeField.setText(postalCode.getCodPostal() + " - " + postalCode.getDescricao());
    }

    @FXML
    private void handleSelectPostalCode() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/postal-code-modal.fxml"));
        loader.setControllerFactory(applicationContext::getBean);
        Parent root = loader.load();
        PostalCodeModalController controller = loader.getController();
        controller.setApplicationContext(applicationContext);
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void handleCreateClient() {
        String name = nameField.getText();
        String nif = nifField.getText();
        String contact = contactField.getText();

        if (name != null && !name.isEmpty() && nif != null && !nif.isEmpty() && contact != null && !contact.isEmpty() && selectedPostalCode != null) {
            Cliente newCliente = new Cliente();
            newCliente.setNome(name);
            newCliente.setNif(nif);
            newCliente.setContacto(contact);
            newCliente.setCodPostal(selectedPostalCode);

            clienteService.saveCliente(newCliente);

            System.out.println("Client created: " + newCliente);
        } else {
            System.out.println("Please enter all the required information");
        }
    }
    @FXML
    public void onDashboardButtonClick() {
        try {
            SceneManager.switchScene("/receptionist-dashboard.fxml", "Dashboard", dashboardButton);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onAppointmentsButtonClick() {
        try {
            SceneManager.switchScene("/schedule-repair.fxml", "Agendamentos", appointmentsButton);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onInventoryButtonClick() {
        try {
            SceneManager.switchScene("/mechanic/inventory.fxml", "Inventário", inventoryButton);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onCreateClientButtonClick() {
        try {
            SceneManager.switchScene("/create-client.fxml", "Criar Cliente", createClientButton);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onCreateInvoiceButtonClick() {
        try {
            SceneManager.switchScene("/create-invoice.fxml", "Criar Fatura", createInvoiceButton);
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
}