package io.github.m1theus.expenseapi.dataprovider.database.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Table("expenseapi.expense")
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseEntity implements Serializable {
    @Id
    @Column("id")
    private Long id;
    @Column("person_name")
    private String personName;
    @Column("description")
    private String description;
    @Column("expense_date")
    private LocalDateTime expenseDate;
    @Column("expense_value")
    private BigDecimal expenseValue;
    @Transient
    private Set<TagEntity> tags = Set.of();
    @Column("created_at")
    @CreatedDate
    private LocalDateTime createdAt = LocalDateTime.now();
}
