package com.example.projeto2.Desktop;

import javafx.fxml.FXML;
import com.example.projeto2.Services.ReparacaoService;
import com.example.projeto2.Tables.Reparacao;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ReparacaoModalController {

    @FXML
    private ListView reparacaoListView;

    @FXML
    private Button selectButton;

    @Autowired
    private ReparacaoService reparacaoService;

    @Autowired
    private ApplicationContext applicationContext;

    @FXML
    private void handleSelect() {
        Reparacao selectedReparacao = (Reparacao) reparacaoListView.getSelectionModel().getSelectedItem();
        if (selectedReparacao != null) {
            System.out.println("Selected reparacao: " + selectedReparacao);
            // Implement logic to pass the selected reparacao back to the CreateInvoiceController
            CreateInvoiceController createInvoiceController = applicationContext.getBean(CreateInvoiceController.class);
            createInvoiceController.setReparacao(selectedReparacao);

            // Close the modal
            Stage stage = (Stage) selectButton.getScene().getWindow();
            stage.close();
        } else {
            System.out.println("No reparacao selected");
        }
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @FXML
    private void handleClose() {
        Stage stage = (Stage) reparacaoListView.getScene().getWindow();
        stage.close();
    }
}