CREATE TABLE IF NOT EXISTS "history" (
    id serial primary key,
    description text,
    from_id int not null,
    to_id int not null,
    value decimal,
    transaction_date timestamp,
    group_id int not null,
    constraint fk_group foreign key (group_id) references "group" (id),
    constraint fk_from foreign key (from_id) references "participant" (id),
    constraint fk_to foreign key (to_id) references "participant" (id)
);

CREATE TABLE IF NOT EXISTS "summary" (
    from_id int not null,
    to_id int not null,
    "value" decimal,
    group_id int not null,
    constraint fk_group foreign key (group_id) references "group" (id),
    constraint fk_from foreign key (from_id) references "participant" (id),
    constraint fk_to foreign key (to_id) references "participant" (id),
    primary key(from_id, to_id, group_id)
);