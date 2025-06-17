package com.example.projeto2.Desktop;

import com.example.projeto2.Services.AgendamentoService;
import com.example.projeto2.Tables.Agendamento;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class AppointmentSlotModalController implements Initializable {

    @FXML
    private ListView appointmentSlotListView;

    @FXML
    private Button selectButton;

    @Autowired
    private AgendamentoService agendamentoService;

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadAppointmentSlots();
    }

    private void loadAppointmentSlots() {
        List<Agendamento> appointmentSlots = agendamentoService.getTodayAppointments();
        ObservableList<Agendamento> observableList = FXCollections.observableArrayList(appointmentSlots);
        appointmentSlotListView.setItems(observableList);
    }

    @FXML
    private void handleSelect() {
        Agendamento selectedAppointmentSlot = (Agendamento) appointmentSlotListView.getSelectionModel().getSelectedItem();
        if (selectedAppointmentSlot != null) {
            System.out.println("Selected appointment slot: " + selectedAppointmentSlot);
            // Implement logic to pass the selected appointment slot back to the ScheduleRepairController
            ScheduleRepairController scheduleRepairController = applicationContext.getBean(ScheduleRepairController.class);
            scheduleRepairController.setAppointmentSlot(selectedAppointmentSlot);

            // Close the modal
            Stage stage = (Stage) selectButton.getScene().getWindow();
            stage.close();
        } else {
            System.out.println("No appointment slot selected");
        }
    }

    @FXML
    private void handleClose() {
        Stage stage = (Stage) selectButton.getScene().getWindow();
        stage.close();
    }
}