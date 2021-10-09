package io.github.m1theus.expenseapi.core.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
public class Expense {
    private Long id;
    private String personName;
    private String description;
    private LocalDateTime expenseDate;
    private BigDecimal expenseValue;
    private Set<String> tags;
    private LocalDateTime createdAt;
}
