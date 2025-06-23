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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Optional;
import java.time.format.DateTimeParseException;

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

    @FXML
    public void initialize() {
        // Initialize loggedInFuncionario (assuming LoginController.getLoggedInUsername() provides the username)
        funcionarioService.getFuncionarioByUsername(LoginController.getLoggedInUsername()).ifPresent(funcionario -> loggedInFuncionario = funcionario);
    }

    public void setAppointmentSlot(Agendamento appointmentSlot) {
        this.selectedAppointmentSlot = appointmentSlot;
        // Format and set the date and time fields from the selected appointment slot
        if (selectedAppointmentSlot.getDataHora() != null) {
            LocalDateTime dateTime = selectedAppointmentSlot.getDataHora().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            dateField.setText(dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            timeField.setText(dateTime.format(DateTimeFormatter.ofPattern("HH:mm")));
        }
        if (selectedAppointmentSlot.getObservacoes() != null) {
            observationsField.setText(selectedAppointmentSlot.getObservacoes());
        }
    }

    @FXML
    private void handleSelectAppointmentSlot() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/appointment-slot-modal.fxml"));
        loader.setControllerFactory(applicationContext::getBean);
        Parent root = loader.load();
        AppointmentSlotModalController controller = loader.getController();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL); // Make it a modal window
        stage.setScene(new Scene(root));
        stage.showAndWait(); // Show and wait for the modal to close
    }

    @FXML
    private void handleConfirm() {
        String dateText = dateField.getText();
        String timeText = timeField.getText();
        String observations = observationsField.getText();

        if (dateText.isEmpty() || timeText.isEmpty() || selectedAppointmentSlot == null || loggedInFuncionario == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Missing Information", "Please select an appointment slot, enter date and time, and ensure a mechanic is logged in.");
            return;
        }

        try {
            // Parse date and time from text fields
            LocalDate localDate = LocalDate.parse(dateText, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            LocalTime localTime = LocalTime.parse(timeText, DateTimeFormatter.ofPattern("HH:mm"));
            LocalDateTime combinedDateTime = LocalDateTime.of(localDate, localTime);
            Date sqlDate = Date.from(combinedDateTime.atZone(ZoneId.systemDefault()).toInstant());

            // Update the selected appointment slot with new data
            selectedAppointmentSlot.setDataHora(sqlDate);
            selectedAppointmentSlot.setObservacoes(observations);
            // Assuming idVeiculo and idFuncionario are already set or will be set elsewhere for Agendamento
            // selectedAppointmentSlot.setIdVeiculo(selectedAppointmentSlot.getIdVeiculo());
            // selectedAppointmentSlot.setIdFuncionario(loggedInFuncionario.getIdFuncionario());

            // Create a new Reparacao object
            Reparacao newReparacao = new Reparacao();
            newReparacao.setDescricao(observations);
            newReparacao.setIdVeiculo(selectedAppointmentSlot.getIdVeiculo());
            // newReparacao.setIdFuncionario(loggedInFuncionario.getIdFuncionario()); // This is set when repair starts
            newReparacao.setEstado("Pendente");
            newReparacao.setDataInicio(new java.sql.Date(sqlDate.getTime())); // Set dataInicio for Reparacao

            // Save the new Reparacao to the database
            reparacaoService.saveReparacao(newReparacao);

            // Save the Agendamento
            agendamentoService.saveAgendamento(selectedAppointmentSlot);

            showAlert(Alert.AlertType.INFORMATION, "Success", "Repair Scheduled", "Repair scheduled successfully.");
        } catch (DateTimeParseException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid Date/Time Format", "Please enter date in dd/MM/yyyy and time in HH:mm format.");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Scheduling Failed", "An error occurred while scheduling the repair: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
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