package com.aurionpro.dao;

import java.sql.*;
import java.util.*;

import com.aurionpro.model.Dashboard;

public class DashboardDao {
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public DashboardDao(Connection connection) {
        this.connection = connection;
    }

    public List<Dashboard> fetchDashboardData() throws SQLException {
        List<Dashboard> dashboardEntries = new ArrayList<>();

        String query = """
        	    SELECT 
        	        s.student_id, 
        	        CONCAT(s.student_fname, ' ', s.student_lname) AS name,
        	        c.course_name, 
        	        sf.paid_fee, 
        	        sf.total_fee,
        	        GROUP_CONCAT(DISTINCT sub.subject_name) AS subjects,
        	        GROUP_CONCAT(DISTINCT CONCAT(t.fname, ' ', t.lname)) AS teachers
        	    FROM students s
        	    JOIN students_profile sp ON s.student_id = sp.student_id
        	    JOIN student_course sc ON s.student_id = sc.student_id
        	    JOIN course c ON sc.course_id = c.course_id
        	    JOIN STUDENT_FEES sf ON s.student_id = sf.student_id
        	    LEFT JOIN course_subject cs ON c.course_id = cs.course_id
        	    LEFT JOIN subject sub ON cs.subject_id = sub.subject_id
        	    LEFT JOIN subject_teacher st ON st.course_subject_id = cs.course_subject_id
        	    LEFT JOIN teachers t ON t.teacher_id = st.teacher_id
        	    LEFT JOIN teacher_profile tp ON tp.teacher_id = t.teacher_id
        	    GROUP BY 
        	        s.student_id, 
        	        s.student_fname, 
        	        s.student_lname,
        	        c.course_name,
        	        sf.paid_fee,
        	        sf.total_fee;
        	""";


        preparedStatement = connection.prepareStatement(query);
        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            int studentId = resultSet.getInt("student_id");
            String name = resultSet.getString("name");
            String course = resultSet.getString("course_name");
            double paid = resultSet.getDouble("paid_fee");
            double total = resultSet.getDouble("total_fee");
            double pending = total - paid;
            List<String> subjects = Arrays.asList(resultSet.getString("subjects").split(","));
            List<String> teachers = Arrays.asList(resultSet.getString("teachers").split(","));

            Dashboard dashboardObject = new Dashboard(studentId, name, course, paid, pending, total, subjects, teachers);
            dashboardEntries.add(dashboardObject);
        }

        return dashboardEntries;
    }
}
