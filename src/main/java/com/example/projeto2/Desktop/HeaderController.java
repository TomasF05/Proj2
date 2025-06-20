package com.example.projeto2.Desktop;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HeaderController {

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
    }

    @FXML
    private void toggleSidebar() {
        if (mainController != null) {
            mainController.toggleSidebar();
        }
    }
}