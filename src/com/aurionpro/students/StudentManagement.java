package com.aurionpro.students;

import java.sql.Connection;	
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.aurionpro.database.*;
import com.aurionpro.students.*;

public class StudentManagement {

	private Connection connection;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;
	int choice;

	public StudentManagement() {
		connection = Database.getInstance().getConnection();
	}
	
	//For validation part
	private boolean checkIfStudentExists(int studentID) throws SQLException {
	    preparedStatement = connection.prepareStatement("SELECT student_id FROM students WHERE student_id = ?");
	    preparedStatement.setInt(1, studentID);
	    resultSet = preparedStatement.executeQuery();
	    return resultSet.next();
	}

	//For validation part
	private boolean checkIfProfileExists(int profileID) throws SQLException {
	    preparedStatement = connection.prepareStatement("SELECT profile_id FROM students_profile WHERE profile_id = ?");
	    preparedStatement.setInt(1, profileID);
	    resultSet = preparedStatement.executeQuery();
	    return resultSet.next();
	}

	

	public void addNewStudent(Scanner scanner) {
	    System.out.println(" ➕ Let's get the basic student details added!");

	    try {
	        // 🔹 Validate student ID (must be int and unique)
	        System.out.print("Enter the student ID: ");
	        if (!scanner.hasNextInt()) {
	            System.out.println("❌ Invalid input. Student ID must be a number.");
	            scanner.next();
	            return;
	        }
	        int studentID = scanner.nextInt();

	        if (checkIfStudentExists(studentID)) {
	            System.out.println("❌ Student ID already exists. Try a different one.");
	            return;
	        }

	        // 🔹 Validate roll no
	        System.out.print("Enter the student roll no: ");
	        if (!scanner.hasNextInt()) {
	            System.out.println("❌ Invalid input. Roll No must be a number.");
	            scanner.next();
	            return;
	        }
	        int studentRollno = scanner.nextInt();

	        scanner.nextLine(); // consume newline

	        System.out.print("Enter the student first name: ");
	        String studentFname = scanner.nextLine();

	        System.out.print("Enter the student last name: ");
	        String studentLname = scanner.nextLine();

	        // 🔹 Validate mobile number format (10-digit)
	        System.out.print("Enter the student Mobile No: ");
	        String studentMobno = scanner.nextLine();
	        if (!studentMobno.matches("\\d{10}")) {
	            System.out.println("❌ Mobile number must be exactly 10 digits.");
	            return;
	        }

	        // 🔹 Validate status
	        System.out.print("Enter the Student status (active / inactive): ");
	        String studentStatus = scanner.nextLine().toLowerCase();
	        if (!studentStatus.equals("active") && !studentStatus.equals("inactive")) {
	            System.out.println("❌ Status must be either 'active' or 'inactive'.");
	            return;
	        }

	        // 🔹 Insert into students table
	        preparedStatement = connection.prepareStatement(
	                "INSERT INTO students(student_id, student_rollno, student_fname, student_lname, student_mobno, student_status) VALUES (?, ?, ?, ?, ?, ?)");
	        preparedStatement.setInt(1, studentID);
	        preparedStatement.setInt(2, studentRollno);
	        preparedStatement.setString(3, studentFname);
	        preparedStatement.setString(4, studentLname);
	        preparedStatement.setString(5, studentMobno);
	        preparedStatement.setString(6, studentStatus);
	        preparedStatement.executeUpdate();
	        System.out.println("✅ Student basic details added successfully.\n-----------------------------------------------------------");

	        // 🔹 Add profile now
	        System.out.println(" ➕ Now on the way to make the student profile");

	        System.out.print("Enter the profile ID: ");
	        if (!scanner.hasNextInt()) {
	            System.out.println("❌ Invalid input. Profile ID must be a number.");
	            scanner.next();
	            return;
	        }
	        int profileID = scanner.nextInt();

	        if (checkIfProfileExists(profileID)) {
	            System.out.println("❌ Profile ID already exists. Try a different one.");
	            return;
	        }

	        scanner.nextLine(); // consume newline
	        System.out.print("Enter the student Address (city): ");
	        String studentAddress = scanner.nextLine();

	        System.out.print("Enter the student Age: ");
	        if (!scanner.hasNextInt()) {
	            System.out.println("❌ Invalid input. Age must be a number.");
	            scanner.next();
	            return;
	        }
	        int studentAge = scanner.nextInt();

	        scanner.nextLine(); // consume newline
	        System.out.print("Enter the student Email ID: ");
	        String studentEmail = scanner.nextLine();
	        if (!studentEmail.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
	            System.out.println("❌ Invalid email format.");
	            return;
	        }

	        System.out.print("Enter the student Gender (Male / Female / Other): ");
	        String studentGender = scanner.nextLine().toLowerCase();
	        if (!studentGender.equals("male") && !studentGender.equals("female") && !studentGender.equals("other")) {
	            System.out.println("❌ Gender must be 'Male', 'Female' or 'Other'.");
	            return;
	        }

	        // 🔹 Insert into students_profile table
	        preparedStatement = connection.prepareStatement(
	                "INSERT INTO students_profile(profile_id, student_address, student_age, student_email, student_gender, student_id) VALUES (?, ?, ?, ?, ?, ?)");
	        preparedStatement.setInt(1, profileID);
	        preparedStatement.setString(2, studentAddress);
	        preparedStatement.setInt(3, studentAge);
	        preparedStatement.setString(4, studentEmail);
	        preparedStatement.setString(5, studentGender);
	        preparedStatement.setInt(6, studentID);
	        preparedStatement.executeUpdate();

	        System.out.println("✅ Student profile created successfully.\n------------------------------------------------------------");

	    } catch (SQLException e) {
	        System.out.println("❌ Error while adding student: " + e.getMessage());
	        e.printStackTrace();
	    }
	}


