package com.aurionpro.service;

import java.util.List;

import com.aurionpro.dao.TeacherDao;
import com.aurionpro.model.Teacher;
import com.aurionpro.model.TeacherProfile;

public class TeacherService {
	private TeacherDao teacherDao;

	public TeacherService() {
		teacherDao = TeacherDao.getInstance();
	}

	public List<Teacher> fetchAllActiveTeachers() {
		return teacherDao.getAllActiveTeachers();
	}
	
	public List<TeacherProfile> fetchAllTeacherProfiles() {
	    return teacherDao.getAllTeacherProfiles();
	}


	private boolean isValidTeacherId(int teacherId) {
		if (teacherId <= 0) {
			System.out.println("Id must be positive");
			return false;
		}
		return true;
	}

	private boolean teacherExists(int teacherId) {
		if (!teacherDao.isTeacherPresent(teacherId)) {
			System.out.println("Teacher not found with ID: " + teacherId);
			return false;
		}
		return true;
	}

	private boolean teacherAlreadyExists(int teacherId) {
		if (teacherDao.isTeacherPresent(teacherId)) {
			System.out.println("Teacher with ID " + teacherId + " already exists.");
			return true;
		}
		return false;
	}

	public void addTeacher(Teacher teacher,TeacherProfile teacherProfile) {
		if (teacher == null) {
			System.out.println("Teacher details cannot be null.");
			return;
		}

		if (teacher.getTeacherId() <= 0) {
			System.out.println("Teacher ID must be a positive number.");
			return;
		}

		if (teacherAlreadyExists(teacher.getTeacherId())) {
			System.out.println("Teacher with ID " + teacher.getTeacherId() + " already exists.");
			return;
		}

		String fname = teacher.getFirstName();
		if (fname == null || fname.trim().isEmpty() || fname.matches("\\d+")) {
			System.out.println("First name cannot be empty or numeric.");
			return;
		}

		String lname = teacher.getLastName();
		if (lname == null || lname.trim().isEmpty() || lname.matches("\\d+")) {
			System.out.println("Last name cannot be empty or numeric.");
			return;
		}

		String mobile = teacher.getMobileNo();
		if (mobile == null || !mobile.matches("\\d{10}")) {
			System.out.println("Mobile number must be exactly 10 digits.");
			return;
		}

		String email = teacher.getEmailId();
		if (email == null || !email.contains("@email")) {
			System.out.println("Email must contain '@email'.");
			return;
		}
		String city = teacherProfile.getCity();
        String qualification = teacherProfile.getQualification();
        int experience = teacherProfile.getExperience();
        double salary = teacherProfile.getSalary();

        if (city == null || city.trim().isEmpty()) {
            System.out.println("City cannot be empty.");
            return;
        }
        
        if (!city.matches("^[a-zA-Z\\s]+$")) {
            System.out.println("City should contain only alphabets.");
            return;
        }

        if (qualification == null || qualification.trim().isEmpty()) {
            System.out.println("Qualification cannot be empty.");
            return;
        }

        if (experience < 0) {
            System.out.println("Experience cannot be negative.");
            return;
        }

        if (salary < 0) {
            System.out.println("Salary cannot be negative.");
            return;
        }
		System.out.println("Teacher added successfully.");
		teacherDao.addTeacher(teacher,teacherProfile);
	}

//	public boolean addTeacherProfile(TeacherProfile teacherProfile) {
//        String city = teacherProfile.getCity();
//        String qualification = teacherProfile.getQualification();
//        int experience = teacherProfile.getExperience();
//        double salary = teacherProfile.getSalary();
//
//        if (city == null || city.trim().isEmpty()) {
//            System.out.println("City cannot be empty.");
//            return false;
//        }
//        
//        if (!city.matches("^[a-zA-Z\\s]+$")) {
//            System.out.println("City should contain only alphabets.");
//            return false;
//        }
//
//        if (qualification == null || qualification.trim().isEmpty()) {
//            System.out.println("Qualification cannot be empty.");
//            return false;
//        }
//
//        if (experience < 0) {
//            System.out.println("Experience cannot be negative.");
//            return false;
//        }
//
//        if (salary < 0) {
//            System.out.println("Salary cannot be negative.");
//            return false;
//        }
//
//        teacherDao.addTeacherProfile(teacherProfile);
//        System.out.println("Teacher profile added successfully.");
//        return true;
//    }

	public void searchATeacher(int teacherId) {
		if (teacherId <= 0) {
			System.out.println("Invalid Teacher ID.");
			teacherDao.searchATeacher(teacherId);
			return;
		}
		if (!teacherDao.isTeacherPresent(teacherId)) {
			System.out.println("Teacher ID " + teacherId + " does not exist.");
			return;
		}
		teacherDao.searchATeacher(teacherId);
	}

	public void deleteTeacher(int teacherId) {
		if (teacherId <= 0) {
			System.out.println("Invalid Teacher ID.");
			return;
		}

		if (!teacherDao.isTeacherPresent(teacherId)) {
			System.out.println("Teacher ID " + teacherId + " does not exist.");
			return;
		}

		if (!teacherDao.isTeacherActive(teacherId)) {
			System.out.println("Teacher ID " + teacherId + " is already deleted.");
			return;
		}

		boolean deleted = teacherDao.deleteATeacher(teacherId);
	}

	public void removeASubject(String courseSubId, int teacherId) {
		if (courseSubId == null || courseSubId.isEmpty()) {
			System.out.println("Course Subject ID cannot be empty.");
			return;
		}
		if (teacherId <= 0) {
			System.out.println("Invalid Teacher ID.");
			return;
		}
		if (!teacherDao.isTeacherPresent(teacherId)) {
			System.out.println("Teacher ID " + teacherId + " does not exist.");
			return;
		}
		teacherDao.removeASubject(courseSubId, teacherId);
	}

	public void assignSubject(String courseSubjectId, int teacherId) {
	    if (courseSubjectId == null || courseSubjectId.trim().isEmpty()) {
	        System.out.println("Course Subject ID cannot be empty.");
	        return;
	    }

	    if (teacherId <= 0) {
	        System.out.println("Teacher ID must be a positive integer.");
	        return;
	    }

	    if (!teacherDao.isTeacherPresent(teacherId)) {
	        System.out.println("Teacher not found with ID: " + teacherId);
	        return;
	    }

	    if (!teacherDao.isCourseSubjectPresent(courseSubjectId)) {
	        System.out.println("Course Subject ID not found: " + courseSubjectId);
	        return;
	    }

	    if (teacherDao.isSubjectAssigned(teacherId, courseSubjectId)) {
	        System.out.println("Subject already assigned to this teacher.");
	        return;
	    }

	    teacherDao.assignSubject(courseSubjectId, teacherId, true);
	}
	
	public boolean activateTeacher(int teacherId) {
        if (!teacherDao.isTeacherPresent(teacherId)) {
            System.out.println("Teacher with ID " + teacherId + " does not exist.");
            return false;
        }

        boolean success = teacherDao.activateTeacher(teacherId);
        if (success) {
            System.out.println("Teacher with ID " + teacherId + " has been activated.");
        } else {
            System.out.println("Failed to activate the teacher.");
        }
        return success;
    }

}
