package org.pras.menus;

import java.sql.Date;
import java.util.Scanner;
import org.pras.models.Admin;
import org.pras.models.Librarian;
import org.pras.services.AdminService;
import org.pras.services.LibrarianService;
import org.pras.services.ReportService;
import org.pras.services.StudentService;

public class AdminMenu {

    Scanner sc = new Scanner(System.in);

    AdminService adminService;
    LibrarianService librarianService;
    StudentService studentService;
    ReportService reportService;

    public AdminMenu(AdminService adminService,
                     LibrarianService librarianService,
                     StudentService studentService,
                     ReportService reportService) {

        this.adminService = adminService;
        this.librarianService = librarianService;
        this.studentService = studentService;
        this.reportService = reportService;
    }

    public void showMenu(Admin admin) {

        while(true) {

            System.out.println("\n===== Admin Menu =====");
            System.out.println("1. Add Librarian");
            System.out.println("2. Remove Librarian");
            System.out.println("3. Update Librarian Details");
            System.out.println("4. View All Librarians");
            System.out.println("5. View All Students");
            System.out.println("6. Remove Student");
            System.out.println("7. Update Student Details");
            System.out.println("8. Reset Student Password");
            System.out.println("9. View Library Report");
            System.out.println("10. View All Borrow Records");
            System.out.println("11. Search Borrow Records By Student ID");
            System.out.println("12. Search Borrow Records By Book ID");
            System.out.println("13. Search Borrow Records By Status");
            System.out.println("14. Update System Settings");
            System.out.println("15. Logout");

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

    case 1: {
        int librarianId = 0;

        while(true) {
            try {
                System.out.print("Enter Librarian ID: ");
                librarianId = sc.nextInt();
                sc.nextLine();

                if(librarianId <= 0) {
                    System.out.println("Librarian ID must be greater than 0");
                    continue;
                }

                break;
            }
            catch(Exception e) {
                System.out.println("Invalid Librarian ID");
                sc.nextLine();
            }
        }

        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Username: ");
        String username = sc.nextLine();

        System.out.print("Enter Password: ");
        String password = sc.nextLine();

        Librarian librarian = new Librarian(
                librarianId,
                name,
                username,
                password
        );

        librarianService.addLibrarian(librarian);
        break;
    }

    case 2: {
        int removeLibrarianId = 0;

        while(true) {
            try {
                System.out.print("Enter Librarian ID to Remove: ");
                removeLibrarianId = sc.nextInt();
                sc.nextLine();

                if(removeLibrarianId <= 0) {
                    System.out.println("Librarian ID must be greater than 0");
                    continue;
                }

                break;
            }
            catch(Exception e) {
                System.out.println("Invalid Librarian ID");
                sc.nextLine();
            }
        }

        librarianService.removeLibrarian(removeLibrarianId);
        break;
    }

    case 3: {
        int updateLibrarianId = 0;

        while(true) {
            try {
                System.out.print("Enter Librarian ID to Update: ");
                updateLibrarianId = sc.nextInt();
                sc.nextLine();

                if(updateLibrarianId <= 0) {
                    System.out.println("Librarian ID must be greater than 0");
                    continue;
                }

                break;
            }
            catch(Exception e) {
                System.out.println("Invalid Librarian ID");
                sc.nextLine();
            }
        }

        System.out.print("Enter New Name: ");
        String newName = sc.nextLine();

        System.out.print("Enter New Username: ");
        String newUsername = sc.nextLine();

        System.out.print("Enter New Password: ");
        String newPassword = sc.nextLine();

        librarianService.updateLibrarianDetails(
                updateLibrarianId,
                newName,
                newUsername,
                newPassword
        );
        break;
    }

    case 4:
        librarianService.displayAllLibrarians();
        break;

    case 5:
        studentService.displayAllStudents();
        break;

    case 6: {
        int removeStudentId = 0;

        while(true) {
            try {
                System.out.print("Enter Student ID to Remove: ");
                removeStudentId = sc.nextInt();
                sc.nextLine();

                if(removeStudentId <= 0) {
                    System.out.println("Student ID must be greater than 0");
                    continue;
                }

                break;
            }
            catch(Exception e) {
                System.out.println("Invalid Student ID");
                sc.nextLine();
            }
        }

        studentService.removeStudent(removeStudentId);
        break;
    }

    case 7: {
        int updateStudentId = 0;

        while(true) {
            try {
                System.out.print("Enter Student ID to Update: ");
                updateStudentId = sc.nextInt();
                sc.nextLine();

                if(updateStudentId <= 0) {
                    System.out.println("Student ID must be greater than 0");
                    continue;
                }

                break;
            }
            catch(Exception e) {
                System.out.println("Invalid Student ID");
                sc.nextLine();
            }
        }

        System.out.print("Enter New Name: ");
        String updatedName = sc.nextLine();

        System.out.print("Enter New Department: ");
        String updatedDepartment = sc.nextLine();

        studentService.updateStudentDetails(
                updateStudentId,
                updatedName,
                updatedDepartment
        );
        break;
    }

    case 8: {
        int resetStudentId = 0;

        while(true) {
            try {
                System.out.print("Enter Student ID: ");
                resetStudentId = sc.nextInt();
                sc.nextLine();

                if(resetStudentId <= 0) {
                    System.out.println("Student ID must be greater than 0");
                    continue;
                }

                break;
            }
            catch(Exception e) {
                System.out.println("Invalid Student ID");
                sc.nextLine();
            }
        }

        System.out.print("Enter New Password: ");
        String resetPassword = sc.nextLine();

        adminService.resetStudentPassword(
                resetStudentId,
                resetPassword
        );
        break;
    }

    case 9: {

        Date todayDate;

        while(true){

            try{
                System.out.print("Enter Today's Date (dd/MM/yyyy): ");
                todayDate = Date.valueOf(sc.nextLine());
                break;
            }catch (Exception e){
                System.out.println("Invalid DateFormat");
            }
        }

        reportService.displayLibraryReport(todayDate);
        break;
    }

    case 10:
        reportService.displayAllBorrowRecords();
        break;

    case 11: {
        int searchStudentId = 0;

        while(true) {
            try {
                System.out.print("Enter Student ID: ");
                searchStudentId = sc.nextInt();
                sc.nextLine();

                if(searchStudentId <= 0) {
                    System.out.println("Student ID must be greater than 0");
                    continue;
                }

                break;
            }
            catch(Exception e) {
                System.out.println("Invalid Student ID");
                sc.nextLine();
            }
        }

        reportService.searchBorrowRecordsByStudentId(searchStudentId);
        break;
    }

    case 12: {
        int searchBookId = 0;

        while(true) {
            try {
                System.out.print("Enter Book ID: ");
                searchBookId = sc.nextInt();
                sc.nextLine();

                if(searchBookId <= 0) {
                    System.out.println("Book ID must be greater than 0");
                    continue;
                }

                break;
            }
            catch(Exception e) {
                System.out.println("Invalid Book ID");
                sc.nextLine();
            }
        }

        reportService.searchBorrowRecordsByBookId(searchBookId);
        break;
    }

    case 13: {
        boolean returned;

        while(true) {
            System.out.print("Enter Status (true for returned, false for not returned): ");
            String status = sc.nextLine().toLowerCase();

            if(status.equals("true")) {
                returned = true;
                break;
            }
            else if(status.equals("false")) {
                returned = false;
                break;
            }
            else {
                System.out.println("Please enter only true or false");
            }
        }

        reportService.searchBorrowRecordsByStatus(returned);
        break;
    }

    case 14: {
        double finePerDay = 0;
        int maxBooksAllowed = 0;
        int borrowDurationDays = 0;
        int maxRenewCount = 0;

        while(true) {
            try {
                System.out.print("Enter Fine Per Day: ");
                finePerDay = sc.nextDouble();
                sc.nextLine();

                if(finePerDay < 0) {
                    System.out.println("Fine cannot be negative");
                    continue;
                }

                break;
            }
            catch(Exception e) {
                System.out.println("Invalid Fine Amount");
                sc.nextLine();
            }
        }

        while(true) {
            try {
                System.out.print("Enter Maximum Books Allowed: ");
                maxBooksAllowed = sc.nextInt();
                sc.nextLine();

                if(maxBooksAllowed <= 0) {
                    System.out.println("Maximum Books Allowed must be greater than 0");
                    continue;
                }

                break;
            }
            catch(Exception e) {
                System.out.println("Invalid Number");
                sc.nextLine();
            }
        }

        while(true) {
            try {
                System.out.print("Enter Borrow Duration Days: ");
                borrowDurationDays = sc.nextInt();
                sc.nextLine();

                if(borrowDurationDays <= 0) {
                    System.out.println("Borrow Duration Days must be greater than 0");
                    continue;
                }

                break;
            }
            catch(Exception e) {
                System.out.println("Invalid Number");
                sc.nextLine();
            }
        }

        while(true) {
            try {
                System.out.print("Enter Maximum Renew Count: ");
                maxRenewCount = sc.nextInt();
                sc.nextLine();

                if(maxRenewCount < 0) {
                    System.out.println("Maximum Renew Count cannot be negative");
                    continue;
                }

                break;
            }
            catch(Exception e) {
                System.out.println("Invalid Number");
                sc.nextLine();
            }
        }

        adminService.updateSystemSettings(
                finePerDay,
                maxBooksAllowed,
                borrowDurationDays,
                maxRenewCount
        );
        break;
    }

    case 15:
        System.out.println("Logging out...");
        return;

    default:
        System.out.println("Invalid choice");
}


        }
    }
}