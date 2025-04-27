package com.example.projeto2;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication(scanBasePackages = {"com.example.projeto2", "com.example.projeto2.Desktop"})
public class Projeto2Application extends Application {

    private static ConfigurableApplicationContext springContext;
    private static String[] savedArgs;

    public static ConfigurableApplicationContext getSpringContext() {
        return springContext;
    }

    public static void main(String[] args) {
        savedArgs = args;
        Application.launch(Projeto2Application.class, args);
    }

    @Override
    public void init() throws Exception {
        springContext = SpringApplication.run(Projeto2Application.class, savedArgs);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            // Load login page first
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
            loader.setControllerFactory(springContext::getBean);
            Parent root = loader.load();

            primaryStage.setTitle("AutoPro Workshop Management");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public void stop() {
        springContext.close();
        Platform.exit();
    }
}
