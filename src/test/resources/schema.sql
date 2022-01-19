DROP TABLE app_user IF EXISTS;
DROP TABLE app_user_role IF EXISTS;
create table app_user (
    user_id integer not null,
    email varchar(255) not null,
    password varchar(255) not null,
    username varchar(255) not null,
    userrole integer,
    primary key (user_id)
);
create table app_user_role (
    role_id integer not null,
    role integer,
    user_id integer,
    primary key (role_id)
);
이거 안됨
