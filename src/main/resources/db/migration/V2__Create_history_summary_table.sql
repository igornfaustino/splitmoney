CREATE TABLE IF NOT EXISTS "history" (
    id serial primary key,
    description text,
    "from" int not null,
    "to" int not null,
    value decimal,
    transactionDate timestamp,
    group_id int not null,
    constraint fk_group foreign key (group_id) references "group" (id),
    constraint fk_from foreign key ("from") references "participant" (id),
    constraint fk_to foreign key ("to") references "participant" (id)
);

CREATE TABLE IF NOT EXISTS "summary" (
    "from" int not null,
    "to" int not null,
    "value" decimal,
    group_id int not null,
    constraint fk_group foreign key (group_id) references "group" (id),
    constraint fk_from foreign key ("from") references "participant" (id),
    constraint fk_to foreign key ("to") references "participant" (id),
    primary key("from", "to", group_id)
);