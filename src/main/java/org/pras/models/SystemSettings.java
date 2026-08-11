package org.pras.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "system_settings")
public class SystemSettings {

    @Id
    @Column(name = "settings_id")
    private int settingsId;

    @Column(name = "fine_per_day", nullable = false)
    private double finePerDay = 0;

    @Column(name = "max_books_allowed", nullable = false)
    private int maxBooksAllowed;

    @Column(name = "borrow_duration_days", nullable = false)
    private int borrowDurationDays;

    @Column(name = "max_renew_count", nullable = false)
    private int maxRenewCount;

    public SystemSettings() {

    }

    public SystemSettings(int settingsId, double finePerDay,
                          int maxBooksAllowed,
                          int borrowDurationDays,
                          int maxRenewCount) {

        this.settingsId = settingsId;
        this.finePerDay = finePerDay;
        this.maxBooksAllowed = maxBooksAllowed;
        this.borrowDurationDays = borrowDurationDays;
        this.maxRenewCount = maxRenewCount;
    }

    public int getSettingsId() {
        return settingsId;
    }

    public void setSettingsId(int settingsId) {
        this.settingsId = settingsId;
    }

    public double getFinePerDay() {
        return finePerDay;
    }

    public void setFinePerDay(double finePerDay) {
        this.finePerDay = finePerDay;
    }

    public int getMaxBooksAllowed() {
        return maxBooksAllowed;
    }

    public void setMaxBooksAllowed(int maxBooksAllowed) {
        this.maxBooksAllowed = maxBooksAllowed;
    }

    public int getBorrowDurationDays() {
        return borrowDurationDays;
    }

    public void setBorrowDurationDays(int borrowDurationDays) {
        this.borrowDurationDays = borrowDurationDays;
    }

    public int getMaxRenewCount() {
        return maxRenewCount;
    }

    public void setMaxRenewCount(int maxRenewCount) {
        this.maxRenewCount = maxRenewCount;
    }

    public void displaySettings() {
        System.out.println("Fine Per Day: Rs. " + finePerDay);
        System.out.println("Maximum Books Allowed: " + maxBooksAllowed);
        System.out.println("Borrow Duration Days: " + borrowDurationDays);
        System.out.println("Maximum Renew Count: " + maxRenewCount);
    }
}