package com.aurionpro.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

import com.aurionpro.model.Course;
import com.aurionpro.model.Subject;
import com.aurionpro.service.CourseService;

public class CourseController {
	private static Scanner scanner = new Scanner(System.in);
	private static CourseService courseService;
	private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	
	static {
		courseService = new CourseService();
	}
	
	public CourseController() {
	}
	
	public static void display() {
		try {
			while(true) {
				System.out.println("\n\n1. View all active courses");
				System.out.println("2. View all inactive courses");
				System.out.println("3. Add new course");
				System.out.println("4. View all active subjects");
				System.out.println("5. View all inactive subjects");
				System.out.println("6. Add new subject");
				System.out.println("7. Add subject into course");
				System.out.println("8. View subject of course");
				System.out.println("9. Search course by id");
				System.out.println("10. Search course by name");
				System.out.println("11. Delete course");
				System.out.println("12. Delete subject");
				System.out.println("13. Exit");
				
				System.out.println("\nEnter your choice : ");
				
				int userInput = scanner.nextInt();
				
				if(userInput == 13) break;
				
				switch(userInput) {
					case 1 : courseService.showAllActiveCourses();
							break;
					case 2 : courseService.showAllInactiveCourses();
							break;
					case 3 : System.out.println("\n\nEnter course id : ");
							int courseId = scanner.nextInt();
							System.out.println("\nEnter course name : ");
							String courseName = reader.readLine();
							System.out.println("\nEnter course fee : ");
							double courseFee = scanner.nextDouble();
							System.out.println("Enter academin year : ");
							String academicYear = scanner.next();
							System.out.println("Enter duration : ");
							String duration = scanner.next();
							System.out.println("Write small 5 words description : ");
							String description = reader.readLine();
							Course course = new Course(courseId, courseName,
										courseFee,academicYear,
										duration, description);
							courseService.addCourse(course);
							break;
					case 4 : courseService.showAllActiveSubjects();
							break;
					case 5 : courseService.showAllInactiveSubjects();
							break;
					case 6 : System.out.println("\n\nEnter subject id : ");
							int subjectId = scanner.nextInt();
							System.out.println("\nEnter subject name : ");
							String subjectName = reader.readLine();
							courseService.addSubject(new Subject(subjectId,subjectName));
							break;
					case 7 : System.out.println("\n\nEnter course id : ");
							courseId = scanner.nextInt();
							System.out.println("\nEnter subject id : ");
							subjectId = scanner.nextInt();
							courseService.addSubjectIntoCourse(courseId, subjectId);
							break;
					case 8 : System.out.println("\n\nEnter course id : ");
							courseId = scanner.nextInt();
							courseService.showAllSubjectsOfCourse(courseId);
							break;
					case 9 : System.out.println("\n\nEnter course id : ");
							courseId = scanner.nextInt();
							if(courseService.checkIfCourseExist(courseId)) {
								System.out.println("\nCongrats !!!!! It exist.");
							}
							else {
								System.out.println("\nSorry, no course exist with given id.");
							}
							break;
					case 10 : System.out.println("\n\nEnter course name : ");
							courseName = reader.readLine();
							if(courseService.checkIfCourseExist(courseName)) {
								System.out.println("\nCongrats !!!!! It exist.");
							}
							else {
								System.out.println("\nSorry, no course exist with given name.");
							}
							break;
					case 11 : System.out.println("\n\nEnter course id : ");
							courseId = scanner.nextInt();
							courseService.deleteCourse(courseId);
							break;
					case 12 : System.out.println("\n\nEnter subject id : ");
							subjectId = scanner.nextInt();
							courseService.deleteSubject(subjectId);
							break;
					default : System.out.println("Invalid choice :) ");
				}
			}
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
	}
	
}