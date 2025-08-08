package com.aurionpro.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.aurionpro.database.Database;

public class AdminDao {
	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;
	
	public AdminDao() {
		connection = Database.getInstance().getConnection();
	}
	
	public boolean checkUserExist(String emailId, String password) {
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select * from admin_credential where email_id = '" + emailId + "'");
			if(!resultSet.next())
				return false;
			return resultSet.getString(2).equals(password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void addAdmin(String emailId, String password) {
		
	}
	
}