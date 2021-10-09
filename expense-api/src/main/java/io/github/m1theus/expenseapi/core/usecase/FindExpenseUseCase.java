package io.github.m1theus.expenseapi.core.usecase;

import io.github.m1theus.expenseapi.core.UseCaseWithInput;
import io.github.m1theus.expenseapi.core.domain.entity.Expense;
import io.github.m1theus.expenseapi.core.domain.repository.ExpenseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class FindExpenseUseCase implements UseCaseWithInput<Long, Mono<Expense>> {
    private static final Logger logger = LoggerFactory.getLogger(FindExpenseUseCase.class);
    private final ExpenseRepository repository;

    public FindExpenseUseCase(final ExpenseRepository repository) {
        this.repository = repository;
    }

    public Mono<Expense> execute(Long id) {
        return repository.findOne(id)
            .flatMap(Mono::justOrEmpty)
            .doOnSuccess(x -> logger.info("c=FindExpenseUseCase result={}", x));
    }
}