	public void showAllStudents() {
		try {
			preparedStatement = connection.prepareStatement("SELECT * FROM students");
			resultSet = preparedStatement.executeQuery();

			System.out.println("\n📋 Displaying All Students' Records \n");

			System.out.println(
					"==========================================================================================");
			System.out.printf("| %-8s | %-12s | %-15s | %-15s | %-13s | %-8s |\n", "ID", "Roll No", "First Name",
					"Last Name", "Mobile No", "Status");
			System.out.println(
					"------------------------------------------------------------------------------------------");

			while (resultSet.next()) {
				System.out.printf("| %-8d | %-12d | %-15s | %-15s | %-13s | %-8s |\n", resultSet.getInt("student_id"),
						resultSet.getInt("student_rollno"), resultSet.getString("student_fname"),
						resultSet.getString("student_lname"), resultSet.getString("student_mobno"),
						resultSet.getString("student_status"));
			}
			System.out.println(
					"==========================================================================================");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void searchAStudent(Scanner scanner) {
	    try {
	        System.out.println("Enter the Student ID of the student you want to search 🔍");

	        // Check if the input is a valid integer
	        if (!scanner.hasNextInt()) {
	            System.out.println("❌ Invalid input. Student ID must be a number.");
	            scanner.next(); // consume invalid input
	            return;
	        }

	        int studentID1 = scanner.nextInt();

	        // Check if the student exists before showing
	        preparedStatement = connection.prepareStatement("SELECT * FROM students WHERE student_id=?");
	        preparedStatement.setInt(1, studentID1);
	        resultSet = preparedStatement.executeQuery();

	        if (!resultSet.isBeforeFirst()) {
	            System.out.println("⚠️ No student found with ID: " + studentID1);
	            return;
	        }

	        System.out.println("\n🔍 Searching student ID: " + studentID1 + " from student records...\n");
	        System.out.println(
	                "==========================================================================================");
	        System.out.printf("| %-8s | %-12s | %-15s | %-15s | %-13s | %-8s |\n", "ID", "Roll No", "First Name",
	                "Last Name", "Mobile No", "Status");
	        System.out.println(
	                "------------------------------------------------------------------------------------------");

	        while (resultSet.next()) {
	            System.out.printf("| %-8d | %-12d | %-15s | %-15s | %-13s | %-8s |\n",
	                    resultSet.getInt("student_id"),
	                    resultSet.getInt("student_rollno"),
	                    resultSet.getString("student_fname"),
	                    resultSet.getString("student_lname"),
	                    resultSet.getString("student_mobno"),
	                    resultSet.getString("student_status"));
	        }

	        System.out.println(
	                "==========================================================================================");

	    } catch (SQLException e) {
	        System.out.println("❌ Error while searching student: " + e.getMessage());
	        e.printStackTrace();
	    }
	}


	public void updateAStudent(Scanner scanner) {
	    try {
	        System.out.println("Enter the student id of the student you want to update: ");
	        int studentID1 = scanner.nextInt();

	        // Validate if student exists
	        preparedStatement = connection.prepareStatement("SELECT 1 FROM students WHERE student_id = ?");
	        preparedStatement.setInt(1, studentID1);
	        resultSet = preparedStatement.executeQuery();

	        if (!resultSet.next()) {
	            System.out.println("❌ Student with ID " + studentID1 + " does not exist.");
	            return;
	        }

	        while (true) {
	            System.out.println("\n 🔄 Update the details of the student you want! ");
	            System.out.println("1. First Name");
	            System.out.println("2. Last Name");
	            System.out.println("3. Mobile No");
	            System.out.println("4. Status");
	            System.out.println("5. Exit");
	            System.out.println(" 🔄 Enter the option which you want to update: ");
	            int choice = scanner.nextInt();

	            switch (choice) {
	                case 1:
	                    System.out.println("Enter the First Name you want to update:");
	                    String studentFname = scanner.next();
	                    if (studentFname.trim().isEmpty()) {
	                        System.out.println("❌ First name cannot be empty.");
	                        break;
	                    }
	                    preparedStatement = connection.prepareStatement(
	                            "UPDATE students SET student_fname=? WHERE student_id = ?");
	                    preparedStatement.setString(1, studentFname);
	                    preparedStatement.setInt(2, studentID1);
	                    preparedStatement.executeUpdate();
	                    break;

	                case 2:
	                    System.out.println("Enter the Last Name you want to update:");
	                    String studentLname = scanner.next();
	                    if (studentLname.trim().isEmpty()) {
	                        System.out.println("❌ Last name cannot be empty.");
	                        break;
	                    }
	                    preparedStatement = connection.prepareStatement(
	                            "UPDATE students SET student_lname=? WHERE student_id = ?");
	                    preparedStatement.setString(1, studentLname);
	                    preparedStatement.setInt(2, studentID1);
	                    preparedStatement.executeUpdate();
	                    break;

	                case 3:
	                    System.out.println("Enter the Mobile No you want to update:");
	                    String studentMobno = scanner.next();
	                    if (!studentMobno.matches("\\d{10}")) {
	                        System.out.println("❌ Mobile number must be exactly 10 digits.");
	                        break;
	                    }
	                    preparedStatement = connection.prepareStatement(
	                            "UPDATE students SET student_mobno=? WHERE student_id = ?");
	                    preparedStatement.setString(1, studentMobno);
	                    preparedStatement.setInt(2, studentID1);
	                    preparedStatement.executeUpdate();
	                    break;

	                case 4:
	                    System.out.println("Enter the student status you want to update (active / inactive):");
	                    String studentStatus = scanner.next().toLowerCase();
	                    if (!studentStatus.equals("active") && !studentStatus.equals("inactive")) {
	                        System.out.println("❌ Status must be either 'active' or 'inactive'.");
	                        break;
	                    }
	                    preparedStatement = connection.prepareStatement(
	                            "UPDATE students SET student_status=? WHERE student_id = ?");
	                    preparedStatement.setString(1, studentStatus);
	                    preparedStatement.setInt(2, studentID1);
	                    preparedStatement.executeUpdate();
	                    break;

	                case 5:
	                    System.out.println("🚪 Exiting the update option!");
	                    return;

	                default:
	                    System.out.println("❌ Please enter a valid option (1-5).");
	            }

	            System.out.println("✅ Student details updated successfully!");
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}


	public void updateAProfileStudent(Scanner scanner) {
	    try {
	        System.out.print("🔍 Enter the Profile ID of the student you want to update: ");
	        if (!scanner.hasNextInt()) {
	            System.out.println("❌ Invalid input. Profile ID must be numeric.");
	            scanner.next(); // clear invalid input
	            return;
	        }

	        int profileID1 = scanner.nextInt();

	        // Check if profile exists
	        preparedStatement = connection.prepareStatement("SELECT 1 FROM students_profile WHERE profile_id = ?");
	        preparedStatement.setInt(1, profileID1);
	        ResultSet resultSet = preparedStatement.executeQuery();

	        if (!resultSet.next()) {
	            System.out.println("❌ Profile ID " + profileID1 + " not found in the database.");
	            return;
	        }

	        while (true) {
	            System.out.println("\n🔄 Update the details of the student profile:");
	            System.out.println("1. Student Address");
	            System.out.println("2. Student Age");
	            System.out.println("3. Student Email");
	            System.out.println("4. Student Gender");
	            System.out.println("5. Exit");
	            System.out.print("Enter your choice: ");

	            if (!scanner.hasNextInt()) {
	                System.out.println("❌ Invalid choice. Please enter a number between 1 and 5.");
	                scanner.next(); // clear invalid input
	                continue;
	            }

	            int choice = scanner.nextInt();
	            scanner.nextLine(); // consume newline

	            switch (choice) {
	                case 1:
	                    System.out.print("Enter the new Student Address: ");
	                    String studentAddress = scanner.nextLine().trim();
	                    if (studentAddress.isEmpty()) {
	                        System.out.println("❌ Address cannot be empty.");
	                        break;
	                    }
	                    preparedStatement = connection.prepareStatement(
	                        "UPDATE students_profile SET student_address=? WHERE profile_id=?");
	                    preparedStatement.setString(1, studentAddress);
	                    preparedStatement.setInt(2, profileID1);
	                    preparedStatement.executeUpdate();
	                    break;

	                case 2:
	                    System.out.print("Enter the new Student Age: ");
	                    if (!scanner.hasNextInt()) {
	                        System.out.println("❌ Age must be a numeric value.");
	                        scanner.next(); // clear invalid input
	                        break;
	                    }
	                    int studentAge = scanner.nextInt();
	                    if (studentAge <= 0 || studentAge > 120) {
	                        System.out.println("❌ Please enter a valid age between 1 and 120.");
	                        break;
	                    }
	                    preparedStatement = connection.prepareStatement(
	                        "UPDATE students_profile SET student_age=? WHERE profile_id=?");
	                    preparedStatement.setInt(1, studentAge);
	                    preparedStatement.setInt(2, profileID1);
	                    preparedStatement.executeUpdate();
	                    break;

	                case 3:
	                    System.out.print("Enter the new Student Email: ");
	                    String studentEmail = scanner.nextLine().trim();
	                    if (!studentEmail.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) {
	                        System.out.println("❌ Invalid email format.");
	                        break;
	                    }
	                    preparedStatement = connection.prepareStatement(
	                        "UPDATE students_profile SET student_email=? WHERE profile_id=?");
	                    preparedStatement.setString(1, studentEmail);
	                    preparedStatement.setInt(2, profileID1);
	                    preparedStatement.executeUpdate();
	                    break;

	                case 4:
	                    System.out.print("Enter the new Student Gender (Male/Female/Other): ");
	                    String studentGender = scanner.nextLine().trim().toLowerCase();
	                    if (!studentGender.equals("male") && !studentGender.equals("female") && !studentGender.equals("other")) {
	                        System.out.println("❌ Gender must be Male, Female, or Other.");
	                        break;
	                    }
	                    preparedStatement = connection.prepareStatement(
	                        "UPDATE students_profile SET student_gender=? WHERE profile_id=?");
	                    preparedStatement.setString(1, capitalize(studentGender));
	                    preparedStatement.setInt(2, profileID1);
	                    preparedStatement.executeUpdate();
	                    break;

	                case 5:
	                    System.out.println("👋 Exiting update menu.");
	                    return;

	                default:
	                    System.out.println("❌ Invalid choice. Please enter a number between 1 and 5.");
	            }

	            System.out.println("✅ Student profile updated successfully!");
	        }

	    } catch (SQLException e) {
	        System.out.println("❌ Database error: " + e.getMessage());
	        e.printStackTrace();
	    }
	}

	// Helper to capitalize first letter (e.g., "female" → "Female")
	private String capitalize(String input) {
	    if (input == null || input.isEmpty()) return input;
	    return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
	}

	public void searchAProfile(Scanner scanner) {
	    try {
	        System.out.print("🔍 Enter the Profile ID of the student you want to search: ");
	        if (!scanner.hasNextInt()) {
	            System.out.println("❌ Invalid input. Please enter a numeric Profile ID.");
	            scanner.next(); // clear invalid input
	            return;
	        }

	        int profileID = scanner.nextInt();

	        // Query to fetch profile
	        preparedStatement = connection.prepareStatement("SELECT * FROM students_profile WHERE profile_id = ?");
	        preparedStatement.setInt(1, profileID);
	        resultSet = preparedStatement.executeQuery();

	        if (!resultSet.isBeforeFirst()) {
	            System.out.println("❌ No profile found with Profile ID: " + profileID);
	            return;
	        }

	        System.out.println("\n🔍 Searching for student profile ID: " + profileID + "\n");

	        System.out.println(
	            "=============================================================================================================");
	        System.out.printf("| %-10s | %-20s | %-10s | %-25s | %-15s | %-10s |\n",
	            "Profile ID", "Student Address", "Student Age", "Student Email", "Gender", "Student ID");
	        System.out.println(
	            "-------------------------------------------------------------------------------------------------------------");

	        while (resultSet.next()) {
	            System.out.printf("| %-10d | %-20s | %-10d | %-25s | %-15s | %-10d |\n",
	                resultSet.getInt("profile_id"),
	                resultSet.getString("student_address"),
	                resultSet.getInt("student_age"),
	                resultSet.getString("student_email"),
	                resultSet.getString("student_gender"),
	                resultSet.getInt("student_id"));
	        }

	        System.out.println(
	            "=============================================================================================================");

	    } catch (SQLException e) {
	        System.out.println("❌ Database error occurred: " + e.getMessage());
	        e.printStackTrace();
	    } catch (InputMismatchException e) {
	        System.out.println("❌ Invalid input. Please enter a numeric value.");
	        scanner.next(); // clear the invalid input
	    }
	}


	public void showALLProfile() {

		try {
			System.out.println("\n📋 Displaying All Student Profiles\n");

			preparedStatement = connection.prepareStatement("SELECT * FROM students_profile");
			resultSet = preparedStatement.executeQuery();

			System.out.println(
					"===============================================================================================================");
			System.out.printf("| %-10s | %-20s | %-12s | %-25s | %-15s | %-10s |\n", "Profile ID", "Student Address",
					"Student Age", "Student Email", "Student Gender", "Student ID");
			System.out.println(
					"---------------------------------------------------------------------------------------------------------------");

			while (resultSet.next()) {
				System.out.printf("| %-10d | %-20s | %-12d | %-25s | %-15s | %-10d |\n", resultSet.getInt("profile_id"),
						resultSet.getString("student_address"), resultSet.getInt("student_age"),
						resultSet.getString("student_email"), resultSet.getString("student_gender"),
						resultSet.getInt("student_id"));
			}

			System.out.println(
					"===============================================================================================================");

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void deleteAStudent(Scanner scanner) {
	    try {
	        System.out.print("✖️ Enter the student ID which you want to delete: ");
	        if (!scanner.hasNextInt()) {
	            System.out.println("❌ Invalid input. Please enter a valid numeric Student ID.");
	            scanner.next(); // clear invalid input
	            return;
	        }

	        int studentID = scanner.nextInt();

	        // Check if student exists in students table
	        preparedStatement = connection.prepareStatement("SELECT 1 FROM students WHERE student_id = ?");
	        preparedStatement.setInt(1, studentID);
	        ResultSet resultSet = preparedStatement.executeQuery();

	        if (!resultSet.next()) {
	            System.out.println("❌ Student with ID " + studentID + " does not exist in the database.");
	            return;
	        }

	        // Deleting from students_profile table first to avoid FK constraint issues
	        System.out.println("✖️ Deleting the record from Students Profile Records...");
	        preparedStatement = connection.prepareStatement("DELETE FROM students_profile WHERE student_id = ?");
	        preparedStatement.setInt(1, studentID);
	        int profileRows = preparedStatement.executeUpdate();

	        // Then delete from students table
	        System.out.println("✖️ Deleting the record from Students table...");
	        preparedStatement = connection.prepareStatement("DELETE FROM students WHERE student_id = ?");
	        preparedStatement.setInt(1, studentID);
	        int rowsAffected = preparedStatement.executeUpdate();

	        if (rowsAffected > 0) {
	            System.out.println("✅ Student with ID " + studentID + " deleted successfully.");
	            if (profileRows > 0) {
	                System.out.println("🗂️ Associated profile data also deleted.");
	            } else {
	                System.out.println("⚠️ No profile data found to delete.");
	            }
	        } else {
	            System.out.println("⚠️ Deletion failed. Something went wrong.");
	        }

	    } catch (SQLException e) {
	        System.out.println("❌ Database error occurred: " + e.getMessage());
	        e.printStackTrace();
	    } catch (InputMismatchException e) {
	        System.out.println("❌ Invalid input. Please enter a valid Student ID.");
	        scanner.next(); // clear the invalid input
	    }
	}


	public void assignACourse(Scanner scanner) {
	    try {
	        System.out.println("📒 Assigning a course to the student!");

	        System.out.print("Enter the student ID: ");
	        int studentID = scanner.nextInt();

	        // Validate if student ID exists
	        preparedStatement = connection.prepareStatement("SELECT * FROM students WHERE student_id = ?");
	        preparedStatement.setInt(1, studentID);
	        resultSet = preparedStatement.executeQuery();
	        if (!resultSet.next()) {
	            System.out.println("❌ No student found with ID " + studentID);
	            return;
	        }

	        System.out.println("=====================================================================================");
	        System.out.println("📖 Available Courses:");
	        System.out.println("1. DATA SCIENCE");
	        System.out.println("2. ARTIFICIAL INTELLIGENCE AND MACHINE LEARNING");
	        System.out.println("3. ARTIFICIAL INTELLIGENCE AND DATA SCIENCE");
	        System.out.println("4. COMPUTER SCIENCE AND ENGINEERING");
	        System.out.println("5. INFORMATION TECHNOLOGY");
	        System.out.println("6. CHEMICAL ENGINEERING");
	        System.out.println("7. MECHANICAL ENGINEERING");
	        System.out.println("8. PRODUCTION ENGINEERING");
	        System.out.println("9. CYBER SECURITY");
	        System.out.println("10. IOT");

	        System.out.print("Enter the course option number (1-10): ");
	        int choice = scanner.nextInt();

	        if (choice < 1 || choice > 10) {
	            System.out.println("❌ Invalid course option selected. Please enter a number between 1 and 10.");
	            return;
	        }

	        int courseID = 100 + choice;

	        // Check if course is already assigned to this student
	        preparedStatement = connection.prepareStatement(
	                "SELECT * FROM student_course WHERE student_id = ? AND course_id = ?");
	        preparedStatement.setInt(1, studentID);
	        preparedStatement.setInt(2, courseID);
	        resultSet = preparedStatement.executeQuery();
	        if (resultSet.next()) {
	            System.out.println("⚠️ Course already assigned to this student.");
	            return;
	        }

	        // Assign course
	        preparedStatement = connection.prepareStatement(
	                "INSERT INTO student_course (student_id, course_id) VALUES (?, ?)");
	        preparedStatement.setInt(1, studentID);
	        preparedStatement.setInt(2, courseID);
	        int rowsInserted = preparedStatement.executeUpdate();

	        if (rowsInserted > 0) {
	            System.out.println("✅ Course assigned successfully to the student!");
	        } else {
	            System.out.println("⚠️ Failed to assign course.");
	        }

	    } catch (InputMismatchException e) {
	        System.out.println("❌ Invalid input. Please enter numeric values only.");
	        scanner.nextLine(); // clear the invalid input
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}


	public void ShowAllAssignedCourses() {
	    try {
	        preparedStatement = connection.prepareStatement(
	                "SELECT " +
	                "    s.student_id, " +
	                "    s.student_rollno, " +
	                "    s.student_fname, " +
	                "    s.student_lname, " +
	                "    GROUP_CONCAT(c.course_name SEPARATOR ', ') AS course_names " +
	                "FROM " +
	                "    students s " +
	                "JOIN " +
	                "    student_course sc ON s.student_id = sc.student_id " +
	                "JOIN " +
	                "    course c ON sc.course_id = c.course_id " +
	                "GROUP BY " +
	                "    s.student_id, s.student_rollno, s.student_fname, s.student_lname"
	        );

	        resultSet = preparedStatement.executeQuery();

	        System.out.println("\n📋 Displaying All Assigned Courses (Grouped by Student)\n");

	        String format = "| %-4s | %-8s | %-15s | %-15s | %-60s |\n";

	        System.out.println("=".repeat(115));
	        System.out.printf(format, "ID", "Roll No", "First Name", "Last Name", "Assigned Courses");
	        System.out.println("-".repeat(115));

	        while (resultSet.next()) {
	            System.out.printf(format,
	                    resultSet.getInt("student_id"),
	                    resultSet.getInt("student_rollno"),
	                    resultSet.getString("student_fname"),
	                    resultSet.getString("student_lname"),
	                    resultSet.getString("course_names"));
	        }

	        System.out.println("=".repeat(115));

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}



}
