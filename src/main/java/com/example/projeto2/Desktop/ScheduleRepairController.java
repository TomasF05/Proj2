package com.example.projeto2.Desktop;

import com.example.projeto2.Services.AgendamentoService;
import com.example.projeto2.Services.FuncionarioService;
import com.example.projeto2.Services.ReparacaoService;
import com.example.projeto2.Tables.Agendamento;
import com.example.projeto2.Tables.Funcionario;
import com.example.projeto2.Tables.Reparacao;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class ScheduleRepairController {

    @FXML
    private TextField dateField;

    @FXML
    private TextField timeField;

     @FXML
     private Button appointmentSlotButton;

     @FXML
     private TextField observationsField;

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
     private FuncionarioService funcionarioService;

     @Autowired
     private AgendamentoService agendamentoService;

     @Autowired
     private ReparacaoService reparacaoService;
 
     private Agendamento selectedAppointmentSlot;
     private Funcionario loggedInFuncionario;

     public void setAppointmentSlot(Agendamento appointmentSlot) {
         // Get the logged-in funcionario
         //loggedInFuncionario = funcionarioService.findByUsername("test1"); //TODO: Get the logged in user
         //funcionarioService.getFuncionarioByUsername(LoginController.getLoggedInUsername()).ifPresent(funcionario -> loggedInFuncionario = funcionario);
         this.selectedAppointmentSlot = appointmentSlot;
         this.timeField.setText(selectedAppointmentSlot.getDataHora().toString());
     }

     @FXML
     private void handleSelectAppointmentSlot() throws IOException {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/appointment-slot-modal.fxml"));
         loader.setControllerFactory(applicationContext::getBean);
         Parent root = loader.load();
         AppointmentSlotModalController controller = loader.getController();
         //controller.setApplicationContext(applicationContext);
         Stage stage = new Stage();
         stage.setScene(new Scene(root));
         stage.show();
     }

    @FXML
    private void handleConfirm() {
        // Implement logic to schedule repair
        if (selectedAppointmentSlot != null) {
            System.out.println("Scheduling repair for: " + selectedAppointmentSlot);

            // Create a new Reparacao object
            Reparacao newReparacao = new Reparacao();
            newReparacao.setDescricao(observationsField.getText());
            newReparacao.setIdVeiculo(selectedAppointmentSlot.getIdVeiculo()); // Assuming you have the vehicle ID
            newReparacao.setIdFuncionario(loggedInFuncionario.getIdFuncionario()); // Set the logged-in funcionario ID
            newReparacao.setEstado("Pendente"); // Set the initial state to "Pendente"

            // Save the new Reparacao to the database
            reparacaoService.saveReparacao(newReparacao);

            // Save the Agendamento
            selectedAppointmentSlot.setObservacoes(observationsField.getText());
            agendamentoService.saveAgendamento(selectedAppointmentSlot);

            System.out.println("Scheduled repair: " + newReparacao);
        } else {
            System.out.println("No appointment slot selected");
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