package com.aurionpro.model;

public class StudentProfile {
	
	private int profileID;
	private String studentAddress;
	private int studentAge;
	
	public enum StudentGender{
		Female, Male, Other;
	}
	
	private StudentGender studentGender;
	
	private String studentEmail;
	
	private int studentID;

	public void setStudentID(int studentID) {
	    this.studentID = studentID;
	}

	public int getStudentID() {
	    return studentID;
	}
	
	public void setStudentGender(StudentGender studentGender) {
		this.studentGender = studentGender;
	}
	
	public StudentGender getStudentGender() {
	    return studentGender;
	}


	public int getProfileID() {
		return profileID;
	}

	public void setProfileID(int profileID) {
		this.profileID = profileID;
	}

	public String getStudentAddress() {
		return studentAddress;
	}

	public void setStudentAddress(String studentAddress) {
		this.studentAddress = studentAddress;
	}

	public int getStudentAge() {
		return studentAge;
	}

	public void setStudentAge(int studentAge) {
		this.studentAge = studentAge;
	}

	public String getStudentEmail() {
		return studentEmail;
	}

	public void setStudentEmail(String studentEmail) {
		this.studentEmail = studentEmail;
	}

	
	public StudentProfile(int profileID, String studentAddress, int studentAge, String studentEmail, StudentGender studentGender, int studentID) {
		super();
		this.profileID = profileID;
		this.studentAddress = studentAddress;
		this.studentAge = studentAge;
		this.studentEmail = studentEmail;
		this.studentGender = studentGender;
		this.studentID = studentID;
	}

	public StudentProfile() {
		super();
		// TODO Auto-generated constructor stub
	}

//	@Override
//	public String toString() {
//		return "StudentProfile [profileID=" + profileID + ", studentAddress=" + studentAddress + ", studentAge="
//				+ studentAge + ", studentGender=" + studentGender + ", studentEmail=" + studentEmail + ", studentID="
//				+ studentID + "]";
//	}

	

	
	

}
