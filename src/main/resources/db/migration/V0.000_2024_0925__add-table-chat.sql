create table chat_rooms
(
    id                 bigserial               not null primary key,
    name               varchar(100)            not null,
    description        text,
    user_id            varchar(100)            not null,
    created_date       timestamp default now() not null,
    last_modified_date timestamp
);

create table chats
(
    id                 bigserial               not null primary key,
    room_id            bigint                  not null,
    user_id            varchar(100)            not null,
    contents           text                    not null,
    created_date       timestamp default now() not null,
    last_modified_date timestamp,

    foreign key (room_id) references chat_rooms (id)
);