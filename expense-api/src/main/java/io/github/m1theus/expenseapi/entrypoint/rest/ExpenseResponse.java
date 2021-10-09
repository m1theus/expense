package io.github.m1theus.expenseapi.entrypoint.rest;

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
    private Long id;
    private String personName;
    private String description;
    private LocalDateTime expenseDate;
    private BigDecimal expenseValue;
    private Set<String> tags;
    private LocalDateTime createdAt;
}
