package io.github.m1theus.expenseapi.entrypoint.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.m1theus.expenseapi.core.domain.IExpense;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@ToString
@EqualsAndHashCode
public class ExpenseRequest implements IExpense {
    @NotNull
    @NotEmpty
    @JsonProperty("person_name")
    private String personName;
    @NotNull
    @NotEmpty
    @JsonProperty("expense_description")
    private String description;
    @NotNull
    @JsonProperty("expense_date")
    private LocalDateTime expenseDate;
    @NotNull
    @JsonProperty("expense_value")
    private BigDecimal expenseValue;
    @NotNull
    @NotEmpty
    @JsonProperty("expense_tags")
    private Set<String> tags;

    public ExpenseRequest(
        final String personName,
        final String description,
        final LocalDateTime expenseDate,
        final BigDecimal expenseValue,
        final Set<String> tags
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

    public Set<String> getTags() {
        return tags;
    }
}
