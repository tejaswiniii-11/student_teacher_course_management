package com.aurionpro.service;

import java.sql.SQLException;
import java.util.List;

import com.aurionpro.dao.StudentDao;
import com.aurionpro.model.Student;
import com.aurionpro.model.StudentProfile;
import com.aurionpro.model.StudentProfile.studentGender;

public class StudentService {

	private StudentDao studentDao;

	public StudentService() {
		studentDao = new StudentDao();

	}

	// Add new student and profile
	public boolean registerStudent(Student student, StudentProfile studentprofile) {
		try {
			boolean studentAdded = studentDao.addStudent(student);
			if (studentAdded) {
				return studentDao.addStudentProfile(studentprofile, student);
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}

		return false;
	}

	// Validation methods

	public void isValidStudentId(int studentId) {
		try {
			if (studentId <= 0 || studentId == ' ') {
				System.out.println("Please eneter correct option");
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

	public void isValidProfileId(int profileId) {
		try {
			if (profileId <= 0 || profileId == ' ') {
				System.out.println("Please eneter correct option");
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

	public boolean doesStudentExist(int studentId) {
		try {
			studentDao.checkIfStudentExists(studentId);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean doesProfileExist(int profileId) {
		try {
			return studentDao.checkIfProfileExists(profileId);
		} catch (Exception e) {
			System.out.println("❌ Error checking profile ID: " + e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	// Validate Student Fields
	public boolean isValidName(String name) {
		if (name == null || name.isBlank()) {
			System.out.println("Name cannot be empty.");
			return false;
		}
		if (!name.matches("[A-Za-z]{2,}")) {
			System.out.println("Name must contain only alphabets and be at least 2 characters.");
			return false;
		}
		return true;
	}

	public boolean isValidMobile(String mobNo) {
		if (mobNo != null && mobNo.matches("^[6-9]\\d{9}$")) {
			return true; //
		}
		System.out.println("❌ Invalid mobile number! Must be 10 digits starting with 6-9.");
		return false;
	}

	public boolean isValidEmail(String email) {
		if (email != null && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
			return true; // ✅ Valid email
		}
		System.out.println("❌ Invalid email! Please enter a valid email address.");
		return false;
	}

	public boolean isValidAge(int age) {
		if (age >= 15 && age <= 35) {
			return true;
		}
		if (!(age >= 15) && !(age <= 35)) {
			System.out.println("❌ Invalid age. Age must be between 5 and 100.");

		}
		return false;
	}

	// Validate Gender Input
	public studentGender getValidGender(String genderStr) {
		System.out.println(genderStr);
	    try {
	        if (genderStr == null || genderStr.isBlank()) {
	            System.out.println("❌ Gender input cannot be empty.");
	            return null;
	        }

	       
	        String output = genderStr.substring(0, 1).toUpperCase() + genderStr.substring(1);
	        return StudentProfile.studentGender.valueOf(output);
	    } catch (Exception e) {
	        System.out.println("Invalid gender. Please enter Male, Female, or Other.");
	    	 return null;
	    }
	    
	}


	// Soft delete (deactivate) student
	public void deleteStudent(int studentId) {
		try {
			// to see if the student is already deactivated
			if (!studentDao.checkStudentDeactive(studentId)) {
				System.out.println("Student is already deactivated");
			}
			if (studentDao.checkStudentDeactive(studentId)) {
				studentDao.deleteStudentById(studentId);
				System.out.println(" Student deactivated successfully.");
			}

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}

	}

	// Assign course to student
	public void assignCourse(int studentId, int courseId) {
		try {
			studentDao.assignCourseToStudent(studentId, courseId);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}

	}

	// Fetch all active students
	public List<Student> getAllActiveStudents() {
		try {
			return studentDao.showActiveStudents();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return null;
	}

	// Fetch all inactive students
	public List<Student> getAllInactiveStudents() {
		try {
			return studentDao.showInActiveStudents();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return null;
	}

	// Fetch active student profiles
	public List<StudentProfile> getAllActiveStudentProfiles() {
		return studentDao.showActiveStudentsProfile();
	}

	// Fetch inactive student profiles
	public List<StudentProfile> getAllInactiveStudentProfiles() {
		return studentDao.showInActiveStudentsProfile();
	}

	// Show a single student
	public void getStudentById(int studentId) {
		try {
			studentDao.readAStudent(studentId);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}

	}

	// Show a student's profile
	public StudentProfile getStudentProfileById(int profileId) {
		try {
			return studentDao.getStudentProfileById(profileId);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return null;
	}

	// Update operations
	public void updateStudentRollno(Student student) {
		studentDao.updateAStudentRollno(student);
	}

	public void updateStudentFname(Student student) {
		studentDao.updateAStudentFname(student);
	}

	public void updateStudentLname(Student student) {
		studentDao.updateAStudentLname(student);
	}

	public void updateStudentMobno(Student student) {
		studentDao.updateAStudentMobno(student);
	}

	public void updateStudentAddress(StudentProfile studentprofile) {
		studentDao.updateAStudentAddress(studentprofile);
	}

	public void updateStudentAge(StudentProfile studentprofile) {
		studentDao.updateAStudentAge(studentprofile);
	}

	public boolean updateStudentGender(StudentProfile profile) {
		try {
			if (!doesProfileExist(profile.getProfileID()))
				return false;
			studentDao.updateAStudentGender(profile);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return true;
	}

	public boolean updateStudentEmail(StudentProfile profile) {
		try {
			if (!doesProfileExist(profile.getProfileID())) {
				System.out.println("❌ Profile ID not found.");
				return false;
			}

			if (!isValidEmail(profile.getStudentEmail())) {
				System.out.println("❌ Invalid Email Format.");
				return false;
			}

			studentDao.updateAStudentEmail(profile);

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return true;
	}

	// Get students with assigned courses
	public List<Student> getAssignedCourses() {
		try {
			return studentDao.ShowAssignedCourses();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return null;

	}

}
