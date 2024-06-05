package com.example.phonebookui.controllers;

import com.example.phonebookui.App;
import com.example.phonebookui.requests.RegistrationRequest;
import com.example.phonebookui.Storage;
import com.example.phonebookui.Token;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RegisterController {
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;


    @FXML
    public void handleRegisterButtonAction() {
        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            RegistrationRequest req = new RegistrationRequest(emailField.getText(), passwordField.getText());
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080/api/auth/register"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(req.toString()))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                ObjectMapper objectMapper = new ObjectMapper();
                Token token = objectMapper.readValue(response.body(), Token.class);
                try (FileOutputStream fileOut = new FileOutputStream("jwt.ser");
                    ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
                    out.writeObject(token);
                    PrintWriter writer = new PrintWriter("auth.txt");
                    writer.println("true");
                    writer.close();

                    Storage.jwt = token;

                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("main-window.fxml"));
                        Parent root = fxmlLoader.load();
                        Scene scene = new Scene(root);

                        // Get the current stage (window) from the event source
                        Stage stage = (Stage) emailField.getScene().getWindow();
                        stage.setWidth(800);
                        stage.setHeight(600);
                        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
                        stage.setScene(scene);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } catch (IOException i) {
                    i.printStackTrace();
                }
            } else {
                Storage.error_message = "Wrong credentials";
                FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("error-message.fxml"));
                Parent root = fxmlLoader.load();
                Scene scene = new Scene(root);
                // Get the current stage (window) from the event source
                Stage stage = (Stage) emailField.getScene().getWindow();
                scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
                stage.setScene(scene);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void handleBackButtonAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("login.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);

            // Get the current stage (window) from the event source
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
            stage.setWidth(800);
            stage.setHeight(600);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
