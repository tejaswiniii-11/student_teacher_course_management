package com.aurionpro.test;

import java.sql.Connection;

import com.aurionpro.controller.CourseController;
import com.aurionpro.database.Database;

public class Test {

	public static void main(String[] args) {
		
		CourseController course = new CourseController();
		course.display();
		
		System.out.println("Happy Ending.");
		
	}

}
