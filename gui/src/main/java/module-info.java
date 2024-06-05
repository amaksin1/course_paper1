module com.example.phonebookui {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires atlantafx.base;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;
    requires java.xml;

    opens com.example.phonebookui to javafx.fxml;
    exports com.example.phonebookui;
    exports com.example.phonebookui.controllers;
    opens com.example.phonebookui.controllers to javafx.fxml;
    exports com.example.phonebookui.responses;
    opens com.example.phonebookui.responses to javafx.fxml;
    exports com.example.phonebookui.requests;
    opens com.example.phonebookui.requests to javafx.fxml;
}