package com.aurionpro.test;

import java.sql.SQLException;
import java.util.List;

import com.aurionpro.dao.StudentDao;
import com.aurionpro.model.Student;
import com.aurionpro.model.StudentProfile;
import com.aurionpro.model.StudentProfile.studentGender;

public class TemporarayTest {
	public static void main(String[] args) {
		StudentDao studentDao = new StudentDao();

//        try {
//            List<Student> students = studentDao.showAllStudents();
//
//            if (students.isEmpty()) {
//                System.out.println("No active students found.");
//            } else {
//                System.out.println("Active Students:");
//                for (Student s : students) {
//                    System.out.println("ID: " + s.getStudentID());
//                    System.out.println("Roll No: " + s.getStudentRollno());
//                    System.out.println("First Name: " + s.getStudentFname());
//                    System.out.println("Last Name: " + s.getStudentLname());
//                    System.out.println("Mobile: " + s.getStudentMobno());
//                    System.out.println("Active: " + s.isActive());
//                    System.out.println("----------------------------");
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

//        int testStudentID = 2; // Replace with an ID you want to test
//
//        try {
//            boolean exists = studentDao.checkIfStudentExists(testStudentID);
//            if (exists) {
//                System.out.println("✅ Student with ID " + testStudentID + " exists.");
//            } else {
//                System.out.println("❌ Student with ID " + testStudentID + " does NOT exist.");
//            }
//        } catch (SQLException e) {
//            System.out.println("Database error occurred.");
//            e.printStackTrace();
//        }

//        int testProfileID = 1; // Change this to an actual ID to test
//
//        try {
//            boolean exists = studentDao.checkIfProfileExists(testProfileID);
//            if (exists) {
//                System.out.println("✅ Profile with ID " + testProfileID + " exists.");
//            } else {
//                System.out.println("❌ Profile with ID " + testProfileID + " does NOT exist.");
//            }
//        } catch (SQLException e) {
//            System.out.println("❌ Database error occurred:");
//            e.printStackTrace();
//        }

//     // Create a new Student object
//        Student student = new Student();
//        student.setStudentID(12);              // Make sure this ID is unique
//        student.setStudentRollno(112);
//        student.setStudentFname("Dheeren");
//        student.setStudentLname("K");
//        student.setStudentMobno("8976345210");
//                     
//
//        try {
//            boolean isAdded = studentDao.addStudent(student);
//            if (isAdded) {
//                System.out.println("✅ Student added successfully.");
//            } else {
//                System.out.println("❌ Failed to add student.");
//            }
//        } catch (SQLException e) {
//            System.out.println("❌ Database error occurred:");
////            e.printStackTrace();
//        }

//     // Create a sample StudentProfile object
//        Student student = new Student();
//        StudentProfile studentProfile = new StudentProfile();
//        studentProfile.setProfileID(11);  // Make sure this is unique
//        studentProfile.setStudentAddress("Mumbai");
//        studentProfile.setStudentAge(21);
//        studentProfile.setStudentEmail("jenitkothar98@example.com");
//        studentProfile.setStudentGender(studentGender.Male); //enum is Gender.MALE
//       
//
//        try {
//            boolean isAdded = studentDao.addStudentProfile(studentProfile, student);
//
//            if (isAdded) {
//                System.out.println("✅ Student profile added successfully.");
//            } else {
//                System.out.println("❌ Failed to add student profile.");
//            }
//
//        } catch (SQLException e) {
//            System.out.println("❌ Database error occurred:");
//            e.printStackTrace(); // This is important for debugging
//        }

//        //show all active student profiles
//        try {
//    
//            List<StudentProfile> profiles = studentDao.showActiveStudentsProfile();
//
//            if (profiles.isEmpty()) {
//                System.out.println("❌ No student profiles found.");
//            } else {
//                System.out.println("✅ Student Profiles:");
//                for (StudentProfile profile : profiles) {
//                    System.out.println(profile);
//                }
//            }
//
//        } catch (Exception e) {
//            System.out.println("❌ Error occurred:");
//            e.printStackTrace();
//        }

//        //read a student
//        int studentID = 1; // Replace with an actual student_id that exists in DB
//
//        try {
//            studentDao.readAStudent(studentID); 
//            System.out.println();// This will invoke your method and fetch student details
//        } catch (SQLException e) {
//            System.out.println("❌ Error occurred while fetching the student:");
//            e.printStackTrace();
//        }

//        //To update mobile no
//     // Create a Student object with updated mobile number
//        Student student = new Student();
//        student.setStudentID(11);  // ID of the student whose number you want to update
//        student.setStudentMobno("1276543210");  // New mobile number
//
//        // Call update method
//        try {
//            studentDao.updateAStudentMobno(student);
//            System.out.println("✅ Student mobile number updated successfully.");
//        } catch (Exception e) {
//            System.out.println("❌ Failed to update student mobile number.");
//            e.printStackTrace();
//        }

//        //Read a single student profile
//        try {
//            
//            // Call the method with a valid student_id
//            int testProfileId = 1; // Replace with a valid profile_id from your DB
//            studentDao.readAStudentProfile(testProfileId);
//
//        } catch (SQLException e) {
//            System.out.println("❌ Error occurred while reading student profile:");
//            e.printStackTrace();
//        }
//
//		// Show all assigned courses
//		try {
//			List<Student> assignedCourses = studentDao.ShowAssignedCourses();
//
//			// Step 4: Display in tabular format
//			System.out.println("\n📋 Assigned Courses by Student\n");
//			String format = "| %-5s | %-8s | %-15s | %-15s | %-50s |\n";
//			System.out.println("=".repeat(105));
//			System.out.printf(format, "ID", "Roll No", "First Name", "Last Name", "Assigned Courses");
//			System.out.println("-".repeat(105));
//
//			for (Student s : assignedCourses) {
//				System.out.printf(format, s.getStudentID(), s.getStudentRollno(), s.getStudentFname(),
//						s.getStudentLname(), s.getCourseNames());
//			}
//
//			System.out.println("=".repeat(105));
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		
//		//Assign a course
//		 try {
//			studentDao.assignCourseToStudent(1, 104);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//		//Delete a student
//		try {
//			 studentDao.deleteStudentById(12);
//		} catch(SQLException e) {
//			e.printStackTrace();
//		}

	}
}
