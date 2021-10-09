package io.github.m1theus.expenseapi.core.usecase;

import io.github.m1theus.expenseapi.core.UseCaseWithInput;
import io.github.m1theus.expenseapi.core.domain.IExpense;
import io.github.m1theus.expenseapi.core.domain.entity.Expense;
import io.github.m1theus.expenseapi.core.domain.repository.ExpenseRepository;
import io.github.m1theus.expenseapi.dataprovider.database.entity.ExpenseEntity;
import io.github.m1theus.expenseapi.dataprovider.database.entity.ExpenseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CreateExpenseUseCase implements UseCaseWithInput<IExpense, Mono<Expense>> {
    private static final Logger logger = LoggerFactory.getLogger(CreateExpenseUseCase.class);
    private final ExpenseRepository repository;
    private final ExpenseMapper mapper;

    public CreateExpenseUseCase(
        final ExpenseRepository repository,
        final ExpenseMapper mapper
    ) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Mono<Expense> execute(IExpense input) {
        final Expense expense = mapper.toDomain(input);
        final ExpenseEntity entity = mapper.toEntity(expense);
        logger.info("c=CreateExpenseUseCase m=execute creating_new_expense expense={}", entity);
        return repository.create(entity);
    }
}
