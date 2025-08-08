package com.aurionpro.service;

import java.util.List;
import java.util.Scanner;

import com.aurionpro.dao.CourseDao;
import com.aurionpro.dao.FeeDao;
import com.aurionpro.dao.StudentDao;
import com.aurionpro.model.Student;
import com.aurionpro.model.StudentProfile;
import com.aurionpro.model.StudentProfile.StudentGender;

public class StudentService {

	private StudentDao studentDao;
	private CourseDao courseDao;
	private Scanner scanner = new Scanner(System.in);
	private FeeDao feeDao;
	
	
	public StudentService() {
		studentDao = new StudentDao();
		courseDao = new CourseDao();
		feeDao = new FeeDao();

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
	public StudentGender getValidGender(String genderStr) {
		System.out.println(genderStr);
		try {
			if (genderStr == null || genderStr.isBlank()) {
				System.out.println("❌ Gender input cannot be empty.");
				return null;
			}

			String output = genderStr.substring(0, 1).toUpperCase() + genderStr.substring(1);
			return StudentProfile.StudentGender.valueOf(output);
		} catch (Exception e) {
			System.out.println("Invalid gender. Please enter Male, Female, or Other.");
			return null;
		}

	}

	// Soft delete (deactivate) student
	public void deleteAStudent(int studentId) {
		try {
			// to see if the student is already deactivated

			if (studentDao.checkIfDeleted(studentId)) {
				System.out.println("Student is already not active");
			}
			if(!(studentDao.checkIfDeleted(studentId))) {
				studentDao.deleteStudentById(studentId);
			}
			

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}

	}

	// Assign course to student
		public void assignCourse(int studentId, int courseId) {
			try {
				if(!courseDao.checkIfCourseExist(courseId)) {
					System.out.println("XXXXX Wrong course id XXXXX");
					return;
				}
				
				double courseFee = courseDao.getCourseFee(courseId);
				while(courseFee == 0) {
					System.out.println("XXX Wrong course id. XXX");
					System.out.println("Enter courseId : ");
					courseId = scanner.nextInt();
					courseFee = courseDao.getCourseFee(courseId);
				}
				System.out.println("Course fee :: " + courseFee);
				double paidFee;
				double pendingFee;
				while(true) {
					System.out.println("Enter initial amount :: ");
					paidFee = scanner.nextDouble();
					pendingFee = courseFee-paidFee;
					if(paidFee < 0) {
						System.out.println("XXXX Invalid amount !!!! XXXX");
						continue;
					}
					else if(pendingFee < 0) {
						System.out.println("***** Amount must be less than courseFee *****");
						continue;
					}
					else break;
				}
				
				studentDao.assignCourseToStudent(studentId, courseId);
				feeDao.addStudent(studentId, paidFee, pendingFee, courseFee);
				
			} catch (IllegalArgumentException e) {
				System.out.println("Error : 190");
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
	
	// Show a single student
		public Student getStudentById(int studentId) {
			
				return studentDao.readAStudent(studentId);
		

		}
	
	// Show a single student
		public Student getStudentWithName(String studentFname, String studentLname) {
		
				return studentDao.getStudentByName(studentFname, studentLname);
			
		}

}
