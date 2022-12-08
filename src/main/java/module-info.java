module edu.hamilton.teacherscheduler {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.microsoft.sqlserver.jdbc;
    requires java.sql;

    opens edu.hamilton.teacherscheduler to javafx.fxml;
    exports edu.hamilton.teacherscheduler;
    exports edu.hamilton.teacherscheduler.controller;
    opens edu.hamilton.teacherscheduler.controller to javafx.fxml;
}