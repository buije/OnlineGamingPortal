
import java.sql.ResultSet;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControlCentreServlet extends HttpServlet{

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws 
	ServletException, IOException{

		//1. Get the user's input from the HTML Page
		String usernameInputByUser = request.getParameter("userName");
		String passwordInputByUser = request.getParameter("passWord");
		 String passwordInputByUser2 = request.getParameter("passWord2");
		
		 
		 
		 if (passwordInputByUser.equals(passwordInputByUser2)) {
			 Connection connection = null;
				try {
					connection = DriverManager.getConnection(
							"jdbc:mysql://localhost:3306/GamingPortal?serverTimezone=UTC", "root", "root");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				try {
					PreparedStatement stmt = connection.prepareStatement(
							"INSERT into User (userName,passWord,Credits)" +" VALUES (?,?,?)");
					//pass in the values as parameters
					stmt.setString(1, usernameInputByUser);
					stmt.setString(2, passwordInputByUser);
					stmt.setInt(3, 500); 
					stmt.executeUpdate();
					stmt.close();
					
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

				String sql = "SELECT * FROM GamingPortal.User WHERE userName=? and passWord=?";
				
				
				
				try {
				PreparedStatement stmt = connection.prepareStatement(sql);
				stmt.setString(1, usernameInputByUser);
				stmt.setString(2, passwordInputByUser);
				
				ResultSet rs = stmt.executeQuery();
					
				if (rs.next()) {
					response.setContentType("text/html");
					PrintWriter out = response.getWriter();
					out.println("<html><head><title>Response Page</title></head><body> Hello User. " + "\t\nUserName :"
							+ usernameInputByUser + "\t\nPassWord:  " + passwordInputByUser + "\t\nCredits: " + rs.getInt("Credits")
							+ ".</br><h1> THANK YOU FOR REGISTERING </h1> </body><html>");
				}
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			
				
        
         }
		 else{
			 response.setContentType("text/html");
			 PrintWriter out = response.getWriter();
			 out.println("<html><head><title>Response Page</title></head><body> Passwords do not match. "
                        +  "\t\nUserName :"+usernameInputByUser+"\t\nPassWord:  "+ passwordInputByUser+
                        ".</br><h1> PLEASE TRY AGAIN </h1> </body><html>");
			 
			 
			 
			 
			 
			 
		 }
		 
	}
}
		 
		 

	

