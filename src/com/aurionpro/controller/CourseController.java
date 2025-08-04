package com.aurionpro.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

import com.aurionpro.dao.CourseDao;
import com.aurionpro.model.Course;
import com.aurionpro.model.Subject;

public class CourseController {
	private Scanner scanner = new Scanner(System.in);
	private CourseDao courseDao;
	private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	
	public CourseController() {
		this.courseDao = new CourseDao();
	}
	
	public void display() {
		try {
			while(true) {
				System.out.println("\n\n1. View all courses \n2. View all subjects \n3. Add new course \n4. Add new subject "
						+ "\n5. Add new subject into course \n6. View subject of course "
						+ "\n7. Search course by id \n8. Search course by name"
						+ "\n9. Delete course by id \n10. Exit");
				
				int userInput = scanner.nextInt();
				
				if(userInput == 10) break;
				
				switch(userInput) {
					case 1 : courseDao.showAllCourses();
							break;
					case 2 : courseDao.showAllSubjects();
							break;
					case 3 : System.out.println("\n\nEnter course id : ");
							int courseId = scanner.nextInt();
							System.out.println("\nEnter course name : ");
							String courseName = reader.readLine();
							System.out.println("\nEnter course fee : ");
							double courseFee = scanner.nextDouble();
							Course course = new Course(courseId, courseName,courseFee);
							courseDao.addCourse(course);
							break;
					case 4 : System.out.println("\n\nEnter subject id : ");
							int subjectId = scanner.nextInt();
							System.out.println("\nEnter subject name : ");
							String subjectName = reader.readLine();
							courseDao.addSubject(new Subject(subjectId,subjectName));
							break;
					case 5 : System.out.println("\n\nEnter course id : ");
							courseId = scanner.nextInt();
							System.out.println("\nEnter subject id : ");
							subjectId = scanner.nextInt();
							courseDao.addSubjectIntoCourse(courseId, subjectId);;
							break;
					case 6 : System.out.println("\n\nEnter course id : ");
							courseId = scanner.nextInt();
							courseDao.showAllSubjectsOfCourse(courseId);
							break;
					case 7 : System.out.println("\n\nEnter course id : ");
							courseId = scanner.nextInt();
							if(courseDao.checkIfCourseExist(courseId)) {
								System.out.println("\nCongrats !!!!! It exist.");
							}
							else {
								System.out.println("\nSorry, no course exist with given id.");
							}
							break;
					case 8 : System.out.println("\n\nEnter course name : ");
							courseName = reader.readLine();
							if(courseDao.checkIfCourseExist(courseName)) {
								System.out.println("\nCongrats !!!!! It exist.");
							}
							else {
								System.out.println("\nSorry, no course exist with given name.");
							}
							break;
					case 9 : System.out.println("\n\nEnter course id : ");
							courseId = scanner.nextInt();
							courseDao.deleteCourse(courseId);
							break;
				}
			}
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
	}
	
}
