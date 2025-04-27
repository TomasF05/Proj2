package com.example.projeto2.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;

import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.net.URL;

@Configuration
public class

FxmlConfig {

    private final ConfigurableApplicationContext context;

    @Autowired
    public FxmlConfig(ConfigurableApplicationContext context) {
        this.context = context;
    }

    @Bean
    public FXMLLoader fxmlLoader() {
        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(context::getBean);
        return loader;
    }

    /**
     * Loads an FXML file with the Spring-managed controller.
     *
     * @param location The location of the FXML file
     * @return The loaded FXMLLoader
     * @throws IOException If the FXML file cannot be loaded
     */
    public FXMLLoader loadFxml(URL location) throws IOException {
        FXMLLoader loader = fxmlLoader();
        loader.setLocation(location);
        loader.load();
        return loader;
    }
}