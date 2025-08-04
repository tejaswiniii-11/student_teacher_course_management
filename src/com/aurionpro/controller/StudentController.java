package com.aurionpro.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.aurionpro.model.Student;
import com.aurionpro.model.StudentProfile;
import com.aurionpro.model.StudentProfile.studentGender;
import com.aurionpro.service.StudentService;

public class StudentController {

	private static final Scanner scanner = new Scanner(System.in);
	private static final StudentService service = new StudentService();
	 static studentGender gender;

	public static void menudrivenDisplay() {
		while (true) {
			try {
				System.out.println("\n🧠====== Student Management Menu ======📚");
				System.out.println("1️.  Register Student");
				System.out.println("2️.  Show All Students");
				System.out.println("3️.  Show Alumni Students");
				System.out.println("4️.  Update Student Details");
				System.out.println("5. Update Student Profile Details");
				System.out.println("6.  Delete Student");
				System.out.println("7️.  Assign Course to A Student");
				System.out.println("8️.  Show All Assigned Courses");
				System.out.println("9️.  Show A Student Profile");
				System.out.println("10. Show All Students Profiles");
				System.out.println("11. Show All Alumni Students Profiles");
				System.out.println("12.  Exit");
				System.out.print("👉 Enter your choice: ");

				int choice = scanner.nextInt();
				scanner.nextLine(); // Consume newline

				switch (choice) {
					case 1 -> registerStudent();
					case 2 -> displayStudents(true);
					case 3 -> displayStudents(false);
					case 4 -> updateStudentDetails();
					case 5 -> updateProfileDetails();
					case 6 -> deleteStudent();
					case 7 -> assignCourse();
					case 8 -> showAssignedCourses();
					case 9 -> showStudentProfile();
					case 10 -> showAllProfiles();
					case 11 -> showAlumniProfiles();
					case 12 -> {
						System.out.println("Exiting... Bye!");
						return;
					}
					default -> System.out.println(" Invalid choice!");
				}
			} catch (Exception e) {
				System.out.println(" Please enter valid choice " + e.getMessage());
				scanner.nextLine(); // Clear invalid input
			}
		}
	}

	private static void registerStudent() {
	    try {
	        System.out.println("\n Registering New Student:");

	        int id;
	        while (true) {
	            System.out.print("🔹 Enter Student ID: ");
	            id = scanner.nextInt();
	            scanner.nextLine();
	            if (!service.doesStudentExist(id)) break;
	            System.out.println(" Student ID already exists. Try again.");
	        }

	        System.out.print("🔹 Enter Roll No: ");
	        int roll = scanner.nextInt();
	        scanner.nextLine();

	        String fname;
	        while (true) {
	            System.out.print("🔹 Enter First Name: ");
	            fname = scanner.nextLine();
	            if (service.isValidName(fname)) break;
	            System.out.println(" Invalid first name. Try again.");
	        }

	        String lname;
	        while (true) {
	            System.out.print("🔹 Enter Last Name: ");
	            lname = scanner.nextLine();
	            if (service.isValidName(lname)) break;
	            System.out.println(" Invalid last name. Try again.");
	        }

	        String mobno;
	        while (true) {
	            System.out.print("🔹 Enter Mobile No: ");
	            mobno = scanner.nextLine();
	            if (service.isValidMobile(mobno)) break;
	            System.out.println(" Invalid mobile number. Try again.");
	        }

	        int pid;
	        while (true) {
	            System.out.print("🔹 Enter Profile ID: ");
	            pid = scanner.nextInt();
	            scanner.nextLine();
	            service.isValidProfileId(pid);
	            break;
	        }


	        System.out.print("🔹 Enter Address: ");
	        String address = scanner.nextLine();

	        int age;
	        while (true) {
	            System.out.print("🔹 Enter Age: ");
	            age = scanner.nextInt();
	            scanner.nextLine();
	            if (service.isValidAge(age))
	            	break;
	            
	        }

	        String email;
	        while (true) {
	            System.out.print("🔹 Enter Email: ");
	            email = scanner.nextLine();
	            if (service.isValidEmail(email)) break;
	            System.out.println(" Invalid email. Try again.");
	        }

	       
	        while (gender == null) {
	            System.out.print("🔹 Enter Gender (Male/Female/Other): ");
	            String genderInput = scanner.nextLine();
	            gender = service.getValidGender(genderInput);
	        }


	        Student student = new Student(id, roll, fname, lname, mobno, true);
	        StudentProfile profile = new StudentProfile(pid, address, age, email, gender, id);
	        System.out.println("succesfully added");
	       boolean result = service.registerStudent(student, profile);  // error getting for addition
	        System.out.println(result ? " Student registered successfully!" : " Registration failed."); // error getting for addition

	    } catch (Exception e) {
	        System.out.println(" Registration failed due to unexpected error.");
	        e.printStackTrace();
	    }
	}


