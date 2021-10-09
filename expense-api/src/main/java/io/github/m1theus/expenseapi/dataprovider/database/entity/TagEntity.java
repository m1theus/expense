package io.github.m1theus.expenseapi.dataprovider.database.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

@Table("expenseapi.tags")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TagEntity implements Serializable {
    @Id
    @ToString.Exclude
    @Column("id")
    private Long id;
    @Column("name")
    private String name;
    @Column("expense_id")
    @ToString.Exclude
    private Long expenseId;

    public TagEntity(String name) {
        this.name = name;
    }
}
