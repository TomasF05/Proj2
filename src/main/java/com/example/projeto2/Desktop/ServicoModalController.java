package com.example.projeto2.Desktop;

import com.example.projeto2.Services.ReparacaoService;
import com.example.projeto2.Tables.Reparacao;
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
    private ListView<Reparacao> reparacaoListView;

    @FXML
    private TextField servicoTextField;

    private Reparacao selectedReparacao;
    private Stage dialogStage;

    @Autowired
    private ReparacaoService reparacaoService;

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reparacaoListView.setCellFactory(param -> new ServicoListCell(this));
        loadReparacoes();
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public Reparacao getSelectedReparacao() {
        return selectedReparacao;
    }

    private void loadReparacoes() {
        List<Reparacao> reparacoes = reparacaoService.getAllReparacoes();
        ObservableList<Reparacao> observableList = FXCollections.observableArrayList(reparacoes);
        reparacaoListView.setItems(observableList);
    }

    public void selectReparacao(Reparacao reparacao) {
        this.selectedReparacao = reparacao;
        if (dialogStage != null) {
            dialogStage.close();
        }
    }

    @FXML
    private void handleAddNewReparacao() {
        String reparacaoDescription = servicoTextField.getText(); // Renamed for clarity

        if (reparacaoDescription.isEmpty()) {
            showAlert("Error", "Missing Information", "Please enter a repair description.");
            return;
        }

        Reparacao reparacao = new Reparacao();
        reparacao.setDescricao(reparacaoDescription);
        // No need to set 'preco' as Reparacao uses 'valorTotal' and it's not directly set here.

        try {
            reparacaoService.saveReparacao(reparacao);
            showAlert("Success", "Repair Added", "New repair added successfully.");
            loadReparacoes(); // Refresh the list
            servicoTextField.clear();
        } catch (Exception e) {
            showAlert("Error", "Failed to Add", "Error adding service: " + e.getMessage());
        }
    }

    @FXML
    private void handleClose() {
        selectedReparacao = null; // Ensure no selection is returned on close
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