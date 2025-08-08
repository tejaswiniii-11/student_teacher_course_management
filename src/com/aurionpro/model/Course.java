package com.aurionpro.model;

public class Course {
	private int courseId;
	private String courseName;
	private double courseFee;
	private String academicYear, duration, description;
	
	public Course(int courseId, String courseName, double courseFee, String academicYear,
			String duration, String description) {
		this.courseId = courseId;
		this.courseName = courseName;
		this.courseFee = courseFee;
		this.academicYear = academicYear;
		this.duration = duration;
		this.description = description;
	}
	
	public Course() {
		
	}
	
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	
	public int getCourseId() {
		return courseId;
	}
	
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	
	public String getCourseName() {
		return courseName;
	}

	public double getCourseFee() {
		return courseFee;
	}

	public void setCourseFee(double courseFee) {
		this.courseFee = courseFee;
	}

	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
}