package com.hexaware.sis.main;

import java.sql.Date;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
import java.util.*;

//import com.hexaware.sis.dao.*;
import com.hexaware.sis.entity.*;
import com.hexaware.sis.exception.*;
import com.hexaware.sis.service.SISServicesImpl;
import com.hexaware.sis.util.DynamicQueryBuilderUtil;

public class SISMainModule {

    private static final Scanner sc = new Scanner(System.in);
    private static final SISServicesImpl sis = new SISServicesImpl();

    public static void main(String[] args) {

    	while (true) {
    	    System.out.println("\nWelcome to Student Information System (SIS)");
    	    System.out.println("1. Student Portal");
    	    System.out.println("2. Teacher Portal");
    	    System.out.println("3. Course Portal");
    	    System.out.println("4. Enrollment Portal");
    	    System.out.println("5. Payment Portal");
    	    System.out.println("6. SIS Services");
    	    System.out.println("7. Exit");

    	    System.out.print("Enter your choice: ");
    	    int option = sc.nextInt();
    	    sc.nextLine(); // consume newline

    	    switch (option) {
    	        case 1:
    	            studentPortal();
    	            break;

    	        case 2:
    	            teacherPortal();
    	            break;

    	        case 3:
    	            coursePortal();
    	            break;

    	        case 4:
    	            enrollmentPortal();
    	            break;

    	        case 5:
    	            paymentPortal();
    	            break;

    	        case 6:
    	            sisServicePortal();
    	            break;

    	        case 7:
    	            System.out.println("Exiting system.");
    	            System.exit(0);

    	        default:
    	            System.out.println("Invalid option. Try again.\n");
    	    }
    	}

    }

