package com.aurionpro.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.aurionpro.database.Database;

public class FeeDao {
	private Connection connection = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	private PreparedStatement preparedStatement = null;
	
	public FeeDao() {
		this.connection = Database.getConnection();
	}
	
	private Statement getStatement() throws SQLException {
		if(statement == null) {
			statement = connection.createStatement();
		}
		return statement;
	}
	
	public boolean checkIfStudentExist(int studentId) {
		try {
			statement = getStatement();
			resultSet = statement.executeQuery("select * from students where student_id = " + studentId);
			return resultSet.next();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public boolean checkIfCourseExist(int courseId) {
		boolean check = false;
		try {
			statement = getStatement();
			resultSet = statement.executeQuery("select * from courses where course_id = " + courseId);
			return resultSet.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return check;
	}
	
	public void feeByStudentId(int studentId) {
		if(!checkIfStudentExist(studentId)) {
			System.out.println("XXXXX Wrong student id. XXXXX");
			return;
		}
		
		try {
			statement = getStatement();
			resultSet = statement.executeQuery(
					"select sf.student_id, CONCAT(student_fname,' ',student_lname) as Name, "
					+ " sf.paid_fee, sf.pending_fee, sf.total_fee from student_fees sf "
					+ " join students s on s.student_id = sf.student_id"
					+ " where s.student_id = " + studentId
					);
			System.out.printf("\n\n%-10s | %-25s | %-10s | %-15s | %-10s\n","Student ID","Name","Paid fee","Pending fee","Total fee");
			
			while(resultSet.next()) {
				System.out.printf("%-10s | %-25s | %-10s | %-15s | %-10s\n",resultSet.getInt(1), resultSet.getString(2), resultSet.getDouble(3), resultSet.getDouble(4),resultSet.getDouble(5));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void feeByCourseId(int courseId) {
		if(!checkIfCourseExist(courseId)) {
			System.out.println("XXX Invalid Course Id XXX");
			return;
		}
		
		try {
			statement = getStatement();
			resultSet = statement.executeQuery("select * from courses where course_id = "+courseId);
			while(resultSet.next()) {
				System.out.println(resultSet.getInt(1) + "\t" + resultSet.getString(2) + "\t" + resultSet.getBoolean(3) +"\t"+resultSet.getDouble(4));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void updateFeeOfCourse(int courseId, double fee) {
		if(!checkIfCourseExist(courseId)) {
			System.out.println("XXX Invalid course id. XXX");
			return;
		}
		try {
			preparedStatement = connection.prepareStatement("update course set course_fee = ? where course_id = ?");
			preparedStatement.setDouble(1, fee);
			preparedStatement.setInt(2, courseId);
			int updates = preparedStatement.executeUpdate();
			System.out.println("✔ Updation successful.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void paidFees() {
		try {
			statement = connection.createStatement();
			String query = "select s.student_id as ID, CONCAT(student_fname,' ',student_lname) as Name, sf.paid_fee as paidFee from student_fees sf "
					+ "join students s "
					+ "on s.student_id = sf.student_id and sf.pending_fee = 0.00";
			resultSet = statement.executeQuery(query);
			if(resultSet.next()) {
				System.out.println("\n");
				System.out.printf("%-10s %-20s %-10s\n", "Student Id","Name","Paid Fee");
				System.out.printf("%-10s %-20s %-10s\n",resultSet.getInt(1),resultSet.getString(2),resultSet.getDouble(3));
				while(resultSet.next()) {
					System.out.printf("%-10s %-20s %-10s\n",resultSet.getInt(1),resultSet.getString(2),resultSet.getDouble(3));
				}
			}
			else {
				System.out.println("\n***** Not a single student has paid there full fees. *****");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void pendingFees() {
		try {
			statement = connection.createStatement();
			String query = "select s.student_id as ID, CONCAT(student_fname,' ',student_lname) as Name,"
					+ " sf.pending_fee as pendingFee from student_fees sf "
					+ " join students s "
					+ " on s.student_id = sf.student_id and sf.pending_fee > 0.00 ";
			resultSet = statement.executeQuery(query);
			if(resultSet.next()) {
				System.out.println("\n");
				System.out.printf("%-10s %-30s %-10s\n", "Student Id","Name","Pending Fee");
				System.out.printf("%-10s %-30s %-10s\n",resultSet.getInt(1),resultSet.getString(2),resultSet.getDouble(3));
				while(resultSet.next()) {
					System.out.printf("%-10s %-30s %-10s\n",resultSet.getInt(1),resultSet.getString(2),resultSet.getDouble(3));
				}
			}
			else {
				System.out.println("\n *** Every student has paid there fees. ***");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void totalEarning() {
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select sum(total_fee) from student_fees");
			resultSet.next();
			System.out.println("Total Earning : " + resultSet.getDouble(1));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
