package org.pras.controllers;

import org.pras.dto.studentDtos.*;
import org.pras.mappers.StudentMappers.ReservationNotificationResponseMapper;
import org.pras.mappers.StudentMappers.StudentResponseMapper;
import org.pras.models.Reservation;
import org.pras.models.Student;
import org.pras.services.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;
    private final StudentResponseMapper studentResponseMapper;
    private final ReservationNotificationResponseMapper reservationNotificationResponseMapper;

    public StudentController(
            StudentService studentService,
            StudentResponseMapper studentResponseMapper,
            ReservationNotificationResponseMapper reservationNotificationResponseMapper) {

        this.studentService = studentService;
        this.studentResponseMapper = studentResponseMapper;
        this.reservationNotificationResponseMapper=reservationNotificationResponseMapper;
    }

    @PostMapping
    public ResponseEntity<StudentResponseDto> registerStudent(
            @RequestBody StudentRegistrationRequestDto request) {

        Student savedStudent =
                studentService.registerStudent(request);

        StudentResponseDto response =
                studentResponseMapper.toResponseDto(savedStudent);

        return ResponseEntity.ok(response);
    }
    @PostMapping("/login")
    public ResponseEntity<StudentResponseDto> loginStudent(
            @RequestBody StudentLoginRequestDto request) {

        Student student =
                studentService.loginStudent(
                        request.getStudentId(),
                        request.getPassword()
                );

        StudentResponseDto response =
                studentResponseMapper.toResponseDto(student);

        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{studentId}")
    public ResponseEntity<StudentResponseDto> removeStudent(
            @PathVariable("studentId") int studentId) {

        Student deletedStudent =
                studentService.removeStudent(studentId);

        StudentResponseDto response =
                studentResponseMapper.toResponseDto(
                        deletedStudent
                );

        return ResponseEntity.ok(response);
    }
    @PutMapping("/{studentId}")
    public ResponseEntity<StudentResponseDto> updateStudentDetails(
            @PathVariable("studentId") int studentId,
            @RequestBody UpdateStudentRequestDto request) {

        Student updatedStudent =
                studentService.updateStudentDetails(
                        studentId,
                        request.getName(),
                        request.getDepartment()
                );

        StudentResponseDto response =
                studentResponseMapper.toResponseDto(
                        updatedStudent
                );

        return ResponseEntity.ok(response);
    }
    @PutMapping("/{studentId}/password")
    public ResponseEntity<StudentResponseDto> resetStudentPassword(
            @PathVariable("studentId") int studentId,
            @RequestBody ResetStudentPasswordRequestDto request) {

        Student updatedStudent =
                studentService.resetStudentPassword(
                        studentId,
                        request.getNewPassword()
                );

        StudentResponseDto response =
                studentResponseMapper.toResponseDto(
                        updatedStudent
                );

        return ResponseEntity.ok(response);
    }
    @GetMapping("/{studentId}/reservation-notifications")
    public ResponseEntity<List<ReservationNotificationResponseDto>>
    getReservationNotifications(
            @PathVariable("studentId") int studentId) {

        List<Reservation> reservations =
                studentService.getReservationNotifications(studentId);

        List<ReservationNotificationResponseDto> response =
                reservations.stream()
                        .map(reservationNotificationResponseMapper::toResponseDto)
                        .toList();

        return ResponseEntity.ok(response);
    }
    @GetMapping("/{studentId}")
    public ResponseEntity<StudentResponseDto> searchStudentById(
            @PathVariable("studentId") int studentId) {

        Student student =
                studentService.searchStudentById(studentId);

        StudentResponseDto response =
                studentResponseMapper.toResponseDto(student);

        return ResponseEntity.ok(response);
    }
    @GetMapping
    public ResponseEntity<List<StudentResponseDto>> getAllStudents() {

        List<Student> students =
                studentService.getAllStudents();

        List<StudentResponseDto> response =
                students.stream()
                        .map(studentResponseMapper::toResponseDto)
                        .toList();

        return ResponseEntity.ok(response);
    }
}