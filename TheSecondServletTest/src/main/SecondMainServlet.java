package main;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/register")
public class SecondMainServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        resp.setContentType("text/html");
//        out.println("<html>This should do for now</html>");

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
}
