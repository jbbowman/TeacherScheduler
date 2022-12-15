package edu.hamilton.teacherscheduler.controller;

import edu.hamilton.teacherscheduler.model.MSSQLSERVER;
import edu.hamilton.teacherscheduler.model.Period;
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

public class SettingsController {
    @FXML
    private TableView<Period> tableView;
    @FXML
    private TableColumn<Period, String> periodColumn;
    @FXML
    private TableColumn<Period, Integer> kColumn;
    @FXML
    private TableColumn<Period, Integer> column1;
    @FXML
    private TableColumn<Period, Integer> column2;
    @FXML
    private TableColumn<Period, Integer> column3;
    @FXML
    private TableColumn<Period, Integer> column4;
    @FXML
    private TableColumn<Period, Integer> column5;

    public void initialize() throws SQLException, FileNotFoundException {
        populateTableView();
    }

    public void populateTableView() throws SQLException, FileNotFoundException {
        ResultSet rs = MSSQLSERVER.executeSelect("SELECT * FROM Period");

        ObservableList<Period> dataList = FXCollections.observableArrayList();
        while (rs.next()) {
            Period data = new Period();
            data.setPeriodID(rs.getInt("PeriodID"));
            data.setPeriod(rs.getString("Period"));
            data.setGradeK(rs.getInt("GradeK"));
            data.setGrade1(rs.getInt("Grade1"));
            data.setGrade2(rs.getInt("Grade2"));
            data.setGrade3(rs.getInt("Grade3"));
            data.setGrade4(rs.getInt("Grade4"));
            data.setGrade5(rs.getInt("Grade5"));
            dataList.add(data);
        }
        tableView.setItems(dataList);

        periodColumn.setCellValueFactory(new PropertyValueFactory<>("period"));
        kColumn.setCellValueFactory(new PropertyValueFactory<>("gradeK"));
        column1.setCellValueFactory(new PropertyValueFactory<>("grade1"));
        column2.setCellValueFactory(new PropertyValueFactory<>("grade2"));
        column3.setCellValueFactory(new PropertyValueFactory<>("grade3"));
        column4.setCellValueFactory(new PropertyValueFactory<>("grade4"));
        column5.setCellValueFactory(new PropertyValueFactory<>("grade5"));
    }

