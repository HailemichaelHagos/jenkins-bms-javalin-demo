package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import exception.SystemException;

public class DBUtil {

	static Connection conn;
	
	static {
		//step 1
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static Connection obtainConnection()throws SystemException{
		
		// design pattern - singleton design pattern
		
		//step 2
		//String connectionUrl = "jdbc:postgresql://database-2.cw4qvkziiwtm.us-east-1.rds.amazonaws.com:5432/bms";
		//String connectionUrl = "jdbc:postgresql://localhost:5432/bms";
		String connectionUrl = "jdbc:postgresql://10.0.0.88:8888/bms";
		
		String userName = "postgres";
		String password = "mysecretpassword";
		
		if(conn == null) {
			try {
				conn = DriverManager.getConnection(connectionUrl, userName, password);
			} catch (SQLException e) {
				throw new SystemException();
			}	
		}
		
		return conn;
	}
	
	static void closeConnection()throws SystemException {
		try {
			conn.close();
		} catch (SQLException e) {
			throw new SystemException();
		}
	}
}
