package com.aurionpro.test;

import java.sql.Connection;

import com.aurionpro.controller.CourseController;
import com.aurionpro.controller.FeesController;
import com.aurionpro.database.Database;

public class Test {

	public static void main(String[] args) {
		
//		CourseController course = new CourseController();
//		course.display();
		
		FeesController fee = new FeesController();
		fee.display();
		
		System.out.println("Happy Ending.");
		
	}

}
