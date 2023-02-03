-- create sequence if not exists node_id_seq;

create table if not exists node_types
(
    type varchar(16) not null primary key
);

create table if not exists roles
(
    role varchar(16) not null primary key
);

create table if not exists users
(
    id       bigserial primary key,
    login    varchar(64) not null unique,
    password varchar(64) not null,
    role     varchar(16) not null references roles
);

create table if not exists quests
(
    id      bigserial primary key,
    user_id bigint not null references users,
    text    text,
    text name
);

create table if not exists nodes
(
--     id         bigint default nextval('node_id_seq'::regclass) not null primary key,
    node_id        bigint       not null,
    short_name     varchar(128) not null,
    text           text         not null,
    parent_id      bigint       not null,
    quest_id       bigint       not null references quests,
    type           varchar(16)  not null references node_types,
    next_lonely_id bigint,
    primary key (node_id, quest_id)
);

-- create table if not exists answers
-- (
-- --     id               bigint default nextval('node_id_seq'::regclass) not null primary key,
--     node_id          bigint       not null,
--     short_name       varchar(128) not null,
--     text             text         not null,
--     parent_id        bigint       not null,
--     quest_id         bigint       not null references quests,
--     type             varchar(16)  not null references answer_types,
--     next_question_id bigint,
--     primary key (node_id, quest_id)
-- );

create table if not exists q_to_a_additional_links
(
    quest_id       bigint not null,
    parent_node_id bigint not null,
    child_node_id  bigint not null,
    primary key (quest_id, parent_node_id, child_node_id)
);
