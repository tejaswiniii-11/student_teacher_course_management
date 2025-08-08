package com.aurionpro.model;

public class TeacherSubjectCourse {
	private int teacherId;
	private String firstName;
	private String lastName;
	private String subjectName;
	private String courseName;

	public TeacherSubjectCourse(int teacherId, String firstName, String lastName, String subjectName, String courseName) {
		this.teacherId = teacherId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.subjectName = subjectName;
		this.courseName = courseName;
	}

	public int getTeacherId() {
		return teacherId;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public String getCourseName() {
		return courseName;
	}
}