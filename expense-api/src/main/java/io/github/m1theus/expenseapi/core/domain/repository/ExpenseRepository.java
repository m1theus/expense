package io.github.m1theus.expenseapi.core.domain.repository;

import io.github.m1theus.expenseapi.core.domain.entity.Expense;
import io.github.m1theus.expenseapi.dataprovider.database.entity.ExpenseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface ExpenseRepository {
    Flux<Expense> findAll();

    Mono<Optional<Expense>> findOne(Long id);

    Mono<Expense> create(ExpenseEntity entity);
}