    private static void studentPortal() {
        boolean inside = true;

        while (inside) {
            System.out.println("\n--- Student Portal ---");
            System.out.println("1. Add Student");
            System.out.println("2. Update Student Info");
            System.out.println("3. Delete Student");
            System.out.println("4. Display Student Info");
            System.out.println("5. Display All Students");
            System.out.println("6. Enroll in Course");
            System.out.println("7. Get Enrolled Courses");
            System.out.println("8. Get Payment History");
            System.out.println("9. Go Back");

            System.out.print("Enter your option: ");
            int opt = sc.nextInt();
            sc.nextLine();

            switch (opt) {

                case 1: // Add Student
                    try {
                        System.out.println("Enter the following Student Details :");
                        System.out.println("Student ID: ");
                        String id = sc.nextLine();
                        System.out.println("First Name: ");
                        String fn = sc.nextLine();
                        System.out.println("Last Name: ");
                        String ln = sc.nextLine();
                        System.out.println("DOB (yyyy-mm-dd): ");
                        Date dob = null;
                        try {
                            dob = Date.valueOf(sc.nextLine());
                        } catch (Exception e) {
                            System.out.println("Invalid Date Format. " + e.getMessage());
                        }

                        //if (dob == null) break;

                        System.out.println("Email: ");
                        String email = sc.nextLine();
                        System.out.println("Phone: ");
                        long phone = 0;
                        try {
                        	phone = sc.nextLong();
                        } catch (Exception e) {
                            System.out.println("Invalid Phone Number Format. " + e.getMessage());
                        }
                        sc.nextLine();

                        Student s = new Student(id, fn, ln, dob, email, phone);
                        boolean result = sis.addStudent(s);

                        if (result)
                            System.out.println("Student inserted successfully.\n");
                        else
                            System.out.println("Student insertion failed.\n");

                    } catch (InvalidStudentDataException e) {
                        System.out.println("Invalid data. " + e.getMessage());
                    }
                    break;

                case 2: // Update Student
                    try {
                        System.out.println("Enter the following Student Details :");
                        System.out.println("Student ID: ");
                        String sid = sc.nextLine();
                        System.out.println("New First Name: ");
                        String fn = sc.nextLine();
                        System.out.println("New Last Name: ");
                        String ln = sc.nextLine();
                        System.out.println("New DOB (yyyy-mm-dd): ");
                        Date dob = null;
                        try {
                            dob = Date.valueOf(sc.nextLine());
                        } catch (Exception e) {
                            System.out.println("Invalid Date Format. " + e.getMessage());
                        }

                        //if (dob == null) break;

                        System.out.println("New Email: ");
                        String email = sc.nextLine();
                        System.out.println("New Phone: ");
                        long phone = 0;
                        try {
                        	phone = sc.nextLong();
                        } catch (Exception e) {
                            System.out.println("Invalid Phone Number Format. " + e.getMessage());
                        }
                        sc.nextLine();

                        boolean result = sis.updateStudentInfo(sid, fn, ln, dob, email, phone);

                        if (result)
                            System.out.println("Student updated successfully.\n");
                        else
                            System.out.println("Student update failed.\n");

                    } catch (InvalidStudentDataException e) {
                        System.out.println("Invalid data. " + e.getMessage());
                    } catch (StudentNotFoundException e) {
                        System.out.println("Student not found. " + e.getMessage());
                    }
                    break;

                case 3: // Delete Student
                    try {
                        System.out.println("Enter Student ID: ");
                        String id = sc.nextLine();
                        boolean result = sis.deleteStudent(id);

                        if (result)
                            System.out.println("Student deleted successfully.\n");
                        else
                            System.out.println("Student deletion failed.\n");

                    } catch (StudentNotFoundException e) {
                        System.out.println("Student not found. " + e.getMessage());
                    }
                    break;

                case 4: // Display Student Info
                    try {
                        System.out.println("Enter Student ID: ");
                        String id = sc.nextLine();
                        Student s = sis.getStudentById(id);

                        System.out.println("Student ID   : " + s.getStudentId());
                        System.out.println("First Name   : " + s.getFirstName());
                        System.out.println("Last Name    : " + s.getLastName());
                        System.out.println("DOB          : " + s.getDateOfBirth());
                        System.out.println("Email        : " + s.getEmail());
                        System.out.println("Phone Number : " + s.getPhoneNumber() + "\n");

                    } catch (StudentNotFoundException e) {
                        System.out.println("Student not found. " + e.getMessage());
                    }
                    break;

                case 5: // Display All Students
                    List<Student> all = sis.getAllStudents();
                    if (all.isEmpty()) {
                        System.out.println("No students found.\n");
                    } else {
                        System.out.println("Student Details List :.\n");
                        for (Student s : all) {
                            System.out.println("Student ID   : " + s.getStudentId());
                            System.out.println("First Name   : " + s.getFirstName());
                            System.out.println("Last Name    : " + s.getLastName());
                            System.out.println("DOB          : " + s.getDateOfBirth());
                            System.out.println("Email        : " + s.getEmail());
                            System.out.println("Phone Number : " + s.getPhoneNumber());
                            System.out.println("----------------------------------\n");
                        }
                    }
                    break;

                case 6: // Enroll in Course
                    try {
                        System.out.println("Enter the following Details :");
                        System.out.print("Enter Student ID: ");
                        String sid = sc.nextLine();

                        System.out.print("Enter Course ID: ");
                        int cid = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Enter Enrollment Date (yyyy-mm-dd): ");
                        Date edate = null;
                        try {
                            edate = Date.valueOf(sc.nextLine());
                        } catch (Exception e) {
                            System.out.println("Invalid Date Format. " + e.getMessage());
                        }

                        //if (edate == null) break;

                        boolean result = sis.enrollInCourse(sid, cid, edate);

                        if (result)
                            System.out.println("Student enrolled in course successfully.\n");
                        else
                            System.out.println("Course enrollment failed.\n");

                    } catch (StudentNotFoundException e) {
                        System.out.println("Student not found. " + e.getMessage());
                    } catch (CourseNotFoundException e) {
                        System.out.println("Course not found. " + e.getMessage());
                    } catch (DuplicateEnrollmentException e) {
                        System.out.println("Enrollment already exists. " + e.getMessage());
                    } catch (InvalidEnrollmentDataException e) {
                        System.out.println("Invalid enrollment data. " + e.getMessage());
                    }
                    break;

                case 7: // Get Enrolled Courses
                    try {
                        System.out.print("Enter Student ID: ");
                        String sid = sc.nextLine();
                        List<Course> courses = sis.getEnrolledCourses(sid);

                        if (courses.isEmpty()) {
                            System.out.println("No courses found.\n");
                        } else {
                            System.out.println("Courses for student ID: " + sid);
                            for (Course c : courses) {
                                System.out.println("- " + c.getCourseName() + " (ID: " + c.getCourseId() + ")");
                            }
                            System.out.println();
                        }

                    } catch (StudentNotFoundException e) {
                        System.out.println("Student not found. " + e.getMessage());
                    }
                    break;

                case 8: // Get Payment History
                    try {
                        System.out.print("Enter Student ID: ");
                        String sid = sc.nextLine();
                        List<Payment> payments = sis.getPaymentHistory(sid);

                        if (payments.isEmpty()) {
                            System.out.println("No payments found.\n");
                        } else {
                            System.out.println("Payments for student ID: " + sid);
                            for (Payment p : payments) {
                                System.out.println("- ₹" + p.getAmount() + " on " + p.getPaymentDate());
                            }
                            System.out.println();
                        }

                    } catch (StudentNotFoundException e) {
                        System.out.println("Student not found. " + e.getMessage());
                    }
                    break;

                case 9:
                    inside = false;
                    break;

                default:
                    System.out.println("Invalid option. Try again.\n");
            }
        }
    }


