package org.pras.menus;

import java.sql.Date;
import java.util.Scanner;
import org.pras.models.Book;
import org.pras.models.Student;
import org.pras.services.BookService;
import org.pras.services.BorrowService;
import org.pras.services.StudentService;

public class StudentMenu {

    Scanner sc = new Scanner(System.in);

    BookService bookService;
    BorrowService borrowService;
    StudentService studentService;

    public StudentMenu(BookService bookService,
                       BorrowService borrowService,
                       StudentService studentService) {

        this.bookService = bookService;
        this.borrowService = borrowService;
        this.studentService = studentService;
    }

    public void showMenu(Student student) {

        while(true) {

            System.out.println("\n===== Student Menu =====");
            System.out.println("1. Search Book");
            System.out.println("2. View Borrowed Books");
            System.out.println("3. View Due Dates");
            System.out.println("4. View Overdue Books");
            System.out.println("5. Reserve Book");
            System.out.println("6. Renew Book");
            System.out.println("7. View Borrowing History");
            System.out.println("8. View Profile");
            System.out.println("9. Logout");

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
                    int bookId = 0;

                    while(true) {
                        try {
                            System.out.print("Enter Book ID: ");
                            bookId = sc.nextInt();
                            sc.nextLine();

                            if(bookId <= 0) {
                                System.out.println("Book ID must be greater than 0");
                                continue;
                            }

                            break;
                        }
                        catch(Exception e) {
                            System.out.println("Invalid input. Please enter a valid Book ID.");
                            sc.nextLine();
                        }
                    }

                    Book book = bookService.searchBookById(bookId);

                    if(book != null) {
                        book.displayBookDetails();
                    }
                    else {
                        System.out.println("Book not found");
                    }
                    break;
                }

                case 2:
                    //borrowService.displayBorrowedBooks(student.getStudentId());
                    break;

                case 3:
                   // borrowService.displayDueDates(student.getStudentId());
                    break;

                case 4: {

                    Date todayDate;
                    while(true){

                        try{
                            System.out.print("Enter Today's Date (yyyy/MM/dd): ");
                            todayDate  =  Date.valueOf(sc.nextLine());
                            break;
                        }catch (Exception e){
                            System.out.println("Invalid DateFormat");
                        }

                    }

                    //borrowService.displayOverdueBooks(student.getStudentId(), todayDate);
                    break;
                }

                case 5: {
                    int reserveBookId = 0;

                    while(true) {
                        try {
                            System.out.print("Enter Book ID: ");
                            reserveBookId = sc.nextInt();
                            sc.nextLine();

                            if(reserveBookId <= 0) {
                                System.out.println("Book ID must be greater than 0");
                                continue;
                            }

                            break;
                        }
                        catch(Exception e) {
                            System.out.println("Invalid input. Please enter a valid Book ID.");
                            sc.nextLine();
                        }
                    }

                    Date reservationDate;
                    while(true){

                        try{
                            System.out.print("Enter Reservation Date (dd/MM/yyyy): ");
                            reservationDate = Date.valueOf(sc.nextLine());
                            break;
                        }catch (Exception e){
                            System.out.println("Invalid DateFormat");
                        }

                    }


                    borrowService.reserveBook(
                            student.getStudentId(),
                            reserveBookId,
                            reservationDate
                    );
                    break;
                }

                case 6: {
                    int renewBookId = 0;

                    while(true) {
                        try {
                            System.out.print("Enter Book ID: ");
                            renewBookId = sc.nextInt();
                            sc.nextLine();

                            if(renewBookId <= 0) {
                                System.out.println("Book ID must be greater than 0");
                                continue;
                            }

                            break;
                        }
                        catch(Exception e) {
                            System.out.println("Invalid input. Please enter a valid Book ID.");
                            sc.nextLine();
                        }
                    }

                    Date newDueDate;
                    while(true){

                        try {
                            System.out.print("Enter New Due Date (yyyy/MM/dd): ");
                            newDueDate = Date.valueOf(sc.nextLine());
                            break;
                        }catch (Exception e){
                            System.out.println("Invalid DateFormat");
                        }
                    }


                    borrowService.renewBook(
                            student.getStudentId(),
                            renewBookId,
                            newDueDate
                    );
                    break;
                }

                case 7:
                    //borrowService.displayBorrowHistory(student.getStudentId());
                    break;

                case 8:
                    student.displayProfile();
                    break;

                case 9:
                    System.out.println("Logging out...");
                    return;

                default:
                    System.out.println("Invalid choice");
}
        }
    }
}