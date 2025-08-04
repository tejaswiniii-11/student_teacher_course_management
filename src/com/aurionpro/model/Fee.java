package com.aurionpro.model;

public class Fee {
	private int studentId = 0;
	private double paidFee = 0, pendingFee = 0, totalFee = 0;
	
	public Fee(int studentId, double paidFee, double pendingFee, double totalFee) {
		this.studentId = studentId;
		this.paidFee = paidFee;
		this.pendingFee = pendingFee;
		this.totalFee = totalFee;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
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
	
}
