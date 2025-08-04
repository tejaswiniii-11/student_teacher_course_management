package com.aurionpro.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.aurionpro.database.Database;
import com.aurionpro.model.Teacher;
import com.aurionpro.model.TeacherProfile;

public class TeacherDao {
	private Connection connection;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;
	private static TeacherDao instance;

	public TeacherDao() {
		connection = Database.getInstance().getConnection();
	}

	public static TeacherDao getInstance() {
		if (instance == null) {
			instance = new TeacherDao();
		}
		return instance;
	}

	public boolean isTeacherPresent(int teacherId) {
		try (PreparedStatement ps = connection.prepareStatement("SELECT 1 FROM teachers WHERE teacher_id = ?")) {
			ps.setInt(1, teacherId);
			ResultSet rs = ps.executeQuery();
			return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean isCourseSubjectPresent(String courseSubjectId) {
	    try {
	    	preparedStatement = connection.prepareStatement("SELECT 1 FROM course_subject WHERE course_subject_id = ?");
	    	preparedStatement.setString(1, courseSubjectId);
	        ResultSet rs = preparedStatement.executeQuery();
	        return rs.next();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}
	
	public boolean isTeacherActive(int teacherId) {
		try {
			preparedStatement = connection.prepareStatement("SELECT isActive FROM teachers WHERE teacher_id = ?");
			preparedStatement.setInt(1, teacherId);
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				return rs.getBoolean("isActive");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}


	public void addTeacher(Teacher teacher,TeacherProfile teacherProfile) {
		try {
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement("INSERT INTO teachers VALUES (?,?,?,?,?,?)");
			preparedStatement.setInt(1, teacher.getTeacherId());
			preparedStatement.setString(2, teacher.getFirstName());
			preparedStatement.setString(3, teacher.getLastName());
			preparedStatement.setString(4, teacher.getMobileNo());
			preparedStatement.setString(5, teacher.getEmailId());
			preparedStatement.setBoolean(6, teacher.isActive());
			preparedStatement.executeUpdate();
			preparedStatement = connection.prepareStatement("INSERT INTO TEACHER_PROFILE VALUES (?,?,?,?,?)");
			preparedStatement.setInt(1, teacherProfile.getTeacherId());
			preparedStatement.setString(2, teacherProfile.getCity());
			preparedStatement.setString(3, teacherProfile.getQualification());
			preparedStatement.setInt(4, teacherProfile.getExperience());
			preparedStatement.setDouble(5, teacherProfile.getSalary());
			preparedStatement.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

//	public void addTeacherProfile(TeacherProfile teacherProfile) {
//		try {
//			preparedStatement = connection.prepareStatement("INSERT INTO TEACHER_PROFILE VALUES (?,?,?,?,?)");
//			preparedStatement.setInt(1, teacherProfile.getTeacherId());
//			preparedStatement.setString(2, teacherProfile.getCity());
//			preparedStatement.setString(3, teacherProfile.getQualification());
//			preparedStatement.setInt(4, teacherProfile.getExperience());
//			preparedStatement.setDouble(5, teacherProfile.getSalary());
//			preparedStatement.executeUpdate();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	public List<Teacher> getAllActiveTeachers() {
		List<Teacher> teacherList = new ArrayList<>();
		try {
			preparedStatement = connection.prepareStatement("SELECT * FROM teachers WHERE isActive = true");
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				int id = resultSet.getInt("teacher_id");
				String fname = resultSet.getString("first_name");
				String lname = resultSet.getString("last_name");
				String mobile = resultSet.getString("mobile_no");
				String email = resultSet.getString("email_id");
				boolean isActive = resultSet.getBoolean("isActive");

				Teacher teacher = new Teacher(id, fname, lname, mobile, email, isActive);
				teacherList.add(teacher);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return teacherList;
	}

	public void searchATeacher(int teacherId) {
	    try {
	        preparedStatement = connection.prepareStatement("SELECT * FROM teachers WHERE teacher_id = ?");
	        preparedStatement.setInt(1, teacherId);
	        resultSet = preparedStatement.executeQuery();

	        String format = "| %-5s | %-15s | %-15s | %-15s | %-25s |%n";
	        String line = "+-------+-----------------+-----------------+-----------------+---------------------------+";

	        if (!resultSet.isBeforeFirst()) {
	            System.out.println("No teacher found with ID: " + teacherId);
	            return;
	        }

	        System.out.println(line);
	        System.out.printf(format, "ID", "First Name", "Last Name", "Mobile", "Email");
	        System.out.println(line);

	        while (resultSet.next()) {
	            System.out.printf(format,
	                    resultSet.getInt("teacher_id"),
	                    resultSet.getString("first_name"),
	                    resultSet.getString("last_name"),
	                    resultSet.getString("mobile_no"),
	                    resultSet.getString("email_id"));
	        }

	        System.out.println(line);

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}


	public boolean deleteATeacher(int teacherId) {
		try {
			preparedStatement = connection.prepareStatement("UPDATE teachers SET isActive = ? WHERE teacher_id = ? AND isActive = true");
			preparedStatement.setBoolean(1, false);
			preparedStatement.setInt(2, teacherId);

			int rows = preparedStatement.executeUpdate();
			if (rows > 0) {
				System.out.println("ID " + teacherId + " deleted successfully");
				return true;
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean activateTeacher(int teacherId) {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("UPDATE teachers SET isActive = true WHERE teacher_id = ?"); 
	    	preparedStatement.setInt(1, teacherId);
	        int updated = preparedStatement.executeUpdate();
	        return updated > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	public void assignSubject(String courseSubjectId, int teacherId, boolean isActive) {
	    String sql = "INSERT INTO subject_teacher (course_subject_id, teacher_id, is_active) VALUES (?, ?, ?)";
	    try {
	    	preparedStatement = connection.prepareStatement(sql);
	        preparedStatement.setString(1, courseSubjectId);
	        preparedStatement.setInt(2, teacherId);
	        preparedStatement.setBoolean(3, isActive);

	        int rows = preparedStatement.executeUpdate();
	        if (rows > 0) {
	            System.out.println("Subject assigned successfully.");
	        } else {
	            System.out.println("Failed to assign subject.");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	public boolean isSubjectAssigned(int teacherId, String courseSubjectId) {
	    String sql = "SELECT 1 FROM subject_teacher WHERE teacher_id = ? AND course_subject_id = ?";
	    try {
	    	preparedStatement = connection.prepareStatement(sql);
	    	preparedStatement.setInt(1, teacherId);
	    	preparedStatement.setString(2, courseSubjectId);
	        ResultSet rs = preparedStatement.executeQuery();
	        return rs.next();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}


	public void removeASubject(String courseSubId, int teacherId) {
		try {
			preparedStatement = connection.prepareStatement(
					"UPDATE subject_teacher SET is_Active = false WHERE course_subject_id = ? AND teacher_id = ?");
			preparedStatement.setString(1, courseSubId);
			preparedStatement.setInt(2, teacherId);
			int row = preparedStatement.executeUpdate();
			if (row > 0) {
				System.out.println("Id " + courseSubId + " deleted successfully");
			} else {
				System.out.println("Subject not found with id " + courseSubId);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<TeacherProfile> getAllTeacherProfiles() {
	    List<TeacherProfile> profiles = new ArrayList<>();
	    String sql = "SELECT tp.teacher_id, city, qualification, experience, salary " +
	                 "FROM teacher_profile tp LEFT JOIN teachers t ON tp.teacher_id = t.teacher_id";

	    try (PreparedStatement ps = connection.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {

	        while (rs.next()) {
	            int id = rs.getInt("teacher_id");
	            String city = rs.getString("city");
	            String qualification = rs.getString("qualification");
	            int experience = rs.getInt("experience");
	            double salary = rs.getDouble("salary");

	            TeacherProfile profile = new TeacherProfile(id, city, qualification, experience, salary);
	            profiles.add(profile);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return profiles;
	}

}