    public void create() {
        // Show a dialog to get input from the user for the new row
        Dialog<Period> dialog = new Dialog<>();
        dialog.setTitle("Create New Period");

        // Set the button types
        ButtonType createButtonType = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(createButtonType, ButtonType.CANCEL);

        // Create the fields for the new Period object
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));


        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 480);
        TextField period = new TextField();
        period.setPromptText("Period");
        Spinner<Integer> gradeK = new Spinner<>();
        gradeK.setPromptText("Grade K");
        gradeK.setValueFactory(valueFactory);
        Spinner<Integer> grade1 = new Spinner<>();
        grade1.setPromptText("Grade 1");
        grade1.setValueFactory(valueFactory);
        Spinner<Integer> grade2 = new Spinner<>();
        grade2.setPromptText("Grade 2");
        grade2.setValueFactory(valueFactory);
        Spinner<Integer> grade3 = new Spinner<>();
        grade3.setPromptText("Grade 3");
        grade3.setValueFactory(valueFactory);
        Spinner<Integer> grade4 = new Spinner<>();
        grade4.setPromptText("Grade 4");
        grade4.setValueFactory(valueFactory);
        Spinner<Integer> grade5 = new Spinner<>();
        grade5.setPromptText("Grade 5");
        grade5.setValueFactory(valueFactory);

        grid.add(new Label("Period:"), 0, 0);
        grid.add(period, 1, 0);
        grid.add(new Label("Grade K:"), 0, 1);
        grid.add(gradeK, 1, 1);
        grid.add(new Label("Grade 1:"), 0, 2);
        grid.add(grade1, 1, 2);
        grid.add(new Label("Grade 2:"), 0, 3);
        grid.add(grade2, 1, 3);
        grid.add(new Label("Grade 3:"), 0, 4);
        grid.add(grade3, 1, 4);
        grid.add(new Label("Grade 4:"), 0, 5);
        grid.add(grade4, 1, 5);
        grid.add(new Label("Grade 5:"), 0, 6);
        grid.add(grade5, 1, 6);

        dialog.getDialogPane().setContent(grid);

        // Convert the result to a Period object when the create button is clicked
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == createButtonType) {
                Period newPeriod = new Period();
                newPeriod.setPeriod(period.getText());
                newPeriod.setGradeK(gradeK.getValue());
                newPeriod.setGrade1(grade1.getValue());
                newPeriod.setGrade2(grade2.getValue());
                newPeriod.setGrade3(grade3.getValue());
                newPeriod.setGrade4(grade4.getValue());
                newPeriod.setGrade5(grade5.getValue());

                try {
                    MSSQLSERVER.execute(String.format("INSERT INTO Period VALUES ('%s', %s, %s, %s, %s, %s, %s)", period.getText(), gradeK.getValue(), grade1.getValue(), grade2.getValue(), grade3.getValue(), grade4.getValue(), grade5.getValue()));
                } catch (SQLException | FileNotFoundException e) {
                    throw new RuntimeException(e);
                }

                return newPeriod;
            }
            return null;
        });
        // Show the dialog and wait for the user to click the create button
        Optional<Period> result = dialog.showAndWait();

        // If a new Period object was created, add it to the table view
        result.ifPresent(value -> tableView.getItems().add(value));
    }
    public void edit() {
        // Get the selected row from the table view
        // Show a dialog to get input from the user to update the selected row
        // Use the input to update the selected Period object
        // Update the selected row in the table view
        // Get the selected row from the table view
        Period selectedPeriod = tableView.getSelectionModel().getSelectedItem();

        // Return if no row is selected
        if (selectedPeriod == null) {
            return;
        }

        // Show a dialog to get input from the user to update the selected row
        Dialog<Period> dialog = new Dialog<>();
        dialog.setTitle("Edit Period");

        // Set the button types
        ButtonType editButtonType = new ButtonType("Edit", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(editButtonType, ButtonType.CANCEL);

        // Create the fields for the selected Period object
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 480);
        TextField period = new TextField(selectedPeriod.getPeriod());
        period.setPromptText("Period");
        Spinner<Integer> gradeK = new Spinner<>(0, Integer.MAX_VALUE, selectedPeriod.getGradeK());
        gradeK.setPromptText("Grade K");
        gradeK.setValueFactory(valueFactory);
        Spinner<Integer> grade1 = new Spinner<>(0, Integer.MAX_VALUE, selectedPeriod.getGrade1());
        grade1.setPromptText("Grade 1");
        grade1.setValueFactory(valueFactory);
        Spinner<Integer> grade2 = new Spinner<>(0, Integer.MAX_VALUE, selectedPeriod.getGrade2());
        grade2.setPromptText("Grade 2");
        grade2.setValueFactory(valueFactory);
        Spinner<Integer> grade3 = new Spinner<>(0, Integer.MAX_VALUE, selectedPeriod.getGrade3());
        grade3.setPromptText("Grade 3");
        grade3.setValueFactory(valueFactory);
        Spinner<Integer> grade4 = new Spinner<>(0, Integer.MAX_VALUE, selectedPeriod.getGrade4());
        grade4.setPromptText("Grade 4");
        grade4.setValueFactory(valueFactory);
        Spinner<Integer> grade5 = new Spinner<>(0, Integer.MAX_VALUE, selectedPeriod.getGrade5());
        grade5.setPromptText("Grade 5");
        grade5.setValueFactory(valueFactory);

        grid.add(new Label("Period:"), 0, 0);
        grid.add(period, 1, 0);
        grid.add(new Label("Grade K:"), 0, 1);
        grid.add(gradeK, 1, 1);
        grid.add(new Label("Grade 1:"), 0, 2);
        grid.add(grade1, 1, 2);
        grid.add(new Label("Grade 2:"), 0, 3);
        grid.add(grade2, 1, 3);
        grid.add(new Label("Grade 3:"), 0, 4);
        grid.add(grade3, 1, 4);
        grid.add(new Label("Grade 4:"), 0, 5);
        grid.add(grade4, 1, 5);
        grid.add(new Label("Grade 5:"), 0, 6);
        grid.add(grade5, 1, 6);

        dialog.getDialogPane().setContent(grid);

        // Convert the result to a Period object when the create button is clicked
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == editButtonType) {
                Period newPeriod = new Period();
                newPeriod.setPeriod(period.getText());
                newPeriod.setGradeK(gradeK.getValue());
                newPeriod.setGrade1(grade1.getValue());
                newPeriod.setGrade2(grade2.getValue());
                newPeriod.setGrade3(grade3.getValue());
                newPeriod.setGrade4(grade4.getValue());
                newPeriod.setGrade5(grade5.getValue());

                try {
                    tableView.getItems().remove(selectedPeriod);
                    MSSQLSERVER.execute(String.format("UPDATE Period SET period = '%s' WHERE periodID = %s", period.getText(), tableView.getSelectionModel().getSelectedItem().getPeriodID()));
                    MSSQLSERVER.execute(String.format("UPDATE Period SET gradeK = %s WHERE periodID = %s", gradeK.getValue(), tableView.getSelectionModel().getSelectedItem().getPeriodID()));
                    MSSQLSERVER.execute(String.format("UPDATE Period SET grade1 = %s WHERE periodID = %s", grade1.getValue(), tableView.getSelectionModel().getSelectedItem().getPeriodID()));
                    MSSQLSERVER.execute(String.format("UPDATE Period SET grade2 = %s WHERE periodID = %s", grade2.getValue(), tableView.getSelectionModel().getSelectedItem().getPeriodID()));
                    MSSQLSERVER.execute(String.format("UPDATE Period SET grade3 = %s WHERE periodID = %s", grade3.getValue(), tableView.getSelectionModel().getSelectedItem().getPeriodID()));
                    MSSQLSERVER.execute(String.format("UPDATE Period SET grade4 = %s WHERE periodID = %s", grade4.getValue(), tableView.getSelectionModel().getSelectedItem().getPeriodID()));
                    MSSQLSERVER.execute(String.format("UPDATE Period SET grade5 = %s WHERE periodID = %s", grade5.getValue(), tableView.getSelectionModel().getSelectedItem().getPeriodID()));

                } catch (SQLException | FileNotFoundException e) {
                    throw new RuntimeException(e);
                }

                return newPeriod;
            }
            return null;
        });
        // Show the dialog and wait for the user to click the create button
        Optional<Period> result = dialog.showAndWait();

        // If a new Period object was created, add it to the table view
        result.ifPresent(value -> tableView.getItems().add(value));
    }

    public void delete() throws SQLException, FileNotFoundException {
        // Get the selected row from the table view
        Period selectedPeriod = tableView.getSelectionModel().getSelectedItem();

        // Remove the selected Period object from the table view
        tableView.getItems().remove(selectedPeriod);
        MSSQLSERVER.execute(String.format("DELETE FROM Period WHERE periodID = %s", tableView.getSelectionModel().getSelectedItem().getPeriodID()));
    }

}
