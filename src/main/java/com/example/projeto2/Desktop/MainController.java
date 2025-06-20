// MainController.java
package com.example.projeto2.Desktop;

import com.example.projeto2.Desktop.mechanic.SidebarControllerMechanic;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
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
    private VBox sidebarContainer; // Container for the sidebar

    @FXML
    private StackPane contentContainer; // Container for the main content

    public StackPane getContentContainer() {
        return contentContainer;
    }

    private VBox currentSidebar; // To hold the currently loaded sidebar

    private final ApplicationContext context;

    private boolean sidebarOpen = false; // Track sidebar state

    @Autowired
    public MainController(ApplicationContext context) {
        this.context = context;
    }

    @FXML
    public void initialize() {
        // The header is now included directly in main.fxml, so no need to load it here.
        // Ensure the HeaderController gets a reference to this MainController.
        // This is typically handled by Spring's FXML loader if HeaderController is a @Component.
        sidebarContainer.setTranslateX(-250); // Start with sidebar hidden
        System.out.println("MainController initialize - sidebarContainer width: " + sidebarContainer.getWidth());
    }

    public void loadUserSpecificContent(BigDecimal userType) {
        try {
            Parent sidebarContent = null;
            Parent dashboardContent = null;

            if (userType.equals(new BigDecimal(1))) { // Mechanic
                FXMLLoader sidebarLoader = new FXMLLoader(getClass().getResource("/mechanic/sidebar.fxml"));
                sidebarLoader.setControllerFactory(context::getBean);
                sidebarContent = sidebarLoader.load();
                com.example.projeto2.Desktop.mechanic.SidebarControllerMechanic sidebarController = sidebarLoader.getController();
                sidebarController.setMainLayout(mainLayout); // Pass mainLayout to sidebar for content loading

                FXMLLoader dashboardLoader = new FXMLLoader(getClass().getResource("/mechanic/dashboard.fxml"));
                dashboardLoader.setControllerFactory(context::getBean);
                dashboardContent = dashboardLoader.load();

            } else if (userType.equals(new BigDecimal(2))) { // Receptionist
                FXMLLoader sidebarLoader = new FXMLLoader(getClass().getResource("/sidebar.fxml")); // Assuming generic sidebar for receptionist
                sidebarLoader.setControllerFactory(context::getBean);
                sidebarContent = sidebarLoader.load();
                com.example.projeto2.Desktop.SidebarController sidebarController = sidebarLoader.getController(); // Assuming a generic SidebarController
                sidebarController.setMainLayout(mainLayout); // Pass mainLayout to sidebar for content loading

                FXMLLoader dashboardLoader = new FXMLLoader(getClass().getResource("/receptionist-dashboard.fxml"));
                dashboardLoader.setControllerFactory(context::getBean);
                dashboardContent = dashboardLoader.load();
            }

            if (sidebarContent instanceof VBox) {
                currentSidebar = (VBox) sidebarContent;
                sidebarContainer.getChildren().setAll(currentSidebar); // Set sidebar into its container
                System.out.println("MainController loadUserSpecificContent - sidebarContainer width after loading: " + sidebarContainer.getWidth());
            }
            if (dashboardContent != null) {
                contentContainer.getChildren().setAll(dashboardContent); // Set dashboard into its container
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void toggleSidebar() {
        if (currentSidebar == null) {
            System.out.println("MainController toggleSidebar - currentSidebar is null. Cannot toggle.");
            return;
        }
        System.out.println("MainController toggleSidebar - sidebarOpen: " + sidebarOpen + ", sidebarContainer width: " + sidebarContainer.getWidth());

        TranslateTransition transition = new TranslateTransition(Duration.millis(300), sidebarContainer); // Animate the container
        if (sidebarOpen) {
            transition.setToX(-sidebarContainer.getWidth()); // Close the sidebar
        } else {
            transition.setToX(0); // Open the sidebar
        }
        transition.play();
        sidebarOpen = !sidebarOpen; // Toggle the state
        System.out.println("MainController toggleSidebar - new sidebarOpen state: " + !sidebarOpen);
    }
}