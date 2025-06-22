package com.example.projeto2.Desktop;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class HeaderController {

    @FXML
    private Button sidebarToggleButton;

    private MainController mainController;
    private final ApplicationContext context;

    @Autowired
    public HeaderController(ApplicationContext context) {
        this.context = context;
    }

    @FXML
    public void initialize() {
        // Retrieve MainController from the ApplicationContext
        this.mainController = context.getBean(MainController.class);
        if (sidebarToggleButton != null) {
            sidebarToggleButton.setOnAction(event -> {
                if (mainController != null) {
                    mainController.onSidebarToggleButtonClick();
                }
            });
        }
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
        if (sidebarToggleButton != null) {
            sidebarToggleButton.setOnAction(event -> {
                if (mainController != null) {
                    mainController.onSidebarToggleButtonClick();
                }
            });
        }
    }

    public void setSidebarToggleButtonText(String text) {
        if (sidebarToggleButton != null) {
            sidebarToggleButton.setText(text);
        }
    }
}