package com.aurionpro.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.aurionpro.database.Database;
import com.aurionpro.model.Course;
import com.aurionpro.model.Subject;

public class CourseDao {
	private Connection connection = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	private PreparedStatement preparedStatement = null;
	
	public CourseDao() {
		connection = Database.getInstance().getConnection();
	}
	
	private Statement getStatement() throws SQLException {
		if(statement == null)
			statement = connection.createStatement();
		
		return statement;
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
	
	public boolean checkIfCourseExist(String courseName) {
		boolean check = false;
		try {
			statement = getStatement();
			resultSet = statement.executeQuery("select * from courses where course_name = '" + courseName + "'");
			return resultSet.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return check;
	}
	
	public boolean checkIfSubjectExist(int subjectId) {
		boolean check = false;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select * from subjects where subject_id = " + subjectId);
			return resultSet.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return check;
	}
	
	public boolean checkIfSubjectExist(String subjectName) {
		boolean check = false;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select * from subjects where subject_name = '" + subjectName +"'");
			return resultSet.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return check;
	}
	
	public List<Course> showAllActiveCourses() {
		List<Course> courses = new ArrayList<>();
		try {	
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select course_id, course_name, course_fee, academic_year"
					+ ", duration, description from courses where is_active = true");
			
			if(resultSet.next()) {
				System.out.printf("|%-10s | %-50s | %-10s | %-25s | %-10s | %-60s|\n","Course ID","Course Name","Course Fee","Year of Establishment",
						"Duration","Description");
				System.out.println("|-----------+----------------------------------------------------+------------+---------------------------+"
						+ "------------+-------------------------------------------------------------|");
				System.out.printf("|%-10s | %-50s | %-10s | %-25s | %-10s | %-60s|\n",resultSet.getInt("course_id"),
						resultSet.getString("course_name"),resultSet.getDouble("course_fee"),resultSet.getString("academic_year"),
						resultSet.getString("duration"),resultSet.getString("description"));
				courses.add(new Course(resultSet.getInt(1), 
						resultSet.getString(2), 
						resultSet.getDouble(3), 
						resultSet.getString(4),
						resultSet.getString(5),
						resultSet.getString(6)));
				while(resultSet.next()) {
					System.out.printf("|%-10s | %-50s | %-10s | %-25s | %-10s | %-60s|\n",resultSet.getInt("course_id"),
							resultSet.getString("course_name"),resultSet.getDouble("course_fee"),resultSet.getString("academic_year"),
							resultSet.getString("duration"),resultSet.getString("description"));
					courses.add(new Course(resultSet.getInt(1), 
							resultSet.getString(2), 
							resultSet.getDouble(3), 
							resultSet.getString(4),
							resultSet.getString(5),
							resultSet.getString(6)));
				}
			}
			else {
				System.out.println("No course active.");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getLocalizedMessage());
		}
		return courses;
	}
	
	public List<Course> showAllInactiveCourses() {
		List<Course> courses = new ArrayList<>();
		try {	
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select * from courses where is_active = false");
			
			if(resultSet.next()) {
				System.out.printf("|%-10s | %-50s | %-10s | %-25s | %-10s | %-60s|\n","Course ID","Course Name","Course Fee","Year of Establishment","Duration","Description");
				System.out.println("|-----------+----------------------------------------------------+------------+---------------------------+"
						+ "------------+-------------------------------------------------------------|");
				System.out.printf("|%-10s | %-50s | %-10s | %-25s | %-10s | %-60s|\n",resultSet.getInt("course_id"),
						resultSet.getString("course_name"),resultSet.getDouble("course_fee"),resultSet.getString("academic_year"),
						resultSet.getString("duration"),resultSet.getString("description"));
				courses.add(new Course(resultSet.getInt(1), 
						resultSet.getString(2), 
						resultSet.getDouble(3), 
						resultSet.getString(4),
						resultSet.getString(5),
						resultSet.getString(6)));
				while(resultSet.next()) {
					System.out.printf("|%-10s | %-50s | %-10s | %-25s | %-10s | %-60s|\n",resultSet.getInt("course_id"),
							resultSet.getString("course_name"),resultSet.getDouble("course_fee"),resultSet.getString("academic_year"),
							resultSet.getString("duration"),resultSet.getString("description"));
					courses.add(new Course(resultSet.getInt(1), 
							resultSet.getString(2), 
							resultSet.getDouble(3), 
							resultSet.getString(4),
							resultSet.getString(5),
							resultSet.getString(6)));
				}
			}
			else {
				System.out.println("No course inactive.");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getLocalizedMessage());
		}
		return courses;
	}
	
	public void showAllActiveSubjects() {
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select * from subjects where is_active = true");
			if(!resultSet.next()) {
				System.out.println("No subjects exist!!!");
				return;
			}
			System.out.printf("|%-10s | %-30s|\n","Subject ID","Subject Name");
			System.out.println("|-----------+-------------------------------|");
			System.out.printf("|%-10s | %-30s|\n",resultSet.getInt(1),resultSet.getString(2));
			
			while(resultSet.next()) {
				System.out.printf("|%-10s | %-30s|\n",resultSet.getInt(1),resultSet.getString(2));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void showAllInactiveSubjects() {
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select * from subjects where is_active = false");
			if(!resultSet.next()) {
				System.out.println("No subjects exist!!!");
				return;
			}
			System.out.printf("|%-10s | %-30s|\n","Subject ID","Subject Name");
			System.out.println("|-----------+-------------------------------|");
			System.out.printf("|%-10s | %-30s|\n",resultSet.getInt(1),resultSet.getString(2),resultSet.getBoolean(3));
			
			while(resultSet.next()) {
				System.out.printf("|%-10s | %-30s|\n",resultSet.getInt(1),resultSet.getString(2),resultSet.getBoolean(3));
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void addCourse(Course course) {
		try {
			statement = getStatement();
			if(checkIfCourseExist(course.getCourseId())) {
				System.out.println("Given course id : "+ course.getCourseId() + " exist into courses.");
				return;
			}
			if(checkIfCourseExist(course.getCourseName())) {
				System.out.println(course.getCourseName() + " is exist in courses.");
				return;
			}
			
			preparedStatement = connection.prepareStatement("insert into courses values (?,?,?,?,?,?,?)");
			preparedStatement.setInt(1, course.getCourseId());
			preparedStatement.setString(2, course.getCourseName());
			preparedStatement.setBoolean(3, true);
			preparedStatement.setDouble(4, course.getCourseFee());
			preparedStatement.setString(5, course.getAcademicYear());
			preparedStatement.setString(6, course.getDuration());
			preparedStatement.setString(7, course.getDescription());
			int updates = preparedStatement.executeUpdate();
			System.out.println("✔ New course added successfully.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void addSubject(Subject subject) {
		if(checkIfSubjectExist(subject.getSubjectId())) {
			System.out.println("Given subject id exist.");
			return;
		}
		if(checkIfSubjectExist(subject.getSubjectName())) {
			System.out.println("Given subject name exist.");
			return;
		}
		
		try {
			preparedStatement = connection.prepareStatement("insert into subjects values (?,?,?)");
			preparedStatement.setInt(1, subject.getSubjectId());
			preparedStatement.setString(2, subject.getSubjectName());
			preparedStatement.setBoolean(3, true);
			
			int updates = preparedStatement.executeUpdate();
			System.out.println("Subject added successful.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addSubjectIntoCourse(int courseId, int subjectId) {
		try {
			if(!checkIfCourseExist(courseId)) {
				System.out.println("No course id : " + courseId + " exist.");
				return;
			}
			
			if(!checkIfSubjectExist(subjectId)) {
				System.out.println("No subject id : " + subjectId + " exist.");
				return;
			}
			
			String courseSubjectId = "C";
			courseSubjectId += String.valueOf(courseId);
			courseSubjectId += "S";
			courseSubjectId += String.valueOf(subjectId);
			
			statement = getStatement();
			
			resultSet = statement.executeQuery("select * from course_subject where course_subject_id = '" + courseSubjectId +"'");
			
			if(resultSet.next()) {
				System.out.println("Already into the course.");
				return;
			}
			
			preparedStatement = connection.prepareStatement("insert into course_subject values (?,?,?,?)");
			preparedStatement.setString(1, courseSubjectId);
			preparedStatement.setInt(2, courseId);
			preparedStatement.setInt(3, subjectId);
			preparedStatement.setBoolean(4, true);
			
			int updates = preparedStatement.executeUpdate();
			System.out.println("Insertion successfully!!");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void addNewSubjectIntoCourse(int courseId, Subject subject) {
		
		try {
			statement = connection.createStatement();
			
			if(!checkIfCourseExist(courseId)) {
				System.out.println("No course exist with course id : " + courseId);
				return;
			}
			
			String courseSubjectId = "C";
			courseSubjectId += String.valueOf(courseId);
			courseSubjectId += "S";
			courseSubjectId += String.valueOf(subject.getSubjectId());
			
			resultSet = statement.executeQuery("select * from course_subject where course_subject_id = '" + courseSubjectId +"'");
			if(resultSet.next()) {
				System.out.println("Given subject exist into the course.");
				return;
			}

			if(checkIfSubjectExist(subject.getSubjectId())) {
				preparedStatement = connection.prepareStatement("insert into course_subject values(?,?,?,?)");
				preparedStatement.setString(1, courseSubjectId);
				preparedStatement.setInt(2, courseId);
				preparedStatement.setInt(3, subject.getSubjectId());
				preparedStatement.setBoolean(4, true);
					
				int updates = preparedStatement.executeUpdate();
					
				System.out.println("Subject Added into the course successfully.");
			}
			else {
				preparedStatement = connection.prepareStatement("insert into subjects values (?,?)");
				preparedStatement.setInt(1, subject.getSubjectId());
				preparedStatement.setString(2, subject.getSubjectName());
				
				preparedStatement.executeUpdate();
				
				preparedStatement = connection.prepareStatement("insert into course_subject values(?,?,?)");
				preparedStatement.setString(1, courseSubjectId);
				preparedStatement.setInt(2, courseId);
				preparedStatement.setInt(3, subject.getSubjectId());
				
				System.out.println("Subject Added into the course successfully.");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void deleteCourse(int courseId) {
		try {
			if(!checkIfCourseExist(courseId)) {
				System.out.println("Sorry, given course id does not exist.");
				return;
			}
			
			preparedStatement = connection.prepareStatement("update courses set is_active = false where course_id = " + courseId);
			
			int updates = preparedStatement.executeUpdate();

			preparedStatement = connection.prepareStatement("update course_subject set is_active = false where course_id = " + courseId);
			
			updates = preparedStatement.executeUpdate();
			
			System.out.println("Deletion successful.");
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void deleteSubject(int subjectId) {
		try {
			if(!checkIfSubjectExist(subjectId)) {
				System.out.println("Sorry, given subject id does not exist.");
				return;
			}
			
			preparedStatement = connection.prepareStatement("update subjects set is_active = false where subject_id = " + subjectId);
			
			int updates = preparedStatement.executeUpdate();

			preparedStatement = connection.prepareStatement("update course_subject set is_active = false where subject_id = " + subjectId);
			
			updates = preparedStatement.executeUpdate();
			
			System.out.println("Deletion successful.");
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void showAllActiveSubjectsOfCourse(int courseId) {
		
		if(!checkIfCourseExist(courseId)) {
			System.out.println("No course exist with given course id : " + courseId);
			return;
		}
		
		List<Subject> subjects = new ArrayList<>();
		
		try {
			statement = connection.createStatement();
			
			resultSet = statement.executeQuery("select s.subject_id, s.subject_name from subjects join s"
					+ "course_subject c "
					+ "on s.subject_id = c.subject_id and c.course_id = " + courseId + " and s.is_active = " + true);
			if(!resultSet.next()) {
				System.out.println("Zero subjects into the course.");
				return;
			}
			
			System.out.println(resultSet.getInt(1) + "\t" + resultSet.getString(2));
			subjects.add(new Subject(resultSet.getInt(1),resultSet.getString(2)));
			
			while(resultSet.next()) {
				System.out.println(resultSet.getInt(1) + "\t" + resultSet.getString(2));
				subjects.add(new Subject(resultSet.getInt(1),resultSet.getString(2)));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void showAllSubjectsOfCourse(int courseId) {
		if(!checkIfCourseExist(courseId)) {
			System.out.println("No course exist with given course id : " + courseId);
			return;
		}
		
		List<Subject> subjects = new ArrayList<>();
		
		try {
			statement = connection.createStatement();
			
			resultSet = statement.executeQuery("select s.subject_id, s.subject_name from subjects s join "
					+ " course_subject c "
					+ " on s.subject_id = c.subject_id and c.course_id = " + courseId);
			
			if(!resultSet.next()) {
				System.out.println("Zero subjects into the course.");
				return;
			}
			
			System.out.println(resultSet.getInt(1) + "\t" + resultSet.getString(2));
			subjects.add(new Subject(resultSet.getInt(1),resultSet.getString(2)));
			
			while(resultSet.next()) {
				System.out.println(resultSet.getInt(1) + "\t" + resultSet.getString(2));
				subjects.add(new Subject(resultSet.getInt(1),resultSet.getString(2)));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void showCourseById(int courseId) {
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select * from course where course_id = " + courseId);
			if(!resultSet.next()) {
				System.out.println("No course exist with id : " + courseId);
				return;
			}
			System.out.printf("%-20s %-20s\n","Course Id","Course Name");
			System.out.printf("%-20s %-20s\n",resultSet.getInt(1),resultSet.getString(2));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public double getCourseFee(int course_id) {
		try {
			if(!checkIfCourseExist(course_id)) {
				System.out.println("XXXXX Wrong course id !!!!! XXXXX");
				return 0; 
			}
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select course_fee from courses where course_id = " + course_id);
			if(resultSet.next()) {
				double courseFee =  resultSet.getDouble(1);
				return courseFee;
			}
			return 0;
		} catch (SQLException e) {
			System.out.println("Error course Dao line : 377");
			e.printStackTrace();
		}
		return 0;
	}

}