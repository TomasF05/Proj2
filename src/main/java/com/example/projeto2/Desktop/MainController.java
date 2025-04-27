// MainController.java
package com.example.projeto2.Desktop;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MainController {

    @FXML
    private BorderPane mainLayout;

    private final ApplicationContext context;

    @Autowired
    public MainController(ApplicationContext context) {
        this.context = context;
    }

    @FXML
    public void initialize() {
        try {
            // Load header content
            FXMLLoader headerLoader = new FXMLLoader(getClass().getResource("/header.fxml"));
            headerLoader.setControllerFactory(context::getBean);
            Parent headerContent = headerLoader.load();

            // Load sidebar content
            FXMLLoader sidebarLoader = new FXMLLoader(getClass().getResource("/sidebar.fxml"));
            sidebarLoader.setControllerFactory(context::getBean);
            Parent sidebarContent = sidebarLoader.load();

            // Get the SidebarController and set the mainLayout
            SidebarController sidebarController = sidebarLoader.getController();
            sidebarController.setMainLayout(mainLayout);

            // Assemble the main layout
            mainLayout.setTop(headerContent);
            mainLayout.setLeft(sidebarContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}