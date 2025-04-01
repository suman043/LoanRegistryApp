package com.exampleLoan.dto.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public class LoanDTO {

    @NotBlank(message = "Customer name is required")
    private String customerName;

    public @Email(message = "Invalid email format") String getEmail() {
        return email;
    }

    public void setEmail(@Email(message = "Invalid email format") String email) {
        this.email = email;
    }

    public @NotBlank(message = "Customer name is required") String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(@NotBlank(message = "Customer name is required") String customerName) {
        this.customerName = customerName;
    }

    public @NotNull(message = "Loan amount is required") @Positive(message = "Loan amount must be positive") BigDecimal getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(@NotNull(message = "Loan amount is required") @Positive(message = "Loan amount must be positive") BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }

    public @NotNull(message = "Loan date is required") LocalDate getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(@NotNull(message = "Loan date is required") LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    public @NotNull(message = "Tenure is required") @Min(value = 6, message = "Minimum tenure is 6 months") Integer getTenureInMonths() {
        return tenureInMonths;
    }

    public void setTenureInMonths(@NotNull(message = "Tenure is required") @Min(value = 6, message = "Minimum tenure is 6 months") Integer tenureInMonths) {
        this.tenureInMonths = tenureInMonths;
    }

    public @NotNull(message = "Interest rate is required") @DecimalMin(value = "0.01", message = "Interest rate must be greater than 0") BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(@NotNull(message = "Interest rate is required") @DecimalMin(value = "0.01", message = "Interest rate must be greater than 0") BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    @Email(message = "Invalid email format")
    private String email;

    @NotNull(message = "Loan amount is required")
    @Positive(message = "Loan amount must be positive")
    private BigDecimal loanAmount;

    @NotNull(message = "Loan date is required")
    private LocalDate loanDate;

    @NotNull(message = "Tenure is required")
    @Min(value = 6, message = "Minimum tenure is 6 months")
    private Integer tenureInMonths;

    @NotNull(message = "Interest rate is required")
    @DecimalMin(value = "0.01", message = "Interest rate must be greater than 0")
    private BigDecimal interestRate;
}