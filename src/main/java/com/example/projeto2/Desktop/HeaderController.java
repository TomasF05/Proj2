package com.example.projeto2.Desktop;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HeaderController {

    private MainController mainController;

    @Autowired
    public HeaderController() {
        // Spring will inject dependencies later
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void toggleSidebar() {
        if (mainController != null) {
            mainController.toggleSidebar();
        }
    }
}