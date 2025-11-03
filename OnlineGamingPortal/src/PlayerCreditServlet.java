

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PlayerCreditServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("userName");
        String password = request.getParameter("passWord");
        String action = request.getParameter("action"); 
        int creditChange = Integer.parseInt(request.getParameter("credits"));

        String sql = "SELECT * FROM GamingPortal.User WHERE userName=? AND passWord=?";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/GamingPortal","root", "root")) {
        
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, username);
                stmt.setString(2, password);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        int currentCredits = rs.getInt("Credits");

             