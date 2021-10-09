package io.github.m1theus.expenseapi.core.usecase;

import io.github.m1theus.expenseapi.core.UseCase;
import io.github.m1theus.expenseapi.core.domain.entity.Expense;
import io.github.m1theus.expenseapi.core.domain.repository.ExpenseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class FindAllExpenseUseCase implements UseCase<Flux<Expense>> {
    private static final Logger logger = LoggerFactory.getLogger(FindAllExpenseUseCase.class);
    private final ExpenseRepository repository;

    public FindAllExpenseUseCase(final ExpenseRepository repository) {
        this.repository = repository;
    }

    @Override
    public Flux<Expense> execute() {
        return repository.findAll()
            .doOnNext(x -> logger.info("c=FindAllExpenseUseCase result={}", x));
    }
}
