package com.aurionpro.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.aurionpro.database.Database;
import com.aurionpro.model.Student;
import com.aurionpro.model.StudentProfile;
import com.aurionpro.model.StudentProfile.StudentGender;

public class StudentDao {

	private Connection connection;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;

	public StudentDao() {
		connection = Database.getInstance().getConnection();
	}

	// For validation
	public boolean checkIfStudentExists(int studentID) {
		try {
			preparedStatement = connection.prepareStatement("SELECT student_id FROM students WHERE student_id = ?");
			preparedStatement.setInt(1, studentID);
			resultSet = preparedStatement.executeQuery();
			return resultSet.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	// For validation
	public boolean checkIfProfileExists(int profileID) {
		try {
			preparedStatement = connection
					.prepareStatement("SELECT profile_id FROM students_profile WHERE profile_id = ?");
			preparedStatement.setInt(1, profileID);
			resultSet = preparedStatement.executeQuery();
			return resultSet.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	// For Validation
	public boolean checkStudentDeactive(int studentID) {
		try {
			PreparedStatement ps = connection.prepareStatement(
					"SELECT EXISTS (SELECT student_id FROM students WHERE student_id = ? and is_Active=0)");
			ps.setInt(1, studentID);
			ResultSet rs = ps.executeQuery();
			if (rs.next() && rs.getBoolean(1)) {
				System.out.println("Student is deactivated");

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean addStudent(Student student) {
		try {
			preparedStatement = connection.prepareStatement(
					"INSERT INTO students(student_id, student_rollno, student_fname, student_lname, student_mobno, is_Active) VALUES (?, ?, ?, ?, ?,?)");
			preparedStatement.setInt(1, student.getStudentID());
			preparedStatement.setInt(2, student.getStudentRollno());
			preparedStatement.setString(3, student.getStudentFname());
			preparedStatement.setString(4, student.getStudentLname());
			preparedStatement.setString(5, student.getStudentMobno());
			preparedStatement.setBoolean(6, true); // always true when inserting
			return preparedStatement.executeUpdate() > 0;
		} catch (SQLException e) {
			System.out.println("8888");
			e.printStackTrace();
		}
		return false;
	}

	public boolean addStudentProfile(StudentProfile studentprofile, Student student) {
		try {
			preparedStatement = connection.prepareStatement(
					"INSERT INTO students_profile(profile_id, student_address, student_age, student_email, student_gender, student_id) VALUES (?, ?, ?, ?, ?, ?)");
			preparedStatement.setInt(1, studentprofile.getProfileID());
			preparedStatement.setString(2, studentprofile.getStudentAddress());
			preparedStatement.setInt(3, studentprofile.getStudentAge());
			preparedStatement.setString(4, studentprofile.getStudentEmail());
			preparedStatement.setString(5, studentprofile.getStudentGender().name());
			preparedStatement.setInt(6, student.getStudentID());

			return preparedStatement.executeUpdate() > 0;

		} catch (SQLException e) {
			System.out.println("Error while adding student profile: " + e.getMessage());
		}
		return false;
	}

	// To show all active students
	public List<Student> showActiveStudents() {
		try {
			List<Student> students = new ArrayList<>();
			String query = "SELECT * FROM students WHERE is_Active = true;";
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Student student = new Student();
				student.setStudentID(resultSet.getInt("student_id"));
				student.setStudentRollno(resultSet.getInt("student_rollno"));
				student.setStudentFname(resultSet.getString("student_fname"));
				student.setStudentLname(resultSet.getString("student_lname"));
				student.setStudentMobno(resultSet.getString("student_mobno"));
				student.setActive(resultSet.getBoolean("is_Active")); // Don't forget this line

				students.add(student);
			}

//			System.out.println(students); 
			return students;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	// To Show all inactive students
	public List<Student> showInActiveStudents() {
		try {
			List<Student> students = new ArrayList<>();
			String query = "SELECT * FROM students where is_Active = false;";
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Student student = new Student();
				student.setStudentID(resultSet.getInt("student_id"));
				student.setStudentRollno(resultSet.getInt("student_rollno"));
				student.setStudentFname(resultSet.getString("student_fname"));
				student.setStudentLname(resultSet.getString("student_lname"));
				student.setStudentMobno(resultSet.getString("student_mobno"));
				students.add(student);
			}
//			System.out.println(students);
			return students;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<StudentProfile> showActiveStudentsProfile() {

		List<StudentProfile> studentprofiles = new ArrayList<>();
		String query = "select * from students_profile where student_id in (select student_id from students where is_Active=true);";
		try {
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				StudentProfile studentprofile = new StudentProfile();
				studentprofile.setProfileID(resultSet.getInt("profile_id"));
				studentprofile.setStudentAddress(resultSet.getString("student_address"));
				studentprofile.setStudentAge(resultSet.getInt("student_age"));
				studentprofile.setStudentEmail(resultSet.getString("student_email"));
				studentprofile.setStudentID(resultSet.getInt("student_id"));
				studentprofiles.add(studentprofile);

				String genderStr = resultSet.getString("student_gender");
				if (genderStr != null) {
					studentprofile.setStudentGender(StudentGender.valueOf(genderStr)); // If gender is stored as a
																						// string
				}
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}
//		System.out.println(studentprofiles);
		return studentprofiles;

	}

	public List<StudentProfile> showInActiveStudentsProfile() {

		List<StudentProfile> studentprofiles = new ArrayList<>();
		String query = "select * from students_profile where student_id in (select student_id from students where is_Active=false);";
		try {
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				StudentProfile studentprofile = new StudentProfile();
				studentprofile.setProfileID(resultSet.getInt("profile_id"));
				studentprofile.setStudentAddress(resultSet.getString("student_address"));
				studentprofile.setStudentAge(resultSet.getInt("student_age"));
				studentprofile.setStudentEmail(resultSet.getString("student_email"));
				studentprofile.setStudentID(resultSet.getInt("student_id"));
				studentprofiles.add(studentprofile);

				String genderStr = resultSet.getString("student_gender");
				if (genderStr != null) {
					studentprofile.setStudentGender(StudentGender.valueOf(genderStr)); // If gender is stored as a
																						// string
				}
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}
//		System.out.println(studentprofiles);
		return studentprofiles;

	}

	

	public void updateAStudentRollno(Student student) {
		try {

			preparedStatement = connection
					.prepareStatement("UPDATE students SET student_rollno=? WHERE student_id = ? and is_Active=true;");
			preparedStatement.setInt(1, student.getStudentRollno());
			preparedStatement.setInt(2, student.getStudentID());
//			preparedStatement.setBoolean(5, student.is_Active());

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void updateAStudentFname(Student student) {
		try {

			preparedStatement = connection
					.prepareStatement("UPDATE students SET student_fname=? WHERE student_id = ? and is_Active=true;");

			preparedStatement.setString(1, student.getStudentFname());
			preparedStatement.setInt(2, student.getStudentID());
//			preparedStatement.setBoolean(5, student.is_Active());

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void updateAStudentLname(Student student) {
		try {

			preparedStatement = connection
					.prepareStatement("UPDATE students SET student_lname=? WHERE student_id = ? and is_Active=true;");

			preparedStatement.setString(1, student.getStudentLname());
			preparedStatement.setInt(2, student.getStudentID());
//			preparedStatement.setBoolean(5, student.is_Active());

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void updateAStudentMobno(Student student) {
		try {

			preparedStatement = connection
					.prepareStatement("UPDATE students SET student_mobno=? WHERE student_id = ? and is_Active=true;");

			preparedStatement.setString(1, student.getStudentMobno());
			preparedStatement.setInt(2, student.getStudentID());
//			preparedStatement.setBoolean(5, student.is_Active());

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void updateAStudentAddress(StudentProfile studentprofile) {
		try {

			preparedStatement = connection
					.prepareStatement("UPDATE students_profile SET student_address=? WHERE profile_id = ?;");
			preparedStatement.setString(1, studentprofile.getStudentAddress());
			preparedStatement.setInt(2, studentprofile.getProfileID());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void updateAStudentAge(StudentProfile studentprofile) {
		try {

			preparedStatement = connection
					.prepareStatement("UPDATE students_profile SET student_age=? WHERE profile_id = ?;");

			preparedStatement.setInt(1, studentprofile.getStudentAge());
			preparedStatement.setInt(2, studentprofile.getProfileID());

			int rowsUpdated = preparedStatement.executeUpdate();
			System.out.println(rowsUpdated + " student(s) updated.");

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void updateAStudentGender(StudentProfile studentprofile) {
		try {

			preparedStatement = connection
					.prepareStatement("UPDATE students_profile SET student_gender=? WHERE profile_id = ?;");

			preparedStatement.setString(1, studentprofile.getStudentGender().name());
			preparedStatement.setInt(2, studentprofile.getProfileID());

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void updateAStudentEmail(StudentProfile studentprofile) {
		try {

			preparedStatement = connection
					.prepareStatement("UPDATE students_profile SET student_email=? WHERE profile_id = ?;");

			preparedStatement.setString(1, studentprofile.getStudentEmail());
			preparedStatement.setInt(2, studentprofile.getProfileID());

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public StudentProfile getStudentProfileById(int profileId) {
		try {
			StudentProfile profile = null;

			String query = "SELECT * FROM students_profile WHERE profile_id = ?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, profileId);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				// Safely parse gender string to match enum (e.g., "female" -> "Female")
				String genderStr = resultSet.getString("student_gender");
				StudentGender gender = StudentGender.Other; // default fallback

				try {
					genderStr = genderStr.substring(0, 1).toUpperCase() + genderStr.substring(1).toLowerCase();
					gender = StudentGender.valueOf(genderStr);
				} catch (Exception e) {
					System.out.println("⚠️ Invalid gender format found in DB. Defaulting to 'Other'.");
				}

				profile = new StudentProfile(resultSet.getInt("profile_id"), resultSet.getString("student_address"),
						resultSet.getInt("student_age"), resultSet.getString("student_email"), gender,
						resultSet.getInt("student_id"));
			}

			return profile;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	
	
	
	
	public void deleteStudentById(int studentId) {
		String query = "UPDATE students SET is_Active = false WHERE student_id = ?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setInt(1, studentId);

			int rowsUpdated = preparedStatement.executeUpdate();
			System.out.println("Rows updated: " + rowsUpdated);
			if (rowsUpdated > 0) {
				System.out.println(" Student with ID " + studentId + " was deactivated (soft deleted).");
			}
			if (!(rowsUpdated > 0)) {
				System.out.println(" No student found with ID: " + studentId);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean checkIfDeleted(int studentId) {
		String query = "SELECT student_id from students where is_Active=0 and student_id=?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        preparedStatement.setInt(1, studentId);
	        try (ResultSet resultSet = preparedStatement.executeQuery()) {
	            return resultSet.next(); // if a row exists, student is soft-deleted
	        }
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
		
	}

	public void assignCourseToStudent(int studentId, int courseId) {
		String query = "INSERT INTO student_course (student_id, course_id) VALUES (?, ?)";
		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setInt(1, studentId);
			preparedStatement.setInt(2, courseId);

			int rowsInserted = preparedStatement.executeUpdate();
			if (rowsInserted > 0) {
				System.out.println(" Course assigned successfully to student with ID: " + studentId);
			} else {
				System.out.println(" Course assignment failed.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Student> ShowAssignedCourses() {
		try {
			List<Student> studentcourses = new ArrayList<>();
			String query = """
					    SELECT
					        s.student_id,
					        s.student_rollno,
					        s.student_fname,
					        s.student_lname,
					        GROUP_CONCAT(c.course_name SEPARATOR ', ') AS course_names
					    FROM
					        students s
					    JOIN
					        student_course sc ON s.student_id = sc.student_id
					    JOIN
					        courses c ON sc.course_id = c.course_id
					    GROUP BY
					        s.student_id, s.student_rollno, s.student_fname, s.student_lname
					""";

			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Student info = new Student();
				info.setStudentID(resultSet.getInt("student_id"));
				info.setStudentRollno(resultSet.getInt("student_rollno"));
				info.setStudentFname(resultSet.getString("student_fname"));
				info.setStudentLname(resultSet.getString("student_lname"));
				info.setCourseNames(resultSet.getString("course_names")); // Use of added field

				studentcourses.add(info);
			}

			return studentcourses;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Student readAStudent(int studentID) {
	
			try {
				  System.out.println("Querying student with ID: " + studentID); // Debug

			        preparedStatement = connection.prepareStatement("SELECT * FROM students WHERE student_id = ?");
			        preparedStatement.setInt(1, studentID);
			        ResultSet rs = preparedStatement.executeQuery();

			        if (rs.next()) {
			            
			            return new Student(
			                rs.getInt("student_id"),
			                rs.getInt("student_rollno"),
			                rs.getString("student_fname"),
			                rs.getString("student_lname"),
			                rs.getString("student_mobno"),
			                rs.getBoolean("is_Active")
			            );
			        } 
			        

			    } catch (SQLException e) {
			        e.printStackTrace();
			    }

			    return null;

	}
	
	public Student getStudentByName(String studentFname, String studentLname) {
		try {
			 preparedStatement = connection.prepareStatement(
			            "SELECT * FROM students WHERE student_fname = ? AND student_lname = ?"
			        );
			        preparedStatement.setString(1, studentFname);
			        preparedStatement.setString(2, studentLname);

			        ResultSet rs = preparedStatement.executeQuery();

			        if (rs.next()) {
			            return new Student(
			                rs.getInt("student_id"),
			                rs.getInt("student_rollno"),
			                rs.getString("student_fname"),
			                rs.getString("student_lname"),
			                rs.getString("student_mobno"),
			                rs.getBoolean("is_Active"));
			             
			        }
			        
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