    private static void teacherPortal() {
        boolean inside = true;

        while (inside) {
            System.out.println("\n--- Teacher Portal ---");
            System.out.println("1. Add Teacher");
            System.out.println("2. Update Teacher Info");
            System.out.println("3. Delete Teacher");
            System.out.println("4. Display Teacher Info");
            System.out.println("5. Display All Teachers");
            System.out.println("6. Get Assigned Courses");
            System.out.println("7. Go Back");

            System.out.print("Enter your option: ");
            int opt = sc.nextInt();
            sc.nextLine();

            switch (opt) {
                case 1:
                    try {
                        System.out.println("Enter the following Teacher Details :");

                        System.out.print("Teacher ID: ");
                        String id = sc.nextLine();
                        System.out.print("First Name: ");
                        String fn = sc.nextLine();
                        System.out.print("Last Name: ");
                        String ln = sc.nextLine();
                        System.out.print("Email: ");
                        String email = sc.nextLine();

                        Teacher t = new Teacher(id, fn, ln, email);
                        if (sis.addTeacher(t)) {
                            System.out.println("Teacher inserted successfully.\n");
                        } else {
                            System.out.println("Teacher insertion failed.\n");
                        }

                    } catch (InvalidTeacherDataException e) {
                        System.out.println("Error. " + e.getMessage());
                    }
                    break;

                case 2:
                    try {
                        System.out.println("Enter the following Updated Teacher Details :");

                        System.out.print("Teacher ID: ");
                        String id = sc.nextLine();
                        System.out.print("New First Name: ");
                        String fn = sc.nextLine();
                        System.out.print("New Last Name: ");
                        String ln = sc.nextLine();
                        System.out.print("New Email: ");
                        String email = sc.nextLine();

                        if (sis.updateTeacherInfo(id, fn, ln, email)) {
                            System.out.println("Teacher info updated successfully.\n");
                        } else {
                            System.out.println("Teacher update failed.\n");
                        }

                    } catch (TeacherNotFoundException e) {
                        System.out.println("Error. " + e.getMessage());
                    } catch (InvalidTeacherDataException e) {
                        System.out.println("Error. " + e.getMessage());
                    }
                    break;

                case 3:
                    try {
                        System.out.print("Enter Teacher ID to delete: ");
                        String id = sc.nextLine();

                        if (sis.deleteTeacher(id)) {
                            System.out.println("Teacher deleted successfully.\n");
                        } else {
                            System.out.println("Teacher deletion failed.\n");
                        }

                    } catch (TeacherNotFoundException e) {
                        System.out.println("Error. " + e.getMessage());
                    }
                    break;

                case 4:
                    try {
                        System.out.print("Enter Teacher ID: ");
                        String id = sc.nextLine();
                        Teacher t = sis.getTeacherById(id);

                        System.out.println("Teacher ID : " + t.getTeacherId());
                        System.out.println("First Name : " + t.getFirstName());
                        System.out.println("Last Name  : " + t.getLastName());
                        System.out.println("Email      : " + t.getEmail() + "\n");

                    } catch (TeacherNotFoundException e) {
                        System.out.println("Error. " + e.getMessage());
                    }
                    break;

                case 5:
                    List<Teacher> all = sis.getAllTeachers();
                    if (all.isEmpty()) {
                        System.out.println("No teachers found.\n");
                    } else {
                        System.out.println("Teacher Details List :\n");
                        for (Teacher t : all) {
                            System.out.println("Teacher ID : " + t.getTeacherId());
                            System.out.println("First Name : " + t.getFirstName());
                            System.out.println("Last Name  : " + t.getLastName());
                            System.out.println("Email      : " + t.getEmail());
                            System.out.println("----------------------------------\n");
                        }
                    }
                    break;

                case 6:
                    try {
                        System.out.print("Enter Teacher ID: ");
                        String id = sc.nextLine();

                        List<Course> assignedCourses = sis.getAssignedCourses(id);
                        if (assignedCourses.isEmpty()) {
                            System.out.println("No courses assigned to teacher ID: " + id + "\n");
                        } else {
                            System.out.println("Courses assigned to teacher ID " + id + ":");
                            for (Course course : assignedCourses) {
                                System.out.println("- " + course.getCourseName() + " (ID: " + course.getCourseId() + ")");
                            }
                            System.out.println();
                        }

                    } catch (TeacherNotFoundException e) {
                        System.out.println("Error. " + e.getMessage());
                    }
                    break;

                case 7:
                    inside = false;
                    break;

                default:
                    System.out.println("Invalid option. Try again.\n");
            }
        }
    }

    
    
