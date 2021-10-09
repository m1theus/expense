package io.github.m1theus.expenseapi.core.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface IExpense {
    String getPersonName();

    String getDescription();

    LocalDateTime getExpenseDate();

    BigDecimal getExpenseValue();

    List<String> getTags();
}
