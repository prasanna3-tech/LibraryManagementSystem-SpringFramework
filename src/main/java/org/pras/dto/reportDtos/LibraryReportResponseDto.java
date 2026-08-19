package org.pras.dto.reportDtos;

public class LibraryReportResponseDto {

    private long totalBooks;
    private long totalStudents;
    private long totalLibrarians;
    private long totalBorrowedBooks;
    private long totalOverdueBooks;
    private double totalFineCollected;

    public LibraryReportResponseDto() {
    }

    public LibraryReportResponseDto(
            long totalBooks,
            long totalStudents,
            long totalLibrarians,
            long totalBorrowedBooks,
            long totalOverdueBooks,
            double totalFineCollected) {

        this.totalBooks = totalBooks;
        this.totalStudents = totalStudents;
        this.totalLibrarians = totalLibrarians;
        this.totalBorrowedBooks = totalBorrowedBooks;
        this.totalOverdueBooks = totalOverdueBooks;
        this.totalFineCollected = totalFineCollected;
    }

    public long getTotalBooks() {
        return totalBooks;
    }

    public void setTotalBooks(long totalBooks) {
        this.totalBooks = totalBooks;
    }

    public long getTotalStudents() {
        return totalStudents;
    }

    public void setTotalStudents(long totalStudents) {
        this.totalStudents = totalStudents;
    }

    public long getTotalLibrarians() {
        return totalLibrarians;
    }

    public void setTotalLibrarians(long totalLibrarians) {
        this.totalLibrarians = totalLibrarians;
    }

    public long getTotalBorrowedBooks() {
        return totalBorrowedBooks;
    }

    public void setTotalBorrowedBooks(long totalBorrowedBooks) {
        this.totalBorrowedBooks = totalBorrowedBooks;
    }

    public long getTotalOverdueBooks() {
        return totalOverdueBooks;
    }

    public void setTotalOverdueBooks(long totalOverdueBooks) {
        this.totalOverdueBooks = totalOverdueBooks;
    }

    public double getTotalFineCollected() {
        return totalFineCollected;
    }

    public void setTotalFineCollected(double totalFineCollected) {
        this.totalFineCollected = totalFineCollected;
    }
}