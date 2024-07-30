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

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final String query = "INSERT INTO BOOKDATA(BOOKNAME, BOOKEDITION, BOOKPRICE) VALUES(?, ?, ?)";
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // Get PrintWriter
        PrintWriter pw = res.getWriter();
        // Set content type
        res.setContentType("text/html");
        // Get the book info
        String bookName = req.getParameter("bookName");
        String bookEdition = req.getParameter("bookEdition");
        String bookPriceStr = req.getParameter("bookPrice");

        // Validate inputs
        if (bookName == null || bookName.isEmpty() || bookEdition == null || bookEdition.isEmpty() || bookPriceStr == null || bookPriceStr.isEmpty()) {
            pw.println("<html>");
            pw.println("<head>");
            pw.println("<style>");
            pw.println("body { background-color: #001f3f; color: white; text-align: center; }");
            pw.println(".message-box { background-color: #003366; border: 2px solid white; border-radius: 10px; padding: 20px; margin: 50px auto; width: 50%; }");
            pw.println(".message-box h2 { margin: 0; color: red; }"); // Red color for error message
            pw.println(".button-container { text-align: center; margin-top: 20px; }");
            pw.println(".button-container a { background-color: #0074D9; color: white; text-decoration: none; padding: 10px 20px; border-radius: 5px; margin: 5px; display: inline-block; }");
            pw.println(".button-container a:hover { background-color: #005bb5; }");
            pw.println("</style>");
            pw.println("</head>");
            pw.println("<body>");
            pw.println("<div class='message-box'>");
            pw.println("<h2>Error: All fields are required.</h2>");
            pw.println("<div class='button-container'>");
            pw.println("<a href='home.html'>Home</a>");
            pw.println("</div>");
            pw.println("</div>");
            pw.println("</body>");
            pw.println("</html>");
            return;
        }

        float bookPrice = Float.parseFloat(bookPriceStr);

        // Load JDBC driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException cnf) {
            cnf.printStackTrace();
        }
        
        // Generate the connection
        try (Connection con = DriverManager.getConnection("jdbc:mysql:///book", "root", "Mamatha160902");
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, bookName);
            ps.setString(2, bookEdition);
            ps.setFloat(3, bookPrice);
            int count = ps.executeUpdate();
            
            pw.println("<html>");
            pw.println("<head>");
            pw.println("<style>");
            pw.println("body { background-color: #001f3f; color: white; text-align: center; }");
            pw.println(".message-box { background-color: #003366; border: 2px solid white; border-radius: 10px; padding: 20px; margin: 50px auto; width: 50%; }");
            pw.println(".message-box h2 { margin: 0; }");
            pw.println(".button-container { text-align: center; margin-top: 20px; }");
            pw.println(".button-container a { background-color: #0074D9; color: white; text-decoration: none; padding: 10px 20px; border-radius: 5px; margin: 5px; display: inline-block; }");
            pw.println(".button-container a:hover { background-color: #005bb5; }");
            pw.println("</style>");
            pw.println("</head>");
            pw.println("<body>");
            pw.println("<div class='message-box'>");
            if (count == 1) {
                pw.println("<h2>Record is Registered Successfully</h2>");
            } else {
                pw.println("<h2>Record was not Registered Successfully</h2>");
            }
            pw.println("<div class='button-container'>");
            pw.println("<a href='home.html'>Home</a>");
            pw.println("<a href='bookList'>Book List</a>");
            pw.println("</div>");
            pw.println("</div>");
            pw.println("</body>");
            pw.println("</html>");
        } catch (SQLException se) {
            se.printStackTrace();
            pw.println("<html><head><style>body { background-color: #001f3f; color: white; text-align: center; }</style></head><body>");
            pw.println("<div class='message-box'><h2>Error: " + se.getMessage() + "</h2>");
            pw.println("<div class='button-container'>");
            pw.println("<a href='home.html'>Home</a>");
            pw.println("</div></div></body></html>");
        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<html><head><style>body { background-color: #001f3f; color: white; text-align: center; }</style></head><body>");
            pw.println("<div class='message-box'><h2>Error: " + e.getMessage() + "</h2>");
            pw.println("<div class='button-container'>");
            pw.println("<a href='home.html'>Home</a>");
            pw.println("</div></div></body></html>");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }
}
