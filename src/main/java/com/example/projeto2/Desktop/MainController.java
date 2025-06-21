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
        System.out.println("MainController: loadUserSpecificContent called with userType: " + userType);
        try {
            Parent dashboardContent = null;

            if (userType.equals(BigDecimal.ONE)) { // Mechanic
                String fxmlPath = "/mechanic/dashboard.fxml";
                java.net.URL fxmlUrl = getClass().getResource(fxmlPath);
                System.out.println("MainController: Attempting to load FXML from path: " + fxmlPath);

                if (fxmlUrl == null) {
                    System.err.println("MainController: FXML resource not found: " + fxmlPath);
                    return; // Exit if resource not found
                }

                FXMLLoader dashboardLoader = new FXMLLoader(fxmlUrl);
                dashboardLoader.setControllerFactory(context::getBean);
                dashboardContent = dashboardLoader.load();

                if (dashboardContent != null) {
                    System.out.println("MainController: FXML content loaded successfully. View is not null.");
                } else {
                    System.out.println("MainController: FXML content loading failed. View is null.");
                }

            } else if (userType.intValue() == 2) { // Receptionist
                String fxmlPath = "/receptionist-dashboard.fxml";
                java.net.URL fxmlUrl = getClass().getResource(fxmlPath);
                System.out.println("MainController: Attempting to load FXML from path: " + fxmlPath);

                if (fxmlUrl == null) {
                    System.err.println("MainController: FXML resource not found: " + fxmlPath);
                    return; // Exit if resource not found
                }

                FXMLLoader dashboardLoader = new FXMLLoader(fxmlUrl);
                dashboardLoader.setControllerFactory(context::getBean);
                dashboardContent = dashboardLoader.load();

                if (dashboardContent != null) {
                    System.out.println("MainController: FXML content loaded successfully. View is not null.");
                } else {
                    System.out.println("MainController: FXML content loading failed. View is null.");
                }
            }

            // Load the initial dashboard content
            if (dashboardContent != null) {
                contentContainer.getChildren().setAll(dashboardContent);
                System.out.println("MainController: Content added to contentContainer. Number of children: " + contentContainer.getChildren().size());
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