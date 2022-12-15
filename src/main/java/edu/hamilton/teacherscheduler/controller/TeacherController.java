package edu.hamilton.teacherscheduler.controller;

import edu.hamilton.teacherscheduler.model.MSSQLSERVER;
import edu.hamilton.teacherscheduler.model.Teacher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class TeacherController {
    @FXML
    public TableView<Teacher> tableView;
    @FXML
    public TableColumn<Teacher, String> fName;
    @FXML
    public TableColumn<Teacher, String> lName;
    @FXML
    public TableColumn<Teacher, String> phone;
    @FXML
    public TableColumn<Teacher, Integer> hoursAvailable;

    public void initialize() throws SQLException, FileNotFoundException {
        populateTableView();
    }

    public void populateTableView() throws SQLException, FileNotFoundException {
        ResultSet rs = MSSQLSERVER.executeSelect("SELECT * FROM Teacher");

        ObservableList<Teacher> dataList = FXCollections.observableArrayList();
        while (rs.next()) {
            Teacher data = new Teacher();
            data.setTeacherID(rs.getInt("teacherID"));
            data.setFirstName(rs.getString("firstName"));
            data.setLastName(rs.getString("lastName"));
            data.setPhone(rs.getString("phone"));
            data.setHoursAvailable(rs.getInt("hoursAvailable"));
            dataList.add(data);
        }
        tableView.setItems(dataList);

        fName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        hoursAvailable.setCellValueFactory(new PropertyValueFactory<>("hoursAvailable"));
    }

    public void create() {
        // Show a dialog to get input from the user for the new row
        Dialog<Teacher> dialog = new Dialog<>();
        dialog.setTitle("Create New Teacher");

        // Set the button types
        ButtonType createButtonType = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(createButtonType, ButtonType.CANCEL);

        // Create the fields for the new Period object
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));


        TextField fName = new TextField();
        fName.setPromptText("First Name:");
        TextField lName = new TextField();
        lName.setPromptText("Last Name:");
        TextField phone = new TextField();
        phone.setPromptText("Phone Number:");
        TextField hoursAvailable = new TextField();
        hoursAvailable.setPromptText("Hours Available:");

        grid.add(new Label("First NameL"), 0, 0);
        grid.add(fName, 1, 0);
        grid.add(new Label("Last Name:"), 0, 1);
        grid.add(lName, 1, 1);
        grid.add(new Label("Phone Number"), 0, 2);
        grid.add(phone, 1, 2);
        grid.add(new Label("Hours Available"), 0, 3);
        grid.add(hoursAvailable, 1, 3);

        dialog.getDialogPane().setContent(grid);

        // Convert the result to a Teacher object when the create button is clicked
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == createButtonType) {
                Teacher newTeacher = new Teacher();
                newTeacher.setFirstName(fName.getText());
                newTeacher.setLastName(lName.getText());
                newTeacher.setPhone(phone.getText());
                newTeacher.setHoursAvailable(Integer.parseInt(hoursAvailable.getText()));

                try {
                    MSSQLSERVER.execute(String.format("INSERT INTO Teacher VALUES ('%s', '%s', '%s', %s)", fName.getText(), lName.getText(), phone.getText(), hoursAvailable.getText()));
                } catch (SQLException | FileNotFoundException e) {
                    throw new RuntimeException(e);
                }

                return newTeacher;
            }
            return null;
        });
        // Show the dialog and wait for the user to click the create button
        Optional<Teacher> result = dialog.showAndWait();

        // If a new Period object was created, add it to the table view
        result.ifPresent(value -> tableView.getItems().add(value));
    }
    public void edit() {
        // Get the selected row from the table view
        // Show a dialog to get input from the user to update the selected row
        // Use the input to update the selected Period object
        // Update the selected row in the table view
        // Get the selected row from the table view
        Teacher selectedTeacher = tableView.getSelectionModel().getSelectedItem();

        // Return if no row is selected
        if (selectedTeacher == null) {
            return;
        }

        // Show a dialog to get input from the user to update the selected row
        Dialog<Teacher> dialog = new Dialog<>();
        dialog.setTitle("Edit Teacher");

        // Set the button types
        ButtonType editButtonType = new ButtonType("Edit", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(editButtonType, ButtonType.CANCEL);

        // Create the fields for the selected Period object
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField fName = new TextField();
        fName.setPromptText("First Name:");
        TextField lName = new TextField();
        lName.setPromptText("Last Name:");
        TextField phone = new TextField();
        phone.setPromptText("Phone Number:");
        TextField hoursAvailable = new TextField();
        hoursAvailable.setPromptText("Hours Available:");

        grid.add(new Label("First NameL"), 0, 0);
        grid.add(fName, 1, 0);
        grid.add(new Label("Last Name:"), 0, 1);
        grid.add(lName, 1, 1);
        grid.add(new Label("Phone Number"), 0, 2);
        grid.add(phone, 1, 2);
        grid.add(new Label("Hours Available"), 0, 3);
        grid.add(hoursAvailable, 1, 3);

        dialog.getDialogPane().setContent(grid);

        // Convert the result to a Period object when the create button is clicked
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == editButtonType) {
                Teacher newTeacher = new Teacher();
                newTeacher.setFirstName(fName.getText());
                newTeacher.setLastName(lName.getText());
                newTeacher.setPhone(phone.getText());
                newTeacher.setHoursAvailable(Integer.parseInt(hoursAvailable.getText()));

                try {
                    tableView.getItems().remove(selectedTeacher);
                    MSSQLSERVER.execute(String.format("UPDATE Teacher SET firstName = '%s' WHERE teacherID = %s", fName.getText(), tableView.getSelectionModel().getSelectedItem().getTeacherID()));
                    MSSQLSERVER.execute(String.format("UPDATE Teacher SET lastName = %s WHERE teacherID = %s", lName.getText(), tableView.getSelectionModel().getSelectedItem().getTeacherID()));
                    MSSQLSERVER.execute(String.format("UPDATE Teacher SET phone = %s WHERE teacherID = %s", phone.getText(), tableView.getSelectionModel().getSelectedItem().getTeacherID()));
                    MSSQLSERVER.execute(String.format("UPDATE Teacher SET hoursAvailable = %s WHERE teacherID = %s", hoursAvailable.getText(), tableView.getSelectionModel().getSelectedItem().getTeacherID()));

                } catch (SQLException | FileNotFoundException e) {
                    throw new RuntimeException(e);
                }

                return newTeacher;
            }
            return null;
        });
        // Show the dialog and wait for the user to click the create button
        Optional<Teacher> result = dialog.showAndWait();

        // If a new Period object was created, add it to the table view
        result.ifPresent(value -> tableView.getItems().add(value));
    }

    public void delete() throws SQLException, FileNotFoundException {
        // Get the selected row from the table view
        Teacher selectedTeacher = tableView.getSelectionModel().getSelectedItem();

        // Remove the selected Period object from the table view
        tableView.getItems().remove(selectedTeacher);
        MSSQLSERVER.execute(String.format("DELETE FROM Teacher WHERE teacherID = %s", tableView.getSelectionModel().getSelectedItem().getTeacherID()));
    }

}
