package com.aurionpro.test;

import java.util.List;	
import java.util.Scanner;

import com.aurionpro.*;
import com.aurionpro.controller.CourseController;
import com.aurionpro.controller.DashboardController;
import com.aurionpro.controller.FeesController;
import com.aurionpro.controller.StudentController;
import com.aurionpro.controller.TeacherController;
import com.aurionpro.dao.DashboardDao;
import com.aurionpro.database.Database;
import com.aurionpro.model.Dashboard;
import com.aurionpro.service.DashboardService;

public class Driver {
	
	
	
	
	
	public static void DriverCall() {
		Scanner scanner = new Scanner(System.in);
		int choice;
		try {
			// Launch the Student Management App
			while (true) {
				System.out.println("\n\n=====================================================================");
				System.out.println("\t Student Management Console App");
				System.out.println("=====================================================================\n");
				
				System.out.println("1. Student Management");
				System.out.println("2. Teacher Management");
				System.out.println("3. Course Management");
				System.out.println("4. Fees Management");
				System.out.println("5. Dashboard");
				System.out.println("0. Exit");

				System.out.println("Enter your choice");
				choice=scanner.nextInt();
				
				if(choice == 0) {
					System.out.println("Thank you For using this application");
					break;
				}
				
				switch(choice) {
				case 1:
					StudentController.menudrivenDisplay();
					break;
				case 2:
					TeacherController.menu();
					break;
				case 3:
					CourseController.display();
					break;
				case 4:
					FeesController.display();
					break;
				case 5:
					DashboardDao dao = new DashboardDao(Database.getInstance().getConnection());
					DashboardService service = new DashboardService(dao);
					DashboardController view = new DashboardController();

					List<Dashboard> dashboardData = service.getDashboardData();
					view.displayDashboard(dashboardData);
					break;		
				default:
					System.out.println("Enter correct!");
					
				}
				

				
			}
		} catch (Exception e) {
			System.out.println("❌ Error fetching dashboard: " + e.getMessage());
			
		}
	}

}




