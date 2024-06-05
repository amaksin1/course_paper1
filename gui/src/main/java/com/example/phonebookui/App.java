package com.example.phonebookui;

import atlantafx.base.theme.PrimerDark;
import atlantafx.base.theme.PrimerLight;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.*;
import java.nio.file.Files;
import java.util.Scanner;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Application.setUserAgentStylesheet(new PrimerDark().getUserAgentStylesheet());

        String fxmlFileName = "login.fxml";

        try {
            File myObj = new File("auth.txt");
            if (!myObj.exists()) {
                Files.createFile(myObj.toPath());
            }
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if (data.equals("true")) {
                    fxmlFileName = "main-window.fxml";

                    Token jwt = null;

                    try (FileInputStream fileIn = new FileInputStream("jwt.ser");
                         ObjectInputStream in = new ObjectInputStream(fileIn)) {
                        jwt = (Token) in.readObject();
                        Storage.jwt = jwt;
                    } catch (IOException i) {
                        i.printStackTrace();
                    } catch (ClassNotFoundException c) {
                        c.printStackTrace();
                    }

                } else if (data.equals("false")) {
                    fxmlFileName = "login.fxml";
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxmlFileName));

        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Phone Book App");
        stage.setScene(scene);
        stage.setWidth(800);
        stage.setHeight(600);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());

        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
