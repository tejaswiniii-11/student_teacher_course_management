package com.aurionpro.model;

import java.util.List;

public class Dashboard {
    private int studentId;
    private String studentName;
    private String course;
    private double paidFee;
    private double pendingFee;
    private double totalFee;
    private List<String> subjects;
    private List<String> teachers;

    // Constructor, Getters, Setters

    public Dashboard(int studentId, String studentName, String course,
                          double paidFee, double pendingFee, double totalFee,
                          List<String> subjects, List<String> teachers) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.course = course;
        this.paidFee = paidFee;
        this.pendingFee = pendingFee;
        this.totalFee = totalFee;
        this.subjects = subjects;
        this.teachers = teachers;
    }

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public double getPaidFee() {
		return paidFee;
	}

	public void setPaidFee(double paidFee) {
		this.paidFee = paidFee;
	}

	public double getPendingFee() {
		return pendingFee;
	}

	public void setPendingFee(double pendingFee) {
		this.pendingFee = pendingFee;
	}

	public double getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(double totalFee) {
		this.totalFee = totalFee;
	}

	public List<String> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<String> subjects) {
		this.subjects = subjects;
	}

	public List<String> getTeachers() {
		return teachers;
	}

	public void setTeachers(List<String> teachers) {
		this.teachers = teachers;
	}

	public Dashboard() {
		super();
		// TODO Auto-generated constructor stub
	}

    
}
