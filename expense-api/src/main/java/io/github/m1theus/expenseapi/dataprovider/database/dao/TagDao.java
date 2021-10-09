package io.github.m1theus.expenseapi.dataprovider.database.dao;

import io.github.m1theus.expenseapi.dataprovider.database.entity.TagEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface TagDao extends ReactiveCrudRepository<TagEntity, Long> {
    Flux<TagEntity> findAllByExpenseId(Long expenseId);
}
