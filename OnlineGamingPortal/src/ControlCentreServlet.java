

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

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws 
	ServletException, IOException{

		//1. Get the user's input from the HTML Page
		String usernameInputByUser = request.getParameter("userName");
		String passwordInputByUser = request.getParameter("passWord");
		 String passwordInputByUser2 = request.getParameter("passWord2");

		//2 Put the user info in the database
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/GamingPortal?serverTimezone=UTC","root", "root");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			PreparedStatement enterMood = connection.prepareStatement(
					"INSERT into Users "
							+ "(userName,passWord)" +" VALUES (?,?)");
			//pass in the values as parameters
			enterMood.setString(1, usernameInputByUser);
			enterMood.setString(2, passwordInputByUser);
			int rowsUpdated = enterMood.executeUpdate();
			enterMood.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		//3. Send back a response to the user
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html><head><title>Response Page</title></head><body> Hello User. "
				+  "User Info is :"+usernameInputByUser+ passwordInputByUser+passwordInputByUser2+
                ".</br><h1> THANK YOU </h1> </body><html>");
	}

}

	

