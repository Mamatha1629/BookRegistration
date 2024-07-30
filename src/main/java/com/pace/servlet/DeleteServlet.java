package com.pace.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/deleteurl")
public class DeleteServlet extends HttpServlet {
    private static final String query = "DELETE FROM BOOKDATA WHERE id=?";
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // Get PrintWriter
        PrintWriter pw = res.getWriter();
        // Set content type
        res.setContentType("text/html");
        // Get the id of the record
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
            int count = ps.executeUpdate();
            
            // Set up HTML response with CSS
            pw.println("<!DOCTYPE html>");
            pw.println("<html>");
            pw.println("<head>");
            pw.println("<meta charset='ISO-8859-1'>");
            pw.println("<title>Delete Status</title>");
            pw.println("<style>");
            pw.println("body { background-color: #001f3f; color: white; text-align: center; }");
            pw.println(".message { background-color: #003366; border: 2px solid white; border-radius: 10px; padding: 20px; margin: 20px auto; width: 50%; }");
            pw.println(".btn-custom { background-color: #0074D9; color: white; border: none; border-radius: 5px; padding: 10px 20px; cursor: pointer; margin: 5px; text-decoration: none; }");
            pw.println(".btn-custom:hover { background-color: #005bb5; }");
            pw.println(".button-container { text-align: center; margin-top: 20px; }");
            pw.println(".button-container a { margin: 5px; display: inline-block; }");
            pw.println("</style>");
            pw.println("</head>");
            pw.println("<body>");
            
            pw.println("<div class='message'>");
            if (count == 1) {
                pw.println("<h2>Record is Deleted Successfully</h2>");
            } else {
                pw.println("<h2>Record was not deleted Successfully</h2>");
            }
            pw.println("</div>");
            
            pw.println("<div class='button-container'>");
            pw.println("<a href='home.html' class='btn-custom'>Home</a>");
            pw.println("<a href='bookList' class='btn-custom'>Book List</a>");
            pw.println("</div>");
            
            pw.println("</body>");
            pw.println("</html>");
            
        } catch (SQLException se) {
            se.printStackTrace();
            pw.println("<html><head><style>body { background-color: #001f3f; color: white; text-align: center; }</style></head><body>");
            pw.println("<div class='message'><h2>Error: " + se.getMessage() + "</h2></div>");
            pw.println("<div class='button-container'>");
            pw.println("<a href='home.html' class='btn-custom'>Home</a>");
            pw.println("</div></body></html>");
        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<html><head><style>body { background-color: #001f3f; color: white; text-align: center; }</style></head><body>");
            pw.println("<div class='message'><h2>Error: " + e.getMessage() + "</h2></div>");
            pw.println("<div class='button-container'>");
            pw.println("<a href='home.html' class='btn-custom'>Home</a>");
            pw.println("</div></body></html>");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }
}
