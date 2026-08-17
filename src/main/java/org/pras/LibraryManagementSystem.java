package org.pras;
import java.util.Scanner;
import org.pras.menus.AdminMenu;
import org.pras.menus.LibrarianMenu;
import org.pras.menus.StudentMenu;
import org.pras.models.Admin;
import org.pras.models.Librarian;
import org.pras.models.Student;
import org.pras.models.SystemSettings;
import org.pras.services.AdminService;
import org.pras.services.BookService;
import org.pras.services.BorrowService;
import org.pras.services.LibrarianService;
import org.pras.services.ReportService;
import org.pras.services.StudentService;

public class LibraryManagementSystem {

    Scanner sc = new Scanner(System.in);


    SystemSettings settings = new SystemSettings(1,10, 3, 7, 2);

    BookService bookService;
    StudentService studentService;
    LibrarianService librarianService;
    AdminService adminService;
    BorrowService borrowService;
    ReportService reportService;

    StudentMenu studentMenu;
    LibrarianMenu librarianMenu;
    AdminMenu adminMenu;


    public LibraryManagementSystem() {

        //bookService = new BookService();

       // studentService = new StudentService();

        librarianService = new LibrarianService();

        adminService = new AdminService();

       // borrowService = new BorrowService(

    //    );

        reportService = new ReportService(

        );

        studentMenu = new StudentMenu(
                bookService,
                borrowService,
                studentService
        );

        librarianMenu = new LibrarianMenu(
                bookService,
                studentService,
                borrowService
        );

        adminMenu = new AdminMenu(
                adminService,
                librarianService,
                studentService,
                reportService
        );
    }

    public void start() {

        while(true) {

            System.out.println("\n===== Library Management System =====");
            System.out.println("1. Student Login");
            System.out.println("2. Librarian Login");
            System.out.println("3. Admin Login");
            System.out.println("4. Exit");

           int choice;

            while(true) {

                try {
                    System.out.print("Enter your choice: ");
                    choice = sc.nextInt();
                    sc.nextLine();
                    break;
                }
                catch(Exception e) {
                    System.out.println("Invalid input. Please enter a number.");
                    sc.nextLine();
                }
            }

            switch(choice) {

                case 1:
                    studentLoginMenu();
                    break;

                case 2:
                    librarianLoginMenu();
                    break;

                case 3:
                    adminLoginMenu();
                    break;

                case 4:
                    System.out.println("Thank you for using LMS");
                    return;

                default:
                    System.out.println("Invalid choice");
            }
        }
    }

   public void studentLoginMenu() {

    int studentId = 0;

    while(true) {

        try {
            System.out.print("Enter Student ID: ");
            studentId = sc.nextInt();
            sc.nextLine();

            if(studentId <= 0) {
                System.out.println("Student ID must be greater than 0");
                continue;
            }

            break;
        }
        catch(Exception e) {
            System.out.println("Invalid input. Please enter a valid Student ID.");
            sc.nextLine();
        }
    }

    System.out.print("Enter Password: ");
    String password = sc.nextLine();

    while(password.trim().isEmpty()) {
        System.out.println("Password cannot be empty");
        System.out.print("Enter Password: ");
        password = sc.nextLine();
    }
    Student student = studentService.loginStudent(studentId, password);

    if(student != null) {
        System.out.println("Login Successfull");
        System.out.println();
        //StudentService.displayReservationNotifications(student.getStudentId());
        studentMenu.showMenu(student);
    }
    else {
        System.out.println("Invalid Student ID or Password");
    }
}

public void librarianLoginMenu() {

    System.out.print("Enter Username: ");
    String username = sc.nextLine();

    while(username.trim().isEmpty()) {
        System.out.println("Username cannot be empty");
        System.out.print("Enter Username: ");
        username = sc.nextLine();
    }

    System.out.print("Enter Password: ");
    String password = sc.nextLine();

    while(password.trim().isEmpty()) {
        System.out.println("Password cannot be empty");
        System.out.print("Enter Password: ");
        password = sc.nextLine();
    }

    Librarian librarian = librarianService.loginLibrarian(username, password);

    if(librarian != null) {
        System.out.println("Login successful");
        librarianMenu.showMenu(librarian);
    }
    else {
        System.out.println("Invalid Username or Password");
    }
}

public void adminLoginMenu() {

    System.out.print("Enter Username: ");
    String username = sc.nextLine();

    while(username.trim().isEmpty()) {
        System.out.println("Username cannot be empty");
        System.out.print("Enter Username: ");
        username = sc.nextLine();
    }

    System.out.print("Enter Password: ");
    String password = sc.nextLine();

    while(password.trim().isEmpty()) {
        System.out.println("Password cannot be empty");
        System.out.print("Enter Password: ");
        password = sc.nextLine();
    }

    Admin admin = adminService.loginAdmin(username, password);

    if(admin != null) {
        System.out.println("Login successful");
        adminMenu.showMenu(admin);
    }
    else {
        System.out.println("Invalid Username or Password");
    }
}
}