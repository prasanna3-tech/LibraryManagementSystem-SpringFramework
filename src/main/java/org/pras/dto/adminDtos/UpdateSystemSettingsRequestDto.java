package org.pras.dto.adminDtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class UpdateSystemSettingsRequestDto {

    @NotNull(message = "Fine per day is required")
    @DecimalMin(
            value = "0.0",
            message = "Fine per day cannot be negative"
    )
    private Double finePerDay;

    @NotNull(message = "Maximum books allowed is required")
    @Min(
            value = 1,
            message = "Maximum books allowed must be at least 1"
    )
    private Integer maxBooksAllowed;

    @NotNull(message = "Borrow duration is required")
    @Min(
            value = 1,
            message = "Borrow duration must be at least 1 day"
    )
    private Integer borrowDurationDays;

    @NotNull(message = "Maximum renew count is required")
    @Min(
            value = 0,
            message = "Maximum renew count cannot be negative"
    )
    private Integer maxRenewCount;

    public UpdateSystemSettingsRequestDto() {
    }

    public Double getFinePerDay() {
        return finePerDay;
    }

    public void setFinePerDay(Double finePerDay) {
        this.finePerDay = finePerDay;
    }

    public Integer getMaxBooksAllowed() {
        return maxBooksAllowed;
    }

    public void setMaxBooksAllowed(Integer maxBooksAllowed) {
        this.maxBooksAllowed = maxBooksAllowed;
    }

    public Integer getBorrowDurationDays() {
        return borrowDurationDays;
    }

    public void setBorrowDurationDays(Integer borrowDurationDays) {
        this.borrowDurationDays = borrowDurationDays;
    }

    public Integer getMaxRenewCount() {
        return maxRenewCount;
    }

    public void setMaxRenewCount(Integer maxRenewCount) {
        this.maxRenewCount = maxRenewCount;
    }
}