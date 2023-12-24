CREATE TABLE IF NOT EXISTS "group" (
    id serial primary key, 
    name varchar(255)
);
CREATE TABLE IF NOT EXISTS "participant" (
    id serial primary key,
    name varchar(255),
    group_id int not null,
    constraint fk_group foreign key (group_id) references "group" (id)
);