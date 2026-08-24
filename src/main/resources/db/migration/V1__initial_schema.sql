create sequence user_id_seq;

create table users
(
    id            bigint primary key,
    full_name     varchar(50) not null,
    email_address varchar(30) not null unique,
    phone_number  varchar(15) not null unique,
    username      varchar(20) not null unique,
    password      varchar(20) not null,
    is_enabled    boolean      not null,
    created_at    timestamp    not null,
    updated_at    timestamp    not null
);