	private static void displayStudents(boolean active) {
		try {
			List<Student> students = active ? service.getAllActiveStudents() : service.getAllInactiveStudents();
			String title = active ? " Active Students List" : " Inactive Students List";
			System.out.println("\n" + title);

			if (students.isEmpty()) {
				System.out.println(" No students found.");
				return;
			}

			System.out.printf("%-10s %-10s %-15s %-15s %-15s\n", "ID", "Roll No", "First Name", "Last Name", "Mobile");
			System.out.println("--------------------------------------------------------------");
			for (Student s : students) {
				System.out.printf("%-10d %-10d %-15s %-15s %-15s\n", s.getStudentID(), s.getStudentRollno(),
						s.getStudentFname(), s.getStudentLname(), s.getStudentMobno());
			}
		} catch (IllegalArgumentException e) {
			System.out.println(" Unable to display students.");
			e.printStackTrace();
		}
	}

	private static void updateStudentDetails() {
		try {
			System.out.print("\n Enter Student ID to update: ");
			int id = scanner.nextInt();
			scanner.nextLine();

			Student student = new Student();
			student.setStudentID(id);

			System.out.println("\n Update Details:");
			System.out.println("1. Roll No");
			System.out.println("2. First Name");
			System.out.println("3. Last Name");
			System.out.println("4. Mobile No");
			System.out.print("👉 Choice: ");
			int choice = scanner.nextInt();
			scanner.nextLine();

			switch (choice) {
				case 1 -> {
					System.out.print("Enter new Roll No: ");
					student.setStudentRollno(scanner.nextInt());
					service.updateStudentRollno(student);
				}
				case 2 -> {
					System.out.print("Enter new First Name: ");
					student.setStudentFname(scanner.nextLine());
					service.isValidName(student.getStudentFname()); //validation
					service.updateStudentFname(student);
				}
				case 3 -> {
					System.out.print("Enter new Last Name: ");
					student.setStudentLname(scanner.nextLine());
					service.isValidName(student.getStudentLname()); //validation
					service.updateStudentLname(student);
				}
				case 4 -> {
					System.out.print("Enter new Mobile No: ");
					student.setStudentMobno(scanner.nextLine());
					service.isValidMobile(student.getStudentMobno()); //validation 
					service.updateStudentMobno(student);
				}
				default -> System.out.println("❌ Invalid choice.");
			}

			System.out.println(" Update completed.");
		} catch (IllegalArgumentException e) {
			System.out.println("❌ Failed to update student.");
			e.printStackTrace();
		}
	}

	private static void updateProfileDetails() {
		try {
			System.out.print("\n Enter Profile ID to update: ");
			int profileid = scanner.nextInt();
			scanner.nextLine();

			StudentProfile profile = new StudentProfile();
			profile.setStudentID(profileid);

			System.out.println("\n Update Profile Details:");
			System.out.println("1. Address");
			System.out.println("2. Age");
			System.out.println("3. Gender");
			System.out.println("4. Email");
			System.out.print("👉 Choice: ");
			int choice = scanner.nextInt();
			scanner.nextLine();

			switch (choice) {
				case 1 -> {
					System.out.print("Enter new Address: ");
					profile.setStudentAddress(scanner.nextLine());
					service.updateStudentAddress(profile);
				}
				case 2 -> {
					System.out.print("Enter new Age: ");
					profile.setStudentAge(scanner.nextInt());
					service.isValidAge(profile.getStudentAge()); //validation
					service.updateStudentAge(profile);
				}
				case 3 -> {
					System.out.print("Enter new Gender (Male/Female/Other): ");
					String inputGender = scanner.nextLine().toUpperCase();
					try {
						profile.setStudentGender(studentGender.valueOf(inputGender));
						service.getValidGender(inputGender); //validation
						service.updateStudentGender(profile);
					} catch (IllegalArgumentException e) {
						System.out.println(" Invalid gender entered.");
					}
				}
				case 4 -> {
					System.out.print("Enter new Email: ");
					profile.setStudentEmail(scanner.nextLine());
					service.isValidEmail(profile.getStudentEmail()); //validation
					service.updateStudentEmail(profile);
				}
				default -> System.out.println(" Invalid choice.");
			}

			System.out.println(" Update completed.");
		} catch (IllegalArgumentException e) {
			System.out.println(" Failed to update profile.");
			e.printStackTrace();
		}
	}

