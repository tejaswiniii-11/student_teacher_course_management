package com.aurionpro.model;

public class TeacherProfile {
	private int teacherId;
	private String city;
	private String qualification;
	private int experience;
	private double salary;
	
	
	public TeacherProfile(int teacherId, String city, String qualification, int experience, double salary) {
		super();
		this.teacherId = teacherId;
		this.city = city;
		this.experience = experience;
		this.qualification = qualification;
		this.salary = salary;
	}


	public int getTeacherId() {
		return teacherId;
	}


	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public int getExperience() {
		return experience;
	}


	public void setExperience(int experience) {
		this.experience = experience;
	}


	public String getQualification() {
		return qualification;
	}


	public void setQualification(String qualification) {
		this.qualification = qualification;
	}


	public double getSalary() {
		return salary;
	}


	public void setSalary(double salary) {
		this.salary = salary;
	}
}
