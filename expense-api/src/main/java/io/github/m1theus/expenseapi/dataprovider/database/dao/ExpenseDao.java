package io.github.m1theus.expenseapi.dataprovider.database.dao;

import io.github.m1theus.expenseapi.dataprovider.database.entity.ExpenseEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ExpenseDao extends ReactiveCrudRepository<ExpenseEntity, Long> {
}
