package com.aurionpro.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.cj.jdbc.CallableStatement;

public class Database {
	private Connection connection = null;
	private static Database databaseConnection = null;
	
	private Database() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/college_management","root","jatinMysql@7680");
			
			System.out.println("Connection successful !!");
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() {
		if(databaseConnection == null) {
			databaseConnection = new Database();
		}
		return databaseConnection.connection;
	}
	
}
