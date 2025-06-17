package com.example.projeto2.Desktop;

import com.example.projeto2.Services.ServicoService;
import com.example.projeto2.Tables.Servico;
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
public class ServicoModalController implements Initializable {

    @FXML
    private ListView servicoListView;

    @FXML
    private Button addNewServicoButton;

    @FXML
    private Button selectButton;

    @FXML
    private TextField servicoTextField;

    @Autowired
    private ServicoService servicoService;

    @Autowired
    private ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadServico();
    }

    private void loadServico() {
        List<Servico> servicos = servicoService.getAllServicos();
        ObservableList<Servico> observableList = FXCollections.observableArrayList(servicos);
        servicoListView.setItems(observableList);
    }

    @FXML
    private void handleAddNewServico() {
        String servicoName = servicoTextField.getText();
        if (servicoName != null && !servicoName.isEmpty()) {
            Servico newServico = new Servico();
            newServico.setNome(servicoName);
            servicoService.saveServico(newServico);
            loadServico(); // Refresh the list
        } else {
            System.out.println("Please enter servico name");
        }
    }

    @FXML
    private void handleSelect() {
        Servico selectedServico = (Servico) servicoListView.getSelectionModel().getSelectedItem();
        if (selectedServico != null) {
            System.out.println("Selected servico: " + selectedServico);
            // Implement logic to pass the selected servico back to the CreateInvoiceController
            CreateInvoiceController createInvoiceController = applicationContext.getBean(CreateInvoiceController.class);
            createInvoiceController.setServico(selectedServico);

            // Close the modal
            Stage stage = (Stage) selectButton.getScene().getWindow();
            stage.close();
        } else {
            System.out.println("No servico selected");
        }
    }

    @FXML
    private void handleClose() {
        Stage stage = (Stage) selectButton.getScene().getWindow();
        stage.close();
    }
}