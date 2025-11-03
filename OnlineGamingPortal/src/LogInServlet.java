//this class needs to be able to log in existing users that where created using the ControlCentreServlet
	
	
	

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

public class LogInServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Get the user's input from the HTML Page
        String usernameInputByUser = request.getParameter("userName");
        String passwordInputByUser = request.getParameter("passWord");

        // Database connection
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/GamingPortal","root", "root");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            String sql = "SELECT * FROM GamingPortal.User WHERE userName=? AND passWord=?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, usernameInputByUser);
                stmt.setString(2, passwordInputByUser);

                try (ResultSet rs = stmt.executeQuery()) {
                    response.setContentType("text/html");
                    PrintWriter out = response.getWriter();

                    if (rs.next()) {
                        // Login successful
                        out.println("<html><body><h1>Login Successful</h1>");
                        out.println("<p>Welcome, " + usernameInputByUser + "!</p>");
                        out.println("<p>Credits: " + rs.getInt("Credits") + "</p>");
                        out.println("</body></html>");
                    } else {
                        // Login failed
                        out.println("<html><body><h1>Login Failed</h1>");
                        out.println("<p>Invalid username or password.</p>");
                        out.println("</body></html>");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Database error occurred", e);
        }
    }
}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	