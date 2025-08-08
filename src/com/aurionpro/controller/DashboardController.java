package com.aurionpro.controller;

import java.util.List;
import com.aurionpro.model.Dashboard;

public class DashboardController {

    public void displayDashboard(List<Dashboard> data) {
        System.out.println("\n📊 ADMIN DASHBOARD");
        System.out.println("=".repeat(230));  // Adjust width for extra column
        System.out.printf("| %-4s | %-6s | %-25s | %-50s | %-10s | %-10s | %-10s | %-50s | %-30s \n",
                "Sr#", "ID", "Name", "Course", "Paid", "Pending", "Total", "Subjects", "Teachers");
        System.out.println("-".repeat(230));

        int srNo = 1;
        for (Dashboard dashboardObject : data) {
            System.out.printf("| %-4d | %-6d | %-25s | %-50s | %-10.2f | %-10.2f | %-10.2f | %-50s | %-30s \n",
                    srNo++,
                    dashboardObject.getStudentId(),
//                    truncate(dashboardObject.getStudentName(), 25),
//                    truncate(dashboardObject.getCourse(), 40),
                    dashboardObject.getStudentName(),
                    dashboardObject.getCourse(),
                    dashboardObject.getPaidFee(),
                    dashboardObject.getPendingFee(),
                    dashboardObject.getTotalFee(),
//                    truncateList(dashboardObject.getSubjects(), 30),
//                    truncateList(dashboardObject.getTeachers(), 30));
                    dashboardObject.getSubjects(),
                    dashboardObject.getTeachers());
        }

        System.out.println("=".repeat(230));
    }

//    // Truncate long strings safely
//    private String truncate(String value, int maxLength) {
//        if (value == null) return "";
//        return value.length() > maxLength ? value.substring(0, maxLength - 3) + "..." : value;
//    }

//    // Convert list to comma-separated string and truncate
//    private String truncateList(List<String> list, int maxLength) {
//        if (list == null) return "";
//        return truncate(String.join(", ", list), maxLength);
//    }
}


