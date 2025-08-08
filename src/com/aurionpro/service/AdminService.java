package com.aurionpro.service;

import com.aurionpro.dao.AdminDao;

public class AdminService {
	private AdminDao adminDao;
	
	public AdminService() {
		this.adminDao = new AdminDao();	
	}
	
	public boolean checkUserExist(String emailId, String password) {
		if(!emailId.contains("@swabhav.edu.com")) {
			System.out.println("XXXXX Invalid Email id. XXXXX");
			return false;
		}
		
		if(!adminDao.checkUserExist(emailId, password)) {
			System.out.println("User not exist !!!!!!");
			return false;
		}
		
		System.out.println("****** Login Successful. ******");
		return true;
	}
	
}