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

    @Autowired
    private MainController mainController; // Spring will inject the MainController instance
    private final ApplicationContext context;

    @Autowired
    public HeaderController(ApplicationContext context) {
        this.context = context;
    }

    @FXML
    public void initialize() {
        // mainController is now directly autowired by Spring
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