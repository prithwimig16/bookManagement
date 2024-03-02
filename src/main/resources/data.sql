drop table if exists role;
drop table if exists user_roles;
drop table if exists school;
drop table if exists deo;
drop table if exists user;



create table role (id bigint not null auto_increment, role_name varchar(255), primary key (id)) engine=MyISAM;
create table user_roles (user_id bigint not null, role_id bigint not null, primary key (user_id, role_id)) engine=MyISAM;

INSERT INTO role (id, role_name) VALUES (1, 'ADMIN');
INSERT INTO role (id, role_name) VALUES (2, 'SCHOOL');
INSERT INTO role (id, role_name) VALUES (3, 'DEO');
INSERT INTO role (id, role_name) VALUES (4, 'IS');