    private static void coursePortal() {
        boolean inside = true;

        while (inside) {
            System.out.println("\n--- Course Portal ---");
            System.out.println("1. Add Course");
            System.out.println("2. Update Course Info");
            System.out.println("3. Delete Course");
            System.out.println("4. Display Course Info");
            System.out.println("5. Display All Courses");
            System.out.println("6. Assign Teacher to Course");
            System.out.println("7. Get Enrollments by Course");
            System.out.println("8. Get Assigned Teacher");
            System.out.println("9. Go Back");

            System.out.print("Enter your option: ");
            int opt = sc.nextInt();
            sc.nextLine();

            switch (opt) {
                case 1:
                    try {
                        System.out.println("Enter the following details:");
                        System.out.print("Course Name: ");
                        String cname = sc.nextLine();
                        System.out.print("Credits: ");
                        int credits = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Teacher ID: ");
                        String tid = sc.nextLine();

                        Course course = new Course(cname, credits, tid);
                        if (sis.addCourse(course)) {
                            System.out.println("Course inserted successfully.\n");
                        } else {
                            System.out.println("Course insertion failed.\n");
                        }

                    } catch (InvalidCourseDataException e) {
                        System.out.println("Error: " + e.getMessage());
                    } catch (TeacherNotFoundException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 2:
                    try {
                        System.out.println("Enter the following details:");
                        System.out.print("Course ID: ");
                        int cid = sc.nextInt();
                        sc.nextLine();
                        System.out.print("New Course Name: ");
                        String cname = sc.nextLine();
                        System.out.print("New Credits: ");
                        int credits = sc.nextInt();
                        sc.nextLine();
                        System.out.print("New Teacher ID: ");
                        String tid = sc.nextLine();

                        Course updated = new Course(cid, cname, credits, tid);
                        if (sis.updateCourse(updated)) {
                            System.out.println("Course updated successfully.\n");
                        } else {
                            System.out.println("Course update failed.\n");
                        }

                    } catch (InvalidCourseDataException e) {
                        System.out.println("Error: " + e.getMessage());
                    } catch (CourseNotFoundException e) {
                        System.out.println("Error: " + e.getMessage());
                    } catch (TeacherNotFoundException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 3:
                    try {
                        System.out.print("Enter Course ID: ");
                        int cid = sc.nextInt();
                        sc.nextLine();
                        if (sis.deleteCourse(cid)) {
                            System.out.println("Course deleted successfully.\n");
                        } else {
                            System.out.println("Course deletion failed.\n");
                        }

                    } catch (CourseNotFoundException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 4:
                    try {
                        System.out.print("Enter Course ID: ");
                        int cid = sc.nextInt();
                        sc.nextLine();
                        Course c = sis.getCourseById(cid);
                        System.out.println("Course ID    : " + c.getCourseId());
                        System.out.println("Course Name  : " + c.getCourseName());
                        System.out.println("Credits      : " + c.getCredits());
                        System.out.println("Teacher ID   : " + c.getTeacherId() + "\n");

                    } catch (CourseNotFoundException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 5:
                    List<Course> allCourses = sis.getAllCourses();
                    if (allCourses.isEmpty()) {
                        System.out.println("No courses found.\n");
                    } else {
                        System.out.println("Course Details List:\n");
                        for (Course c : allCourses) {
                            try {
                                Course full = sis.getCourseById(c.getCourseId());
                                System.out.println("Course ID    : " + full.getCourseId());
                                System.out.println("Course Name  : " + full.getCourseName());
                                System.out.println("Credits      : " + full.getCredits());
                                System.out.println("Teacher ID   : " + full.getTeacherId());
                                System.out.println("----------------------------------\n");
                            } catch (CourseNotFoundException ignored) {}
                        }
                    }
                    break;

                case 6:
                    try {
                        System.out.println("Enter the following details:");
                        System.out.print("Course ID: ");
                        int cid = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Teacher ID: ");
                        String tid = sc.nextLine();

                        if (sis.assignTeacherToCourse(cid, tid)) {
                            System.out.println("Teacher assigned to course successfully.\n");
                        } else {
                            System.out.println("Failed to assign teacher.\n");
                        }

                    } catch (CourseNotFoundException e) {
                        System.out.println("Error: " + e.getMessage());
                    } catch (TeacherNotFoundException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 7:
                    try {
                        System.out.print("Enter Course ID: ");
                        int cid = sc.nextInt();
                        sc.nextLine();
                        List<Enrollment> enrollments = sis.getEnrollmentsByCourseViaCourse(cid);

                        if (enrollments.isEmpty()) {
                            System.out.println("No enrollments found for this course.\n");
                        } else {
                            System.out.println("Enrollments for course ID " + cid + ":");
                            for (Enrollment e : enrollments) {
                                System.out.println("- Enrollment ID: " + e.getEnrollmentId() +
                                        ", Student ID: " + e.getStudentId() +
                                        ", Date: " + e.getEnrollmentDate());
                                System.out.println("----------------------------------\n");
                            }
                        }

                    } catch (CourseNotFoundException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 8:
                    try {
                        System.out.print("Enter Course ID: ");
                        int cid = sc.nextInt();
                        sc.nextLine();
                        Teacher t = sis.getTeacherByCourse(cid);
                        System.out.println("Teacher assigned to course ID " + cid + ":");
                        System.out.println("Teacher ID : " + t.getTeacherId());
                        System.out.println("First Name : " + t.getFirstName());
                        System.out.println("Last Name  : " + t.getLastName());
                        System.out.println("Email      : " + t.getEmail() + "\n");

                    } catch (CourseNotFoundException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 9:
                    inside = false;
                    break;

                default:
                    System.out.println("Invalid option. Try again.\n");
            }
        }
    }

    
    private static void enrollmentPortal() {
        boolean inside = true;

        while (inside) {
            System.out.println("\n--- Enrollment Portal ---");
            System.out.println("1. Insert Enrollment");
            System.out.println("2. Delete Enrollment");
            System.out.println("3. Display Enrollment by ID");
            System.out.println("4. Display All Enrollments");
            System.out.println("5. Display Enrollments by Student ID");
            System.out.println("6. Display Enrollments by Course ID");
            System.out.println("7. Go Back");

            System.out.print("Enter your option: ");
            int opt = sc.nextInt();
            sc.nextLine();

            switch (opt) {
                case 1:
                    try {
                        System.out.println("Enter the following details:");
                        System.out.print("Enter Student ID: ");
                        String sid = sc.nextLine();
                        System.out.print("Enter Course ID: ");
                        int cid = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Enter Enrollment Date (yyyy-mm-dd): ");
                        Date edate = null;
                        try {
                            edate = Date.valueOf(sc.nextLine());
                        } catch (Exception e) {
                            System.out.println("Invalid Date Format. " + e.getMessage());
                            break;
                        }

                        Enrollment e = new Enrollment(sid, cid, edate);
                        boolean added = sis.addEnrollment(e);
                        if (!added) {
                            System.out.println("Enrollment insertion failed.\n");
                        }

                    } catch (InvalidEnrollmentDataException e) {
                        System.out.println("Invalid data. " + e.getMessage());
                    } catch (StudentNotFoundException e) {
                        System.out.println("Student error. " + e.getMessage());
                    } catch (CourseNotFoundException e) {
                        System.out.println("Course error. " + e.getMessage());
                    } catch (DuplicateEnrollmentException e) {
                        System.out.println("Duplicate error. " + e.getMessage());
                    }
                    break;

                case 2:
                    try {
                        System.out.print("Enter Enrollment ID to delete: ");
                        int eid = sc.nextInt();
                        sc.nextLine();
                        boolean deleted = sis.removeEnrollment(eid);
                        if (!deleted) {
                            System.out.println("Enrollment deletion failed.\n");
                        }

                    } catch (EnrollmentNotFoundException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 3:
                    try {
                        System.out.print("Enter Enrollment ID: ");
                        int eid = sc.nextInt();
                        sc.nextLine();

                        Enrollment e = sis.getEnrollmentById(eid);

                        System.out.println("Enrollment ID : " + e.getEnrollmentId());
                        System.out.println("Student ID    : " + e.getStudentId());
                        System.out.println("Course ID     : " + e.getCourseId());
                        System.out.println("Enroll Date   : " + e.getEnrollmentDate());
                        System.out.println("----------------------------------\n");

                    } catch (EnrollmentNotFoundException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 4:
                    List<Enrollment> allEnrollments = sis.getAllEnrollments();
                    if (allEnrollments.isEmpty()) {
                        System.out.println("No enrollments found.\n");
                    } else {
                        System.out.println("Enrollment Details List:\n");
                        for (Enrollment e : allEnrollments) {
                            try {
                                Enrollment full = sis.getEnrollmentById(e.getEnrollmentId());
                                System.out.println("Enrollment ID : " + full.getEnrollmentId());
                                System.out.println("Student ID    : " + full.getStudentId());
                                System.out.println("Course ID     : " + full.getCourseId());
                                System.out.println("Enroll Date   : " + full.getEnrollmentDate());
                                System.out.println("----------------------------------\n");
                            } catch (EnrollmentNotFoundException ignored) {}
                        }
                    }
                    break;
                   

                case 5:
                    try {
                        System.out.print("Enter Student ID: ");
                        String sid = sc.nextLine();

                        List<Enrollment> studentEnrollments = sis.getEnrollmentsByStudent(sid);

                        if (studentEnrollments.isEmpty()) {
                            System.out.println("No enrollments found for student ID: " + sid + "\n");
                        } else {
                            System.out.println("Enrollments for Student ID " + sid + ":\n");
                            for (Enrollment e : studentEnrollments) {
                                System.out.println("- Enrollment ID: " + e.getEnrollmentId());
                                System.out.println("  Course ID     : " + e.getCourseId());
                                System.out.println("  Enroll Date   : " + e.getEnrollmentDate());
                                System.out.println("----------------------------------\n");
                            }
                        }

                    } catch (StudentNotFoundException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 6:
                    try {
                        System.out.print("Enter Course ID: ");
                        int cid = sc.nextInt();
                        sc.nextLine();
                        List<Enrollment> list = sis.getEnrollmentsByCourseViaEnrollment(cid);
                        if (list.isEmpty()) {
                            System.out.println("No enrollments found for course ID: " + cid + "\n");
                        } else {
                            System.out.println("Enrollments for Course ID " + cid + ":\n");
                            for (Enrollment e : list) {
                                System.out.println("- Enrollment ID: " + e.getEnrollmentId());
                                System.out.println("  Student ID   : " + e.getStudentId());
                                System.out.println("  Enroll Date  : " + e.getEnrollmentDate());
                                System.out.println("----------------------------------\n");
                            }
                        }
                    } catch (CourseNotFoundException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 7:
                    inside = false;
                    break;

                default:
                    System.out.println("Invalid option. Try again.\n");
            }
        }
    }
    
    private static void paymentPortal() {
        boolean inside = true;

        while (inside) {
            System.out.println("\n--- Payment Portal ---");
            System.out.println("1. Add Payment");
            System.out.println("2. Display Payment by ID");
            System.out.println("3. Display All Payments");
            System.out.println("4. Display Payments by Student ID");
            System.out.println("5. Go Back");

            System.out.print("Enter your option: ");
            int opt = sc.nextInt();
            sc.nextLine(); // clear newline

            switch (opt) {
                case 1:
                    try {
                        System.out.println("Enter the following Payment Details:");

                        System.out.print("Student ID: ");
                        String sid = sc.nextLine();

                        System.out.print("Amount ($): ");
                        double amount = sc.nextDouble();
                        sc.nextLine();

                        System.out.print("Payment Date (yyyy-mm-dd): ");
                        Date pdate = null;
                        try {
                            pdate = Date.valueOf(sc.nextLine());
                        } catch (Exception e) {
                            System.out.println("Invalid Date Format. " + e.getMessage());
                            break;
                        }

                        Payment payment = new Payment(sid, amount, pdate);
                        boolean result = sis.addPayment(payment);

                        if (result) {
                            System.out.println("Payment inserted successfully.\n");
                        } else {
                            System.out.println("Payment insertion failed.\n");
                        }

                    } catch (PaymentValidationException e) {
                        System.out.println("Validation Error: " + e.getMessage());
                    } catch (StudentNotFoundException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 2:
                    try {
                        System.out.print("Enter Payment ID: ");
                        int pid = sc.nextInt();
                        sc.nextLine();

                        Payment p = sis.getPaymentById(pid);
                        System.out.println("Payment ID   : " + p.getPaymentId());
                        System.out.println("Student ID   : " + p.getStudentId());
                        System.out.println("Amount Paid  : $" + p.getAmount());
                        System.out.println("Payment Date : " + p.getPaymentDate() + "\n");

                    } catch (PaymentNotFoundException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 3:
                    List<Payment> all = sis.getAllPayments();
                    if (all.isEmpty()) {
                        System.out.println("No payments found.\n");
                    } else {
                        System.out.println("Payment Details List:\n");
                        for (Payment p : all) {
                            try {
                                Payment full = sis.getPaymentById(p.getPaymentId());
                                System.out.println("Payment ID   : " + full.getPaymentId());
                                System.out.println("Student ID   : " + full.getStudentId());
                                System.out.println("Amount Paid  : $" + full.getAmount());
                                System.out.println("Payment Date : " + full.getPaymentDate());
                                System.out.println("----------------------------------\n");
                            } catch (PaymentNotFoundException ignored) {}
                        }
                    }
                    break;

                case 4:
                    try {
                        System.out.print("Enter Student ID: ");
                        String sid = sc.nextLine();

                        List<Payment> list = sis.getPaymentsByStudent(sid);
                        if (list.isEmpty()) {
                            System.out.println("No payments found for student ID: " + sid + "\n");
                        } else {
                            System.out.println("Payments for student ID " + sid + ":\n");
                            for (Payment p : list) {
                                System.out.println("- $" + p.getAmount() + " on " + p.getPaymentDate());
                            }
                            System.out.println();
                        }

                    } catch (StudentNotFoundException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 5:
                    inside = false;
                    break;

                default:
                    System.out.println("Invalid option. Try again.\n");
            }
        }
    }
    
    
    
    private static void sisServicePortal() {
        boolean inside = true;

        while (inside) {
            System.out.println("\n--- SIS Service Portal ---");
            System.out.println("1. Enroll Student in Course");
            System.out.println("2. Assign Teacher to Course");
            System.out.println("3. Record Payment");
            System.out.println("4. Generate Enrollment Report");
            System.out.println("5. Generate Payment Report");
            System.out.println("6. Course Statistics");
            System.out.println("7. Run Custom SQL Query");
            System.out.println("8. Go Back");

            System.out.print("Enter your option: ");
            int opt = sc.nextInt();
            sc.nextLine();

            switch (opt) {
                case 1: // Enroll Student in Course
                    try {
                        System.out.print("Enter Student ID: ");
                        String sid = sc.nextLine();
                        Student student = sis.getStudentById(sid);

                        System.out.print("Enter Course ID: ");
                        int cid = sc.nextInt();
                        sc.nextLine();
                        Course course = sis.getCourseById(cid);

                        boolean result = sis.enrollStudentInCourse(student, course);
                        if (result)
                            System.out.println("Student enrolled successfully.\n");
                        else
                            System.out.println("Enrollment failed.\n");

                    } catch (StudentNotFoundException e) {
                        System.out.println("Error: " + e.getMessage());
                    } catch (CourseNotFoundException e) {
                        System.out.println("Error: " + e.getMessage());
                    } catch (DuplicateEnrollmentException e) {
                        System.out.println("Error: " + e.getMessage());
                    } catch (InvalidEnrollmentDataException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 2: // Assign Teacher to Course
                    try {
                        System.out.print("Enter Teacher ID: ");
                        String tid = sc.nextLine();
                        Teacher teacher = sis.getTeacherById(tid);

                        System.out.print("Enter Course ID: ");
                        int cid = sc.nextInt();
                        sc.nextLine();
                        Course course = sis.getCourseById(cid);

                        boolean result = sis.assignTeacherToCourse(teacher, course);
                        if (result)
                            System.out.println("Teacher assigned successfully.\n");
                        else
                            System.out.println("Assignment failed.\n");

                    } catch (TeacherNotFoundException e) {
                        System.out.println("Error: " + e.getMessage());
                    } catch (CourseNotFoundException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 3: // Record Payment
                    try {
                        System.out.print("Enter Student ID: ");
                        String sid = sc.nextLine();
                        Student student = sis.getStudentById(sid);

                        System.out.print("Enter Amount ($): ");
                        double amount = sc.nextDouble();
                        sc.nextLine();

                        System.out.print("Enter Payment Date (yyyy-mm-dd): ");
                        Date pdate = null;
                        try {
                            pdate = Date.valueOf(sc.nextLine());
                        } catch (Exception e) {
                            System.out.println("Invalid date format. " + e.getMessage());
                            break;
                        }

                        boolean result = sis.recordPayment(student, amount, pdate);
                        if (result)
                            System.out.println("Payment recorded successfully.\n");
                        else
                            System.out.println("Payment failed.\n");

                    } catch (StudentNotFoundException e) {
                        System.out.println("Error: " + e.getMessage());
                    } catch (PaymentValidationException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 4: // Generate Enrollment Report
                    try {
                        System.out.print("Enter Course ID: ");
                        int cid = sc.nextInt();
                        sc.nextLine();
                        Course course = sis.getCourseById(cid);

                        List<Enrollment> list = sis.generateEnrollmentReport(course);

                        System.out.println("Enrollment Report for Course: " + course.getCourseName());
                        if (list.isEmpty()) {
                            System.out.println("No students enrolled.\n");
                        } else {
                            for (Enrollment e : list) {
                                System.out.println("- Enrollment ID: " + e.getEnrollmentId()
                                        + ", Student ID: " + e.getStudentId()
                                        + ", Date: " + e.getEnrollmentDate());
                            }
                            System.out.println("Total Enrollments: " + list.size() + "\n");
                        }

                    } catch (CourseNotFoundException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 5: // Generate Payment Report
                    try {
                        System.out.print("Enter Student ID: ");
                        String sid = sc.nextLine();
                        Student student = sis.getStudentById(sid);

                        List<Payment> payments = sis.generatePaymentReport(student);
                        double total = 0;

                        System.out.println("Payment Report for: " + student.getFirstName() + " " + student.getLastName());

                        if (payments.isEmpty()) {
                            System.out.println("No payments found.\n");
                        } else {
                            for (Payment p : payments) {
                                System.out.println("- ₹" + p.getAmount() + " on " + p.getPaymentDate());
                                total += p.getAmount();
                            }
                            System.out.println("Total Paid: ₹" + total + "\n");
                        }

                    } catch (StudentNotFoundException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 6: // Course Statistics
                    try {
                        System.out.print("Enter Course ID: ");
                        int cid = sc.nextInt();
                        sc.nextLine();
                        Course course = sis.getCourseById(cid);

                        int count = sis.calculateCourseStatistics(course);
                        System.out.println("Total Enrollments for " + course.getCourseName() + ": " + count + "\n");

                    } catch (CourseNotFoundException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                    
                case 7:
                    try {
                        System.out.print("Enter table name (Students, Courses, etc.): ");
                        String table = sc.nextLine().trim();

                        System.out.print("Enter columns to select (comma-separated or *): ");
                        String cols = sc.nextLine().trim();
                        String[] columns = cols.equals("*") ? null : cols.split(",");

                        System.out.print("Enter condition (e.g. student_id = 'S001') or leave blank: ");
                        String condition = sc.nextLine().trim();
                        if (condition.isEmpty()) condition = null;

                        System.out.print("Enter order by column (e.g. first_name ASC) or leave blank: ");
                        String orderBy = sc.nextLine().trim();
                        if (orderBy.isEmpty()) orderBy = null;

                        List<String[]> rows = DynamicQueryBuilderUtil.executeDynamicQuery(columns, table, condition, orderBy);

                        if (rows.isEmpty()) {
                            System.out.println("No data found.\n");
                        } else {
                            System.out.println("\nQuery Result:");
                            for (String[] row : rows) {
                                for (String val : row) {
                                    System.out.print(val + "\t");
                                }
                                System.out.println();
                            }
                            System.out.println();
                        }

                    } catch (Exception e) {
                        System.out.println("Error running query: " + e.getMessage());
                    }
                    break;

                case 8:
                    inside = false;
                    break;

                default:
                    System.out.println("Invalid option. Try again.\n");
            }
        }
    }


}
