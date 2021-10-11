package io.github.m1theus.expenseapi.core.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

public interface IExpense {
    String getPersonName();

    String getDescription();

    LocalDateTime getExpenseDate();

    BigDecimal getExpenseValue();

    Set<String> getTags();
}
