package com.testing;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import java.sql.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("hello world");
        String url = "jdbc:mysql:///person";
        String user = "samuel";
        String password = "1234";

        // connecting to a database using jdbc dependency on maven

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url,user,password);
            System.out.println("connection established: "+ url);

            String query = "SELECT * FROM person_info";
            PreparedStatement stmt = con.prepareStatement(query);

            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                System.out.println(rs.getString(2) + " " + rs.getString(4));
            }


        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
