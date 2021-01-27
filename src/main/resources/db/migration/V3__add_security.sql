create table roles 
(
role_id  bigserial not null, 
role_name varchar(255) not null unique, 
primary key (role_id)
);

create table users 
(
user_id bigserial not null, 
user_username varchar(255) not null unique, 
user_password varchar(255) not null, 
user_person_id int8 not null, 
user_enabled boolean not null default true, 
primary key (user_id));

create table authorities 
(
authority_id bigserial not null, 
authority_name varchar(255) not null unique,
primary key (authority_id));

create table user_roles 
(
user_id int8 not null, 
user_role_id int8 not null
);

create table role_authorities 
(
role_id int8 not null, 
authority_id int8 not null
);

alter table users add constraint user_person_id_fk foreign key (user_person_id) references persons;

alter table user_roles add constraint user_id_fk foreign key (user_id) references users;
alter table user_roles add constraint user_role_id_fk foreign key (user_role_id) references roles;
alter table user_roles add constraint user_id_and_user_role_id_unique unique (user_id, user_role_id);

alter table role_authorities add constraint role_id_fk foreign key (role_id) references roles;
alter table role_authorities add constraint authority_id_fk foreign key (authority_id) references authorities;
alter table role_authorities add constraint role_and_authority_unique unique (role_id, authority_id);