// MainController.java
package com.example.projeto2.Desktop;

import com.example.projeto2.Tables.Funcionario;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
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

    private VBox currentSidebar; // To hold the currently loaded sidebar

    private final ApplicationContext context;

    private boolean sidebarOpen = true; // Track sidebar state

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
            HeaderController headerController = headerLoader.getController();
            headerController.setMainController(this); // Pass reference to MainController
            mainLayout.setTop(headerContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadUserSpecificContent(BigDecimal userType) {
        try {
            Parent sidebarContent = null;
            Parent dashboardContent = null;

            if (userType.equals(new BigDecimal(1))) { // Mechanic
                FXMLLoader sidebarLoader = new FXMLLoader(getClass().getResource("/mechanic/sidebar.fxml"));
                sidebarLoader.setControllerFactory(context::getBean);
                sidebarContent = sidebarLoader.load();
                com.example.projeto2.Desktop.mechanic.SidebarController sidebarController = sidebarLoader.getController();
                sidebarController.setMainLayout(mainLayout);

                FXMLLoader dashboardLoader = new FXMLLoader(getClass().getResource("/mechanic/dashboard.fxml"));
                dashboardLoader.setControllerFactory(context::getBean);
                dashboardContent = dashboardLoader.load();

            } else if (userType.equals(new BigDecimal(2))) { // Receptionist
                FXMLLoader sidebarLoader = new FXMLLoader(getClass().getResource("/sidebar.fxml")); // Assuming generic sidebar for receptionist
                sidebarLoader.setControllerFactory(context::getBean);
                sidebarContent = sidebarLoader.load();
                com.example.projeto2.Desktop.SidebarController sidebarController = sidebarLoader.getController(); // Assuming a generic SidebarController
                sidebarController.setMainLayout(mainLayout);

                FXMLLoader dashboardLoader = new FXMLLoader(getClass().getResource("/receptionist-dashboard.fxml"));
                dashboardLoader.setControllerFactory(context::getBean);
                dashboardContent = dashboardLoader.load();
            }

            if (sidebarContent instanceof VBox) {
                currentSidebar = (VBox) sidebarContent;
                mainLayout.setLeft(currentSidebar);
                sidebarOpen = true; // Ensure sidebar is open when loaded
            }
            if (dashboardContent != null) {
                mainLayout.setCenter(dashboardContent);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void toggleSidebar() {
        if (currentSidebar == null) {
            return;
        }

        TranslateTransition transition = new TranslateTransition(Duration.millis(300), currentSidebar);
        if (sidebarOpen) {
            transition.setByX(-currentSidebar.getWidth());
        } else {
            transition.setByX(currentSidebar.getWidth());
        }
        transition.play();
        sidebarOpen = !sidebarOpen;
    }
}