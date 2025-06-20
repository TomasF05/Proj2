package com.example.projeto2.Desktop;

import com.example.projeto2.Services.FaturaClienteService;
import com.example.projeto2.Services.ReparacaoService;
import com.example.projeto2.Services.ServicoService;
import com.example.projeto2.Tables.FaturaCliente;
import com.example.projeto2.Tables.Peca;
import com.example.projeto2.Tables.Reparacao;
import com.example.projeto2.Tables.Servico;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import com.example.projeto2.Desktop.PecasSelectionListener;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Component
public class CreateInvoiceController implements PecasSelectionListener {

    @FXML
    private TextField clientNameField;

    @FXML
    private Button selectReparacaoButton;

    @FXML
    private Button selectServicoButton;

    @FXML
    private Button selectPecasButton;

    @FXML
    private TextField laborCostField;

    @FXML
    private ComboBox paymentMethodComboBox;

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
    private ApplicationContext applicationContext;

    @Autowired
    private FaturaClienteService faturaClienteService;

    @Autowired
    private ReparacaoService reparacaoService;

    @Autowired
    private ServicoService servicoService;

    private Reparacao selectedReparacao;

    private Servico selectedServico;

    @FXML
    private void initialize() {
        // Set payment methods
        List paymentMethods = Arrays.asList("Credit Card", "Debit Card", "Cash", "Paypal");
        ObservableList paymentMethodsList = FXCollections.observableArrayList(paymentMethods);
        paymentMethodComboBox.setItems(paymentMethodsList);
    }

    public void setReparacao(Reparacao reparacao) {
        this.selectedReparacao = reparacao;
        if (reparacao.getCliente() != null) {
            this.clientNameField.setText(reparacao.getCliente().getNome());
        }
    }

    public void setServico(Servico servico) {
        this.selectedServico = servico;
    }

    @FXML
    private void handleSelectReparacao() throws IOException {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/reparacao-modal.fxml"));
         loader.setControllerFactory(applicationContext::getBean);
         Parent root = loader.load();
         ReparacaoModalController controller = loader.getController();
         controller.setApplicationContext(applicationContext);
         Stage stage = new Stage();
         stage.setScene(new Scene(root));
         stage.show();
    }

    @FXML
    private void handleSelectServico() throws IOException {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/servico-modal.fxml"));
         loader.setControllerFactory(applicationContext::getBean);
         Parent root = loader.load();
         ServicoModalController controller = loader.getController();
         controller.setApplicationContext(applicationContext);
         Stage stage = new Stage();
         stage.setScene(new Scene(root));
         stage.show();
    }

    @FXML
    private void handleSelectPecas() throws IOException {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/pecas-modal.fxml"));
         loader.setControllerFactory(applicationContext::getBean);
         Parent root = loader.load();
         PecasModalController controller = loader.getController();
         controller.setApplicationContext(applicationContext);
           Stage stage = new Stage();
           stage.setScene(new Scene(root));
           stage.show();
    }

    @FXML
    private void handleConfirm() {
        // Implement logic to create invoice
        if (selectedReparacao != null) {
            System.out.println("Creating invoice for: " + selectedReparacao);

            // Get the last invoice
            FaturaCliente lastFatura = faturaClienteService.getLastFaturaCliente();
            BigDecimal nextInvoiceNumber = BigDecimal.ONE; // Default value
            if (lastFatura != null) {
                // Get the last invoice number and increment it
                nextInvoiceNumber = lastFatura.getnFatura().add(BigDecimal.ONE);
            }
            // Create a new FaturaCliente object
            FaturaCliente newFaturaCliente = new FaturaCliente();
            newFaturaCliente.setnFatura(nextInvoiceNumber); // Set the new invoice number
            newFaturaCliente.setData(java.sql.Date.valueOf(LocalDate.now())); // Set current date
            newFaturaCliente.setIdCliente(selectedReparacao.getCliente().getIdCliente()); // Set client ID
            newFaturaCliente.setValorTotal(selectedReparacao.getValorTotal()); // Set total value
            newFaturaCliente.setMetodoPagamento(paymentMethodComboBox.getValue().toString()); // Set payment method

            // Save the new FaturaCliente to the database
            faturaClienteService.saveFaturaCliente(newFaturaCliente);

            System.out.println("Created invoice: " + newFaturaCliente);
        } else {
            System.out.println("No reparacao selected");
        }
    }

    @Override
    public void onPecasSelected(List<Peca> selectedPecas) {
        // Implement logic to handle selected pecas
        System.out.println("Selected pecas: " + selectedPecas);
        // You can now use the selectedPecas list to update the UI or perform other actions
        // For example, you can display the selected pecas in the pecasListView
        // or calculate the total cost of the selected pecas
        selectedPecas.forEach(peca -> System.out.println(peca.getNome()));
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