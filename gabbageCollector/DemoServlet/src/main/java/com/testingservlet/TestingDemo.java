package com.testingservlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/register")
public class TestingDemo extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // give "out" the ability to pencil things out
        // write response to client in html format
        PrintWriter out = resp.getWriter();
        resp.setContentType("text/html");

        System.out.println("hello world");

        String url = "jdbc:mysql://localhost:3306/person";
        String user = "samuel";
        String password = "1234";

        final String query = "INSERT INTO person_info (first_name, last_name, email_address) VALUES (?,?,?)";

        // get the params needed;
        String firstName = req.getParameter("first_name");
        String lastName = req.getParameter("last_name");
        String emailAddress = req.getParameter("email_address");

        Connection con = null;
        PreparedStatement stmt = null;
        System.out.println("hello world");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, password);
            System.out.println("Connection established: " + url);
            stmt = con.prepareStatement(query);

            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, emailAddress);
            int count = stmt.executeUpdate();
            if (count == 1) {
                out.println("<p>Person added successfully!</p>");
            } else {
                out.println("<p>Something went wrong!</p>");
            }
        } catch (ClassNotFoundException | SQLException e) {
            out.println("<p>Error: " + e.getMessage() + "</p>");
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                out.println("<p>Error closing resources: " + e.getMessage() + "</p>");
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
