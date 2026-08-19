package org.pras.dto.adminDtos;

public class SystemSettingsResponseDto {

    private int settingsId;
    private double finePerDay;
    private int maxBooksAllowed;
    private int borrowDurationDays;
    private int maxRenewCount;

    public SystemSettingsResponseDto() {
    }

    public SystemSettingsResponseDto(
            int settingsId,
            double finePerDay,
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
}