package io.github.m1theus.expenseapi.entrypoint.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseResponse {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("person_name")
    private String personName;
    @JsonProperty("expense_description")
    private String description;
    @JsonProperty("expense_date")
    private LocalDateTime expenseDate;
    @JsonProperty("expense_value")
    private BigDecimal expenseValue;
    @JsonProperty("expense_tags")
    private Set<String> tags;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
}
