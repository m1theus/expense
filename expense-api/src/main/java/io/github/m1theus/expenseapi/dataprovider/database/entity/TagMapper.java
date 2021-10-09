package io.github.m1theus.expenseapi.dataprovider.database.entity;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.Collection;
import java.util.HashSet;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface TagMapper {
    default Collection<TagEntity> toEntity(final Long expenseId, final Collection<TagEntity> tags) {
        if (tags == null) {
            return new HashSet<>();
        }

        tags.forEach(tag -> tag.setExpenseId(expenseId));

        return new HashSet<>(tags);
    }
}
