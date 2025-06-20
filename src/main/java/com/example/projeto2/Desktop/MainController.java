// MainController.java
package com.example.projeto2.Desktop;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;

@Component
public class MainController {

    @FXML
    private BorderPane mainLayout;


    @FXML
    private StackPane contentContainer; // Container for the main content

    public StackPane getContentContainer() {
        return contentContainer;
    }

    private VBox currentSidebar; // To hold the currently loaded sidebar

    private final ApplicationContext context;

    @Autowired
    public MainController(ApplicationContext context) {
        this.context = context;
        System.out.println("MainController constructor called. Instance hash: " + this.hashCode());
    }

    @FXML
    public void initialize() {
        System.out.println("MainController initialize - contentContainer initialized.");
    }

    public void loadUserSpecificContent(BigDecimal userType) {
        System.out.println("MainController loadUserSpecificContent entered. UserType: " + userType + ", Instance hash: " + this.hashCode());
        try {
            Parent dashboardContent = null;

            if (userType.equals(new BigDecimal(1))) { // Mechanic
                FXMLLoader dashboardLoader = new FXMLLoader(getClass().getResource("/mechanic/dashboard.fxml"));
                dashboardLoader.setControllerFactory(context::getBean);
                dashboardContent = dashboardLoader.load();

            } else if (userType.equals(new BigDecimal(2))) { // Receptionist
                FXMLLoader dashboardLoader = new FXMLLoader(getClass().getResource("/receptionist-dashboard.fxml"));
                dashboardLoader.setControllerFactory(context::getBean);
                dashboardContent = dashboardLoader.load();
            }

            // Load the initial dashboard content
            if (dashboardContent != null) {
                contentContainer.getChildren().setAll(dashboardContent);
            }

        } catch (IOException e) {
            System.err.println("MainController loadUserSpecificContent - IOException during FXML loading: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("MainController loadUserSpecificContent - An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}