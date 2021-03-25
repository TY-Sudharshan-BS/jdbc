package com.tyss.jdbcapp.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.mysql.cj.jdbc.Driver;


public class MyFirstJDBCProgram {
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException {

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		FileInputStream inputstream = new FileInputStream("task.properties");
		Properties pro = new Properties();
		pro.load(inputstream);
		 
		
//		String name = (String) pro.get("name");
//		String password = (String) pro.get("password");


		// Step 1: Load the driver
		try {
			Driver driver = new Driver();
			DriverManager.registerDriver(driver);
			
//			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			
			//2. get the DB "connection" via driver
			String dbURL = "jdbc:mysql://localhost:3306/tyss_db?autoReconnect=true&useSSL=false";
			
			
			//overloaded method with 1parameter 
//			conn = DriverManager.getConnection(dbURL);
//			System.out.println("loaded and registered");
			
			
			//overloaded method with 3parameters
//			conn = DriverManager.getConnection(dbURL, pro.getProperty("name"), pro.getProperty("password") );
			
			
			//overloaded method with 2parameters
			conn = DriverManager.getConnection(dbURL, pro ); //Attempts to establish a connection to the given database URL
			

			// 3.Issue SQL queries via Connection

			String query = "select * from emp";
			stmt = conn.createStatement(); //Creates a Statement object for sendingSQL statements to the database
			rs = stmt.executeQuery(query);

			// 4. Process the results returned by "SQL queries"
			while (rs.next()) {
				int empId = rs.getInt("empno");
				String empname = rs.getString("ename");
				int departmentId = rs.getInt("deptno");
				System.out.println("===========================");

				System.out.println("Id = " + empId);
				System.out.println("name = " + empname);
				System.out.println("department no = " + departmentId);
				System.out.println("***********************************");
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
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
