CREATE TABLE IF NOT EXISTS expenseapi.expense
(
    id          serial                  not null,
    person_name varchar(255)            not null,
    description varchar(255)            not null,
    expense_value numeric(5,2)            not null,
    expense_date  TIMESTAMP DEFAULT now() NOT NULL,
    created_at  TIMESTAMP DEFAULT now() NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS expenseapi.tags
(
    id       serial       not null,
    name     varchar(255) not null,
    expense_id integer      null,
    PRIMARY KEY (id),
    FOREIGN KEY (expense_id) REFERENCES expense (id) ON DELETE CASCADE
);
