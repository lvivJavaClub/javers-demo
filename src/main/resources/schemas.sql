CREATE TABLE manager (
    id serial,
    first_name varchar(200),
    last_name varchar(200),
    bonus integer,
    created_at timestamp,
    created_by varchar(255),
    updated_at timestamp,
    updated_by varchar(255)
);

CREATE TABLE customer (
    id  serial,
    first_name varchar(200),
    last_name varchar(200),
    birth_date varchar(50),
    phones text,
    emails text,
    manager_id integer,
    created_at timestamp,
    created_by varchar(255),
    updated_at timestamp,
    updated_by varchar(255)
);

CREATE TABLE address (
    id serial,
    country varchar(2),
    city varchar(100),
    customer_id integer,
    created_at timestamp,
    created_by varchar(255),
    updated_at timestamp,
    updated_by varchar(255)
);
