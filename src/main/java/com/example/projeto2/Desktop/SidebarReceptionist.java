package com.example.projeto2.Desktop;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import org.springframework.stereotype.Component;

@Component
public class SidebarReceptionist {

    @FXML
    private Button dashboardButton;

    @FXML
    private Button agendamentosButton;

    @FXML
    private Button inventarioButton;

    @FXML
    private Button reparacoesButton;

    @FXML
    private Button sairButton;

    private BorderPane mainLayout;

    public void setMainLayout(BorderPane mainLayout) {
        this.mainLayout = mainLayout;
    }

    @FXML
    private void initialize() {
        dashboardButton.setOnAction(event -> carregarDashboard());
        agendamentosButton.setOnAction(event -> carregarAgendamentos());
        inventarioButton.setOnAction(event -> carregarInventario());
        reparacoesButton.setOnAction(event -> carregarReparacoes());
        sairButton.setOnAction(event -> sair());
    }

    private void carregarDashboard() {
        try {
            Parent view = FXMLLoader.load(getClass().getResource("/mechanic/dashboard.fxml"));
            mainLayout.setCenter(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void carregarAgendamentos() {
        try {
            Parent view = FXMLLoader.load(getClass().getResource("/mechanic/agendamentos.fxml"));
            mainLayout.setCenter(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void carregarInventario() {
        try {
            Parent view = FXMLLoader.load(getClass().getResource("/mechanic/inventario.fxml"));
            mainLayout.setCenter(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void carregarReparacoes() {
        try {
            Parent view = FXMLLoader.load(getClass().getResource("/mechanic/reparacoes.fxml"));
            mainLayout.setCenter(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sair() {
        try {
            Parent view = FXMLLoader.load(getClass().getResource("/login.fxml"));
            mainLayout.getScene().setRoot(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}