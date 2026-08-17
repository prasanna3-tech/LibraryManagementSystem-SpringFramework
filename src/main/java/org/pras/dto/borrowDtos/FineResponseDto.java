package org.pras.dto.borrowDtos;

public class FineResponseDto {

    private int studentId;
    private double totalFine;

    public FineResponseDto() {
    }

    public FineResponseDto(int studentId, double totalFine) {
        this.studentId = studentId;
        this.totalFine = totalFine;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public double getTotalFine() {
        return totalFine;
    }

    public void setTotalFine(double totalFine) {
        this.totalFine = totalFine;
    }
}