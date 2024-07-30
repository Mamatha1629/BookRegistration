package com.pace.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/editScreen")
public class EditScreenServlet extends HttpServlet {
    private static final String query = "SELECT BOOKNAME, BOOKEDITION, BOOKPRICE FROM BOOKDATA WHERE id=?";
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // Get PrintWriter
        PrintWriter pw = res.getWriter();
        // Set content type
        res.setContentType("text/html");
        // Get the id of record
        int id = Integer.parseInt(req.getParameter("id"));
        
        // Load JDBC driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException cnf) {
            cnf.printStackTrace();
        }
        
        // Generate the connection
        try (Connection con = DriverManager.getConnection("jdbc:mysql:///book", "root", "Mamatha160902");
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            
            pw.println("<html>");
            pw.println("<head>");
            pw.println("<style>");
            pw.println("body { background-color: #001f3f; color: white; text-align: center; }");
            pw.println("table { border-collapse: collapse; width: 50%; margin: auto; color: white; }");
            pw.println("th, td { border: 1px solid white; padding: 8px; text-align: left; }");
            pw.println("th { background-color: #0074D9; text-align: center; font-size: 1.5rem; font-weight: bold; }");
            pw.println("tr:nth-child(even) { background-color: #001f3f; }");
            pw.println("tr:nth-child(odd) { background-color: #001f3f; }");
            pw.println("input[type='text'] { width: 100%; padding: 5px; box-sizing: border-box; }");
            pw.println("input[type='submit'], input[type='reset'] { background-color: #0074D9; color: white; padding: 10px 20px; border: none; border-radius: 5px; cursor: pointer; margin: 5px; }");
            pw.println("input[type='submit']:hover, input[type='reset']:hover { background-color: #005bb5; }");
            pw.println("a.button { background-color: #0074D9; color: white; padding: 10px 20px; text-align: center; display: inline-block; border-radius: 5px; margin: 20px auto; text-decoration: none; }");
            pw.println("</style>");
            pw.println("</head>");
            pw.println("<body>");
            pw.println("<form action='editurl?id=" + id + "' method='post'>");
            pw.println("<table>");
            pw.println("<tr>");
            pw.println("<th colspan='2'>Edit Book</th>");
            pw.println("</tr>");
            pw.println("<tr>");
            pw.println("<td>Book Name</td>");
            pw.println("<td><input type='text' name='bookName' value='" + rs.getString(1) + "'></td>");
            pw.println("</tr>");
            pw.println("<tr>");
            pw.println("<td>Book Edition</td>");
            pw.println("<td><input type='text' name='bookEdition' value='" + rs.getString(2) + "'></td>");
            pw.println("</tr>");
            pw.println("<tr>");
            pw.println("<td>Book Price</td>");
            pw.println("<td><input type='text' name='bookPrice' value='" + rs.getFloat(3) + "'></td>");
            pw.println("</tr>");
            pw.println("<tr>");
            pw.println("<td colspan='2' style='text-align: center;'>");
            pw.println("<input type='submit' value='Edit'>");
            pw.println("<input type='reset' value='Cancel'>");
            pw.println("<a href='home.html' class='button'>Home</a>");
            pw.println("</td>");
            pw.println("</tr>");
            pw.println("</table>");
            pw.println("</form>");
            pw.println("</body>");
            pw.println("</html>");
        } catch (SQLException se) {
            se.printStackTrace();
            pw.println("<h1>" + se.getMessage() + "</h1>");
        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<h1>" + e.getMessage() + "</h1>");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }
}
