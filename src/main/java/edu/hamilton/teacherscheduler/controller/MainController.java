package edu.hamilton.teacherscheduler.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.scene.layout.Pane;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainController {
    @FXML
    private Pane rootPane;
    String[] fileNames = {"schedule-view.fxml", "settings-view.fxml"};
    @FXML
    public void initialize() {changeIndex(fileNames, 0);}
    @FXML
    private void handleHomeB(ActionEvent event) {changeIndex(fileNames, 0);}
    @FXML
    private void handleScheduleB(ActionEvent event) {changeIndex(fileNames, 1);}
    @FXML
    private void handleSpecialsB(ActionEvent event) {changeIndex(fileNames, 2);}
    @FXML
    private void handleTeachersB(ActionEvent event) {changeIndex(fileNames, 3);}
    @FXML
    private void handleSettingsB(ActionEvent event) {changeIndex(fileNames, 4);}

    private void changeIndex(String[] fileNames, int index) {
        try {
            List<Pane> array = loadPages(fileNames);
            rootPane.getChildren().clear();
            rootPane.getChildren().add(array.get(index));
        } catch (IOException e) {e.printStackTrace();}
    }

    private List<Pane> loadPages(String[] fileNames) throws IOException {
        List<Pane> pages = new ArrayList<>();

        for (String fileName : fileNames) {
            File file = new File("src/main/resources/edu/hamilton/teacherscheduler/" + fileName);
            pages.add(new FXMLLoader(file.toURL()).load());
        }
        return pages;
    }
}
