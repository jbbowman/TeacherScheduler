package edu.hamilton.teacherscheduler.controller;

import edu.hamilton.teacherscheduler.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.scene.layout.Pane;


import java.io.IOException;

public class MainController {
    @FXML
    private Pane rootPane;
    @FXML
    public void initialize() throws IOException {
        rootPane.getChildren().add(new FXMLLoader(Main.class.getResource("settings-view.fxml")).load());
    }

    @FXML
    private void handleHomeB(ActionEvent event) {changeIndex(0);}
    @FXML
    private void handleScheduleB(ActionEvent event) {changeIndex(1);}
    @FXML
    private void handleSpecialsB(ActionEvent event) {changeIndex(2);}
    @FXML
    private void handleTeachersB(ActionEvent event) {changeIndex(3);}
    @FXML
    private void handleSettingsB(ActionEvent event) {changeIndex(4);}

    private void changeIndex(int index) {
        this.rootPane.getChildren().get(index).toFront();
    }
}