	private static void deleteStudent() {
		try {
			System.out.print("\n Enter Student ID to deactivate: ");
			int id = scanner.nextInt();
			scanner.nextLine();
			service.deleteStudent(id); //validation used
			
		} catch (IllegalArgumentException e) {
			System.out.println(" Failed to deactivate student.");
			e.printStackTrace();
		}
	}


	private static void assignCourse() {
		try {
			System.out.print("\n Enter Student ID: ");
			int sid = scanner.nextInt();

			System.out.print(" Enter Course ID: ");
			int cid = scanner.nextInt();

			service.assignCourse(sid, cid);
			System.out.println(" Course assigned successfully.");
		} catch (IllegalArgumentException e) {
			System.out.println(" Failed to assign course.");
			e.printStackTrace();
		}
	}

	private static void showAssignedCourses() {
		try {
			List<Student> students = service.getAssignedCourses();
			if (students.isEmpty()) {
				System.out.println("No course assignments found.");
				return;
			}

			System.out.printf("%-15s %-15s %-40s\n", "First Name", "Last Name", "Assigned Courses");
			System.out.println("-------------------------------------------------------------------------");
			for (Student s : students) {
				System.out.printf("%-15s %-15s %-40s\n", s.getStudentFname(), s.getStudentLname(), s.getCourseNames());
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

	private static void showStudentProfile() {
		try {
			System.out.print("\nEnter Profile ID to view: ");
			int pid = scanner.nextInt();
			scanner.nextLine();

			StudentProfile profile = service.getStudentProfileById(pid);
			if (profile == null) {
				System.out.println("Profile not found.");
				return;
			}

			System.out.println("\n🎓 Student Profile Details:");
			System.out.println("+------------+----------------------------+");
			System.out.printf("| %-10s | %-26s |\n", "Profile ID", profile.getProfileID());
			System.out.printf("| %-10s | %-26s |\n", "Address", profile.getStudentAddress());
			System.out.printf("| %-10s | %-26s |\n", "Age", profile.getStudentAge());
			System.out.printf("| %-10s | %-26s |\n", "Email", profile.getStudentEmail());
			System.out.printf("| %-10s | %-26s |\n", "Gender", profile.getStudentGender());
			System.out.printf("| %-10s | %-26s |\n", "Student ID", profile.getStudentID());
			System.out.println("+------------+----------------------------+");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

	private static void showAllProfiles() {
		try {
			List<StudentProfile> profiles = service.getAllActiveStudentProfiles();
			if (profiles.isEmpty()) {
				System.out.println("No active profiles found.");
				return;
			}

			System.out.printf("%-12s %-25s %-5s %-30s %-10s %-12s\n", "Profile ID", "Address", "Age", "Email", "Gender",
					"Student ID");
			System.out.println("---------------------------------------------------------------------------------------------");

			for (StudentProfile profile : profiles) {
				System.out.printf("%-12d %-25s %-5d %-30s %-10s %-12d\n", profile.getProfileID(),
						profile.getStudentAddress(), profile.getStudentAge(), profile.getStudentEmail(),
						profile.getStudentGender(), profile.getStudentID());
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

	private static void showAlumniProfiles() {
		try {
			List<StudentProfile> profiles = service.getAllInactiveStudentProfiles();
			if (profiles.isEmpty()) {
				System.out.println("No alumni profiles found.");
				return;
			}

			System.out.printf("%-12s %-25s %-5s %-30s %-10s %-12s\n", "Profile ID", "Address", "Age", "Email", "Gender",
					"Student ID");
			System.out.println("---------------------------------------------------------------------------------------------");

			for (StudentProfile profile : profiles) {
				System.out.printf("%-12d %-25s %-5d %-30s %-10s %-12d\n", profile.getProfileID(),
						profile.getStudentAddress(), profile.getStudentAge(), profile.getStudentEmail(),
						profile.getStudentGender(), profile.getStudentID());
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}
}
