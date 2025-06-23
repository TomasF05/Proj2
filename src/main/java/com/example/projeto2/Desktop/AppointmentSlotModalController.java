package com.example.projeto2.Desktop;

import com.example.projeto2.Services.AgendamentoService;
import com.example.projeto2.Tables.Agendamento;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    private ListView<Agendamento> appointmentSlotListView;

    private Agendamento selectedAppointmentSlot;

    @Autowired
    private AgendamentoService agendamentoService;

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appointmentSlotListView.setCellFactory(param -> new AppointmentSlotListCell(this));
        loadAppointmentSlots();
    }

    private void loadAppointmentSlots() {
        List<Agendamento> appointmentSlots = agendamentoService.getTodayAppointments();
        ObservableList<Agendamento> observableList = FXCollections.observableArrayList(appointmentSlots);
        appointmentSlotListView.setItems(observableList);
    }

    public void selectAppointmentSlot(Agendamento agendamento) {
        this.selectedAppointmentSlot = agendamento;
        ScheduleRepairController scheduleRepairController = applicationContext.getBean(ScheduleRepairController.class);
        scheduleRepairController.setAppointmentSlot(selectedAppointmentSlot);

        Stage stage = (Stage) appointmentSlotListView.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleClose() {
        Stage stage = (Stage) appointmentSlotListView.getScene().getWindow();
        stage.close();
    }
}