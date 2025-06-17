package com.example.projeto2.Desktop;

import com.example.projeto2.Tables.Peca;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import com.example.projeto2.Desktop.PecasSelectionListener;

import java.util.List;
import java.util.ArrayList;

@Component
public class PecasModalController {

    @FXML
    private ListView pecasListView;

    @FXML
    private Button selectButton;

    @Autowired
    private ApplicationContext applicationContext;

    //@Autowired
    //private PecasSelectionListener selectionListener;

    @FXML
    private void handleSelect() {
        // Implement logic to select pecas
        List<Peca> selectedPecas = new ArrayList<>();
        selectedPecas.addAll(pecasListView.getSelectionModel().getSelectedItems());

        CreateInvoiceController createInvoiceController = applicationContext.getBean(CreateInvoiceController.class);
        createInvoiceController.onPecasSelected(selectedPecas);

        // Close the modal
        Stage stage = (Stage) selectButton.getScene().getWindow();
        stage.close();
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    //public void setSelectionListener(PecasSelectionListener selectionListener) {
    //    this.selectionListener = selectionListener;
    //}

    @FXML
    private void handleClose() {
        Stage stage = (Stage) selectButton.getScene().getWindow();
        stage.close();
    }
}