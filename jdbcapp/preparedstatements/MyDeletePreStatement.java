package com.tyss.jdbcapp.preparedstatements;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import com.mysql.cj.jdbc.Driver;

public class MyDeletePreStatement {
	public static void main(String[] args)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException {

		Connection conn = null;
		PreparedStatement prepstmt = null;
		ResultSet rs = null;

		FileInputStream inputstream = new FileInputStream("task.properties");
		Properties pro = new Properties();
		pro.load(inputstream);



		// Step 1: Load the driver
		try {
			Driver driver = new Driver();
			DriverManager.registerDriver(driver);

//			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

			// 2. get the DB "connection" via driver
			String dbURL = "jdbc:mysql://localhost:3306/tyss_db?autoReconnect=true&useSSL=false";

			// overloaded method with 1parameter
//			conn = DriverManager.getConnection(dbURL);
//			System.out.println("loaded and registered");

			// overloaded method with 3parameters
//			conn = DriverManager.getConnection(dbURL, pro.getProperty("name"), pro.getProperty("password") );

			// overloaded method with 2parameters
			conn = DriverManager.getConnection(dbURL, pro);

			// 3.Issue SQL queries via Connection

			String query = "delete from personinfo where id =?";
			prepstmt = conn.prepareStatement(query);
			prepstmt.setInt(1, Integer.parseInt(args[0]));
			
			int rowsaffected = prepstmt.executeUpdate();
		
			if(rowsaffected!=0) {
				System.out.println("deleted "+ rowsaffected + " rows");
			} else {
				System.out.println("record not deleted");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 5. close all JDBC Objects
			try {
				if (conn != null) {
					conn.close();
				}
				if (rs != null) {
					rs.close();
				}
				if (prepstmt != null) {
					prepstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
