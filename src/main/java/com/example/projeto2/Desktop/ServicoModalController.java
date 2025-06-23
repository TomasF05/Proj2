package com.example.projeto2.Desktop;

import com.example.projeto2.Services.ServicoService;
import com.example.projeto2.Tables.Servico;
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

import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class ServicoModalController implements Initializable {

    @FXML
    private ListView<Servico> servicoListView;

    @FXML
    private TextField servicoTextField;

    private Servico selectedServico;
    private Stage dialogStage;

    @Autowired
    private ServicoService servicoService;

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        servicoListView.setCellFactory(param -> new ServicoListCell(this));
        loadServicos();
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public Servico getSelectedServico() {
        return selectedServico;
    }

    private void loadServicos() {
        List<Servico> servicos = servicoService.getAllServicos();
        ObservableList<Servico> observableList = FXCollections.observableArrayList(servicos);
        servicoListView.setItems(observableList);
    }

    public void selectServico(Servico servico) {
        this.selectedServico = servico;
        if (dialogStage != null) {
            dialogStage.close();
        }
    }

    @FXML
    private void handleAddNewServico() {
        String servicoName = servicoTextField.getText();

        if (servicoName.isEmpty()) {
            showAlert("Error", "Missing Information", "Please enter a service name.");
            return;
        }

        Servico servico = new Servico();
        servico.setNome(servicoName);
        // Set default price or prompt for it if needed
        servico.setPreco(new BigDecimal("0.00")); // Default price

        try {
            servicoService.saveServico(servico);
            showAlert("Success", "Service Added", "New service added successfully.");
            loadServicos(); // Refresh the list
            servicoTextField.clear();
        } catch (Exception e) {
            showAlert("Error", "Failed to Add", "Error adding service: " + e.getMessage());
        }
    }

    @FXML
    private void handleClose() {
        selectedServico = null; // Ensure no selection is returned on close
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