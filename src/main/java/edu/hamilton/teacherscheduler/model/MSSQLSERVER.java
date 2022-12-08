package edu.hamilton.teacherscheduler.model;

import java.io.FileNotFoundException;
import java.sql.DriverManager;
import java.io.File;
import java.sql.SQLException;
import java.util.Scanner;

public class MSSQLSERVER {
    static void connect() throws FileNotFoundException, SQLException {
        File connectionDetails = new File("src/main/java/edu/hamilton/teacherscheduler/model/connectionDetails.txt");
        Scanner scanner = new Scanner(connectionDetails);
        final String connectionURL =
                String.format("jdbc:sqlserver://%s;" +
                        "database=Hamilton;" +
                        "user=TeacherScheduler;" +
                        "password=%s;" +
                        "integratedSecurity=false;" +
                        "encrypt=true;" +
                        "trustServerCertificate=true;" +
                        "loginTimeout=20", scanner.nextLine(), scanner.nextLine());
        DriverManager.getConnection(connectionURL);
    }

    public static void main(String[] args) {
        try {connect(); System.out.println("Successfully connected");}
        catch (FileNotFoundException | SQLException e) {e.printStackTrace();}
    }
}