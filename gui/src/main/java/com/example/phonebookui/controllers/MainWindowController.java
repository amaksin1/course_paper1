package com.example.phonebookui.controllers;

import com.example.phonebookui.responses.AllContactsResponse;
import com.example.phonebookui.App;
import com.example.phonebookui.responses.ContactResponse;
import com.example.phonebookui.Storage;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class MainWindowController {
    @FXML
    private Button logoutButton;
    @FXML
    private TableView<ContactResponse> tableView;
    @FXML
    private TableColumn<ContactResponse, String> nameColumn, phoneColumn;
    @FXML
    private TableColumn<ContactResponse, Boolean> selColumn;

    private final ToggleGroup radioGroup = new ToggleGroup();

    @FXML
    public void handleDeleteButtonAction() {

        ObservableList<ContactResponse> contacts = tableView.getItems();

        int id = -1;
        ContactResponse deletedContact = null;

        for (var item: contacts) {
            if (item.isSelected()) {
                id = Integer.parseInt(item.getId());
                deletedContact = item;
                break;
            }
        }

        if (deletedContact != null) {
            try {
                HttpClient httpClient = HttpClient.newHttpClient();

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(new URI("http://localhost:8080/api/contacts/" + id))
                        .header("Content-Type", "application/json")
                        .header("Authorization", "Bearer " + Storage.jwt.getToken())
                        .DELETE()
                        .build();


                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                if (response.statusCode() == 200) {
                    contacts.remove(deletedContact);
                    tableView.setItems(contacts);
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @FXML
    public void initialize() {

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        // Configure the third column with a RadioButton
        selColumn.setCellValueFactory(new PropertyValueFactory<>("selected"));
        selColumn.setCellFactory(new Callback<TableColumn<ContactResponse, Boolean>, TableCell<ContactResponse, Boolean>>() {
            @Override
            public TableCell<ContactResponse, Boolean> call(TableColumn<ContactResponse, Boolean> param) {
                return new TableCell<ContactResponse, Boolean>() {
                    private final RadioButton radioButton = new RadioButton();

                    {
                        radioButton.setToggleGroup(radioGroup);
                        radioButton.setOnAction(event -> {
                            ContactResponse person = getTableView().getItems().get(getIndex());
                            person.setSelected(radioButton.isSelected());
                        });
                    }

                    @Override
                    protected void updateItem(Boolean item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(radioButton);
                            radioButton.setSelected(item != null && item);
                        }
                    }
                };
            }
        });
        try {
            HttpClient httpClient = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080/api/contacts"))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + Storage.jwt.getToken())
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                ObjectMapper objectMapper = new ObjectMapper();
                AllContactsResponse contactResponses = objectMapper.readValue(response.body(), AllContactsResponse.class);

                ObservableList<ContactResponse> data = FXCollections.observableArrayList(contactResponses.getAllContacts());

                tableView.setItems(data);
            }

        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public void handleEditButtonAction(Event event) {
        ObservableList<ContactResponse> contacts = tableView.getItems();

        int id = -1;
        ContactResponse editedContact = null;

        for (var item: contacts) {
            if (item.isSelected()) {
                id = Integer.parseInt(item.getId());
                Storage.selected_id = id;
                editedContact = item;
                break;
            }
        }

        if (editedContact != null) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("edit-contact.fxml"));
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

    public void handleAddButtonAction(Event event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("add-contact.fxml"));
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

    public void handleLogoutButtonAction() {
        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080/api/auth/logout"))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + Storage.jwt.getToken())
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {

                PrintWriter writer = new PrintWriter("auth.txt");
                writer.println("false");
                writer.close();
                FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("login.fxml"));



                Parent root = fxmlLoader.load();
                Scene scene = new Scene(root);

                // Get the current stage (window) from the event source
                Stage stage = (Stage) logoutButton.getScene().getWindow();
                stage.setWidth(800);
                stage.setHeight(600);
                scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
                stage.setScene(scene);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
