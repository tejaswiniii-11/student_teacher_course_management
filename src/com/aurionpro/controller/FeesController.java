package com.aurionpro.controller;

import java.util.Scanner;

import com.aurionpro.dao.FeeDao;

public class FeesController {
	private FeeDao feeDao = null;
	private Scanner scanner = new Scanner(System.in);
	
	public FeesController() {
		feeDao = new FeeDao();
	}
	
	public void display() {
		while(true) {
			try {
				System.out.println("\n\n1. View Total Paid Fees \n2. View Total Pending Fees \n3. View Fees By Student"
						+ "\n4. View Fees By Course \n5. Update Fees of a Course \n6. Total Earning \n7. Exit");
				int input = scanner.nextInt();
				
				if(input == 7) {
					System.out.println("***** Fee Management Exit. *****");
					break;
				}
				
				if(input > 7 || input < 1) {
					System.out.println("XXXXX Wrong input!!!! XXXXX");
					continue;
				}
				
				switch(input) {
				case 1: feeDao.paidFees();
						break;
				case 2: feeDao.pendingFees();
						break;
				case 3: System.out.println("Enter student id : ");
						int studentId = scanner.nextInt();
						feeDao.feeByStudentId(studentId);
						break;
				case 4: System.out.println("Enter course id : ");
						int courseId = scanner.nextInt();
						feeDao.feeByCourseId(courseId);
						break;
				case 5: System.out.println("Enter course id : ");
						courseId = scanner.nextInt();
						System.out.println("Enter new fee of course : ");
						double courseFee = scanner.nextDouble();
						
						feeDao.updateFeeOfCourse(courseId, courseFee);
						break;
				case 6: feeDao.totalEarning();
						break;
				}
				
			} catch (Exception e) {
				System.out.println(e.getLocalizedMessage());
			}
		}
		
		
	}
	
}
