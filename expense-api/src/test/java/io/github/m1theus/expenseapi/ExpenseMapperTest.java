package io.github.m1theus.expenseapi;

import io.github.m1theus.expenseapi.core.domain.entity.Expense;
import io.github.m1theus.expenseapi.dataprovider.database.entity.ExpenseEntity;
import io.github.m1theus.expenseapi.dataprovider.database.entity.ExpenseMapper;
import io.github.m1theus.expenseapi.dataprovider.database.entity.TagEntity;
import io.github.m1theus.expenseapi.entrypoint.rest.ExpenseRequest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Set;

import static java.math.RoundingMode.HALF_UP;
import static org.assertj.core.api.Assertions.assertThat;

class ExpenseMapperTest {
    final ExpenseMapper mapper = ExpenseMapper.INSTANCE;

    @Test
    void should_map_domain_to_entity() {
        final var domain = mockExpense();

        final var entity = mapper.toEntity(domain);

        assertThat(entity)
            .usingRecursiveComparison()
            .ignoringFields("tags", "createdAt")
            .isEqualTo(domain);

        assertThat(entity.getCreatedAt()).isNotNull();
        assertThat(entity.getTags()).isNotNull();
    }

    @Test
    void should_map_entity_to_domain() {
        final var entity = mockExpenseEntity();

        final var domain = mapper.toDomain(entity);

        assertThat(domain)
            .usingRecursiveComparison()
            .ignoringFields("tags")
            .isEqualTo(entity);

        assertThat(domain.getTags()).isNotNull();
    }

    @Test
    void should_map_domain_to_response() {
        final var domain = mockExpense();

        final var response = mapper.from(domain);

        assertThat(response)
            .usingRecursiveComparison()
            .isEqualTo(domain);
    }

    @Test
    void should_map_input_to_domain() {
        final var request = new ExpenseRequest(
            "Matheus",
            "Fui ao shopping e gastei muito!",
            LocalDateTime.now(),
            new BigDecimal("129.90").setScale(2, RoundingMode.HALF_UP),
            Set.of("shopping, roupas, comida, cinema")
        );

        final var domain = mapper.toDomain(request);

        assertThat(domain)
            .usingRecursiveComparison()
            .ignoringFields("id", "createdAt")
            .isEqualTo(request);

        assertThat(domain.getId()).isNull();
        assertThat(domain.getCreatedAt()).isNull();
    }

    private Expense mockExpense() {
        return new Expense(
            null,
            "personName",
            "description",
            LocalDateTime.of(2021, Month.DECEMBER, 1, 6, 21),
            new BigDecimal("199.99").setScale(2, HALF_UP),
            Set.of("shopping", "compras", "comida"),
            null
        );
    }

    private ExpenseEntity mockExpenseEntity() {
        return new ExpenseEntity(
            1337L,
            "personName",
            "description",
            LocalDateTime.of(2021, Month.DECEMBER, 1, 6, 21),
            new BigDecimal("199.99").setScale(2, HALF_UP),
            Set.of(new TagEntity("shopping"), new TagEntity("compras"), new TagEntity("comida")),
            LocalDateTime.now()
        );
    }
}
