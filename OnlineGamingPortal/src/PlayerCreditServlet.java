

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
        String action = request.getParameter("action"); // "add" or "spend"
        int creditChange = Integer.parseInt(request.getParameter("credits"));

        String sql = "SELECT * FROM GamingPortal.User WHERE userName=? AND passWord=?";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/GamingPortal","root", "root")) {
        
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, username);
                stmt.setString(2, password);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        int currentCredits = rs.getInt("Credits"); 

                        // Perform action based on user input
                        if ("add".equalsIgnoreCase(action)) {
                            currentCredits += creditChange;
                        } else if ("spend".equalsIgnoreCase(action)) {
                            if (currentCredits >= creditChange) {
                                currentCredits -= creditChange;
                            } else {
                                sendResponse(response, username, currentCredits, "Insufficient credits to complete the transaction.");
                                return;
                            }
                        }

                        // Update credits in the database
                        String updateSql = "UPDATE GamingPortal.User SET Credits=? WHERE userName=?";
                        try (PreparedStatement updateStmt = connection.prepareStatement(updateSql)) {
                            updateStmt.setInt(1, currentCredits);
                            updateStmt.setString(2, username);
                            updateStmt.executeUpdate();
                        }

                        // Send success response
                        sendResponse(response, username, currentCredits, "Transaction successful!");
                    } else {
                        sendResponse(response, username, 0, "Invalid username or password.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error occurred.");
        }
    }

    private void sendResponse(HttpServletResponse response, String username, int credits, String message) throws IOException {
        response.setContentType("text/html");
        try (PrintWriter out = response.getWriter()) {
            out.println("<html><head><title>Player Credits</title></head><body>");
            out.println("<h1>" + message + "</h1>");
            out.println("<p>Gamer Tag: " + username + "</p>");
            out.println("<p>Current Credits: " + credits + "</p>");
            out.println("</body></html>");
        }
    }
}
