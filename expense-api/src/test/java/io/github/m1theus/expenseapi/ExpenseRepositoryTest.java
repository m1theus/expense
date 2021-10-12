package io.github.m1theus.expenseapi;

import io.github.m1theus.expenseapi.core.domain.entity.Expense;
import io.github.m1theus.expenseapi.core.domain.repository.ExpenseRepository;
import io.github.m1theus.expenseapi.dataprovider.database.ExpenseRepositoryImpl;
import io.github.m1theus.expenseapi.dataprovider.database.dao.ExpenseDao;
import io.github.m1theus.expenseapi.dataprovider.database.dao.TagDao;
import io.github.m1theus.expenseapi.dataprovider.database.entity.ExpenseEntity;
import io.github.m1theus.expenseapi.dataprovider.database.entity.ExpenseMapper;
import io.github.m1theus.expenseapi.dataprovider.database.entity.TagEntity;
import io.github.m1theus.expenseapi.dataprovider.database.entity.TagMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyIterable;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class ExpenseRepositoryTest {
    @Mock
    private ExpenseDao expenseDaoMock;
    @Mock
    private TagDao tagDaoMock;

    private final ExpenseMapper mapperMock = ExpenseMapper.INSTANCE;
    private final TagMapper tagMapperMock = TagMapper.INSTANCE;

    private ExpenseRepository repository;

    @BeforeEach
    void setup() {
        repository = new ExpenseRepositoryImpl(
            expenseDaoMock,
            tagDaoMock,
            mapperMock,
            tagMapperMock
        );
    }

    @Test
    void findAll_should_return_an_list_of_expenses() {
        final var entitiesMock = Flux.just(mockEntity("personName"), mockEntity("person_name"));
        when(expenseDaoMock.findAll()).thenReturn(entitiesMock);
        when(tagDaoMock.findAllByExpenseId(any())).thenReturn(Flux.just(mock(TagEntity.class), mock(TagEntity.class)));

        final Flux<Expense> entities = repository.findAll();

        StepVerifier.create(entities)
            .expectNextCount(2)
            .verifyComplete();
        verify(expenseDaoMock).findAll();
        verify(tagDaoMock, times(2)).findAllByExpenseId(anyLong());
    }

    @Test
    void findOne_should_return_an_expense_by_id() {
        when(expenseDaoMock.findById(anyLong())).thenReturn(Mono.just(mockEntity("personName")));
        when(tagDaoMock.findAllByExpenseId(any())).thenReturn(Flux.just(mock(TagEntity.class), mock(TagEntity.class)));

        final Mono<Optional<Expense>> optEntity = repository.findOne(anyLong());

        StepVerifier.create(optEntity)
            .expectNextMatches(opt -> {
                assertThat(opt).isPresent();
                assertThat(opt.get()).isNotNull();
                return true;
            })
            .verifyComplete();
        verify(expenseDaoMock).findById(anyLong());
        verify(tagDaoMock).findAllByExpenseId(anyLong());
    }

    @Test
    void should_create_an_new_expense() {
        final ExpenseEntity entity = mockEntity("personName");
        when(expenseDaoMock.save(any())).thenReturn(Mono.just(entity));
        when(tagDaoMock.saveAll(anyIterable())).thenReturn(Flux.just(mock(TagEntity.class), mock(TagEntity.class)));

        final Mono<Expense> expenseMono = repository.create(any());

        StepVerifier.create(expenseMono)
            .expectNextMatches(expense -> {
                assertThat(expense).isNotNull();
                assertThat(expense)
                    .usingRecursiveComparison()
                    .ignoringFields("tags")
                    .isEqualTo(entity);
                return true;
            })
            .verifyComplete();

        verify(expenseDaoMock).save(any());
        verify(tagDaoMock).saveAll(anyIterable());
    }

    private ExpenseEntity mockEntity(String name) {
        return new ExpenseEntity(
            2L,
            name,
            "desc",
            LocalDateTime.now(),
            new BigDecimal("20.00").setScale(2, RoundingMode.HALF_UP),
            Set.of(new TagEntity("TAG")),
            LocalDateTime.now()
        );
    }
}
