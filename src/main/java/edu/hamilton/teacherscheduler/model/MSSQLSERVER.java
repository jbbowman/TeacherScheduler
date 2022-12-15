package edu.hamilton.teacherscheduler.model;

import java.io.FileNotFoundException;
import java.sql.*;
import java.io.File;
import java.util.Scanner;

public class MSSQLSERVER {
    public static Connection connect() throws FileNotFoundException, SQLException {
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
        return DriverManager.getConnection(connectionURL);
    }

    public static ResultSet executeSelect(String sql) throws SQLException, FileNotFoundException {
        Connection conn = connect();
        Statement stmt = conn.createStatement();
        return stmt.executeQuery(sql);
    }

    public static void execute(String sql) throws SQLException, FileNotFoundException {
        Connection conn = connect();
        Statement stmt = conn.createStatement();
        stmt.execute(sql);
    }

    public static void main(String[] args) {
        try {connect(); System.out.println("Successfully connected");}
        catch (FileNotFoundException | SQLException e) {e.printStackTrace();}
    }
}