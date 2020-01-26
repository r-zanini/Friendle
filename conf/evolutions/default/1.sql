# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table community (
  comm_name_unity               varchar(255) auto_increment not null,
  random                        bigint not null,
  constraint pk_community primary key (comm_name_unity)
);

create table event (
  event_id                      varchar(255) not null,
  event_name                    varchar(255),
  event_description             varchar(255),
  event_start                   timestamp,
  event_end                     timestamp,
  constraint pk_event primary key (event_id)
);

create table user (
  user_name                     varchar(255) not null,
  user_password                 varchar(255),
  first_name                    varchar(255),
  last_name                     varchar(255),
  user_email                    varchar(255),
  phone_number                  varchar(255),
  user_address                  varchar(255),
  user_type                     integer,
  constraint ck_user_user_type check ( user_type in (0,1,2)),
  constraint uq_user_user_email unique (user_email),
  constraint uq_user_phone_number unique (phone_number),
  constraint pk_user primary key (user_name)
);

create table user_community (
  relationship_id               varchar(255) not null,
  related_user_user_name        varchar(255),
  related_community_comm_name_unity varchar(255),
  user_type                     integer,
  constraint ck_user_community_user_type check ( user_type in (0,1,2)),
  constraint pk_user_community primary key (relationship_id)
);

create index ix_user_community_related_user_user_name on user_community (related_user_user_name);
alter table user_community add constraint fk_user_community_related_user_user_name foreign key (related_user_user_name) references user (user_name) on delete restrict on update restrict;

create index ix_user_community_related_community_comm_name_unity on user_community (related_community_comm_name_unity);
alter table user_community add constraint fk_user_community_related_community_comm_name_unity foreign key (related_community_comm_name_unity) references community (comm_name_unity) on delete restrict on update restrict;


# --- !Downs

alter table user_community drop constraint if exists fk_user_community_related_user_user_name;
drop index if exists ix_user_community_related_user_user_name;

alter table user_community drop constraint if exists fk_user_community_related_community_comm_name_unity;
drop index if exists ix_user_community_related_community_comm_name_unity;

drop table if exists community;

drop table if exists event;

drop table if exists user;

drop table if exists user_community;

