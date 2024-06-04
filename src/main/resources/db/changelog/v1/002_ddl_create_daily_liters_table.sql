create table if not exists daily_liters
(
    id        bigserial primary key,
    farmer_cow_id bigint not null
        constraint dl_liters_to_fk_cow
            references farmer_cow
            on delete cascade,
    date      timestamp,
    liters    integer,
);