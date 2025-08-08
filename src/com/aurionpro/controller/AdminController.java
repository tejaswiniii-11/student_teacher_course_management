package com.aurionpro.controller;

import java.util.Scanner;

import com.aurionpro.service.AdminService;

public class AdminController {
	private AdminService adminService;
	private Scanner scanner = new Scanner(System.in);
	private static AdminController adminController = null;
	
	public AdminController() {
		this.adminService = new AdminService();
	}
	
	public boolean login() {
		System.out.println("***** Welcome to the Application *****");
		System.out.print("Enter email id : ");
		String emailId = scanner.next();
		System.out.print("Enter password : ");
		String password = scanner.next();
		
		return adminService.checkUserExist(emailId, password);
	}
	
	public static AdminController getObject() {
		if(adminController == null) {
			adminController = new AdminController();
		}
		
		return adminController;
	}
	
}