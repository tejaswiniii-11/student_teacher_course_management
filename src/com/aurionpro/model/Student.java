package com.aurionpro.model;

public class Student {
	
	private int studentID;
	private int studentRollno;
	private String studentFname;
	private String studentLname;
	private String studentMobno;
	private boolean isActive;
	
	private String courseNames;

	public String getCourseNames() {
	    return courseNames;
	}

	public void setCourseNames(String courseNames) {
	    this.courseNames = courseNames;
	}

	
	public int getStudentID() {
		return studentID;
	}
	
	public void setStudentID(int studentID) {
		this.studentID = studentID;
	}
	
	public int getStudentRollno() {
		return studentRollno;
	}
	
	public void setStudentRollno(int studentRollno) {
		this.studentRollno = studentRollno;
	}
	
	public String getStudentFname() {
		return studentFname;
	}
	
	public void setStudentFname(String studentFname) {
		this.studentFname = studentFname;
	}
	

	public String getStudentLname() {
		return studentLname;
	}
	
	public void setStudentLname(String studentLname) {
		this.studentLname = studentLname;
	}
	
	public String getStudentMobno() {
		return studentMobno;
	}
	
	public void setStudentMobno(String studentMobno) {
		this.studentMobno = studentMobno;
	}
	
	public boolean isActive() {
		return isActive;
	}
	
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public Student(int studentID, int studentRollno, String studentFname, String studentLname, String studentMobno, boolean isActive) {
		super();
		this.studentID = studentID;
		this.studentRollno = studentRollno;
		this.studentFname = studentFname;
		this.studentLname = studentLname;
		this.studentMobno = studentMobno;
		this.isActive = isActive;
	}

	public Student() {
		super();
		// TODO Auto-generated constructor stub
	}

//	@Override
//	public String toString() {
//		return "Student [studentID=" + studentID + ", studentRollno=" + studentRollno + ", studentFname=" + studentFname
//				+ ", studentLname=" + studentLname + ", studentMobno=" + studentMobno + ", isActive=" + isActive + "]";
//	}
//	
	

}
