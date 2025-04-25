package com.example.projeto2.Desktop;

import com.example.projeto2.Projeto2Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * A utility class to manage scene transitions in the application.
 * This ensures consistent scene loading with Spring integration.
 */
@Component
public class SceneManager {

    /**
     * Load and switch to a new scene
     * 
     * @param fxmlPath The path to the FXML file
     * @param title The window title
     * @param sourceNode A node from the current scene
     * @throws IOException If the FXML file cannot be loaded
     */
    public static void switchScene(String fxmlPath, String title, Node sourceNode) throws IOException {
        try {
            // Get Spring context
            ConfigurableApplicationContext springContext = Projeto2Application.getSpringContext();
            
            // Set up the FXMLLoader with Spring context
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(fxmlPath));
            loader.setControllerFactory(springContext::getBean);
            
            // Load the view
            Parent root = loader.load();
            
            // Create a new scene
            Scene scene = new Scene(root);
            
            // Get the stage from the source node
            Stage stage = (Stage) sourceNode.getScene().getWindow();
            
            // Set the new scene and title
            stage.setScene(scene);
            stage.setTitle(title);
            
            System.out.println("Successfully switched to scene: " + fxmlPath);
        } catch (Exception e) {
            System.out.println("Error switching to scene " + fxmlPath + ": " + e.getMessage());
            e.printStackTrace();
            throw new IOException("Failed to load scene: " + fxmlPath, e);
        }
    }
    
    /**
     * Open a new window with the specified FXML
     * 
     * @param fxmlPath The path to the FXML file
     * @param title The window title
     * @throws IOException If the FXML file cannot be loaded
     */
    public static void openNewWindow(String fxmlPath, String title) throws IOException {
        try {
            // Get Spring context
            ConfigurableApplicationContext springContext = Projeto2Application.getSpringContext();
            
            // Set up the FXMLLoader with Spring context
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(fxmlPath));
            loader.setControllerFactory(springContext::getBean);
            
            // Load the view
            Parent root = loader.load();
            
            // Create a new scene and stage
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            
            // Set the scene and title
            stage.setScene(scene);
            stage.setTitle(title);
            
            // Show the new window
            stage.show();
            
            System.out.println("Successfully opened new window: " + fxmlPath);
        } catch (Exception e) {
            System.out.println("Error opening new window " + fxmlPath + ": " + e.getMessage());
            e.printStackTrace();
            throw new IOException("Failed to open new window: " + fxmlPath, e);
        }
    }
}