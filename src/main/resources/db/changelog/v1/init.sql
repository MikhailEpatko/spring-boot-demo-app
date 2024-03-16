create table if not exists farmer
(
    id          bigserial primary key,
    first_name  text,
    middle_name text,
    last_name   text
);

create table if not exists farmer_cow
(
    id        bigserial primary key,
    farmer_id bigint not null
        constraint fk_cow_to_farmer
            references farmer
            on delete cascade,
    name      varchar(50),
    color     varchar(10),
    liters    int2,
    status    varchar(10)
);
