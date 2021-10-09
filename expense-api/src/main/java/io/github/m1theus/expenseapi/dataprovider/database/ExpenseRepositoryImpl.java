package io.github.m1theus.expenseapi.dataprovider.database;

import io.github.m1theus.expenseapi.core.domain.entity.Expense;
import io.github.m1theus.expenseapi.core.domain.repository.ExpenseRepository;
import io.github.m1theus.expenseapi.dataprovider.database.dao.ExpenseDao;
import io.github.m1theus.expenseapi.dataprovider.database.dao.TagDao;
import io.github.m1theus.expenseapi.dataprovider.database.entity.ExpenseEntity;
import io.github.m1theus.expenseapi.dataprovider.database.entity.ExpenseMapper;
import io.github.m1theus.expenseapi.dataprovider.database.entity.TagEntity;
import io.github.m1theus.expenseapi.dataprovider.database.entity.TagMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class ExpenseRepositoryImpl implements ExpenseRepository {
    private final ExpenseDao dao;
    private final TagDao tagDao;
    private final ExpenseMapper mapper;
    private final TagMapper tagMapper;

    public ExpenseRepositoryImpl(
        final ExpenseDao dao,
        final TagDao tagDao,
        final ExpenseMapper mapper,
        final TagMapper tagMapper
    ) {
        this.dao = dao;
        this.tagDao = tagDao;
        this.mapper = mapper;
        this.tagMapper = tagMapper;
    }

    @Override
    public Flux<Expense> findAll() {
        return dao.findAll()
            .flatMap(this::loadTags)
            .map(mapper::toDomain);
    }

    @Override
    public Mono<Optional<Expense>> findOne(Long id) {
        return dao.findById(id)
            .flatMap(this::loadTags)
            .map(entity -> {
                final var domain = mapper.toDomain(entity);
                return Optional.ofNullable(domain);
            });
    }

    @Override
    @Transactional
    public Mono<Expense> create(ExpenseEntity entity) {
        return dao.save(entity)
            .flatMap(this::saveAllTags)
            .map(mapper::toDomain);
    }

    private Mono<ExpenseEntity> saveAllTags(final ExpenseEntity savedEntity) {
        final var tags = tagMapper.toEntity(savedEntity.getId(), savedEntity.getTags());
        return tagDao.saveAll(tags)
            .then(Mono.just(savedEntity));
    }

    private Mono<ExpenseEntity> loadTags(final ExpenseEntity entity) {
        return Mono.just(entity)
            .zipWith(tagDao.findAllByExpenseId(entity.getId()).collect(Collectors.toSet()))
            .map(x -> {
                final ExpenseEntity expense = x.getT1();
                final Set<TagEntity> tags = x.getT2();

                expense.setTags(tags);

                return expense;
            });
    }
}
