package com.aurionpro.database;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;	
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database {

	//Followed Singleton Design  Principle - used single instance
    private static Database instance;
    private static Connection connection;
    private static final String URL = "jdbc:mysql://localhost:3306/miniproject";
    private static final String USER = "root";
    private static final String PASSWORD = "#Teju@2003";

    private Database() {
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            connection = DriverManager.getConnection(URL, USER, PASSWORD);
//            System.out.println("Connection Established Successfully \n\n");
//        } catch (ClassNotFoundException | SQLException e) {
//            e.printStackTrace();
//        }
        
        
    	Properties props = new Properties();
    	try (FileInputStream fis = new FileInputStream("D:\\Swabhav_TB\\basic_assignments_app\\student_teacher_course_management-main\\src\\com\\aurionpro\\db.properties")) {
    	    props.load(fis);
    	    String url = props.getProperty("db.url");
    	    String username = props.getProperty("db.username");
    	    String password = props.getProperty("db.password");
    	    connection = DriverManager.getConnection(url, username, password);
    	    if (connection == null) {
    	        System.out.println("Database connection is null!");
    	    }
    	} catch (Exception e) {
    	    e.printStackTrace();
    	}
    }

    public static Database getInstance() {
        if (instance == null) {
            synchronized (Database.class) {
                if (instance == null) {
                    instance = new Database();
                }
            }
        }
        return instance;
    }

    public static Connection getConnection() {
        return connection;
    }
}
