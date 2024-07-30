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

@WebServlet("/editurl")
public class EditServlet extends HttpServlet {
    private static final String query = "UPDATE BOOKDATA SET BOOKNAME=?, BOOKEDITION=?, BOOKPRICE=? WHERE id=?";
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // Get PrintWriter
        PrintWriter pw = res.getWriter();
        // Set content type
        res.setContentType("text/html");
        
        // Get the id of the record
        int id = Integer.parseInt(req.getParameter("id"));
        // Get the edit data we want to update
        String bookName = req.getParameter("bookName");
        String bookEdition = req.getParameter("bookEdition");
        float bookPrice = Float.parseFloat(req.getParameter("bookPrice"));
        
        // Define the CSS style
        String css = "<style>"
            + "body { background-color: #001f3f; color: white; font-family: Arial, sans-serif; text-align: center; margin: 0; padding: 0; }"
            + ".message { background-color: #003366; border: 2px solid white; border-radius: 10px; padding: 15px; width: 40%; min-height: 150px; margin: 50px auto; display: flex; flex-direction: column; justify-content: center; align-items: center; text-align: center; }"
            + ".bg-danger { color: white; font-size: 1.5rem; margin: 0; padding: 0; }"
            + ".btn-custom { background-color: #0074D9; color: white; border: none; border-radius: 5px; padding: 12px 24px; cursor: pointer; font-size: 14px; text-decoration: none; margin: 5px; }"
            + ".btn-custom:hover { background-color: #005bb5; }"
            + ".button-container { text-align: center; margin-top: 20px; }"
            + ".button-container a { margin: 5px; display: inline-block; }"
            + "</style>";
        
        // Load JDBC driver and generate connection
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException cnf) {
            cnf.printStackTrace();
        }
        
        try (Connection con = DriverManager.getConnection("jdbc:mysql:///book", "root", "Mamatha160902");
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, bookName);
            ps.setString(2, bookEdition);
            ps.setFloat(3, bookPrice);
            ps.setInt(4, id);
            int count = ps.executeUpdate();
            
            pw.println("<html><head><title>Edit Status</title>" + css + "</head><body>");
            pw.println("<div class='message'>");
            if (count == 1) {
                pw.println("<div class='bg-danger'>Record is Edited Successfully</div>");
            } else {
                pw.println("<div class='bg-danger'>Record is not Edited Successfully</div>");
            }
            pw.println("</div>");
            pw.println("<div class='button-container'>");
            pw.println("<a href='home.html' class='btn-custom'>Home</a>");
            pw.println("<a href='bookList' class='btn-custom'>Book List</a>");
            pw.println("</div>");
            pw.println("</body></html>");
        } catch (SQLException se) {
            se.printStackTrace();
            pw.println("<html><head><title>Error</title>" + css + "</head><body>");
            pw.println("<div class='message'><div class='bg-danger'>Error: " + se.getMessage() + "</div>");
            pw.println("<div class='button-container'>");
            pw.println("<a href='home.html' class='btn-custom'>Home</a>");
            pw.println("</div></div></body></html>");
        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<html><head><title>Error</title>" + css + "</head><body>");
            pw.println("<div class='message'><div class='bg-danger'>Error: " + e.getMessage() + "</div>");
            pw.println("<div class='button-container'>");
            pw.println("<a href='home.html' class='btn-custom'>Home</a>");
            pw.println("</div></div></body></html>");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }
}
