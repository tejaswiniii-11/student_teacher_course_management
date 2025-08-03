package com.aurionpro.service;

import com.aurionpro.dao.FeeDao;

public class FeesService {
	private FeeDao feeDao = null;
	
	public FeesService() {
		feeDao = new FeeDao();
	}
	
	public void paidFees() {
		feeDao.paidFees();
	}
	
	public void pendingFees() {
		feeDao.pendingFees();
	}
	
	public void feeByStudentId(int studentId) {
		feeDao.feeByStudentId(studentId);
	}
	
	public void feeByCourseId(int courseId) {
		feeDao.feeByCourseId(courseId);
	}
	
	public void updateFeeOfCourse(int courseId, double courseFee) {
		feeDao.updateFeeOfCourse(courseId, courseFee);
	}
	
	public void totalEarning() {
		feeDao.totalEarning();
	}
	
	
}
