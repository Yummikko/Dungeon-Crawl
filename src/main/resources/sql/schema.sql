CREATE TABLE IF NOT EXISTS ACTOR (
    id       serial primary key,
    type     char    not null,
    y        integer not null,
    x        integer not null,
    health   integer not null,
    strength integer not null
);

CREATE TABLE IF NOT EXISTS PLAYER (
    actor_id  integer not null primary key references ACTOR (id),
    name      varchar,
    hasWeapon bool,
    hasKey    bool
);

CREATE TABLE IF NOT EXISTS GAME (
    id        serial primary key,
    player_id integer not null references PLAYER (actor_id),
    stage     integer not null,
    save_date timestamp
);

CREATE TABLE IF NOT EXISTS GAME_ENEMIES (
    game_id  integer not null references GAME (id),
    actor_id integer not null references ACTOR (id),
    primary key (game_id, actor_id)
);

CREATE TABLE IF NOT EXISTS ITEM (
    id   serial primary key,
    type char    not null,
    y    integer not null,
    x    integer not null
);

CREATE TABLE IF NOT EXISTS GAME_ITEMS (
    game_id integer not null references GAME (id),
    item_id integer not null references ITEM (id),
    primary key (game_id, item_id)
);

CREATE TABLE IF NOT EXISTS PLAYER_ITEM (
    id   serial primary key,
    type char    not null,
    name varchar not null
);

CREATE TABLE IF NOT EXISTS PLAYER_PLAYER_ITEMS (
    actor_id       integer not null references PLAYER (actor_id),
    player_item_id integer not null references PLAYER_ITEM (id),
    primary key (actor_id, player_item_id)
);
