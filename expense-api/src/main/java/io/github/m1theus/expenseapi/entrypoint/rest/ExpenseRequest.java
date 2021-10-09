package io.github.m1theus.expenseapi.entrypoint.rest;

import io.github.m1theus.expenseapi.core.domain.IExpense;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.List;

public class ExpenseRequest implements IExpense {
    @NotNull
    @NotEmpty
    private String personName;
    @NotNull
    @NotEmpty
    private String description;
    @NotNull
    private LocalDateTime expenseDate;
    @NotNull
    private BigDecimal expenseValue;
    @NotNull
    @NotEmpty
    private List<String> tags;

    public ExpenseRequest(
        final String personName,
        final String description,
        final LocalDateTime expenseDate,
        final BigDecimal expenseValue,
        final List<String> tags
    ) {
        this.personName = personName;
        this.description = description;
        this.expenseDate = expenseDate;
        this.expenseValue = expenseValue;
        this.tags = tags;
    }

    public String getPersonName() {
        return personName;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getExpenseDate() {
        return expenseDate;
    }

    public BigDecimal getExpenseValue() {
        return expenseValue;
    }

    public List<String> getTags() {
        return tags;
    }
}
