package serial_com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbConnector {
	
	Connection myConn = null;
	
	Statement myStatement = null;

	ResultSet myRs = null;
	
	public DbConnector() throws SQLException {
		

		this.myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/medicalschema","root","a1b2c3d4!");
		
		this.myStatement = myConn.createStatement();
		
		this.myRs = myStatement.executeQuery("select * from patients");
		
	}



}
