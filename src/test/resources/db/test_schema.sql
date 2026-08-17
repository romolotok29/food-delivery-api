create table users
(
    id            bigserial primary key,
    full_name     varchar(255) not null,
    email_address varchar(255) not null,
    phone_number  varchar(255) not null,
    username      varchar(255) not null,
    password      varchar(255) not null,
    role          varchar(20)  not null,
    is_enabled    boolean      not null,
    created_at    timestamp    not null,
    updated_at    timestamp    not null
);
