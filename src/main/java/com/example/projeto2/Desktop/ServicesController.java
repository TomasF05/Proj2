package com.example.projeto2.Desktop;

import com.example.projeto2.Services.ServicoService;
import com.example.projeto2.Tables.Servico;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Component
public class ServicesController implements Initializable {

    @FXML
    private ListView<String> servicesList;
    
    private final ServicoService servicoService;
    
    @Autowired
    public ServicesController(ServicoService servicoService) {
        this.servicoService = servicoService;
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Load services data from the database
        loadServicesData();
    }
    
    private void loadServicesData() {
        // Get all services from the database
        List<Servico> servicos = servicoService.getAllServicos();
        
        // Convert the service objects to strings for display in the ListView
        List<String> servicoStrings = servicos.stream()
                .map(servico -> servico.getNome() + " - " + servico.getDescricao())
                .collect(Collectors.toList());
        
        // Convert the list to an ObservableList
        ObservableList<String> data = FXCollections.observableArrayList(servicoStrings);
        
        // Set the data to the ListView
        servicesList.setItems(data);
    }
    
    // You can add methods for adding, updating, and deleting services here
}