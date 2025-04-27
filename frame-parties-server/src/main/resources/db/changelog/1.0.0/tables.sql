--liquibase formatted sql

--changeset Enzhine:KTX-10.3
create table community (
    id bigserial not null primary key,
    created_at timestamptz not null,
    updated_at timestamptz not null,
    ---
    name varchar not null,
    is_public boolean not null,
    description text
);
--rollback drop table if exists community;

create table membership (
    id bigserial not null primary key,
    joined_at timestamptz not null,
    ---
    user_id bigint not null,
    community_id bigint not null,
    ---
    foreign key (community_id) references community (id)
);
--rollback drop table if exists membership;

create unique index ix_membership__user_id on membership (user_id);
--rollback drop index concurrently if exists ix_membership__user_id;

create table role (
    id bigserial not null primary key,
    created_at timestamptz not null,
    updated_at timestamptz not null,
    ---
    community_id bigint not null,
    name varchar not null,
    priority int not null,
    rights jsonb not null,
    ---
    foreign key (community_id) references community (id)
);
--rollback drop table if exists role;

create index ix_role__community_id on role (community_id);
--rollback drop index concurrently if exists ix_role__community_id;

create table membership_role (
    id bigserial not null primary key,
    assigned_at timestamptz not null,
    ---
    user_id bigint not null,
    community_id bigint not null,
    role_id bigint not null,
    ---
    foreign key (community_id) references community (id),
    foreign key (role_id) references role (id)
);
--rollback drop table if exists membership_role;

create index ix_membership_role__user_id_community_id on membership_role (user_id, community_id);
--rollback drop index concurrently if exists ix_membership_role__user_id_community_id;

create table invitation_token (
    id bigserial not null primary key,
    created_at timestamptz not null,
    created_by bigint not null,
    ---
    token varchar not null unique,
    community_id bigint not null,
    is_one_time boolean not null,
    expires_at timestamptz,
    ---
    foreign key (community_id) references community(id)
);
--rollback drop table if exists invitation_token;

create unique index ix_invitation_token__token on invitation_token (token);
--rollback drop index concurrently if exists ix_invitation_token__token;

create table directory (
    id bigserial not null primary key,
    created_at timestamptz not null,
    updated_at timestamptz not null,
    ---
    community_id bigint not null,
    name varchar not null,
    parent_directory_id bigint,
    pos int not null,
    ---
    foreign key (community_id) references community (id),
    foreign key (parent_directory_id) references directory (id)
);
--rollback drop table if exists directory;

create index ix_directory__community_id on directory (community_id);
--rollback drop index concurrently if exists ix_directory__community_id;

create table chat (
    id bigserial not null primary key,
    created_at timestamptz not null,
    updated_at timestamptz not null,
    ---
    community_id bigint not null,
    name varchar not null,
    parent_directory_id bigint not null,
    pos int not null,
    ---
    foreign key (community_id) references community(id),
    foreign key (parent_directory_id) references directory(id)
);
--rollback drop table if exists chat;

create index ix_chat__community_id on chat (community_id);
--rollback drop index concurrently if exists ix_chat__community_id;

create table voice (
    id bigserial not null primary key,
    created_at timestamptz not null,
    updated_at timestamptz not null,
    ---
    community_id bigint not null,
    name varchar not null,
    parent_directory_id bigint not null,
    pos int not null,
    ---
    foreign key (community_id) references community (id),
    foreign key (parent_directory_id) references directory(id)
);
--rollback drop table if exists voice;

create index ix_voice__community_id on voice (community_id);
--rollback drop index concurrently if exists ix_voice__community_id;

CREATE TABLE text_message (
    id bigserial not null primary key,
    created_at timestamptz not null,
    ---
    chat_id bigint not null,
    user_id bigint not null,
    message text not null,
    ---
    foreign key (chat_id) references chat (id)
);
--rollback drop table if exists text_message;

create index ix_text_message__chat_id on text_message (chat_id);
--rollback drop index concurrently if exists ix_text_message__chat_id;
