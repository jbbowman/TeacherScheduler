package edu.hamilton.teacherscheduler.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.*;
import javafx.scene.*;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class MainController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    public void switchToSettings(ActionEvent event) throws IOException {
            Parent root = FXMLLoader.load(getClass().getResource("settings-view.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
    }
}
