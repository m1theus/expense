package io.github.m1theus.expenseapi.dataprovider.database.entity;

import io.github.m1theus.expenseapi.core.domain.IExpense;
import io.github.m1theus.expenseapi.core.domain.entity.Expense;
import io.github.m1theus.expenseapi.entrypoint.rest.ExpenseResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.stream.Collectors;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    imports = {Collectors.class}
)
public interface ExpenseMapper {
    ExpenseMapper INSTANCE = Mappers.getMapper(ExpenseMapper.class);

    @Mapping(target = "tags", expression = "java(entity.getTags().stream().map(TagEntity::getName).collect(Collectors.toSet()))")
    Expense toDomain(ExpenseEntity entity);

    Expense toDomain(IExpense input);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "tags", expression = "java(domain.getTags().stream().map(tag -> new TagEntity(tag)).collect(Collectors.toSet()))")
    ExpenseEntity toEntity(Expense domain);

    ExpenseResponse from(Expense output);
}
