package com.example.phonebookui.controllers;

import com.example.phonebookui.App;
import com.example.phonebookui.responses.ContactResponse;
import com.example.phonebookui.Storage;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class AddContactController {
    @FXML
    private TextField phoneNumber;

    @FXML
    private TextField name;

    public void handleBackButtonAction(Event event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("main-window.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
            stage.setWidth(800);
            stage.setHeight(600);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleAddButtonAction() {
        ContactResponse contactResponse = new ContactResponse(phoneNumber.getText(), name.getText());

        try {
            HttpClient httpClient = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080/api/contacts/add"))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + Storage.jwt.getToken())
                    .POST(HttpRequest.BodyPublishers.ofString(contactResponse.toString()))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("main-window.fxml"));
                Parent root = fxmlLoader.load();
                Scene scene = new Scene(root);

                Stage stage = (Stage) name.getScene().getWindow();
                scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
                stage.setWidth(800);
                stage.setHeight(600);
                stage.setScene(scene);
            } else {
                Storage.error_message = "You've entered an invalid phone number";
                System.out.println(response.body());
                FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("error-message.fxml"));
                Parent root = fxmlLoader.load();
                Scene scene = new Scene(root);

                Stage stage = (Stage) name.getScene().getWindow();
                scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
                stage.setScene(scene);
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
