package com.example.phonebookui.controllers;

import com.example.phonebookui.App;
import com.example.phonebookui.Storage;
import com.example.phonebookui.Token;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Scanner;

public class ErrorMessageController {

    @FXML
    private Label errorLabel;

    public void initialize() {
        errorLabel.setText(Storage.error_message);
    }

    public void handleOkButtonAction(ActionEvent event) {

        String fxmlFileName = "login.fxml";


        try {
            File myObj = new File("auth.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if (data.equals("true")) {
                    fxmlFileName = "main-window.fxml";

                    Token jwt = null;

                    FileInputStream fileIn = new FileInputStream("jwt.ser");
                    ObjectInputStream in = new ObjectInputStream(fileIn);
                    jwt = (Token) in.readObject();
                    Storage.jwt = jwt;

                } else if (data.equals("false")) {
                    fxmlFileName = "login.fxml";
                }
            }
            myReader.close();
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxmlFileName));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
            stage.setWidth(800);
            stage.setHeight(600);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
