package org.pras.services;

import org.hibernate.Transaction;
import org.hibernate.Session;
import org.pras.config.HibernateUtil;
import org.pras.dto.studentDtos.ResetStudentPasswordRequestDto;
import org.pras.dto.studentDtos.StudentRegistrationRequestDto;
import org.pras.exceptions.*;
import org.pras.models.Student;

import java.util.List;

import org.pras.models.*;
import org.pras.repositories.BorrowRecordRepository;
import org.pras.repositories.ReservationRepository;
import org.pras.repositories.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final BorrowRecordRepository borrowRecordRepository;
    private final ReservationRepository reservationRepository;

    public StudentService(
            StudentRepository studentRepository,
            BorrowRecordRepository borrowRecordRepository,ReservationRepository reservationRepository
            ) {

        this.studentRepository = studentRepository;
        this.borrowRecordRepository = borrowRecordRepository;
        this.reservationRepository=reservationRepository;
    }

    public List<Reservation> getReservationNotifications(int studentId) {

        studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new StudentNotFoundException(studentId));

        return reservationRepository
                .findByStudentStudentIdAndNotifiedTrue(studentId);
    }

    @Transactional
    public Student registerStudent(StudentRegistrationRequestDto request) {

        if (studentRepository.existsById(request.getStudentId())) {
            throw new StudentAlreadyExistsException(
                    request.getStudentId()
            );
        }
        Student student = new Student();

        student.setStudentId(request.getStudentId());
        student.setName(request.getName());
        student.setDepartment(request.getDepartment());
        student.setPassword(request.getPassword());

        return studentRepository.save(student);
    }

    public Student loginStudent(
            int studentId,
            String password) {

        return studentRepository
                .findByStudentIdAndPassword(
                        studentId,
                        password
                )
                .orElseThrow(() ->
                        new InvalidStudentCredentialsException());
    }

    public Student searchStudentById(int studentId) {

        return studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new StudentNotFoundException(studentId));
    }

    public List<Student> getAllStudents() {

        return studentRepository.findAll();
    }

    @Transactional
    public Student removeStudent(int studentId) {

        Student student =
                studentRepository.findById(studentId)
                        .orElseThrow(() ->
                                new StudentNotFoundException(studentId));

        // Check borrowed books

        boolean hasBorrowedBooks =
                borrowRecordRepository
                        .existsByStudentStudentIdAndReturnedFalse(
                                studentId
                        );

        if (hasBorrowedBooks) {

            throw new StudentHasBorrowedBooksException(studentId);
        }

        // Check pending fine

        double totalFine =
                borrowRecordRepository
                        .getTotalPendingFine(studentId);

        if (totalFine > 0) {

            throw new StudentHasPendingFineException(studentId);
        }

        // Remove student

        studentRepository.delete(student);
        return student;
    }

    @Transactional
    public Student updateStudentDetails(
            int studentId,
            String newName,
            String newDepartment) {

        Student student =
                studentRepository.findById(studentId)
                        .orElseThrow(() ->
                                new StudentNotFoundException(studentId));

        student.setName(newName);
        student.setDepartment(newDepartment);

        return student;
    }

    @Transactional
    public Student resetStudentPassword(
            int studentId,
            String newPassword) {

        Student student =
                studentRepository.findById(studentId)
                        .orElseThrow(() ->
                                new StudentNotFoundException(studentId));

        student.setPassword(newPassword);

        return student;
    }
}